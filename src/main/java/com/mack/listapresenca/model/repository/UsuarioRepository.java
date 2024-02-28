package com.mack.listapresenca.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mack.listapresenca.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
