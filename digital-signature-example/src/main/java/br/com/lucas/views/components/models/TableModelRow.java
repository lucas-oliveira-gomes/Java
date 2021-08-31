package br.com.lucas.views.components.models;

import br.com.lucas.keystore.loaders.KeyStoreLoader;

public class TableModelRow {
	private String alias;
	private String owner;
	private KeyStoreLoader source;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public KeyStoreLoader getKeyStoreSource() {
		return source;
	}

	public void setKeyStoreSource(KeyStoreLoader source) {
		this.source = source;
	}

	public String getSource() {
		return this.source.getSource();
	}

	public String getSourceType() {
		return this.source.getKeyStoreLoaderType().name();
	}

}
