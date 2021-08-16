package br.com.lucas.bouncycastle.signers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lucas.bouncycastle.signers.impl.PKCS12Signer;

class SignerTest {
	private static final Logger logger = LoggerFactory.getLogger(SignerTest.class);
	private static final char[] passwd = "ABC123".toCharArray();

	@Test
	void testSignUsingPKCS12Store() {
		try {
			String basePath = getClass().getClassLoader().getResource(".").getFile();
			String pkcs12 = basePath + "/pkcs12";
			File finalPKCS12File = new File(pkcs12, "simple-end-user-cert.p12");
			logger.info("Loading file {}", finalPKCS12File.getAbsolutePath());

			Signer signer = new PKCS12Signer(finalPKCS12File.getAbsolutePath(), passwd);
			logger.info("Signing...");
			byte[] signedContent = signer.sign("abcdef".getBytes());
			assertNotNull(signedContent);
			String pkcs7 = basePath + "/testpkcs7";
			FileUtils.writeByteArrayToFile(new File(pkcs7, "p7sFile.p7s"), signedContent);
		} catch (Exception e) {
			logger.error("Method testSignUsingPKCS12Store failed!", e);
			fail("Method testSignUsingPKCS12Store failed!");
		}
	}

}
