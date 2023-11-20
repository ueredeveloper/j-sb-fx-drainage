package models;

public class Documento {
	

	private int docId;
	private String docNumero;
	private Processo docProcesso;
	private int docSEI;
	private DocumentoTipo docTipo;
	private Endereco docEndereco;

	// constructor
	public Documento() {
		super();
	}

	public Documento(int docId, String docNumero, Processo docProcesso, int docSEI, DocumentoTipo docTipo,
			Endereco docEndereco) {
		super();
		this.docId = docId;
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
	}

	public Documento(String docNumero, int docSEI, DocumentoTipo docTipo) {
		super();
		this.docNumero = docNumero;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocNumero() {
		return docNumero;
	}

	public void setDocNumero(String docNumero) {
		this.docNumero = docNumero;
	}

	public Processo getDocProcesso() {
		return docProcesso;
	}

	public void setDocProcesso(Processo docProcesso) {
		this.docProcesso = docProcesso;
	}

	public int getDocSEI() {
		return docSEI;
	}

	public void setDocSEI(int docSEI) {
		this.docSEI = docSEI;
	}

	public DocumentoTipo getDocTipo() {
		return docTipo;
	}

	public void setDocTipo(DocumentoTipo docTipo) {
		this.docTipo = docTipo;
	}

	public Endereco getDocEndereco() {
		return docEndereco;
	}

	public void setDocEndereco(Endereco docEndereco) {
		this.docEndereco = docEndereco;
	}

}
