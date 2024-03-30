package com.mack.listapresenca.api.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.service.AlunoService;
import com.mack.listapresenca.service.ChamadaService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoResource {

		private final AlunoService service;
		private final ChamadaService chamadaService;
		
		@GetMapping("{id}")
		public ResponseEntity obterFrequencia(@PathVariable("id") Long id) {
			
			Optional<Aluno> aluno = service.obterPorId(id);
			
			if(!aluno.isPresent()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			String taxa = chamadaService.obterPresensaPorAluno(id);
			return ResponseEntity.ok(taxa);
		}		
}
