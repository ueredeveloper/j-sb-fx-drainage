package models;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
	

	private Long id;
	private String nome;
	private Integer cpfCnpj;

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

	public Usuario(Long id, String nome, Integer cpfCnpj, Set<Documento> documentos) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.documentos = documentos;
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

	public Integer getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(Integer cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return nome;
	}

}
