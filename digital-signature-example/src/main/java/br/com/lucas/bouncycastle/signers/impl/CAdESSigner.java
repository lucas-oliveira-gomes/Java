package br.com.lucas.bouncycastle.signers.impl;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
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

import br.com.lucas.bouncycastle.signers.Signer;
import br.com.lucas.views.components.models.TableModelRow;

public class CAdESSigner implements Signer {

	private TableModelRow selectedRow;

	public CAdESSigner(TableModelRow selectedRow) {
		super();
		this.selectedRow = selectedRow;
	}

	@Override
	public byte[] sign(byte[] contentToBeSigned) throws Exception {
		X509Certificate signerCert = selectedRow.getKeyStoreSource().getCertificate(selectedRow.getAlias());

		CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

		ASN1EncodableVector signedAttributes = new ASN1EncodableVector();
		signedAttributes.add(getSigningCertificateV2(signerCert));

		AttributeTable signedAttributesTable = new AttributeTable(signedAttributes);

		DefaultSignedAttributeTableGenerator signedAttributeTableGenerator = new DefaultSignedAttributeTableGenerator(
				signedAttributesTable);

		JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuider = new JcaSignerInfoGeneratorBuilder(
				new JcaDigestCalculatorProviderBuilder().build());

		signerInfoGeneratorBuider.setSignedAttributeGenerator(signedAttributeTableGenerator);

		PrivateKey pvtKey = selectedRow.getKeyStoreSource().getPrivateKey(selectedRow.getAlias());
		ContentSigner contentSigner = new JcaContentSignerBuilder("Sha256WithRSA").build(pvtKey);

		SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuider.build(contentSigner, signerCert);

		generator.addSignerInfoGenerator(signerInfoGenerator);
		generator.addCertificate(new X509CertificateHolder(signerCert.getEncoded()));

		CMSTypedData contentToSign = new CMSProcessableByteArray(contentToBeSigned);

		CMSSignedData signedData = generator.generate(contentToSign, false);
		return signedData.getEncoded();
	}

	private Attribute getSigningCertificateV2(X509Certificate signerCert) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] signerCertHash = md.digest(signerCert.getEncoded());

		ESSCertIDv2 essCertIdv2 = new ESSCertIDv2(new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256),
				signerCertHash);

		SigningCertificateV2 signingCertificateV2 = new SigningCertificateV2(new ESSCertIDv2[] { essCertIdv2 });

		return new Attribute(PKCSObjectIdentifiers.id_aa_signingCertificateV2, new DERSet(signingCertificateV2));
	}

}
