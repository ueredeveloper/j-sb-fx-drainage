package models;

public class Demanda {

	private Long id;

	private Double vazao; // litros/dia

	private Integer tempo; // horas/dia

	private Integer periodo; // dias/mês.

	private Integer mes;

	private TipoFinalidade tipoFinalidade;

	private Interferencia interferencia;

	public Demanda() {
		super();
	}

	public Demanda(TipoFinalidade tipoFinalidade) {
		super();
		this.tipoFinalidade = tipoFinalidade;
	}

	public Demanda(int mes) {
		super();
		this.mes = mes;
	}

	
	
	public Demanda(Double vazao, Integer tempo, Integer periodo) {
		super();
		this.vazao = vazao;
		this.tempo = tempo;
		this.periodo = periodo;
	}

	public Demanda(int mes, TipoFinalidade tipoFinalidade) {
		super();
		this.mes = mes;
		this.tipoFinalidade = tipoFinalidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getVazao() {
		return vazao;
	}

	public void setVazao(Double vazao) {
		this.vazao = vazao;
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
	
	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	@Override
	public String toString() {
		return "Demanda [id=" + id + ", vazao=" + vazao + " L/dia, tempo=" + tempo + " h/dia, periodo=" + periodo
				+ " dias/mês, mês do ano= " + mes + " + e id interferencia=" + interferencia + "]";
	}

}
