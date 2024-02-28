package com.mack.listapresenca.service;

import com.mack.listapresenca.model.entity.Usuario;

public interface UsuarioService {
	
		Usuario autenticar(String email, String senha);
		
		Usuario salvarUsuario(Usuario usuario);
		
		void validarEmail(String email);
		
}
