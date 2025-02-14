package models;

import java.util.Set;

public class Subterranea extends Interferencia {

	private Boolean caesb; // tem caesb () sim () não

	private String nivelEstatico; // em metros

	private String nivelDinamico; // em metros

	private String profundidade; // em metros

	private Integer vazaoOutorgavel; //

	private Integer vazaoSistema; //

	private Integer vazaoTeste; //

	private TipoPoco tipoPoco;

	private String sistema;

	private String subsistema;
	
	private String codPlan;
	
	public Subterranea() {
		super();
	}

	public Subterranea(Double latitude, Double longitude) {
		super(latitude, longitude);
		// TODO Auto-generated constructor stub
	}

	public Subterranea(Double latitude, Double longitude, Endereco endereco, TipoInterferencia tipoInterferencia,
			TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga, SituacaoProcesso situacaoProcesso, TipoAto tipoAto,
			Set<Finalidade> finalidades, Set<Demanda> demandas) {
		super(latitude, longitude, endereco, tipoInterferencia, tipoOutorga, subtipoOutorga, situacaoProcesso, tipoAto,
				finalidades, demandas);
		// TODO Auto-generated constructor stub
	}

	public Subterranea(Long id, Double latitude, Double longitude, Endereco endereco,
			TipoInterferencia tipoInterferencia, TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga,
			SituacaoProcesso situacaoProcesso, TipoAto tipoAto, Set<Finalidade> finalidades, Set<Demanda> demandas) {
		super(id, latitude, longitude, endereco, tipoInterferencia, tipoOutorga, subtipoOutorga, situacaoProcesso,
				tipoAto, finalidades, demandas);
		// TODO Auto-generated constructor stub
	}
	
	public Subterranea(Boolean caesb, String nivelEstatico, String nivelDinamico, String profundidade,
			Integer vazaoOutorgavel, Integer vazaoSistema, Integer vazaoTeste, TipoPoco tipoPoco, String sistema,
			String subsistema, String codPlan) {
		super();
		this.caesb = caesb;
		this.nivelEstatico = nivelEstatico;
		this.nivelDinamico = nivelDinamico;
		this.profundidade = profundidade;
		this.vazaoOutorgavel = vazaoOutorgavel;
		this.vazaoSistema = vazaoSistema;
		this.vazaoTeste = vazaoTeste;
		this.tipoPoco = tipoPoco;
		this.sistema = sistema;
		this.subsistema = subsistema;
		this.codPlan = codPlan;
	}

	public Boolean getCaesb() {
		return caesb;
	}

	public void setCaesb(Boolean caesb) {
		this.caesb = caesb;
	}

	public String getNivelEstatico() {
		return nivelEstatico;
	}

	public void setNivelEstatico(String nivelEstatico) {
		this.nivelEstatico = nivelEstatico;
	}

	public String getNivelDinamico() {
		return nivelDinamico;
	}

	public void setNivelDinamico(String nivelDinamico) {
		this.nivelDinamico = nivelDinamico;
	}

	public String getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}

	public Integer getVazaoOutorgavel() {
		return vazaoOutorgavel;
	}

	public void setVazaoOutorgavel(Integer vazaoOutorgavel) {
		this.vazaoOutorgavel = vazaoOutorgavel;
	}

	public Integer getVazaoSistema() {
		return vazaoSistema;
	}

	public void setVazaoSistema(Integer vazaoSistema) {
		this.vazaoSistema = vazaoSistema;
	}

	public Integer getVazaoTeste() {
		return vazaoTeste;
	}

	public void setVazaoTeste(Integer vazaoTeste) {
		this.vazaoTeste = vazaoTeste;
	}

	public TipoPoco getTipoPoco() {
		return tipoPoco;
	}

	public void setTipoPoco(TipoPoco tipoPoco) {
		this.tipoPoco = tipoPoco;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getSubsistema() {
		return subsistema;
	}

	public void setSubsistema(String subsistema) {
		this.subsistema = subsistema;
	}
	
	public String getCodPlan() {
		return codPlan;
	}

	public void setCodPlan(String codPlan) {
		this.codPlan = codPlan;
	}

	@Override
	public String toString() {
		return "Subterranea: " + getLatitude() + ", " + getLongitude();
	}

}