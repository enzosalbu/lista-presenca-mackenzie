package com.mack.listapresenca.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.repository.AlunoRepository;
import com.mack.listapresenca.service.AlunoService;



@Service
public class AlunoServiceImpl implements  AlunoService {

	private AlunoRepository repository;
	
	@Autowired
	public AlunoServiceImpl(AlunoRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public Optional<Aluno> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Aluno salvarAluno(Aluno aluno) {
		return repository.save(aluno);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscar(Aluno alunoFiltro) {
		Example example = Example.of(alunoFiltro);		 
		return repository.findAll(example, Sort.by("nome"));
		
	}

	
	
}
