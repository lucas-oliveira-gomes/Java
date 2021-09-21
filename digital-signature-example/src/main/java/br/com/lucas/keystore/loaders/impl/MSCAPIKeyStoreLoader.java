package br.com.lucas.keystore.loaders.impl;

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

public class MSCAPIKeyStoreLoader implements KeyStoreLoader {
	private KeyStore internalKeyStore;

	@Override
	public void load() throws Exception {
		internalKeyStore = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
		internalKeyStore.load(null, null);
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
		PrivateKey pvtKey = (PrivateKey) internalKeyStore.getKey(alias, null);
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

	@Override
	public String getSource() {
		return "Windows Keystore";
	}

	@Override
	public KeyStoreLoaderType getKeyStoreLoaderType() {
		return KeyStoreLoaderType.MSCAPI;
	}

}
