package com.mack.listapresenca.model.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mack.listapresenca.model.entity.Aluno;
import com.mack.listapresenca.model.entity.Chamada;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ChamadaRepositoryTest {

	@Autowired
	ChamadaRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	
	@Test
	public void deveSalvarUmaChamada() {
		Chamada chamada = criarChamada();
		chamada = repository.save(chamada);
		Assertions.assertThat(chamada.getId()).isNotNull();
	}
	@Test
	public void deveAtualizarUmaChamada() {
		Chamada chamada = criarEPeristirUmaChamada(); 
		chamada.setPresente(true);
		
		repository.save(chamada);
		Chamada chamadaAtualizada = entityManager.find(Chamada.class, chamada.getId());
		
		Assertions.assertThat(chamadaAtualizada.getPresente()).isEqualTo(true);
	}
	
	@Test
	public void deveBuscarUmaChaamdaPorId() {
		
		Chamada chamada = criarEPeristirUmaChamada();
		Optional<Chamada> chamadaEncontrada = repository.findById(chamada.getId());
		Assertions.assertThat(chamadaEncontrada.isPresent()).isTrue();
		
	}
	
	
	
	private Chamada criarEPeristirUmaChamada() {
		Chamada chamada = criarChamada();
		entityManager.persist(chamada);
		return chamada;
		
	}
	public static Chamada criarChamada() {
		return Chamada.builder().data(LocalDate.now()).presente(true).aluno(criarAluno()).build();
	}
	public static Aluno criarAluno() {
		return Aluno.builder().nome("Teste").build();
		
	}
}
