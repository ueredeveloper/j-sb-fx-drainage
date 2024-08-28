package models;

import java.util.Map;

public class Dominio {

	private Map<String, TipoInterferencia> tipoInterferencia;
	private Map<String, TipoAto> tipoAto;
	private Map<String, TipoOutorga> tipoOutorga;
	private Map<String, Estado> estado;

	public Dominio() {
		super();
	}

	public Map<String, TipoInterferencia> getTipoInterferencia() {
		return tipoInterferencia;
	}

	public void setTipoInterferencia(Map<String, TipoInterferencia> tipoInterferencia) {
		this.tipoInterferencia = tipoInterferencia;
	}

	public Map<String, TipoAto> getTipoAto() {
		return tipoAto;
	}

	public void setTipoAto(Map<String, TipoAto> tipoAto) {
		this.tipoAto = tipoAto;
	}

	public Map<String, TipoOutorga> getTipoOutorga() {
		return tipoOutorga;
	}

	public void setTipoOutorga(Map<String, TipoOutorga> tipoOutorga) {
		this.tipoOutorga = tipoOutorga;
	}

	public Map<String, Estado> getEstado() {
		return estado;
	}

	public void setEstado(Map<String, Estado> estado) {
		this.estado = estado;
	}
	
}
