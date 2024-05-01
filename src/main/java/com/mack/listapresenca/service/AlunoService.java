package com.mack.listapresenca.service;

import java.util.Optional;
import java.util.List;
import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.entity.Usuario;

public interface AlunoService {
		Optional<Aluno> obterPorId(Long id);
		Aluno salvarAluno(Aluno aluno);
		List<Aluno> buscar(Aluno alunoFiltro);
}
