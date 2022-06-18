
package br.com.bradseg.baixaalta.sql.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.bradseg.baixaalta.domain.SubInformacao;

public class SubInformacaoRowMapper implements RowMapper<SubInformacao> {

	private final String SUB_INFO_ID = "sub_info_id";
	private final String INFO_ID = "info_id";
	private final String SUB_DADOS = "sub_dados";
	private final String DATA_CRIACAO = "data_criacao";
	private final String DATA_ATUALIZACAO = "data_atualizaca";

	@Override
	public SubInformacao mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubInformacao subInfo = new SubInformacao();
		subInfo.setSubInfoId(rs.getInt(SUB_INFO_ID));
		subInfo.setInfoId(rs.getInt(INFO_ID));
		subInfo.setSubDados(rs.getString(SUB_DADOS));
		subInfo.setDataCriacao(rs.getTimestamp(DATA_CRIACAO));
		subInfo.setDataAtualizacao(rs.getTimestamp(DATA_ATUALIZACAO));
		return subInfo;
	}

}
