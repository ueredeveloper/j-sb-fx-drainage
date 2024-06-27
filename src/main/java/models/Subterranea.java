package models;

public class Subterranea extends Interferencia {

	private Boolean subCaesb; // tem caesb () sim () não

	private String subNivelEstatico; // em metros

	private String subDinamico; // em metros

	public Subterranea() {
		super();
	}

	public Subterranea(Double interLatitude, Double interLongitude, Endereco interEndereco,
			InterferenciaTipo interferenciaTipo) {
		super(interLatitude, interLongitude, interEndereco, interferenciaTipo);
	}

	public Boolean getSubCaesb() {
		return subCaesb;
	}

	public void setSubCaesb(Boolean subCaesb) {
		this.subCaesb = subCaesb;
	}

	public String getSubNivelEstatico() {
		return subNivelEstatico;
	}

	public void setSubNivelEstatico(String subNivelEstatico) {
		this.subNivelEstatico = subNivelEstatico;
	}

	public String getSubDinamico() {
		return subDinamico;
	}

	public void setSubDinamico(String subDinamico) {
		this.subDinamico = subDinamico;
	}

	@Override
	public String toString() {
		return "Subterranea [getInterLatitude()=" + getInterLatitude()
				+ ", getInterLongitude()=" + getInterLongitude() + ", getInterEndereco()=" + getInterEndereco()
				+ ", getInterferenciaTipo()=" + getInterferenciaTipo() + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	

}
