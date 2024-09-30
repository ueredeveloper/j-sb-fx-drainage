package models;

public class Template {
	

	private Long id;
	private String descricao;
	private String html;
	private String pasta;
	private String nome;
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


	public Template(String descricao, String html) {
		super();
		this.descricao = descricao;
		this.html = html;
	}

	public Template(Long id, String descricao, String html) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.html = html;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getPasta() {
		return pasta;
	}


	public void setPasta(String pasta) {
		this.pasta = pasta;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getConteudo() {
		return conteudo;
	}


	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}


	@Override
	public String toString() {
		return "Template [descricao=" + descricao + ", pasta=" + pasta + ", nome=" + nome + ", conteudo=" + conteudo
				+ "]";
	}
	
	

}