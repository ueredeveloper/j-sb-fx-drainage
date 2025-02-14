package models;

public class HidrogeoPoroso {

	private Long objectid;
	private String codPlan;
	private String sistema;
	private Double qMedia;

	public HidrogeoPoroso() {
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

	public Double getqMedia() {
		return qMedia;
	}

	public void setqMedia(Double qMedia) {
		this.qMedia = qMedia;
	}
	

}
