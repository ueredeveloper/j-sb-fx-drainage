package models;

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

}
