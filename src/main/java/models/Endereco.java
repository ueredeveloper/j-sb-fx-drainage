package models;

public class Endereco {

	private int endId;
	private String endLogradouro;

	public Endereco() {
		super();
	}

	public Endereco(int endId, String endLogradouro) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
	}

	public int getEndId() {
		return endId;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}

	public String getEndLogradouro() {
		return endLogradouro;
	}

	public void setEndLogradouro(String endLogradouro) {
		this.endLogradouro = endLogradouro;
	}
	
	

}
