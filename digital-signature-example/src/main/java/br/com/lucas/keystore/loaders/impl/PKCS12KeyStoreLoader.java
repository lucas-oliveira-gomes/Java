package br.com.lucas.keystore.loaders.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.com.lucas.keystore.loaders.KeyStoreLoader;
import br.com.lucas.keystore.types.KeyStoreLoaderType;

public class PKCS12KeyStoreLoader implements KeyStoreLoader {

	private KeyStore internalKeyStore;
	private char[] keyStorePwd;
	private File keyStoreFile;

	public PKCS12KeyStoreLoader(File keyStore) {
		this.keyStoreFile = keyStore;
	}

	private KeyStore loadKeystore(File keyStoreFile, char[] passwd) throws Exception {
		KeyStore pkcs12 = KeyStore.getInstance("PKCS12");
		try (FileInputStream fis = new FileInputStream(keyStoreFile)) {
			pkcs12.load(fis, passwd);
		}
		return pkcs12;
	}

	@Override
	public void load() throws Exception {
		internalKeyStore = loadKeystore(keyStoreFile, keyStorePwd);

	}

	@Override
	public List<String> listKeyAliases() throws KeyStoreException {
		Enumeration<String> aliases = internalKeyStore.aliases();
		ArrayList<String> keyAliases = new ArrayList<>();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (internalKeyStore.isKeyEntry(alias))
				keyAliases.add(alias);
		}
		return keyAliases;
	}

	@Override
	public PrivateKey getPrivateKey(String alias)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		PrivateKey pvtKey = (PrivateKey) internalKeyStore.getKey(alias, keyStorePwd);
		return pvtKey;
	}

	@Override
	public X509Certificate getCertificate(String alias) throws KeyStoreException {
		X509Certificate cert = (X509Certificate) internalKeyStore.getCertificate(alias);
		return cert;
	}

	@Override
	public Certificate[] getCertificateChain(String alias) throws KeyStoreException {
		Certificate[] chain = internalKeyStore.getCertificateChain(alias);
		return chain;
	}

	public void setPasswd(char[] passwd) {
		this.keyStorePwd = passwd;
	}

	@Override
	public String getSource() {
		return keyStoreFile.getAbsolutePath();
	}

	@Override
	public KeyStoreLoaderType getKeyStoreLoaderType() {
		return KeyStoreLoaderType.PKCS12;
	}

}
