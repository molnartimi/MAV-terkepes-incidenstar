package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayLine;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayLineRepository;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayStopRepository;

@Service
public class DbInitService {

	@Autowired
	RailwayLineRepository railwayLineRepository;
	@Autowired
	RailwayStopRepository railwayStopRepository;

	public void readEntitiesFromFile() {		
		try {
			readRailwayLines();
			readRailwayStops();
			bindRailwayStopsToLines();
			linkStations();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void readRailwayLines() throws FileNotFoundException {
		Scanner scanner;					
		scanner = new Scanner(new File("./src/main/resources/static/vasutvonalak.csv"));
		scanner.useDelimiter(System.getProperty("line.separator"));
		
		// ignore first line
		scanner.next();
		
		while (scanner.hasNext()) {
			String[] lineArgs = scanner.next().split(";");
			
			RailwayLine newLine = new RailwayLine();
			newLine.setId(lineArgs[0]);
			newLine.setName(lineArgs[1]);
			
			railwayLineRepository.save(newLine);
		}		
		
		scanner.close();
	}	
	
	private void readRailwayStops() throws FileNotFoundException {
		Scanner scanner;					
		scanner = new Scanner(new File("./src/main/resources/static/allomasok.csv"));
		scanner.useDelimiter(System.getProperty("line.separator"));
		
		// ignore first line
		scanner.next();
		
		while (scanner.hasNext()) {
			String[] lineArgs = scanner.next().split(";");
			
			RailwayStop newStop = new RailwayStop();
			newStop.setGeoId(Integer.parseInt(lineArgs[0]));
			newStop.setCity(lineArgs[1]);
			newStop.setName(lineArgs[2]);
			newStop.setLatitude(Double.parseDouble(lineArgs[3]));
			newStop.setLongitude(Double.parseDouble(lineArgs[4]));
			
			railwayStopRepository.save(newStop);
		}		
		
		scanner.close();
	}
	
	private void bindRailwayStopsToLines() throws FileNotFoundException {
		Scanner scanner;					
		scanner = new Scanner(new File("./src/main/resources/static/vasutvonal_allomasok.csv"));
		scanner.useDelimiter(System.getProperty("line.separator"));
		
		// ignore first line
		scanner.next();
		
		while (scanner.hasNext()) {
			String[] lineArgs = scanner.next().split(";");
			
			RailwayLine line = railwayLineRepository.findOneById(lineArgs[0]);			
			RailwayStop stop = railwayStopRepository.findOneByGeoId(Integer.parseInt(lineArgs[2]));
			
			line.getRailwayStops().add(stop);
			stop.getRailwayLines().add(line);
			
			railwayStopRepository.save(stop);
			railwayLineRepository.save(line);
		}		
		
		scanner.close();
	}
	
	/**
	 * TODO: add route string with specified target station to RailwayStop.routes 		
	 */
	private void linkStations() {
		
	}

}
