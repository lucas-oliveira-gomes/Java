package br.com.bradseg.baixaalta.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import br.com.bradseg.baixaalta.dao.SubInformacaoDAO;
import br.com.bradseg.baixaalta.domain.SubInformacao;
import br.com.bradseg.baixaalta.sql.mappers.SubInformacaoRowMapper;

@Repository
public class SubInformacaoDAOImpl implements SubInformacaoDAO {
	private static final String INSERT_SUB_INFORMACAO = "insert into sub_informacao (info_id, sub_dados, data_criacao) values (?, ?, ?)";
	private static final String SELECT_SUB_INFORMACAO_BY_ID = "select sub_info_id, info_id, sub_dados, data_criacao, data_atualizacao from sub_informacao where sub_info_id = ? fetch first 1 rows only";
	private static final String UPDATE_SUB_INFORMACAO = "update sub_informacao set sub_dados = ?, data_atualizacao = ? where sub_info_id = ? and info_id = ?";
	private static final String DELETE_SUB_INFORMACAO = "delete from sub_informacao where sub_info_id = ?";
	private static final String SELECT_ALL_SUB_INFORMACAO = "select sub_info_id, info_id, sub_dados, data_criacao, data_atualizacao from sub_informacao where sub_info_id = ? fetch first ? rows only";
	private static final int DEFAULT_MAX_ROWS = 1000;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertSubInfo(final int infoId, final String subDados) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_SUB_INFORMACAO,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, infoId);
			ps.setString(2, subDados);
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			return ps;
		}, keyHolder);
		return (int) keyHolder.getKeys().get("sub_info_id");
	}

	@Override
	public SubInformacao getSubInfo(final int subInfoId) {
		return jdbcTemplate
				.query(SELECT_SUB_INFORMACAO_BY_ID, ps -> ps.setInt(1, subInfoId), new SubInformacaoRowMapper())
				.stream().findFirst().orElse(null);
	}

	@Override
	public int updateSubInfo(final int subInfoId, final int infoId, final String subDados) {
		return jdbcTemplate.update(UPDATE_SUB_INFORMACAO, ps -> {
			ps.setString(1, subDados);
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(3, subInfoId);
			ps.setInt(4, infoId);
		});
	}

	@Override
	public int deleteSubInfo(final int subInfoId) {
		return jdbcTemplate.update(DELETE_SUB_INFORMACAO, ps -> ps.setInt(1, subInfoId));
	}

	@Override
	public List<SubInformacao> getAllSubInfo(final int maxRows) {
		return jdbcTemplate.query(SELECT_ALL_SUB_INFORMACAO,
				ps -> ps.setInt(1, maxRows < 1 ? DEFAULT_MAX_ROWS : maxRows), new SubInformacaoRowMapper());
	}
}
