package models;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe que representa um Documento.
 */
public class Documento {

	private Long id;
	private String numero;
	private Processo processo;
	private Long numeroSei;
	private DocumentoTipo tipoDocumento;
	private Endereco endereco;

	private Set<Usuario> usuarios = new HashSet<>();

	// Construtor padrão
	public Documento() {
		super();
	}

	public Documento(String numero, Processo processo, Long numeroSei, DocumentoTipo tipoDocumento, Endereco endereco) {
		super();
		this.numero = numero;
		this.processo = processo;
		this.numeroSei = numeroSei;
		this.tipoDocumento = tipoDocumento;
		this.endereco = endereco;
	}

	public Documento(String numero, Processo processo, DocumentoTipo tipoDocumento) {
		super();
		this.numero = numero;
		this.processo = processo;
		this.tipoDocumento = tipoDocumento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public Documento(String numero, Processo processo) {
		super();
		this.numero = numero;
		this.processo = processo;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Long getNumeroSei() {
		return numeroSei;
	}

	public void setNumeroSei(Long numeroSei) {
		this.numeroSei = numeroSei;
	}

	
	public DocumentoTipo getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(DocumentoTipo tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public StringProperty getEnderecoLogradouroProperty() {
		StringProperty enderecoLogradouroProperty = new SimpleStringProperty("");
		Endereco endereco = this.getEndereco();

		if (endereco != null) {
			String enderecoValue = endereco.getLogradouro();
			if (enderecoValue != null) {
				enderecoLogradouroProperty.set(enderecoValue);
			}
		}
		return enderecoLogradouroProperty;
	}

	public <T> StringProperty getProperty(Function<Documento, T> propertyAccessor) {
		StringProperty stringProperty = new SimpleStringProperty("");
		T value = propertyAccessor.apply(this);

		if (value != null) {
			String stringValue = String.valueOf(value);
			stringProperty.set(stringValue);
		}

		return stringProperty;
	}

	public String getProcessoNumero() {
		if (this.processo != null) {
			return this.processo.getNumero();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public String getEnderecoLogradouro() {
		if (this.endereco != null) {
			return this.endereco.getLogradouro();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public String getTipoDescricao() {
		if (this.tipoDocumento != null) {
			return this.tipoDocumento.getDescricao();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

}
