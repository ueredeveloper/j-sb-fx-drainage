package models;

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
	public String toString() {
		return dtDescricao;
	}

}
