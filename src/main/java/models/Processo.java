package models;

public class Processo {

	private Long id;
	private String numero;
	private Anexo anexo;
	private Usuario usuario;

	public Processo() {
		super();

	}

	public Processo(String numero) {
		super();
		this.numero = numero;
	}

	public Processo(Long id, String numero) {
		super();
		this.id = id;
		this.numero = numero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Anexo getAnexo() {
		return anexo;
	}

	public void setAnexo(Anexo anexo) {
		this.anexo = anexo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// Override toString() method to display numero in ComboBox
	@Override
    public String toString() {
        return numero; // Display numero in ComboBox
    }

}
