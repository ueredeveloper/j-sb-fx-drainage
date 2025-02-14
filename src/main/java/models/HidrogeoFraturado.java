package models;

public class HidrogeoFraturado {

	private Long objectid;
	private String codPlan;
	private String sistema;
	private String subsistema;
	private Double vazao;

	public HidrogeoFraturado() {
		super();
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getCodPlan() {
		return codPlan;
	}

	public void setCodPlan(String codPlan) {
		this.codPlan = codPlan;
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

	public Double getVazao() {
		return vazao;
	}

	public void setVazao(Double vazao) {
		this.vazao = vazao;
	}
	

}
