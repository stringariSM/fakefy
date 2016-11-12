package web.ucs.dao;

import java.io.Serializable;

public class FiltroProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	private int primeiroRegistro = 0;
	private int quantidadeRegistros = 5;
	private String nome;
	private String codigo;

	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}

	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}

	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}

	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
			this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
			this.codigo = codigo;
	}

	public boolean filtraPorNome() {
		return this.getNome() != null && !this.getNome().isEmpty();
	}

	public boolean filtraPorCodigo() {
		return this.getCodigo() != null && !this.getCodigo().isEmpty();
	}

	@Override
	public String toString() {
		return "FiltroProduto [primeiroRegistro=" + primeiroRegistro
				+ ", quantidadeRegistros=" + quantidadeRegistros + ", nome="
				+ nome + ", codigo=" + codigo + "]";
	}

	public void resetCampos() {
		setPrimeiroRegistro(0);
		setCodigo(null);
		setNome(null);
	}

}
