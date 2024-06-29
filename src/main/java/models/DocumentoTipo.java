package models;

import java.util.Objects;

public class DocumentoTipo {

	private Long dtId;
	private String dtDescricao;

	// constructor
	public DocumentoTipo() {
		super();
	}

	public DocumentoTipo(Long dtId, String dtDescricao) {
		this.dtId = dtId;
		this.dtDescricao = dtDescricao;
	}

	public DocumentoTipo(Long dtId) {
		this.dtId = dtId;
	}

	// getters and setters
	public Long getDtId() {
		return dtId;
	}

	public void setDtId(Long dtId) {
		this.dtId = dtId;
	}

	public String getDtDescricao() {
		return dtDescricao;
	}

	public void setDtDescricao(String dtDescricao) {
		this.dtDescricao = dtDescricao;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(dtId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoTipo other = (DocumentoTipo) obj;
		return Objects.equals(dtId, other.dtId);
	}

	@Override
	public String toString() {
		return dtDescricao;
	}

}
