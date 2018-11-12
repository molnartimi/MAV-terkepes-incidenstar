package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.SelectStationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.StationHeader;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.StationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayLine;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayStopRepository;

@Service
public class RailwayStopService {
	@Autowired
	RailwayStopRepository railwayStopRepository;
	
	public StationInfo getStation(Integer id) {
		
		RailwayStop railwayStop = railwayStopRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Id cannot be found."));
		
		return new StationInfo(
				railwayStop.getId(), 
				railwayStop.getName(),
				railwayStop.getLatitude(),
				railwayStop.getLongitude());
	}
	
	public List<SelectStationInfo> getSelectStationInfo() {
		List<SelectStationInfo> result = new ArrayList<SelectStationInfo>();
		
		List<RailwayStop> everyStop = railwayStopRepository.findAll();
		for (RailwayStop stop : everyStop) {
			StationHeader station = new StationHeader(stop.getId(), stop.getName());
			SelectStationInfo stationInfo = new SelectStationInfo(station);
			
			for (RailwayLine line : stop.getRailwayLines()) {
				for (RailwayStop neighbour : line.getRailwayStops()) {
					stationInfo.addStation(new StationHeader(
								neighbour.getId(),
								neighbour.getName()
							));
				}				
			}
			result.add(stationInfo);
		}
		
		return result;
	}

}
