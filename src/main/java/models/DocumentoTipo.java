package models;

import java.util.Objects;

public class DocumentoTipo {

	private Long id;
	private String descricao;

	// constructor
	public DocumentoTipo() {
		super();
	}

	public DocumentoTipo(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return descricao;
	}

}
