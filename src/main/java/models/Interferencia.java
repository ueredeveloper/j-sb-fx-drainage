package models;

import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//import com.vividsolutions.jts.geom.Geometry;

public class Interferencia {
	

	private Long interId;

	private Double interLatitude;

	private Double interLongitude;

	// private Geometry interGeometry;

	private Endereco interEndereco;
	
	private InterferenciaTipo interferenciaTipo;

	
	public Interferencia() {
		super();
	}
	
	public Interferencia(Endereco interEndereco) {
		super();
		this.interEndereco = interEndereco;
	}
	
	public Interferencia(Double interLatitude, Double interLongitude) {
		super();
		this.interLatitude = interLatitude;
		this.interLongitude = interLongitude;
	}
	
	
	public Interferencia(Double interLatitude, Double interLongitude, Endereco interEndereco,
			InterferenciaTipo interferenciaTipo) {
		super();
		this.interLatitude = interLatitude;
		this.interLongitude = interLongitude;
		this.interEndereco = interEndereco;
		this.interferenciaTipo = interferenciaTipo;
	}

	public Long getInterId() {
		return interId;
	}

	public void setInterId(Long interId) {
		this.interId = interId;
	}

	
	public Double getInterLatitude() {
		return interLatitude;
	}

	public void setInterLatitude(Double interLatitude) {
		this.interLatitude = interLatitude;
	}

	public Double getInterLongitude() {
		return interLongitude;
	}

	public void setInterLongitude(Double interLongitude) {
		this.interLongitude = interLongitude;
	}

	public Endereco getInterEndereco() {
		return interEndereco;
	}

	public void setInterEndereco(Endereco interEndereco) {
		this.interEndereco = interEndereco;
	}
	

	public InterferenciaTipo getInterferenciaTipo() {
		return interferenciaTipo;
	}

	public void setInterferenciaTipo(InterferenciaTipo interferenciaTipo) {
		this.interferenciaTipo = interferenciaTipo;
	}

	@Override
	public String toString() {
		return this.interLatitude + ", " + this.interLongitude;
		
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
		if (this.interEndereco != null) {
			return this.interEndereco.getEndLogradouro();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}
	public String getInterferenciaTipoDescricao() {
		if (this.interferenciaTipo != null) {
			return this.interferenciaTipo.getDescricao();
		} else {
			return null; // or handle the case when docProcesso is null
		}
	}
	
}
