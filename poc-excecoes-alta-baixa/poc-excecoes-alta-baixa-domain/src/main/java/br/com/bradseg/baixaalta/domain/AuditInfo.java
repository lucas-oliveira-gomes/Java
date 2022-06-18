package br.com.bradseg.baixaalta.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class AuditInfo implements Serializable {

	private static final long serialVersionUID = -6692366828895874643L;

	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtualizacao;

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataCriacao(Timestamp dataCriacao) {
		if (dataCriacao != null)
			this.dataCriacao = dataCriacao.toLocalDateTime();
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public void setDataAtualizacao(Timestamp dataAtualizacao) {
		if (dataAtualizacao != null)
			this.dataAtualizacao = dataAtualizacao.toLocalDateTime();
	}

}
