package models;

public class Processo {

	
	private Long procId;
	private String procNumero;
	private Anexo anexo;

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

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	// Override toString() method to display procNumero in ComboBox
	@Override
	public String toString() {
		return procNumero;
	}

}
