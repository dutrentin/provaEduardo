package br.com.poc.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe responsável por indicar os limites
 * de seleção de itens de um lista
 * 
 * @author jose
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Paginacao implements Serializable {

	private static final long serialVersionUID = 6815181156995819701L;

	private Integer posicao;
	private Integer limite;

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Integer getLimite() {
		return limite;
	}

	public void setLimite(Integer limite) {
		this.limite = limite;
	}
}
