package models;

import java.util.Objects;

public class DocumentoTipo {
	private int dtId;
	private String dtDescricao;

	// constructor
	public DocumentoTipo() {
		super();
	}

	public DocumentoTipo(int dtId, String dtDescricao) {
		this.dtId = dtId;
		this.dtDescricao = dtDescricao;
	}

	public DocumentoTipo(int dtId) {
		this.dtId = dtId;
	}

	// getters and setters
	public int getDtId() {
		return dtId;
	}

	public void setDtId(int dtId) {
		this.dtId = dtId;
	}

	public String getDtDescricao() {
		return dtDescricao;
	}

	public void setDtDescricao(String dtDescricao) {
		this.dtDescricao = dtDescricao;
	}

	@Override
	public String toString() {
		return dtDescricao;
	}

	// Comparação de objetos para testes
	/*
	 * It seems that the verifyThat method in the TestFX test is expecting an exact
	 * match of the DocumentoTipo object for the selected item, but the assertion
	 * fails because it's comparing different instances of the DocumentoTipo class,
	 * even if they represent the same item.
	 * 
	 * To fix this, you can override the equals method in your DocumentoTipo class
	 * to define when two instances are considered equal based on their dtDescricao
	 * or dtId values. Here's an example:
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DocumentoTipo other = (DocumentoTipo) obj;
		return dtId == other.dtId && Objects.equals(dtDescricao, other.dtDescricao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dtId, dtDescricao);
	}

}
