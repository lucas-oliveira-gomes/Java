package br.com.lucas.bouncycastle.certificate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lucas.bouncycastle.certificate.model.CertificateData;

class CertificateCreatorTest {
	private static final Logger logger = LoggerFactory.getLogger(CertificateCreatorTest.class);

	@ParameterizedTest
	@EnumSource(CertificateType.class)
	void testGetInstance(CertificateType instanceType) {
		CertificateCreator certCreator = CertificateCreator.getInstance(instanceType);
		switch (instanceType) {
		case Simple:
			assertTrue(certCreator instanceof SimpleCertificateCreator);
			break;
		case ICP:
			assertTrue(certCreator instanceof ICPCertificateCreator);
			break;
		default:
			fail("Unexpected CertificateType");
		}
	}

	@Test
	void testBuildSimpleSelfSignedCACertificate() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		boolean isCACertificate = true;
		CertificateCreator certCreator = CertificateCreator.getInstance(CertificateType.Simple);
		String commonName = "Tester";
		LocalDateTime now = LocalDateTime.now();
		Date notBefore = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		Date notAfter = Date.from(now.plusYears(1).atZone(ZoneId.systemDefault()).toInstant());
		try {
			CertificateData certificate = certCreator.build(commonName, notAfter, notBefore, isCACertificate, null);
			assertNotNull(certificate, "Expected non-null");
		} catch (Exception e) {
			logger.error("Failed to build certificate", e);
			fail("Failed to build certificate");
		}
	}

}
