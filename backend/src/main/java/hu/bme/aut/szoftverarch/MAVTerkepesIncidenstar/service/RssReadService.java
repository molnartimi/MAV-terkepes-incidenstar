package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.Incident;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayLine;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.IncidentRepository;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayLineRepository;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayStopRepository;

@Service
public class RssReadService {
	
	@Autowired
	RailwayLineRepository railwayLineRepository;	
	@Autowired
	RailwayStopRepository railwayStopRepository;
	@Autowired
	IncidentRepository incidentRepository;
	
	private List<RailwayLine> everyLine;
	private List<RailwayStop> everyStop;
	
	/**
	 * Inserts a new entry into the database (if it does not exist yet)
	 * @param entry
	 */
	public void addEntry(SyndEntry entry) {
		Incident alreadyExistingIncident = incidentRepository.findOneByTitle(entry.getTitle());
		if (alreadyExistingIncident != null) return;		
		
		if (everyLine == null) everyLine = railwayLineRepository.findAll();
		if (everyStop == null) everyStop = railwayStopRepository.findAll();
		
		Incident incident = new Incident();
		
		incident.setTitle(entry.getTitle());
		incident.setLink(entry.getLink());
		incident.setPublicationDate(entry.getPublishedDate());
		incident.setMentionedRailwayLines(new ArrayList<RailwayLine>());
		incident.setMentionedRailwayStops(new ArrayList<RailwayStop>());
		
		if (incident.getTitle().contains("vonal")) {
			assignRailwayLines(incident);
		} else {
			assignRailwayStops(incident);
		}
		
		if (!incident.getMentionedRailwayLines().isEmpty() || !incident.getMentionedRailwayStops().isEmpty()) {
			incidentRepository.save(incident);
		}
	}
	
	/**
	 * Assigns railwayLine(s) to an incident based on its title content
	 * @param incident
	 */
	private void assignRailwayLines(Incident incident) {
		List<RailwayLine> candidates = new ArrayList<RailwayLine>();
		
		for (RailwayLine line : everyLine) {			
			String[] lineIdentifiers = line.getName().replaceAll("\\s+","").toLowerCase().split("-");				
			
			int cnt = 0;
			for (String city : lineIdentifiers) {
				if (incident.getTitle().toLowerCase().contains(city)) {
					if (!candidates.contains(line)) {
						candidates.add(line);						
					}
					cnt++;
				}
			}
			if (cnt > 1) {
				incident.getMentionedRailwayLines().add(line);
			}
		}
		if (incident.getMentionedRailwayLines().isEmpty()) {
			incident.getMentionedRailwayLines().addAll(candidates);
		}
		for (RailwayLine line : incident.getMentionedRailwayLines()) {
			for (RailwayStop stop : line.getRailwayStops()) {
				if (!incident.getMentionedRailwayStops().contains(stop)) {
					incident.getMentionedRailwayStops().add(stop);
				}
			}
		}
	}

	/**
	 * Assigns railwayStop(s) to an incident based on its title content
	 * @param incident
	 */
	private void assignRailwayStops(Incident incident) {
		for (RailwayStop stop : everyStop) {
			if (incident.getTitle().toLowerCase().contains(stop.getCity().toLowerCase())) {
				incident.getMentionedRailwayStops().add(stop);
			}
		}
	}
}
