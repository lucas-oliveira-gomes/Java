package br.com.lucas.bouncycastle.certificate.model;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CertificateData {
	private static final String KEY_ENTRY = "_KEY_ENTRY", CERT_ENTRY = "_CERT_ENTRY";

	private final String guid = UUID.randomUUID().toString();
	private CertificateData parent;
	private X509Certificate certificate;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public CertificateData getParent() {
		return parent;
	}

	public void setParent(CertificateData parent) {
		this.parent = parent;
	}

	public File toPkcs12(String destinationFolder, char[] passwd) throws Exception {
		File storeFileDir = new File(destinationFolder);
		if (!storeFileDir.exists()) {
			storeFileDir.mkdirs();
		}
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(null);
		keyStore.setCertificateEntry(guid + CERT_ENTRY, getCertificate());
		keyStore.setKeyEntry(guid + KEY_ENTRY, privateKey, passwd, getChain().toArray(new Certificate[] {}));
		File finalPKCS12File = new File(storeFileDir, guid + ".p12");
		try (FileOutputStream fos = new FileOutputStream(finalPKCS12File)) {
			keyStore.store(fos, passwd);
		}
		return finalPKCS12File;
	}

	protected List<Certificate> getChain() {
		ArrayList<Certificate> toReturn = new ArrayList<Certificate>();
		toReturn.add(getCertificate());
		if (getParent() != null) {
			toReturn.addAll(getParent().getChain());
		}
		return toReturn;
	}

	public CertificateData fromStore(KeyStore keyStore, char[] passwd) throws Exception {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
