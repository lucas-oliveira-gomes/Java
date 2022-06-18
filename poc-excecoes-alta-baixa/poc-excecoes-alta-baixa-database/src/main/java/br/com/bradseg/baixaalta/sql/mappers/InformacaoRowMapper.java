package br.com.bradseg.baixaalta.sql.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.bradseg.baixaalta.domain.Informacao;

public class InformacaoRowMapper implements RowMapper<Informacao> {

	private static final String DADOS = "DADOS";
	private static final String INFO_ID = "INFO_ID";
	private static final String DATA_CRIACAO = "data_criacao";
	private static final String DATA_ATUALIZACAO = "data_atualizacao";

	@Override
	public Informacao mapRow(ResultSet rs, int rowNum) throws SQLException {
		Informacao info = new Informacao();
		info.setInfoId(rs.getInt(INFO_ID));
		info.setDados(rs.getString(DADOS));
		info.setDataCriacao(rs.getTimestamp(DATA_CRIACAO));
		info.setDataAtualizacao(rs.getTimestamp(DATA_ATUALIZACAO));
		return info;
	}
}
