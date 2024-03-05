package com.mack.listapresenca.service;

import java.util.Optional;

import com.mack.listapresenca.model.entity.Aluno;

public interface AlunoService {
		Optional<Aluno> obterPorId(Long id);
}
