package models;

import java.util.Objects;

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
	
	/*
	@Override
	public int hashCode() {
		return Objects.hash(procId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		return Objects.equals(procId, other.procId);
	}*/

	// Override toString() method to display procNumero in ComboBox
	@Override
	public String toString() {
		return procNumero;
	}

}
