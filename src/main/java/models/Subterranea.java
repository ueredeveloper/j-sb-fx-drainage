package models;

public class Subterranea extends Interferencia {

	private Boolean caesb; // tem caesb () sim () não

	private String nivelEstatico; // em metros

	private String nivelDinamico; // em metros

	public Subterranea() {
		super();
	}

	public Subterranea(Double interLatitude, Double interLongitude, Endereco interEndereco,
			TipoInterferencia interferenciaTipo) {
		super(interLatitude, interLongitude, interEndereco, interferenciaTipo);
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

	@Override
	public String toString() {
		return "Subterranea [getLatitude()=" + getLatitude() + ", getLongitude()=" + getLongitude() + ", getEndereco()="
				+ getEndereco() + ", getTipoInterferencia()=" + getTipoInterferencia() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
