package models;

public class Template {

	private Long id;
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

	public Template(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Template(String descricao) {
		super();
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Template [descricao=" + descricao + ", diretorio=" + diretorio + ", arquivo=" + arquivo + ", conteudo=" + conteudo
				+ "]";
	}

}