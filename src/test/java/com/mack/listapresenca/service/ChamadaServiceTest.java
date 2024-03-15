package com.mack.listapresenca.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mack.listapresenca.exception.RegraNegocioException;
import com.mack.listapresenca.model.entity.Chamada;
import com.mack.listapresenca.model.repository.ChamadaRepository;
import com.mack.listapresenca.model.repository.ChamadaRepositoryTest;
import com.mack.listapresenca.service.impl.ChamadaServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ChamadaServiceTest {

	@SpyBean
	ChamadaServiceImpl service;
	
	@MockBean
	ChamadaRepository repository;
	
	
	@Test
	public void deveSalvarUmaChamada() {
		Chamada chamadaASalvar = ChamadaRepositoryTest.criarChamada();
		Mockito.doNothing().when(service).validar(chamadaASalvar);
		
		Chamada chamadaSalva = ChamadaRepositoryTest.criarChamada();
		chamadaSalva.setId(1l);
		chamadaSalva.setPresente(true);
		Mockito.when(repository.save(chamadaASalvar)).thenReturn(chamadaSalva);
		
		Chamada chamada = service.salvar(chamadaASalvar);
		
		Assertions.assertThat(chamada.getId() ).isEqualTo(chamadaSalva.getId());
		Assertions.assertThat(chamada.getPresente()).isEqualTo(true);
	}
	
	@Test
	public void naoDeveSalvarUmaChamadaQuandoHouverErroDeValidacao() {
		
		Chamada chamadaASalvar = ChamadaRepositoryTest.criarChamada();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(chamadaASalvar);
		
		Assertions.catchThrowableOfType(() -> service.salvar(chamadaASalvar), RegraNegocioException.class);
		
		Mockito.verify(repository, Mockito.never()).save(chamadaASalvar);
		
	}
	
	@Test
	public void deveAtualizarUmaChamada() {
		Chamada chamadaSalva = ChamadaRepositoryTest.criarChamada();
		chamadaSalva.setId(1l);
		chamadaSalva.setPresente(true);
		
	
		Mockito.doNothing().when(service).validar(chamadaSalva);
		
		
		Mockito.when(repository.save(chamadaSalva)).thenReturn(chamadaSalva);
		
		service.realizar(chamadaSalva);
		
		Mockito.verify(repository, Mockito.times(1)).save(chamadaSalva);
	}
	@Test
	public void deveLancarErroAoTentarAtualizarUmaChamadaQueAindaNaoFoiSalva() {
		
		Chamada chamadaASalvar = ChamadaRepositoryTest.criarChamada();
		
		
		Assertions.catchThrowableOfType(() -> service.realizar(chamadaASalvar), NullPointerException.class);
		
		Mockito.verify(repository, Mockito.never()).save(chamadaASalvar);
		
	}
	
	@Test 
	public void deveFiltrarChamadas() {
		Chamada chamada = ChamadaRepositoryTest.criarChamada();
		chamada.setId(1l);
		
		List<Chamada> lista = Arrays.asList(chamada);
		Mockito.when(repository.findAll(Mockito.any(Example.class)) ).thenReturn(lista);
		
		List<Chamada> resultado = service.buscar(chamada);
		
		Assertions
				.assertThat(resultado)
				.isNotEmpty()
				.hasSize(1)
				.contains(chamada);
		}
	
	@Test
	public void deveAtualizarStatusChamadas() {
		Chamada chamada = ChamadaRepositoryTest.criarChamada();
		chamada.setId(1l);
		Mockito.doReturn(chamada).when(service).realizar(chamada);
				
		
		service.atualizarStatus(chamada, false);
		
		Assertions.assertThat(chamada.getPresente()).isEqualTo(false);
		Mockito.verify(service).realizar(chamada);

	}
	@Test
	public void deveObterUmaChamadaPorId() {
		Long id = 1l;
		Chamada chamada = ChamadaRepositoryTest.criarChamada();
		chamada.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(chamada));
		
		Optional<Chamada> resultado = service.obterPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
		
		
	}
	@Test
	public void deveVazioQuandoUmaChamadaNaoExiste() {
		Long id = 1l;
		Chamada chamada = ChamadaRepositoryTest.criarChamada();
		chamada.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Chamada> resultado = service.obterPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isFalse();
				
	}
	@Test
	public void deveRetornarRegraDeNegocioExceptionDataIncorreta() {
		Chamada chamada = ChamadaRepositoryTest.criarChamada();
		chamada.setData(null);
		
		
		Throwable erro = Assertions.catchThrowable(() -> service.validar(chamada));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma data válida");
	
		
	}
}
