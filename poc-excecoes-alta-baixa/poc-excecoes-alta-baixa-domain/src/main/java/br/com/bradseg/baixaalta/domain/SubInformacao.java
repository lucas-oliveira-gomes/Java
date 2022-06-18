package br.com.bradseg.baixaalta.domain;

import java.util.Objects;

public class SubInformacao extends AuditInfo {

	private static final long serialVersionUID = -8689291252041058135L;

	private int subInfoId;
	private int infoId;
	private String subDados;

	public int getSubInfoId() {
		return subInfoId;
	}

	public void setSubInfoId(int subInfoId) {
		this.subInfoId = subInfoId;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getSubDados() {
		return subDados;
	}

	public void setSubDados(String subDados) {
		this.subDados = subDados;
	}

	@Override
	public int hashCode() {
		return Objects.hash(infoId, subInfoId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubInformacao other = (SubInformacao) obj;
		return infoId == other.infoId && subInfoId == other.subInfoId;
	}
}
