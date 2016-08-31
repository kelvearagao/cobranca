package com.kelvearagao.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kelvearagao.cobranca.model.StatusTitulo;
import com.kelvearagao.cobranca.model.Titulo;
import com.kelvearagao.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	/**
	 * Repositório de titulos.
	 * 
	 */
	@Autowired
	private Titulos titulos;
	
	/**
	 * Retorna tela de cadastro de títulos.
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
				
		return mv;
	}
	
	/**
	 * Salva um novo título no banco de dados e retorna
	 * a tela de cadastro de título.
	 * 
	 * @param titulo
	 * @return ModelAndView
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		titulos.save(titulo);
		
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject("mensagem", "Título salvo com sucesso!");
		
		return mv;
	}
	
	@RequestMapping
	public String pesquisar() {
		return "PesquisaTitulos";
	}
	
	/**
	 * Passa pra view todos os status de título.
	 * 
	 * @return List<StatusTitulo>
	 */
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
}
