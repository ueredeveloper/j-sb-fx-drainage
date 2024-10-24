package models;

public class Template {

	private Long id;
	private String nome;
	private String descricao;
	private String diretorio;
	private String arquivo;
	private String conteudo;

	public Template() {
		super();
	}

	public Template(Long id) {
		super();
		this.id = id;
	}

	public Template(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Template(String nome) {
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return nome;
	}

}