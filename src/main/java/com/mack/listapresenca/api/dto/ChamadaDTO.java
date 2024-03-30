package com.mack.listapresenca.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ChamadaDTO {
	
	private Long id;
	private Long aluno;
	private Boolean presente;
	private LocalDate data;
	private String motivo;
}
