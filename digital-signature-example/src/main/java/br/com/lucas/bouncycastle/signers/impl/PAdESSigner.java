package br.com.lucas.bouncycastle.signers.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;

import br.com.lucas.views.components.models.TableModelRow;

public class PAdESSigner extends CAdESSigner implements SignatureInterface {

	public PAdESSigner(TableModelRow selectedRow) {
		super(selectedRow);
	}

	@Override
	public byte[] sign(InputStream content) throws IOException {
		byte[] contentBytes = IOUtils.toByteArray(content);
		try {
			return sign(contentBytes);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}
