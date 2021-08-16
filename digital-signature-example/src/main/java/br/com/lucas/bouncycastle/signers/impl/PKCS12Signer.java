package br.com.lucas.bouncycastle.signers.impl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.x500.X500Principal;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.DefaultSignedAttributeTableGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import br.com.lucas.bouncycastle.signers.Signer;

public class PKCS12Signer implements Signer {
	private KeyStore keyStore;
	private char[] passwd;

	private KeyStore loadKeystore(String keyStoreFile, char[] passwd) throws Exception {
		KeyStore pkcs12 = KeyStore.getInstance("PKCS12");
		try (FileInputStream fis = new FileInputStream(keyStoreFile)) {
			pkcs12.load(fis, passwd);
		}
		return pkcs12;
	}

	public PKCS12Signer(String keyStoreFile, char[] passwd) throws Exception {
		this.passwd = passwd;
		this.keyStore = loadKeystore(keyStoreFile, passwd);
	}

	public PKCS12Signer(String keyStoreFile, String passwd) throws Exception {
		this(keyStoreFile, passwd.toCharArray());
	}

	@Override
	public byte[] sign(byte[] contentToBeSigned) throws Exception {
		Enumeration<String> aliases = keyStore.aliases();
		ArrayList<String> keyAliases = new ArrayList<>();
		JComboBox<String> certificateComboBox = new JComboBox<>();
		Map<String, String> certificateAliasMap = new HashMap<String, String>();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (keyStore.isKeyEntry(alias))
				keyAliases.add(alias);
		}

		for (String keyAlias : keyAliases) {
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(keyAlias);
			if (cert != null) {
				String subjectName = cert.getSubjectX500Principal().getName(X500Principal.CANONICAL);
				certificateComboBox.addItem(subjectName);
				certificateAliasMap.put(subjectName, keyAlias);
			}
		}
		int selectedOption = JOptionPane.showOptionDialog(null, certificateComboBox, "Select a certificate",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (selectedOption == JOptionPane.OK_OPTION) {
			String selectedCert = certificateComboBox.getItemAt(certificateComboBox.getSelectedIndex());
			String alias = certificateAliasMap.get(selectedCert);
			PrivateKey pvtKey = (PrivateKey) keyStore.getKey(alias, passwd);
			Certificate[] chain = keyStore.getCertificateChain(alias);

			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
			SignerInfoGenerator signerInfoGenerator = createSignerInfoGenerator(pvtKey, chain);

			generator.addSignerInfoGenerator(signerInfoGenerator);

			Store<?> certStore = new JcaCertStore(Arrays.asList(chain));

			generator.addCertificates(certStore);
			CMSTypedData contentToSign = new CMSProcessableByteArray(contentToBeSigned);
			CMSSignedData signedData = generator.generate(contentToSign, true);
			return signedData.getEncoded();
		}
		return null;
	}

	private SignerInfoGenerator createSignerInfoGenerator(PrivateKey pvtKey, Certificate[] chain) throws Exception {
		ContentSigner contentSigner = new JcaContentSignerBuilder("Sha256WithRSA").build(pvtKey);
		DefaultSignedAttributeTableGenerator sugnedAttributeGenerator = new DefaultSignedAttributeTableGenerator(
				new AttributeTable(generateCades((X509Certificate) chain[0], (X509Certificate) chain[1])));
		JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuider = new JcaSignerInfoGeneratorBuilder(
				new JcaDigestCalculatorProviderBuilder().build());
		signerInfoGeneratorBuider.setSignedAttributeGenerator(sugnedAttributeGenerator);
		SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuider.build(contentSigner,
				new X509CertificateHolder(chain[0].getEncoded()));
		return signerInfoGenerator;
	}

	private ASN1EncodableVector generateCades(X509Certificate signer, X509Certificate issuer) throws Exception {

		ASN1EncodableVector signedAttribute = new ASN1EncodableVector();

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] signCerthash = md.digest(signer.getEncoded());
		IssuerSerial issuerSerial = new IssuerSerial(
				new X500Name(issuer.getSubjectX500Principal().getName(X500Principal.CANONICAL)),
				issuer.getSerialNumber());
		ESSCertIDv2 essCertIdV2 = new ESSCertIDv2(
				new AlgorithmIdentifier(new ASN1ObjectIdentifier("2.16.840.1.101.3.4.2.1")), signCerthash,
				issuerSerial);
		SigningCertificateV2 signinCertV2 = new SigningCertificateV2(new ESSCertIDv2[] { essCertIdV2 });
		signedAttribute.add(new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2, new DERSet(signinCertV2)));
		return signedAttribute;
	}
}
