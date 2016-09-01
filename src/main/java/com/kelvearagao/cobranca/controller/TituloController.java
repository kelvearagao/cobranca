package com.kelvearagao.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelvearagao.cobranca.model.StatusTitulo;
import com.kelvearagao.cobranca.model.Titulo;
import com.kelvearagao.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	/**
	 * Constante que representa o nome da tela de catastro de título.
	 * 
	 */
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
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
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		
		mv.addObject(new Titulo());
				
		return mv;
	}
	
	/**
	 * Salva um novo título no banco de dados e retorna
	 * a tela de cadastro de título.
	 * 
	 * @param titulo
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		titulos.save(titulo);
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
		
		return "redirect:/titulos/novo";
	}
	/**
	 * Retorna página de pesquisa de títulos.
	 * 
	 * @return
	 */
	@RequestMapping
	public ModelAndView pesquisar() {
		List<Titulo> todosTitulos = titulos.findAll();
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		
		mv.addObject("titulos", todosTitulos);
		
		return mv;
	}
	
	/**
	 * Recebe um titulo e retorna a página para editá-lo.
	 * 
	 * @param titulo
	 * @return ModelAndView
	 */
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		
		mv.addObject(titulo);
		
		return mv;
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
