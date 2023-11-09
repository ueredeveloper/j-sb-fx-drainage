package models;

public class Endereco {

	private int end_id;
	private String end_logradouro;

	public Endereco() {
		super();
	}

	public Endereco(int end_id, String end_logradouro) {
		super();
		this.end_id = end_id;
		this.end_logradouro = end_logradouro;
	}

	public int getEnd_id() {
		return end_id;
	}

	public void setEnd_id(int end_id) {
		this.end_id = end_id;
	}

	public String getEnd_logradouro() {
		return end_logradouro;
	}

	public void setEnd_logradouro(String end_logradouro) {
		this.end_logradouro = end_logradouro;
	}

}
