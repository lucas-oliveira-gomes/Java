/**
 * 
 */
package br.com.lucas.keystore.loaders;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import br.com.lucas.keystore.types.KeyStoreLoaderType;

/**
 * @author lucas.gomes
 *
 */
public interface KeyStoreLoader {
	void load() throws Exception;

	List<String> listKeyAliases() throws Exception;

	PrivateKey getPrivateKey(String alias) throws Exception;

	X509Certificate getCertificate(String alias) throws Exception;

	Certificate[] getCertificateChain(String alias) throws Exception;
	
	String getSource();
	
	KeyStoreLoaderType getKeyStoreLoaderType();
}
