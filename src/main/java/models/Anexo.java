package models;

import java.util.HashSet;
import java.util.Set;

public class Anexo {

	private Long id;
	private String numero;
	private Set<Processo> processos = new HashSet<Processo>();

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
	
	public Anexo(Long id, String numero, Set<Processo> processos) {
		super();
		this.id = id;
		this.numero = numero;
		this.processos = processos;
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

	public Set<Processo> getProcessos() {
		return processos;
	}

	public void setProcessos(Set<Processo> processos) {
		this.processos = processos;
	}

	@Override
	public String toString() {
		return numero;
	}

}
