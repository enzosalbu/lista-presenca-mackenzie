package com.mack.listapresenca.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mack.listapresenca.exception.RegraNegocioException;
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
	public Chamada salvar(Chamada chamada) {
		validar(chamada);
		return repository.save(chamada);
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
		if(chamada.getData() == null) {
			throw new RegraNegocioException("Informe uma data válida");
		}
	}
	
	@Override
	public Optional<Chamada> obterPorId(Long id) {
		return repository.findById(id);
		
	}
	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterPresensaPorAluno(Long id) {
		BigDecimal presencas = repository.obterPresencaPorAluno(id, true);
		BigDecimal faltas = repository.obterPresencaPorAluno(id, false);
		if (presencas == null) {
			presencas = BigDecimal.ZERO;
		}
		if(faltas == null) {
			faltas = BigDecimal.ZERO;
		}
		BigDecimal total = presencas.add(faltas);
		return presencas.divide(total);
	}

	
}
