package com.mack.listapresenca.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
