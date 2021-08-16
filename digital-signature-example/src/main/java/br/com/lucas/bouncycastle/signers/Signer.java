package br.com.lucas.bouncycastle.signers;

public interface Signer {

	byte[] sign(byte[] contentToBeSigned) throws Exception;
}
