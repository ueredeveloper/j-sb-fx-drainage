package models;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//import com.vividsolutions.jts.geom.Geometry;

public class Interferencia {

	private Long id;

	private Double latitude;

	private Double longitude;

	private String geometry;

	private Endereco endereco;

	private TipoInterferencia tipoInterferencia;

	private TipoOutorga tipoOutorga;

	private SubtipoOutorga subtipoOutorga;

	private SituacaoProcesso situacaoProcesso;

	private TipoAto tipoAto;

	private BaciaHidrografica baciaHidrografica;

	private UnidadeHidrografica unidadeHidrografica;

	private Set<Finalidade> finalidades = new HashSet<>();

	private Set<Demanda> demandas = new HashSet<>();

	public Interferencia() {
		super();
	}

	public Interferencia(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Interferencia(Long id, Double latitude, Double longitude, Endereco endereco,
			TipoInterferencia tipoInterferencia, TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga,
			SituacaoProcesso situacaoProcesso, TipoAto tipoAto, Set<Finalidade> finalidades, Set<Demanda> demandas) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.endereco = endereco;
		this.tipoInterferencia = tipoInterferencia;
		this.tipoOutorga = tipoOutorga;
		this.subtipoOutorga = subtipoOutorga;
		this.situacaoProcesso = situacaoProcesso;
		this.tipoAto = tipoAto;
		this.finalidades = finalidades;
		this.demandas = demandas;
	}

	public Interferencia(Double latitude, Double longitude, Endereco endereco, TipoInterferencia tipoInterferencia,
			TipoOutorga tipoOutorga, SubtipoOutorga subtipoOutorga, SituacaoProcesso situacaoProcesso, TipoAto tipoAto,
			Set<Finalidade> finalidades, Set<Demanda> demandas) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.endereco = endereco;
		this.tipoInterferencia = tipoInterferencia;
		this.tipoOutorga = tipoOutorga;
		this.subtipoOutorga = subtipoOutorga;
		this.situacaoProcesso = situacaoProcesso;
		this.tipoAto = tipoAto;
		this.finalidades = finalidades;
		this.demandas = demandas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public TipoInterferencia getTipoInterferencia() {
		return tipoInterferencia;
	}

	public void setTipoInterferencia(TipoInterferencia tipoInterferencia) {
		this.tipoInterferencia = tipoInterferencia;
	}

	public TipoOutorga getTipoOutorga() {
		return tipoOutorga;
	}

	public void setTipoOutorga(TipoOutorga tipoOutorga) {
		this.tipoOutorga = tipoOutorga;
	}

	public SubtipoOutorga getSubtipoOutorga() {
		return subtipoOutorga;
	}

	public void setSubtipoOutorga(SubtipoOutorga subtipoOutorga) {
		this.subtipoOutorga = subtipoOutorga;
	}

	public SituacaoProcesso getSituacaoProcesso() {
		return situacaoProcesso;
	}

	public void setSituacaoProcesso(SituacaoProcesso situacaoProcesso) {
		this.situacaoProcesso = situacaoProcesso;
	}

	public TipoAto getTipoAto() {
		return tipoAto;
	}

	public void setTipoAto(TipoAto tipoAto) {
		this.tipoAto = tipoAto;
	}

	public Set<Finalidade> getFinalidades() {
		return finalidades;
	}

	public void setFinalidades(Set<Finalidade> finalidades) {
		this.finalidades = finalidades;
	}

	public Set<Demanda> getDemandas() {
		return demandas;
	}

	public void setDemandas(Set<Demanda> demandas) {
		this.demandas = demandas;
	}

	@Override
	public String toString() {
		return this.latitude + ", " + this.longitude;

	}

	public <T> StringProperty getProperty(Function<Interferencia, T> propertyAccessor) {
		StringProperty stringProperty = new SimpleStringProperty("");
		T value = propertyAccessor.apply(this);

		if (value != null) {
			String stringValue = String.valueOf(value);
			stringProperty.set(stringValue);
		}

		return stringProperty;
	}

	public String getEnderecoLogradouro() {
		if (this.endereco != null) {
			return this.endereco.getLogradouro();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

	public String getTipoInterferenciaDescricao() {
		if (this.tipoInterferencia != null) {
			return this.tipoInterferencia.getDescricao();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}

}
