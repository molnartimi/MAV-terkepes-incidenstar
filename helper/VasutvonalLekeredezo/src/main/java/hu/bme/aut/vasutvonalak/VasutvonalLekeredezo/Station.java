package hu.bme.aut.vasutvonalak.VasutvonalLekeredezo;

public class Station {
	public final String geoId;
	public String shortName;
	public final String name;
	public final String asciiName;
	public final Double latitude;
	public final Double longitude;

	public Station(String geoId, String name, String asciiName, Double latitude, Double longitude) {
		this.geoId = geoId;
		this.name = name;
		this.asciiName = asciiName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static String getCsvHeader() {
		return "geo_id" + ";" + "city" + ";" + "station" + ";" + "ascii_station" + ";" + "latitude" + ";" + "longitude" + "\n";
	}

	public String toCsvString() {
		return geoId + ";" + shortName + ";" + name + ";" + asciiName + ";" + latitude + ";" + longitude + "\n";
	}

}
