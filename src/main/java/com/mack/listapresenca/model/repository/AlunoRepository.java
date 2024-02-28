package com.mack.listapresenca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mack.listapresenca.model.entity.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{
	

}
