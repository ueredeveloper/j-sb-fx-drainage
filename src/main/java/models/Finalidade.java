package models;

public class Finalidade {

	private Long id;

	private String finalidade;

	private String subfinalidade;

	private Double quantidade;

	private Double consumo;
	
	private Double total;

	private Interferencia interferencia;

	private TipoFinalidade tipoFinalidade;
	
	
	public Finalidade() {
		super();
	}
	
	public Finalidade(TipoFinalidade tipoFinalidade) {
		super();
		this.tipoFinalidade = tipoFinalidade;
	}

	public Finalidade(String finalidade, String subfinalidade, Double quantidade, Double consumo,
			Interferencia interferencia, TipoFinalidade tipoFinalidade) {
		super();
		this.finalidade = finalidade;
		this.subfinalidade = subfinalidade;
		this.quantidade = quantidade;
		this.consumo = consumo;
		this.interferencia = interferencia;
		this.tipoFinalidade = tipoFinalidade;
	}
	
	

	public Finalidade(String finalidade) {
		super();
		this.finalidade = finalidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getSubfinalidade() {
		return subfinalidade;
	}

	public void setSubfinalidade(String subfinalidade) {
		this.subfinalidade = subfinalidade;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public Interferencia getInterferencia() {
		return interferencia;
	}

	public void setInterferencia(Interferencia interferencia) {
		this.interferencia = interferencia;
	}

	public TipoFinalidade getTipoFinalidade() {
		return tipoFinalidade;
	}

	public void setTipoFinalidade(TipoFinalidade tipoFinalidade) {
		this.tipoFinalidade = tipoFinalidade;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Finalidade [finalidade=" + finalidade + ", subfinalidade=" + subfinalidade + ", quantidade="
				+ quantidade + ", consumo=" + consumo + ", total=" + total + "]";
	}
	
	
	
	
	

}
