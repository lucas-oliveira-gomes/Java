package br.com.lucas.keystore.loaders.impl;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import br.com.lucas.keystore.loaders.KeyStoreLoader;
import br.com.lucas.keystore.types.KeyStoreLoaderType;

public class MSCAPIKeyStoreLoader implements KeyStoreLoader {

	@Override
	public void load() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> listKeyAliases() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrivateKey getPrivateKey(String alias) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public X509Certificate getCertificate(String alias) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Certificate[] getCertificateChain(String alias) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyStoreLoaderType getKeyStoreLoaderType() {
		return KeyStoreLoaderType.MSCAPI;
	}

}
