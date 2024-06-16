package models;

public class Processo {

	private Long procId;
	private String procNumero;

	public Processo() {
		super();

	}
	

	public Processo(String procNumero) {
		super();
		this.procNumero = procNumero;
	}

	public Processo(Long procId, String procNumero) {
		super();
		this.procId = procId;
		this.procNumero = procNumero;
	}

	public Long getProcId() {
		return procId;
	}

	public void setProcId(Long procId) {
		this.procId = procId;
	}

	public String getProcNumero() {
		return procNumero;
	}

	public void setProcNumero(String procNumero) {
		this.procNumero = procNumero;
	}
	
	 // Override toString() method to display procNumero in ComboBox
    @Override
    public String toString() {
        return procNumero;
    }

}
