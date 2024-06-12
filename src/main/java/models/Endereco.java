package models;

public class Endereco {

	private Long endId;
	private String endLogradouro;
	private String endCidade;
	private String endCep;
	private String endBairro;
	private String endEstado;

	public Endereco() {
		super();
	}

	public Endereco(String endLogradouro) {
		super();
		this.endLogradouro = endLogradouro;
	}

	public Endereco(Long endId, String endLogradouro) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
	}

	public Endereco(Long endId, String endLogradouro, String endCidade, String endCep) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
		this.endCidade = endCidade;
		this.endCep = endCep;
	}
	
	public Endereco(Long endId) {
		super();
		this.endId = endId;
	}

	// Método toString para mostrar o logradouro no combobox.
	@Override
	public String toString() {
		return endLogradouro;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public String getEndLogradouro() {
		return endLogradouro;
	}

	public void setEndLogradouro(String endLogradouro) {
		this.endLogradouro = endLogradouro;
	}

	public String getEndCidade() {
		return endCidade;
	}

	public void setEndCidade(String endCidade) {
		this.endCidade = endCidade;
	}

	public String getEndCep() {
		return endCep;
	}

	public void setEndCep(String endCep) {
		this.endCep = endCep;
	}

	public String getEndBairro() {
		return endBairro;
	}

	public void setEndBairro(String endBairro) {
		this.endBairro = endBairro;
	}

	public String getEndEstado() {
		return endEstado;
	}

	public void setEndEstado(String endEstado) {
		this.endEstado = endEstado;
	}
}
