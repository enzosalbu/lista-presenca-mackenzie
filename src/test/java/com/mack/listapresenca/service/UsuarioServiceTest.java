package com.mack.listapresenca.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mack.listapresenca.exception.ErroAutenticacao;
import com.mack.listapresenca.exception.RegraNegocioException;
import com.mack.listapresenca.model.entity.Usuario;
import com.mack.listapresenca.model.repository.UsuarioRepository;
import com.mack.listapresenca.service.impl.UsuarioServiceImpl;



@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	
	@Test
	public void deveSalvarUmUsuario() {
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1l).nome("nome").email("email@email.com").senha("senha").build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		Assertions.assertNotNull(usuarioSalvo);
		Assertions.assertEquals(1L, usuarioSalvo.getId());
		Assertions.assertEquals("nome", usuarioSalvo.getNome());
		Assertions.assertEquals("email@email.com", usuarioSalvo.getEmail());
		Assertions.assertEquals("senha", usuarioSalvo.getSenha());
	}
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		Usuario usuario = Usuario.builder().email("email@email.com").build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail("email@email.com");
		
		Assertions.assertThrows(RegraNegocioException.class, () -> service.salvarUsuario(usuario));
		
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		assertDoesNotThrow(() -> service.autenticar(email, senha));
	}
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado( ) {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		Throwable exception = assertThrows(ErroAutenticacao.class, () -> {
            service.autenticar("qualquer@email.com", "senha");
        });

        Assertions.assertEquals("Usuário não encontrado.", exception.getMessage());
	}
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		
		Usuario usuario = Usuario.builder().email("email@email.com").senha("senha").build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		 Throwable exception = assertThrows(ErroAutenticacao.class, () -> {
	            service.autenticar("qualquer@email.com", "senhaErrada");
	        });

	        Assertions.assertEquals("Senha inválida.", exception.getMessage());
		
	} 

	@Test
    public void deveValidarEmail() {
		
		 Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        
        assertDoesNotThrow(() -> service.validarEmail("email@email.com"));
    }
	
	@Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		 Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
        
        Assertions.assertThrows(RegraNegocioException.class, () -> service.validarEmail("email@email.com"));
    }
    
}
