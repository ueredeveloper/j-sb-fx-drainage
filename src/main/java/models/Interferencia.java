package models;

import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//import com.vividsolutions.jts.geom.Geometry;

public class Interferencia {

	private Long id;

	private Double latitude;

	private Double longitude;

	// private Geometry interGeometry;

	private Endereco endereco;

	private TipoInterferencia tipoInterferencia;

	public Interferencia() {
		super();
	}

	public Interferencia(Endereco endereco) {
		super();
		this.endereco = endereco;
	}

	public Interferencia(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Interferencia(Double latitude, Double longitude, Endereco endereco, TipoInterferencia tipoInterferencia) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.endereco = endereco;
		this.tipoInterferencia = tipoInterferencia;
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
