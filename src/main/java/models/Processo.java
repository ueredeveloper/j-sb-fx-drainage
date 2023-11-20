package models;

public class Processo {
	private int procId;
	private String procDescricao;

	public Processo() {
		super();

	}

	public Processo(int procId, String procDescricao) {
		super();
		this.procId = procId;
		this.procDescricao = procDescricao;
	}

	public Processo(String procDescricao) {
		super();
		this.procDescricao = procDescricao;
	}

	public int getProcId() {
		return procId;
	}

	public void setProcId(int procId) {
		this.procId = procId;
	}

	public String getProcDescricao() {
		return procDescricao;
	}

	public void setProcDescricao(String procDescricao) {
		this.procDescricao = procDescricao;
	}

}
