package models;

public class Demanda {

	private Long id;

	private Double vazao; // litros/dia

	private int tempo; // horas/dia

	private int periodo; // dias/mês.

	private int mes;

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

	public Demanda(Double vazao, int tempo, int periodo, Interferencia interferencia) {
		super();
		this.vazao = vazao;
		this.tempo = tempo;
		this.periodo = periodo;
		this.interferencia = interferencia;
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

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Interferencia getInterferencia() {
		return interferencia;
	}

	public void setInterferencia(Interferencia interferencia) {
		this.interferencia = interferencia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public TipoFinalidade getTipoFinalidade() {
		return tipoFinalidade;
	}

	public void setTipoFinalidade(TipoFinalidade tipoFinalidade) {
		this.tipoFinalidade = tipoFinalidade;
	}

	@Override
	public String toString() {
		return "Demanda [id=" + id + ", vazao=" + vazao + " L/dia, tempo=" + tempo + " h/dia, periodo=" + periodo
				+ " dias/mês, mês do ano= " + mes + " + e id interferencia=" + interferencia + "]";
	}

}
