package models;

public class TipoInterferencia {

	private Long id;

	private String descricao;

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
	
	 public TipoInterferencia() {
		super();
	}

	public TipoInterferencia(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        TipoInterferencia that = (TipoInterferencia) o;
	        return id.equals(that.id);
	    }

	    @Override
	    public int hashCode() {
	        return id.hashCode();
	    }

	@Override
	public String toString() {
		return descricao;
	}

}
