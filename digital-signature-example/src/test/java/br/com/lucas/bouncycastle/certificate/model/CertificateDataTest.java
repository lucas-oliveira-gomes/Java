package br.com.lucas.bouncycastle.certificate.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import br.com.lucas.test.utils.TestUtilities;

class CertificateDataTest {
	private static final Logger logger = LogManager.getLogger(CertificateDataTest.class);
	private static final char[] passwd = "ABC123".toCharArray();

	@Test
	void testStorePKCS12FromSelfSignedCertificate() {
		TestUtilities testUtilities = new TestUtilities();
		try {
			CertificateData certificate = testUtilities.createSimpleSelfSignedTestCertificate();
			assertNotNull(certificate, "Expected non-null");
			String newFile = getClass().getClassLoader().getResource(".").getFile();
			newFile = newFile + "/testpkcs12";
			File finalPKCS12File = certificate.toPkcs12(newFile, passwd);
			logger.info("Created file {}", finalPKCS12File.getAbsolutePath());
			assertTrue(finalPKCS12File.exists());
		} catch (Exception e) {
			logger.error("Failed to build certificate", e);
			fail("Failed to build certificate");
		}
	}

}
