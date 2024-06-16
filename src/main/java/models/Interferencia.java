package models;

//import com.vividsolutions.jts.geom.Geometry;

public class Interferencia {

	private Long interId;

	private Double interLatitude;

	private Double interLongitude;

	// private Geometry interGeometry;

	private Endereco interEndereco;

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

	@Override
	public String toString() {
		return interLatitude + ", " + interLongitude;
	}

}
