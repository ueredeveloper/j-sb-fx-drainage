package models;

import java.util.ArrayList;
import java.util.List;

public class Anexo {

	private Long id;
	private String numero;
	private List<Processo> processos = new ArrayList<>();

	public Anexo() {
		super();
	}

	public Anexo(String numero) {
		super();
		this.numero = numero;
	}

	public Anexo(Long id, String numero) {
		super();
		this.id = id;
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(List<Processo> processos) {
		this.processos = processos;
	}

	@Override
	public String toString() {
		return numero;
	}

}
