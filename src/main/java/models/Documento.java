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
	

	private Long docId;
	private String docNumero;
	private Processo docProcesso;
	private Long docSei;
	private DocumentoTipo docTipo;
	private Endereco docEndereco;

	private Set<Usuario> usuarios = new HashSet<>();

	// Construtor padrão
	public Documento() {
		super();
	}

	public Documento(String docNumero, Processo docProcesso, Long docSei, DocumentoTipo docTipo, Endereco docEndereco) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSei = docSei;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
	}
	
	public Documento(String docNumero, Processo docProcesso, DocumentoTipo docTipo) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docTipo = docTipo;
	}

	public Documento(String docNumero, Processo docProcesso) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
	}
	

	public Documento(String docNumero, Processo docProcesso, Long docSei, DocumentoTipo docTipo,
			Endereco docEndereco, Set<Usuario> usuarios) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSei = docSei;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
		this.usuarios = usuarios;
	}

	public StringProperty getEnderecoLogradouroProperty() {
		StringProperty enderecoLogradouroProperty = new SimpleStringProperty("");
		Endereco endereco = this.getDocEndereco();

		if (endereco != null) {
			String enderecoValue = endereco.getEndLogradouro();
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

	public String getDocProcessoProcNumero() {
		if (this.docProcesso != null) {
			return this.docProcesso.getProcNumero();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public String getDocEnderecoLogradouro() {
		if (this.docEndereco != null) {
			return this.docEndereco.getEndLogradouro();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public String getDocTipoDescricao() {
		if (this.docTipo != null) {
			return this.docTipo.getDtDescricao();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDocNumero() {
		return docNumero;
	}

	public void setDocNumero(String docNumero) {
		this.docNumero = docNumero;
	}

	public Processo getDocProcesso() {
		return docProcesso;
	}

	public void setDocProcesso(Processo docProcesso) {
		this.docProcesso = docProcesso;
	}

	public DocumentoTipo getDocTipo() {
		return docTipo;
	}

	public void setDocTipo(DocumentoTipo docTipo) {
		this.docTipo = docTipo;
	}

	public Endereco getDocEndereco() {
		return docEndereco;
	}

	public void setDocEndereco(Endereco docEndereco) {
		this.docEndereco = docEndereco;
	}

	public Long getDocSei() {
		return docSei;
	}

	public void setDocSei(Long docSei) {
		this.docSei = docSei;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
