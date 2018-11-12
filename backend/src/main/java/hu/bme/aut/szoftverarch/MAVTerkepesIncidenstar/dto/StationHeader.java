package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto;

import lombok.Data;

@Data
public class StationHeader {
	
	private Integer id;
	
	private String name;
	
	public StationHeader(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}