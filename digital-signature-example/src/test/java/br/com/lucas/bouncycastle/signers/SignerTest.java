package br.com.lucas.bouncycastle.signers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import br.com.lucas.bouncycastle.signers.impl.CAdESSigner;
import br.com.lucas.keystore.loaders.impl.PKCS12KeyStoreLoader;
import br.com.lucas.views.components.models.TableModelRow;

class SignerTest {
	private static final Logger logger = LogManager.getLogger(SignerTest.class);
	private static final char[] passwd = "ABC123".toCharArray();

	@Test
	void testCreateCadesSignature() {
		try {
			String basePath = getClass().getClassLoader().getResource(".").getFile();
			String pkcs12 = basePath + "/pkcs12";
			File finalPKCS12File = new File(pkcs12, "simple-end-user-cert.p12");
			logger.info("Loading file {}", finalPKCS12File.getAbsolutePath());
			PKCS12KeyStoreLoader keyStoreLoader = new PKCS12KeyStoreLoader(finalPKCS12File);
			keyStoreLoader.setPasswd(passwd);
			keyStoreLoader.load();
			List<String> keyAliases = keyStoreLoader.listKeyAliases();
			String alias = keyAliases.get(0);

			X509Certificate cert = keyStoreLoader.getCertificate(alias);
			TableModelRow row = new TableModelRow();
			row.setAlias(alias);
			row.setOwner(cert.getSubjectX500Principal().getName());
			row.setKeyStoreSource(keyStoreLoader);

			CAdESSigner signer = new CAdESSigner(row);
			logger.info("Signing...");
			byte[] signedContent = signer.sign("abcdef".getBytes());
			assertNotNull(signedContent);

			String pkcs7 = basePath + "/testpkcs7";
			FileUtils.writeByteArrayToFile(new File(pkcs7, "p7sFile.p7b"), signedContent);
		} catch (Exception e) {
			logger.error("Method testSignUsingPKCS12Store failed!", e);
			fail("Method testSignUsingPKCS12Store failed!");
		}
	}

}
