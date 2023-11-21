package models;

public class Anexo {

	private Long anId;

	private String anNumero;

	private Processo anPrincipal;

	public Anexo(Long anId, String anNumero, Processo anPrincipal) {
		super();
		this.anId = anId;
		this.anNumero = anNumero;
		this.anPrincipal = anPrincipal;
	}

	public Anexo(String anNumero, Processo anPrincipal) {
		super();
		this.anNumero = anNumero;
		this.anPrincipal = anPrincipal;
	}

	public Long getAnId() {
		return anId;
	}

	public void setAnId(Long anId) {
		this.anId = anId;
	}

	public String getAnNumero() {
		return anNumero;
	}

	public void setAnNumero(String anNumero) {
		this.anNumero = anNumero;
	}

	public Processo getAnPrincipal() {
		return anPrincipal;
	}

	public void setAnPrincipal(Processo anPrincipal) {
		this.anPrincipal = anPrincipal;
	}
	
	
}
