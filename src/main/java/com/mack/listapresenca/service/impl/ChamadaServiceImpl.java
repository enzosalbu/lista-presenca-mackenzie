package com.mack.listapresenca.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mack.listapresenca.model.entity.Chamada;
import com.mack.listapresenca.model.repository.ChamadaRepository;
import com.mack.listapresenca.service.ChamadaService;



@Service
public class ChamadaServiceImpl implements ChamadaService{

	private ChamadaRepository repository;
	
	public ChamadaServiceImpl(ChamadaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Chamada realizar(Chamada chamada) {
		Objects.requireNonNull(chamada.getId());
		return repository.save(chamada);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chamada> buscar(Chamada chamadaFiltro) {
		 Example example = Example.of(chamadaFiltro);
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Chamada chamada, Boolean presente) {
		chamada.setPresente(presente);
		realizar(chamada);
	
	}

	@Override
	public void validar(Chamada chamada) {
		
	}

}
