package br.com.bradseg.baixaalta.dao;

import java.util.List;

import br.com.bradseg.baixaalta.domain.Informacao;

public interface InformacaoDAO {

	int insertInformacao(String dados);

	List<Informacao> getAllInformacao(final int maxRows);

	int deleteInformacao(int infoId);

	int updateInformacao(int infoId, String dados);

	Informacao getInformcao(int infoId);

}
