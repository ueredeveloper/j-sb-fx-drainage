package models;

import java.util.HashSet;
import java.util.Set;

public class Usuario {

	private Long id;
	private String nome;
	private String cpfCnpj;

	private Set<Documento> documentos = new HashSet<>();

	public Usuario() {
		super();
	}

	public Usuario(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Usuario(String nome) {
		super();
		this.nome = nome;
	}
	
	
	public Usuario(String nome, String cpfCnpj) {
		super();
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
	}


	public Usuario(Long id, String nome, String cpfCnpj, Set<Documento> documentos) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}
}
