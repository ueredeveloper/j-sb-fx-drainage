package models;

import java.util.Map;

public class Dominio {

	private Map<String, TipoInterferencia> tipoInterferencia;
	private Map<String, TipoOutorga> tipoOutorga;
	private Map<String, SubtipoOutorga> subtipoOutorga;
	private Map<String, SituacaoProcesso> situacaoProcesso;
	private Map<String, TipoAto> tipoAto;
	private Map<String, Estado> estado;

	public Dominio() {
		super();
	}

	public Map<String, TipoInterferencia> getTypesOfInterferencesMap() {
		return tipoInterferencia;
	}

	public void setTypesOfInterferencesMap(Map<String, TipoInterferencia> tipoInterferencia) {
		this.tipoInterferencia = tipoInterferencia;
	}

	public Map<String, TipoOutorga> getTypesOfGrantsMap() {
		return tipoOutorga;
	}

	public void setTypesOfGrantsMap(Map<String, TipoOutorga> tipoOutorga) {
		this.tipoOutorga = tipoOutorga;
	}

	public Map<String, SubtipoOutorga> getSubtypesOfGrantsMap() {
		return subtipoOutorga;
	}

	public void setSubtypesOfGrantsMap(Map<String, SubtipoOutorga> subtipoOutorga) {
		this.subtipoOutorga = subtipoOutorga;
	}

	public Map<String, SituacaoProcesso> getProcessesSituationsMap() {
		return situacaoProcesso;
	}

	public void setProcessesSituationsMap(Map<String, SituacaoProcesso> situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public Map<String, TipoAto> getTypesOfActsMap() {
		return tipoAto;
	}

	public void setTypesOfActsMap(Map<String, TipoAto> tipoAto) {
		this.tipoAto = tipoAto;
	}

	public Map<String, Estado> getStatesMap() {
		return estado;
	}

	public void setStatesMap(Map<String, Estado> estado) {
		this.estado = estado;
	}

}
