package models;

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
	private Long docSEI;
	private DocumentoTipo docTipo;
	private Endereco docEndereco;

	// Construtor padrão
	public Documento() {
		super();
	}

	public Documento(Long docId, String docNumero, Processo docProcesso, Long docSEI, DocumentoTipo docTipo,
			Anexo docAnexo, Endereco docEndereco) {
		super();
		this.docId = docId;
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
	}

	public Documento(Long docId, String docNumero) {
		super();
		this.docId = docId;
		this.docNumero = docNumero;
	}

	public Documento(String docNumero, Processo docProcesso, Long docSEI, DocumentoTipo docTipo) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
	}

	public Documento(String docNumero, Processo docProcesso, Long docSEI, DocumentoTipo docTipo, Anexo docAnexo) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
	}

	public Documento(String docNumero, Processo docProcesso, Long docSEI, DocumentoTipo docTipo, Endereco docEndereco) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
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

	public Long getDocSEI() {
		return docSEI;
	}

	public void setDocSEI(Long docSEI) {
		this.docSEI = docSEI;
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

}
