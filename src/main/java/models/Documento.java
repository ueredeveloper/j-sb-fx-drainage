package models;

/**
 * Classe que representa um Documento.
 */
public class Documento {

	private int docId;
	private String docNumero;
	private Processo docProcesso;
	private int docSEI;
	private DocumentoTipo docTipo;
	private Endereco docEndereco;

	// Construtor padrão
	public Documento() {
		super();
	}

	/**
	 * Construtor com todos os atributos.
	 *
	 * @param docId
	 *            Identificador do documento.
	 * @param docNumero
	 *            Número do documento.
	 * @param docProcesso
	 *            Processo relacionado ao documento.
	 * @param docSEI
	 *            Número SEI do documento.
	 * @param docTipo
	 *            Tipo do documento.
	 * @param docEndereco
	 *            Endereço do documento.
	 */
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
	
	
	public Documento(String docNumero, Processo docProcesso, int docSEI, DocumentoTipo docTipo) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
	}

	public Documento(String docNumero, Processo docProcesso, int docSEI, DocumentoTipo docTipo, Endereco docEndereco) {
		super();
		this.docNumero = docNumero;
		this.docProcesso = docProcesso;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
		this.docEndereco = docEndereco;
	}

	/**
	 * Construtor para criar um documento com número, número SEI e tipo do
	 * documento.
	 *
	 * @param docNumero
	 *            Número do documento.
	 * @param docSEI
	 *            Número SEI do documento.
	 * @param docTipo
	 *            Tipo do documento.
	 */
	public Documento(String docNumero, int docSEI, DocumentoTipo docTipo) {
		super();
		this.docNumero = docNumero;
		this.docSEI = docSEI;
		this.docTipo = docTipo;
	}

	/**
	 * Obtém o identificador do documento.
	 *
	 * @return O identificador do documento.
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * Define o identificador do documento.
	 *
	 * @param docId
	 *            O identificador do documento.
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * Obtém o número do documento.
	 *
	 * @return O número do documento.
	 */
	public String getDocNumero() {
		return docNumero;
	}

	/**
	 * Define o número do documento.
	 *
	 * @param docNumero
	 *            O número do documento.
	 */
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
