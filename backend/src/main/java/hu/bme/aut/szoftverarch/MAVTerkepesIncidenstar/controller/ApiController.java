package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.IncidentInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.SelectStationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.DbInitService;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.RailwayStopService;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	DbInitService dbInitService;
	
	@Autowired
	RailwayStopService railwayStopService;
	
	@GetMapping("/getSelectStationInfo")
	public List<SelectStationInfo> getSelectStationInfo() {
		return railwayStopService.getSelectStationInfo();
	}
	
	@GetMapping("getIncidents")
	public List<IncidentInfo> getIncidents(
			@RequestParam(value="stationId1", required=true) Integer stationId1,
			@RequestParam(value="stationId2", required=false) Integer stationId2,
			@RequestParam(value="fromDate", required=false) String fromDate,
			@RequestParam(value="toDate", required=false) String toDate) {
		return railwayStopService.getIncidentInfo(stationId1, stationId2, fromDate, toDate);
	}
		
	/**
	 * This mapping constructs the database with values from static resource files.
	 * Should only be run once.
	 */
//	@GetMapping("/dbinit")
//	public void init() {			
//		dbInitService.readEntitiesFromFile();
//	}
	
}
