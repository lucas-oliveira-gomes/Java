package br.com.lucas.bouncycastle.signers;

import java.io.File;

public interface Signer {
	File sign(File originalFile);
}
