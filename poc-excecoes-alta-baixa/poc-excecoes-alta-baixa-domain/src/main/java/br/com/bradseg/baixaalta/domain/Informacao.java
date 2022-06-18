package br.com.bradseg.baixaalta.domain;

import java.util.Objects;

public class Informacao extends AuditInfo {

	private static final long serialVersionUID = 1622098168907229824L;

	private int infoId;
	private String dados;

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(infoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informacao other = (Informacao) obj;
		return infoId == other.infoId;
	}

	public Informacao() {
		super();
	}

	public Informacao(int infoId, String dados) {
		this();
		this.infoId = infoId;
		this.dados = dados;
	}
}
