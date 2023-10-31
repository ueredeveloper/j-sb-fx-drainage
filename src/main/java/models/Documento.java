package models;

import models.DocumentoTipo;

public class Documento {

	private int doc_id;
	private String doc_numero;
	private String doc_processo;
	private String doc_sei;
	private DocumentoTipo doc_tipo; // Can be modified based on the expected response format
	
	
	// constructor
	public Documento() {
		super();
	}

	// getters and setters
	public int getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(int doc_id) {
		this.doc_id = doc_id;
	}

	public String getDoc_numero() {
		return doc_numero;
	}

	public void setDoc_numero(String doc_numero) {
		this.doc_numero = doc_numero;
	}

	public String getDoc_processo() {
		return doc_processo;
	}

	public void setDoc_processo(String doc_processo) {
		this.doc_processo = doc_processo;
	}

	public String getDoc_sei() {
		return doc_sei;
	}

	public void setDoc_sei(String doc_sei) {
		this.doc_sei = doc_sei;
	}

	public DocumentoTipo getDoc_tipo() {
		return doc_tipo;
	}

	public void setDoc_tipo(DocumentoTipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}

	@Override
	public String toString() {
		return "Document{" + "doc_id=" + this.doc_id + ", doc_numero='" + this.doc_numero + '\'' + ", doc_processo='"
				+ this.doc_processo + '\'' + ", doc_sei='" + this.doc_sei + '\'' + ", doc_tipo="
				+ this.doc_tipo.getDt_descricao() + '}';
	}

}
