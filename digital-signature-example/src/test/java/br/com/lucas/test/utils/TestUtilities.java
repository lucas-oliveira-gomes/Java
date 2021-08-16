package br.com.lucas.test.utils;

import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.com.lucas.bouncycastle.certificate.CertificateCreator;
import br.com.lucas.bouncycastle.certificate.CertificateType;
import br.com.lucas.bouncycastle.certificate.model.CertificateData;

public class TestUtilities {

	private Date notBefore;
	private Date notAfter;

	private void initializeDates() {
		LocalDateTime now = LocalDateTime.now();
		notBefore = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		notAfter = Date.from(now.plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
	}

	public CertificateData createSimpleSelfSignedTestCertificate() throws Exception {
		CertificateData toReturn;
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		CertificateCreator certCreator = CertificateCreator.getInstance(CertificateType.Simple);
		String commonName = "Root";
		initializeDates();
		toReturn = certCreator.build(commonName, notAfter, notBefore, true, null);
		return toReturn;
	}

	public CertificateData createSimpleIntermediateCertificate() throws Exception {
		CertificateData parentCertificate = createSimpleSelfSignedTestCertificate();
		CertificateCreator certCreator = CertificateCreator.getInstance(CertificateType.Simple);
		String commonName = "Intermediate";
		initializeDates();

		return certCreator.build(commonName, notAfter, notBefore, true, parentCertificate);
	}

	public CertificateData createSimpleEndUserCertificate() throws Exception {
		CertificateData parentCertificate = createSimpleIntermediateCertificate();
		CertificateCreator certCreator = CertificateCreator.getInstance(CertificateType.Simple);
		String commonName = "EndUser";
		initializeDates();

		return certCreator.build(commonName, notAfter, notBefore, false, parentCertificate);
	}
}
