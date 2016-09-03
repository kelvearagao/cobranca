package com.kelvearagao.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kelvearagao.cobranca.model.StatusTitulo;
import com.kelvearagao.cobranca.model.Titulo;
import com.kelvearagao.cobranca.repository.Titulos;
import com.kelvearagao.cobranca.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;

	/**
	 * Salava um título ou retorna uma excessao caso of formatado data seja
	 * inválido
	 * 
	 * @param titulo
	 */
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	/**
	 * Exclui um título.
	 * 
	 * @param codigo
	 */
	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}

	/**
	 * Atualiza o campo status do título como RECEBIDO e retorna a descrição do
	 * mesmo.
	 * 
	 * @param codigo
	 * @return String
	 */
	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);

		titulo.setStatus(StatusTitulo.RECEBIDO);

		titulos.save(titulo);

		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	/**
	 * Realiza um filtro baseado na descrição do título.
	 * 
	 * @param filtro
	 * @return List<Titulo>
	 */
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return titulos.findByDescricaoContaining(filtro.getDescricao());
	}

}
