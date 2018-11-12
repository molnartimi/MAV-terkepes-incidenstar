package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SelectStationInfo {
	StationHeader station;
	
	List<StationHeader> accessibleStations;
	
	public SelectStationInfo(StationHeader station) {
		this.station = station;
		this.accessibleStations = new ArrayList<StationHeader>();
	}
	
	public void addStation(StationHeader station) {
		this.accessibleStations.add(station);
	}
	
}