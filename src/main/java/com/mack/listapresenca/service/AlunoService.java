package com.mack.listapresenca.service;

import java.util.Optional;

import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.entity.Usuario;

public interface AlunoService {
		Optional<Aluno> obterPorId(Long id);
		Aluno salvarAluno(Aluno aluno);
}
