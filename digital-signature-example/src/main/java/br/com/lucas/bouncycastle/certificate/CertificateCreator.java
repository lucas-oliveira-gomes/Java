package br.com.lucas.bouncycastle.certificate;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import br.com.lucas.bouncycastle.certificate.model.CertificateData;

public abstract class CertificateCreator {

	public static final CertificateCreator getInstance(CertificateType instanceType) {
		switch (instanceType) {
		case Simple:
			return new SimpleCertificateCreator();
		case ICP:
			return new ICPCertificateCreator();
		default:
			throw new UnsupportedOperationException();
		}
	}

	abstract CertificateData build(String commonName, Date notAfter, Date notBefore, CertificateData parent)
			throws Exception;

}

final class SimpleCertificateCreator extends CertificateCreator {

	@Override
	CertificateData build(String commonName, Date notAfter, Date notBefore, CertificateData parentCertificate)
			throws Exception {		
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
		keyPairGenerator.initialize(4096, new SecureRandom());
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		CertificateData toReturn = new CertificateData();
		toReturn.setPrivateKey(keyPair.getPrivate());
		toReturn.setPublicKey(keyPair.getPublic());
		
		X500Name issuerName;
		PrivateKey signerKey;
		if (parentCertificate != null) {
			issuerName = new X500Name(
					parentCertificate.getCertificate().getSubjectX500Principal().getName(X500Principal.RFC1779));
			signerKey = parentCertificate.getPrivateKey();
		} else {
			issuerName = new X500Name("cn=" + commonName);
			signerKey = keyPair.getPrivate();
		}

		SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());

		X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(issuerName,
				new BigInteger(UUID.randomUUID().toString(), 16), notBefore, notAfter, new X500Name("cn=" + commonName),
				publicKeyInfo);
		ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA")
				.setProvider(BouncyCastleProvider.PROVIDER_NAME).build(signerKey);
		X509CertificateHolder certHolder = certificateBuilder.build(signer);
		X509Certificate cert = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
				.getCertificate(certHolder);
		
		toReturn.setCertificate(cert);
		
		return toReturn;
	}

}

final class ICPCertificateCreator extends CertificateCreator {

	@Override
	CertificateData build(String commonName, Date notAfter, Date notBefore, CertificateData parent) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

enum CertificateType {
	Simple, ICP
}
