package com.mack.listapresenca.service;

import java.util.List;

import com.mack.listapresenca.model.entity.Chamada;

public interface ChamadaService {
	
	
	Chamada realizar(Chamada chamada);
	
	List<Chamada> buscar(Chamada chamadaFiltro);
	
	void atualizarStatus(Chamada chamada, Boolean presente);
	
	void validar(Chamada chamada);
}
