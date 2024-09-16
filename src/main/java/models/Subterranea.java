package models;

import java.util.Set;

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
	
	

	public Subterranea(Double latitude, Double longitude, Endereco endereco, TipoInterferencia tipoInterferencia,
			TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga, SituacaoProcesso situacaoProcesso,
			TipoAto tipoAto) {
		super(latitude, longitude, endereco, tipoInterferencia, tipoOutorga, subtipoOutorga, situacaoProcesso, tipoAto);
	}
	
	public Subterranea(Double latitude, Double longitude, String geometry, Endereco endereco,
			TipoInterferencia tipoInterferencia, TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga,
			SituacaoProcesso situacaoProcesso, TipoAto tipoAto, BaciaHidrografica baciaHidrografica,
			UnidadeHidrografica unidadeHidrografica, Set<Finalidade> finalidades) {
		super(latitude, longitude, geometry, endereco, tipoInterferencia, tipoOutorga, subtipoOutorga, situacaoProcesso,
				tipoAto, baciaHidrografica, unidadeHidrografica, finalidades);
	}

	public Subterranea(Long id, Double latitude, Double longitude, String geometry, Endereco endereco,
			TipoInterferencia tipoInterferencia, TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga,
			SituacaoProcesso situacaoProcesso, TipoAto tipoAto, BaciaHidrografica baciaHidrografica,
			UnidadeHidrografica unidadeHidrografica, Set<Finalidade> finalidades) {
		super(id, latitude, longitude, geometry, endereco, tipoInterferencia, tipoOutorga, subtipoOutorga, situacaoProcesso,
				tipoAto, baciaHidrografica, unidadeHidrografica, finalidades);
	}

	public Subterranea(Double latitude, Double longitude) {
		super(latitude, longitude);
		// TODO Auto-generated constructor stub
	}

	
	
	public Subterranea(Endereco endereco) {
		super(endereco);
		// TODO Auto-generated constructor stub
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
