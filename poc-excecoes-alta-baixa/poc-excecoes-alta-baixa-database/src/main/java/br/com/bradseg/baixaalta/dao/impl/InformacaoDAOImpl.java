package br.com.bradseg.baixaalta.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import br.com.bradseg.baixaalta.dao.InformacaoDAO;
import br.com.bradseg.baixaalta.domain.Informacao;
import br.com.bradseg.baixaalta.sql.mappers.InformacaoRowMapper;

@Repository
public class InformacaoDAOImpl implements InformacaoDAO {
	private static final String INSERT_INFORMACAO = "insert into informacao (dados, data_criacao) values(?, ?)";
	private static final String SELECT_INFORMACAO_BY_ID = "select info_id, dados, data_criacao, data_atualizacao from informacao where info_id = ? fetch first 1 rows only";
	private static final String UPDATE_INFORMACAO = "update informacao set dados = ?, data_atualizacao = ? where info_id = ?";
	private static final String DELETE_INFORMACAO = "delete from informacao where info_id = ?";
	private static final String SELECT_ALL_INFORMACAO = "select info_id, dados, data_criacao, data_atualizacao from informacao fetch first ? rows only";
	private static final int DEFAULT_MAX_ROWS = 1000;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertInformacao(final String dados) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(INSERT_INFORMACAO, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, dados);
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			return ps;
		}, keyHolder);

		return (int) keyHolder.getKeys().get("info_id");
	}

	@Override
	public Informacao getInformcao(final int infoId) {
		return jdbcTemplate.query(SELECT_INFORMACAO_BY_ID, ps -> ps.setInt(1, infoId), new InformacaoRowMapper())
				.stream().findFirst().orElse(null);
	}

	@Override
	public int updateInformacao(final int infoId, final String dados) {
		return jdbcTemplate.update(UPDATE_INFORMACAO, ps -> {
			ps.setString(1, dados);
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(3, infoId);
		});
	}

	@Override
	public int deleteInformacao(final int infoId) {
		return jdbcTemplate.update(DELETE_INFORMACAO, ps -> ps.setInt(1, infoId));
	}

	@Override
	public List<Informacao> getAllInformacao(final int maxRows) {
		return jdbcTemplate.query(SELECT_ALL_INFORMACAO, ps -> ps.setInt(1, maxRows < 1 ? DEFAULT_MAX_ROWS : maxRows),
				new InformacaoRowMapper());
	}
}
