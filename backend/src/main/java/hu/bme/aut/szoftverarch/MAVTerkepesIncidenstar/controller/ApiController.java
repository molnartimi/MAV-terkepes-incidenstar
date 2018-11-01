package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.DbInitService;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	DbInitService dbInitService;
	
	@GetMapping("/dbinit")
	public void init() {			
		dbInitService.readEntitiesFromFile();
	}
	
	
}
