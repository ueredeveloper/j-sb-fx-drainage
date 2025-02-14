package models;

public class SubsystemCodeAttributes {

	String latitude;
	String longitude;
	TipoInterferencia typeOfInterference;

	public SubsystemCodeAttributes() {
		super();
	}
	
	public SubsystemCodeAttributes(String latitude, String longitude, TipoInterferencia typeOfInterference) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.typeOfInterference = typeOfInterference;
	}



	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public TipoInterferencia getTypeOfInterference() {
		return typeOfInterference;
	}

	public void setTypeOfInterference(TipoInterferencia typeOfInterference) {
		this.typeOfInterference = typeOfInterference;
	}

}
