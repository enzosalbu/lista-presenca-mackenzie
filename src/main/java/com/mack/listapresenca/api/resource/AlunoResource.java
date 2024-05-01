package com.mack.listapresenca.api.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mack.listapresenca.api.dto.AlunoDTO;
import com.mack.listapresenca.api.dto.UsuarioDTO;
import com.mack.listapresenca.exception.RegraNegocioException;
import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.entity.Chamada;
import com.mack.listapresenca.model.entity.Usuario;
import com.mack.listapresenca.service.AlunoService;
import com.mack.listapresenca.service.ChamadaService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
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
		@PostMapping
		public ResponseEntity salvar(@RequestBody AlunoDTO dto) {
			Aluno aluno = Aluno.builder()
					.nome(dto.getNome())
					.turma(dto.getTurma()).build();
			
			try {
				Aluno alunoSalvo = service.salvarAluno(aluno);
				return new ResponseEntity(alunoSalvo, HttpStatus.CREATED);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}
		
		@GetMapping
		public ResponseEntity buscar(@RequestParam(value = "turma", required = false) String turma) {			 	
			Aluno alunoFiltro = new Aluno();
			alunoFiltro.setTurma(turma);
			 try {	
				 List<Aluno> alunos = service.buscar(alunoFiltro);
				 System.out.println(alunoFiltro);
			 	 return ResponseEntity.ok(alunos);
			 }catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
		}
}
		
}
