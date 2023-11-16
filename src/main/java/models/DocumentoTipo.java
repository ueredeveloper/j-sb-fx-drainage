package models;

import java.util.Objects;

public class DocumentoTipo {
	private int dt_id;
	private String dt_descricao;

	// constructor
	public DocumentoTipo() {
		super();
	}

	public DocumentoTipo(int dt_id, String dt_descricao) {
		this.dt_id = dt_id;
		this.dt_descricao = dt_descricao;
	}

	public DocumentoTipo(int dt_id) {
		this.dt_id = dt_id;
	}

	// getters and setters
	public int getDt_id() {
		return dt_id;
	}

	public void setDt_id(int dt_id) {
		this.dt_id = dt_id;
	}

	public String getDt_descricao() {
		return dt_descricao;
	}

	public void setDt_descricao(String dt_descricao) {
		this.dt_descricao = dt_descricao;
	}

	@Override
	public String toString() {
		return dt_descricao;
	}

	// Comparação de objetos para testes
	/*
	 * It seems that the verifyThat method in the TestFX test is expecting an exact
	 * match of the DocumentoTipo object for the selected item, but the assertion
	 * fails because it's comparing different instances of the DocumentoTipo class,
	 * even if they represent the same item.
	 * 
	 * To fix this, you can override the equals method in your DocumentoTipo class
	 * to define when two instances are considered equal based on their dt_descricao
	 * or dt_id values. Here's an example:
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
		return dt_id == other.dt_id && Objects.equals(dt_descricao, other.dt_descricao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dt_id, dt_descricao);
	}

}
