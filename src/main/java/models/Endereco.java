package models;

import java.util.Objects;

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
	

	public Endereco(String endLogradouro, String endCidade, String endCep, String endBairro, String endEstado) {
		super();
		this.endLogradouro = endLogradouro;
		this.endCidade = endCidade;
		this.endCep = endCep;
		this.endBairro = endBairro;
		this.endEstado = endEstado;
	}

	public Endereco(Long endId, String endLogradouro, String endCidade, String endCep) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
		this.endCidade = endCidade;
		this.endCep = endCep;
	}
	
	public Endereco(Long endId, String endLogradouro, String endCidade, String endCep, String endBairro, String endEstado) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
		this.endCidade = endCidade;
		this.endCep = endCep;
		this.endBairro = endBairro;
		this.endEstado = endEstado;
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
	
	// Metodos

	// Método toString para mostrar o logradouro no combobox.
	@Override
	public String toString() {
		return endLogradouro;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(endLogradouro);
	}

	/* 
	 * Quando você usa coleções que dependem de comparações, como ObservableList, HashSet ou HashMap, é crucial que 
	 * os métodos hashCode() e equals() sejam consistentes e adequados.
	 * Se esses métodos não estiverem definidos ou estiverem definidos incorretamente, 
	 * a coleção pode não funcionar como esperado, resultando em comportamento imprevisível, 
	 * como perda de caracteres ou falha ao encontrar e atualizar elementos corretamente.
	 * Relacionado ao combobox editável do AddressComboBoxController e outros...
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(endLogradouro, other.endLogradouro);
	}
	
	
	
}
