package models;

public class Documento {

	private int doc_id;
	private String doc_numero;
	private String doc_processo;
	private int doc_sei;
	private DocumentoTipo doc_tipo;
	private Endereco doc_endereco;
	
	
	// constructor
	public Documento() {
		super();
	}
	
	public Documento(int doc_id, String doc_numero, String doc_processo, int doc_sei, DocumentoTipo doc_tipo,
			Endereco doc_endereco) {
		super();
		this.doc_id = doc_id;
		this.doc_numero = doc_numero;
		this.doc_processo = doc_processo;
		this.doc_sei = doc_sei;
		this.doc_tipo = doc_tipo;
		this.doc_endereco = doc_endereco;
	}
	
	public Documento(String doc_numero, String doc_processo, int doc_sei, DocumentoTipo doc_tipo) {
		super();
		this.doc_numero = doc_numero;
		this.doc_processo = doc_processo;
		this.doc_sei = doc_sei;
		this.doc_tipo = doc_tipo;
	}

	public Documento(String doc_numero, int doc_sei, DocumentoTipo doc_tipo) {
		super();
		this.doc_numero = doc_numero;
		this.doc_sei = doc_sei;
		this.doc_tipo = doc_tipo;
	}

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


	public int getDoc_sei() {
		return doc_sei;
	}


	public void setDoc_sei(int doc_sei) {
		this.doc_sei = doc_sei;
	}


	public DocumentoTipo getDoc_tipo() {
		return doc_tipo;
	}


	public void setDoc_tipo(DocumentoTipo doc_tipo) {
		this.doc_tipo = doc_tipo;
	}


	public Endereco getDoc_endereco() {
		return doc_endereco;
	}


	public void setDoc_endereco(Endereco doc_endereco) {
		this.doc_endereco = doc_endereco;
	}

	

}
