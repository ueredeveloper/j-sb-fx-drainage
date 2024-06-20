package models;

import java.util.ArrayList;
import java.util.List;

public class Anexo {
	

	private Long anId;
	private String anNumero;
	private List<Processo> processos = new ArrayList<>();

	public Anexo() {
		super();
	}

	public Anexo(String anNumero) {
		super();
		this.anNumero = anNumero;
	}

	public Anexo(Long anId, String anNumero) {
		super();
		this.anId = anId;
		this.anNumero = anNumero;
	}

	public Long getAnId() {
		return anId;
	}

	public void setAnId(Long anId) {
		this.anId = anId;
	}

	public String getAnNumero() {
		return anNumero;
	}

	public void setAnNumero(String anNumero) {
		this.anNumero = anNumero;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	@Override
	public String toString() {
		return anNumero;
	}

}
