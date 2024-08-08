package models;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
	

	private Long id;
	private String nome;
	private Integer cpdCnpj;

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

	public Integer getCpdCnpj() {
		return cpdCnpj;
	}

	public void setCpdCnpj(Integer cpdCnpj) {
		this.cpdCnpj = cpdCnpj;
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
