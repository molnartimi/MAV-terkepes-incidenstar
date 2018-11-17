package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto;

import java.util.List;

import lombok.Data;

@Data
public class IncidentInfo {
	StationHeader station;

	Double longitude;

	Double latitude;

	List<Report> reports;

	public IncidentInfo(StationHeader station, Double longitude, Double latitude, List<Report> reports) {
		this.station = station;
		this.longitude = longitude;
		this.latitude = latitude;
		this.reports = reports;
	}
}
