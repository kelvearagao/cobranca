package com.kelvearagao.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelvearagao.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo, Long> {
	
}
