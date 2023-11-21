package models;

public class Processo {
	
	private int procId;
	private String procNumero;

	public Processo() {
		super();

	}
	
	public Processo(String procNumero) {
		super();
		this.procNumero = procNumero;
	}

	public Processo(int procId, String procNumero) {
		super();
		this.procId = procId;
		this.procNumero = procNumero;
	}


	public int getProcId() {
		return procId;
	}

	public void setProcId(int procId) {
		this.procId = procId;
	}

	public String getProcNumero() {
		return procNumero;
	}

	public void setProcNumero(String procNumero) {
		this.procNumero = procNumero;
	}

	

}
