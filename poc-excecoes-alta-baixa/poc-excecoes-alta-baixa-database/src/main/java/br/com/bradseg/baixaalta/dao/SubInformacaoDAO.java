package br.com.bradseg.baixaalta.dao;

import java.util.List;

import br.com.bradseg.baixaalta.domain.SubInformacao;

public interface SubInformacaoDAO {

	int insertSubInfo(int infoId, String subDados);

	SubInformacao getSubInfo(int subInfoId);

	int updateSubInfo(int subInfoId, int infoId, String subDados);

	int deleteSubInfo(int subInfoId);

	List<SubInformacao> getAllSubInfo(int maxRows);

}
