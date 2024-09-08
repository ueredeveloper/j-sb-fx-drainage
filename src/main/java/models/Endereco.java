package models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Endereco {

	private Long id;

	private String logradouro;

	private String cidade;

	private String bairro;

	private String cep;

	private Set<Documento> documentos = new HashSet<>();

	private Set<Interferencia> interferencias = new HashSet<>();

	private Estado estado;

	public Endereco() {
		super();
	}

	public Endereco(String logradouro) {
		super();
		this.logradouro = logradouro;
	}

	public Endereco(Long id, String logradouro) {
		super();
		this.id = id;
		this.logradouro = logradouro;
	}

	public Endereco(Long id, String logradouro, String cidade, String cep) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.cep = cep;
	}

	public Endereco(Long id, String logradouro, String cidade, String cep, String bairro, Estado estado) {
		super();
		this.id = id;
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.cep = cep;
		this.bairro = bairro;
		this.estado = estado;
	}

	public Endereco(String logradouro, String cidade, String cep, String bairro, Estado estado) {
		super();
		this.logradouro = logradouro;
		this.cidade = cidade;
		this.cep = cep;
		this.bairro = bairro;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	public Set<Interferencia> getInterferencias() {
		return interferencias;
	}

	public void setInterferencias(Set<Interferencia> interferencias) {
		this.interferencias = interferencias;
	}

	// Método toString para mostrar o logradouro no combobox.
	@Override
	public String toString() {
		return logradouro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(logradouro);
	}

	/*
	 * Quando você usa coleções que dependem de comparações, como ObservableList,
	 * HashSet ou HashMap, é crucial que os métodos hashCode() e equals() sejam
	 * consistentes e adequados. Se esses métodos não estiverem definidos ou
	 * estiverem definidos incorretamente, a coleção pode não funcionar como
	 * esperado, resultando em comportamento imprevisível, como perda de caracteres
	 * ou falha ao encontrar e atualizar elementos corretamente. Relacionado ao
	 * combobox editável do AddressComboBoxController e outros...
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
		return Objects.equals(logradouro, other.logradouro);
	}

	public <T> StringProperty getProperty(Function<Endereco, T> propertyAccessor) {
		StringProperty stringProperty = new SimpleStringProperty("");
		T value = propertyAccessor.apply(this);

		if (value != null) {
			String stringValue = String.valueOf(value);
			stringProperty.set(stringValue);
		}

		return stringProperty;
	}

	public String getStringEstado() {
		if (this.estado != null) {
			return this.getEstado().getDescricao();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

}
