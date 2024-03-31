package com.mack.listapresenca.api.resource;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mack.listapresenca.api.dto.ChamadaDTO;
import com.mack.listapresenca.api.dto.RealizaChamadaDTO;
import com.mack.listapresenca.exception.RegraNegocioException;
import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.entity.Chamada;
import com.mack.listapresenca.service.AlunoService;
import com.mack.listapresenca.service.ChamadaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chamadas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ChamadaResource {
	
	private final ChamadaService service;
	private final AlunoService alunoService;

	@GetMapping
	public ResponseEntity buscar(@RequestParam("data") LocalDate data, @RequestParam(value = "turma", required = false) String turma) {
		 	Chamada chamadaFiltro = new Chamada();
		 	chamadaFiltro.setData(data);
		 	chamadaFiltro.setTurma(turma);
		 	List<Chamada> chamadas = service.buscar(chamadaFiltro);
		 	return ResponseEntity.ok(chamadas);
			
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterChamada(@PathVariable("id") Long id) {
			return service.obterPorId(id)
					.map(chamada -> new ResponseEntity(converterDTO(chamada), HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}
	
	@PutMapping("{id}/realiza-chamada")
	public ResponseEntity realizarChamada(@PathVariable("id")Long id) {
		
		return service.obterPorId(id).map(chamada -> {
			try {
					service.atualizarStatus(chamada);
					return ResponseEntity.ok(chamada);					
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			}).orElseGet( () -> new ResponseEntity("Chamada não encontrada na base de Dados.", HttpStatus.BAD_REQUEST));   
		}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody ChamadaDTO dto) {
		
		try {
			Chamada entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id,@RequestBody ChamadaDTO dto) {
		
	return service.obterPorId(id).map(entity -> {
		try {
				Chamada chamada = converter(dto);
				chamada.setId(entity.getId());
				service.atualizar(chamada);
				return ResponseEntity.ok(chamada);					
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		}).orElseGet( () -> new ResponseEntity("Chamada não encontrada na base de Dados.", HttpStatus.BAD_REQUEST));   
	}
	
	
		
	
	private ChamadaDTO converterDTO(Chamada chamada) {
		return ChamadaDTO.builder().id(chamada.getId()).aluno(chamada.getAluno().getId()).data(chamada.getData()).presente(chamada.getPresente()).motivo(chamada.getMotivo()).build();
	}
	
	private Chamada converter(ChamadaDTO dto) {
		Chamada chamada = new Chamada();
		chamada.setId(dto.getId());
		chamada.setPresente(dto.getPresente());
		chamada.setData(dto.getData());
		chamada.setMotivo(dto.getMotivo());
		Aluno aluno = alunoService.obterPorId(dto.getAluno()).orElseThrow(() -> new RegraNegocioException("Aluno não encontrado para o Id informado."));
		chamada.setAluno(aluno);
		chamada.setTurma(aluno.getTurma());
		
		return chamada;
	}
}
