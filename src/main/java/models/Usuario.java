package models;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
	

	private Long usId;
	private String usNome;
	private Integer usCpfCnpj;

	private Set<Documento> documentos = new HashSet<>();

	public Usuario() {
		super();
	}

	public Usuario(Long usId, String usNome) {
		super();
		this.usId = usId;
		this.usNome = usNome;
	}

	public Usuario(String usNome) {
		super();
		this.usNome = usNome;
	}

	public Long getUsId() {
		return usId;
	}

	public void setUsId(Long usId) {
		this.usId = usId;
	}

	public String getUsNome() {
		return usNome;
	}

	public void setUsNome(String usNome) {
		this.usNome = usNome;
	}

	public Integer getUsCpfCnpj() {
		return usCpfCnpj;
	}

	public void setUsCpfCnpj(Integer usCpfCnpj) {
		this.usCpfCnpj = usCpfCnpj;
	}

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@Override
	public String toString() {
		return usNome;
	}

}
