package com.kelvearagao.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelvearagao.cobranca.model.StatusTitulo;
import com.kelvearagao.cobranca.model.Titulo;
import com.kelvearagao.cobranca.repository.Titulos;
import com.kelvearagao.cobranca.repository.filter.TituloFilter;
import com.kelvearagao.cobranca.service.CadastroTituloService;

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
	 * Camada de serviços.
	 * 
	 */
	@Autowired
	private CadastroTituloService cadastroTituloService;

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
	 * Salva um novo título no banco de dados e retorna a tela de cadastro de
	 * título.
	 * 
	 * @param titulo
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}

		try {
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");

			return "redirect:/titulos/novo";
		} catch (DataIntegrityViolationException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());

			return CADASTRO_VIEW;
		}
	}

	/**
	 * Retorna página de pesquisa de títulos.
	 * 
	 * @return
	 */
	@RequestMapping
	//public ModelAndView pesquisar(@RequestParam(defaultValue = "%") String descricao) {
	public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) {
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		
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
	 * Exclui um título e redireciona pra página de listagem com uma mesagem.
	 * 
	 * @param codigo
	 * @param attributes
	 * @return String
	 */
	@RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroTituloService.excluir(codigo);

		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");

		return "redirect:/titulos";
	}
	
	/**
	 * Atualiza o status do título para RECEBIDO e retorna a descrição do mesmo.
	 * 
	 * @param codigo
	 * @return String
	 */
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroTituloService.receber(codigo);
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
