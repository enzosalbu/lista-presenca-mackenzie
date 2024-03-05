package com.mack.listapresenca.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.mack.listapresenca.model.entity.Chamada;

public interface ChamadaService {
	
	Chamada salvar(Chamada chamada);
		
	Chamada realizar(Chamada chamada);
	
	List<Chamada> buscar(Chamada chamadaFiltro);
	
	void atualizarStatus(Chamada chamada, Boolean presente);
	
	void validar(Chamada chamada);
	
	Optional<Chamada> obterPorId(Long id);
	
	BigDecimal obterPresensaPorAluno(Long id);

}
