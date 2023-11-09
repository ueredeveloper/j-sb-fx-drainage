package models;

public class Endereco {
	
	private int end_id;
	private String end_endereco;
	
	public Endereco() {
		super();
	}
	
	public Endereco(int end_id, String end_endereco) {
		super();
		this.end_id = end_id;
		this.end_endereco = end_endereco;
	}

	public int getEnd_id() {
		return end_id;
	}

	public void setEnd_id(int end_id) {
		this.end_id = end_id;
	}

	public String getEnd_endereco() {
		return end_endereco;
	}

	public void setEnd_endereco(String end_endereco) {
		this.end_endereco = end_endereco;
	}
	
	
	

}
