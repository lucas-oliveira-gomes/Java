package br.com.lucas.bouncycastle.signers.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;
import javax.swing.JComboBox;

import br.com.lucas.bouncycastle.signers.Signer;

public class PKCS12Signer implements Signer {
	private KeyStore keyStore;

	private KeyStore loadKeystore(String keyStoreFile, char[] passwd) throws Exception {
		KeyStore pkcs12 = KeyStore.getInstance("PKCS12", "SunJCE");
		try (FileInputStream fis = new FileInputStream(keyStoreFile)) {
			pkcs12.load(fis, passwd);
		}
		return pkcs12;
	}

	public PKCS12Signer(String keyStoreFile, String passwd) throws Exception {
		this.keyStore = loadKeystore(keyStoreFile, passwd.toCharArray());
	}

	@Override
	public File sign(File originalFile) throws Exception {
		Enumeration<String> aliases = keyStore.aliases();
		ArrayList<String> keyAliases = new ArrayList<>();
		JComboBox<String> certificateComboBox = new JComboBox<>();
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
			}
		}
		return null;
	}

}
