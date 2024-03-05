package com.mack.listapresenca.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mack.listapresenca.model.entity.Chamada;

public interface ChamadaRepository extends JpaRepository<Chamada, Long>{
	
		@Query(value = " select Count(c.id) from Chamada c join c.aluno a"
				+ " where a.id =:idAluno and c.presente =:presente group by a ")
		BigDecimal obterPresencaPorAluno( @Param("idAluno")  Long idAluno, @Param("presente") Boolean presente);
		
}
