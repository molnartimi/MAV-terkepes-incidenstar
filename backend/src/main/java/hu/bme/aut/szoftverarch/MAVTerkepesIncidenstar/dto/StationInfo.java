package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto;

import lombok.Data;

@Data
public class StationInfo {
	
	private Integer id;
	
	private String name;
	
	private Double longitude;
	
	private Double latitude;
	
	public StationInfo(
			Integer id, 
			String name, 
			Double latitude,
			Double longitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}