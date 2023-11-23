package models;

public class Endereco {
	

	private Long endId;
	private String endLogradouro;
	private String endCidade;
	private String endCEP;

	public Endereco() {
		super();
	}
	public Endereco(String endLogradouro) {
		super();
		this.endLogradouro = endLogradouro;
	}
	
	public Endereco(Long endId, String endLogradouro) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
	}

	public Endereco(Long endId, String endLogradouro, String endCidade, String endCEP) {
		super();
		this.endId = endId;
		this.endLogradouro = endLogradouro;
		this.endCidade = endCidade;
		this.endCEP = endCEP;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public String getEndLogradouro() {
		return endLogradouro;
	}

	public void setEndLogradouro(String endLogradouro) {
		this.endLogradouro = endLogradouro;
	}

	public String getEndCidade() {
		return endCidade;
	}

	public void setEndCidade(String endCidade) {
		this.endCidade = endCidade;
	}

	public String getEndCEP() {
		return endCEP;
	}

	public void setEndCEP(String endCEP) {
		this.endCEP = endCEP;
	}
	
	@Override
	public String toString() {
		return endLogradouro;
	}

	
}
