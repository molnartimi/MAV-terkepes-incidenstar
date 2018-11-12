package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.SelectStationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.StationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.DbInitService;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.RailwayStopService;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	DbInitService dbInitService;
	
	@Autowired
	RailwayStopService RailwayStopService;
	
	@GetMapping("/getStation/{id}")
	public StationInfo getStationById(@PathVariable(value = "id") Integer id) {
		return RailwayStopService.getStation(id);
	}
	
	@GetMapping("/getSelectStationInfo")
	public List<SelectStationInfo> getSelectStationInfo() {
		return RailwayStopService.getSelectStationInfo();
	}
		
	/**
	 * This mapping constructs the database with values from static resource files.
	 * Should only be run once.
	 */
	@GetMapping("/dbinit")
	public void init() {			
		dbInitService.readEntitiesFromFile();
	}
	
	
}
