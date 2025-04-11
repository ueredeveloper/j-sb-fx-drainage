package utilities;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

public class CoordinateConverter {

	public CoordinateConverter() {
		super();
	}

	public ProjCoordinate convertUtmlToDecimal(Double x, Double y, String zone, String hemisphere) {
		// Validate input parameters
		if (x == null || y == null || zone == null || hemisphere == null) {
			throw new IllegalArgumentException("x, y, zone, and hemisphere cannot be null.");
		}

		// Normalize hemisphere input
		String _hemisphere;
		if (hemisphere.equalsIgnoreCase("S") || hemisphere.equalsIgnoreCase("south")) {
			_hemisphere = "south";
		} else if (hemisphere.equalsIgnoreCase("N") || hemisphere.equalsIgnoreCase("north")) {
			_hemisphere = "north";
		} else {
			throw new IllegalArgumentException("Invalid hemisphere value. Use 'S', 'N', 'south', or 'north'.");
		}

		CRSFactory crsFactory = new CRSFactory();

		// Define UTM CRS
		CoordinateReferenceSystem utmCRS = crsFactory.createFromParameters("UTM Zone " + zone + " " + _hemisphere,
				"+proj=utm +zone=" + zone + " +" + _hemisphere + " +datum=WGS84 +units=m +no_defs");

		// Define WGS84 CRS
		CoordinateReferenceSystem wgs84 = crsFactory.createFromParameters("WGS84",
				"+proj=longlat +datum=WGS84 +no_defs");

		if (utmCRS == null || wgs84 == null) {
			System.err.println("Failed to load CRS!");
			return null;
		}

		CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
		CoordinateTransform transform = ctFactory.createTransform(utmCRS, wgs84);

		ProjCoordinate utm = new ProjCoordinate(x, y);
		ProjCoordinate latlon = new ProjCoordinate();

		transform.transform(utm, latlon);

		return latlon;
	}

	// Convert GMS (Degrees, Minutes, Seconds) to Decimal Degrees
	public double convertGMSToDecimal(int degrees, int minutes, double seconds, boolean isNegative) {
		double decimal = degrees + (minutes / 60.0) + (seconds / 3600.0);
		return isNegative ? -decimal : decimal;
	}

}
