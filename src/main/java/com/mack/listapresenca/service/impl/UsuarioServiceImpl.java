package com.mack.listapresenca.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mack.listapresenca.exception.ErroAutenticacao;
import com.mack.listapresenca.exception.RegraNegocioException;
import com.mack.listapresenca.model.entity.Usuario;
import com.mack.listapresenca.model.repository.UsuarioRepository;
import com.mack.listapresenca.service.UsuarioService;

import jakarta.transaction.Transactional;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	
	private UsuarioRepository repository;

	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario= repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado.");
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}
	

}
