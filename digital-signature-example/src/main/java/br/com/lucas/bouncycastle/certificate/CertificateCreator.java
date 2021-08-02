package br.com.lucas.bouncycastle.certificate;

import java.security.KeyPairGenerator;
import java.util.Date;

import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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

	abstract CertificateData build(String commonName, Date notAfter, Date notBefore, CertificateData parent) throws Exception;

}

final class SimpleCertificateCreator extends CertificateCreator {

	@Override
	CertificateData build(String commonName, Date notAfter, Date notBefore, CertificateData parent) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
		X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(null, null, notBefore, notAfter, null, null);
		
		return null;
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
