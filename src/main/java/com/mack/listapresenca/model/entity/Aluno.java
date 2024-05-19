package com.mack.listapresenca.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aluno", schema = "presencas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "turma")
	private String turma;
	
	@Column(name = "email")
	private String email;
	
	@Column(name= "data_cadastro")
	private LocalDate data_cadastro;

	
}
