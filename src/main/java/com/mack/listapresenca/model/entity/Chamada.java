package com.mack.listapresenca.model.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "chamada", schema = "presencas")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chamada {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;
	
	@Column(name = "presente")
	private Boolean presente;
	
	@Column(name = "data")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate data;
	
}
