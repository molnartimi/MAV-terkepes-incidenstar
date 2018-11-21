package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.IncidentInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.Report;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.SelectStationInfo;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto.StationHeader;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.Incident;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayLine;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.IncidentRepository;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayStopRepository;

@Service
public class RailwayStopService {
	@Autowired
	RailwayStopRepository railwayStopRepository;
	@Autowired
	IncidentRepository incidentRepository;

	/**
	 * Returns a list of all stations.
	 * 
	 * @return
	 */
	public List<SelectStationInfo> getSelectStationInfo() {
		List<SelectStationInfo> result = new ArrayList<SelectStationInfo>();

		List<RailwayStop> everyStop = railwayStopRepository.findAll();
		for (RailwayStop stop : everyStop) {
			StationHeader station = new StationHeader(stop.getId(), stop.getName());
			SelectStationInfo stationInfo = new SelectStationInfo(station);

			for (RailwayLine line : stop.getRailwayLines()) {
				for (RailwayStop neighbour : line.getRailwayStops()) {
					stationInfo.addStation(new StationHeader(neighbour.getId(), neighbour.getName()));
				}
			}
			result.add(stationInfo);
		}

		return result;
	}

	/**
	 * Returns a list of IncidentInfo object based on system requirements:
	 * 
	 * If both stationIds are filled, it has to return the incidents occured on the
	 * path between them, otherwise the incidents occured on that single station.
	 * 
	 * @param id1
	 * @param id2
	 * @param from
	 * @param to
	 * @return
	 */
	public List<IncidentInfo> getIncidentInfo(Integer id1, Integer id2, String from, String to) {
		List<IncidentInfo> incidentInfos = new ArrayList<IncidentInfo>();

		Date fromDate = null;
		Date toDate = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			fromDate = from == null ? sdf.parse("2000-01-01") : sdf.parse(from);
			toDate = to == null ? new Date() : sdf.parse(to);

			Calendar c = Calendar.getInstance();
			c.setTime(toDate);
			c.add(Calendar.DATE, 1);
			toDate = c.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (id2 == null) {
			RailwayStop stop = railwayStopRepository.findById(id1)
					.orElseThrow(() -> new RuntimeException("Id cannot be found."));

			incidentInfos.add(fetchIncidentInfo(stop, fromDate, toDate));
		} else {
			RailwayStop stop1 = railwayStopRepository.findById(id1)
					.orElseThrow(() -> new RuntimeException("Id cannot be found."));
			RailwayStop stop2 = railwayStopRepository.findById(id2)
					.orElseThrow(() -> new RuntimeException("Id cannot be found."));

			incidentInfos.add(fetchIncidentInfo(stop1, fromDate, toDate));
			incidentInfos.add(fetchIncidentInfo(stop2, fromDate, toDate));

			for (RailwayStop stop : findCommonRailwayLine(stop1, stop2).getRailwayStops()) {
				if (stop.getLongitude() > Math.min(stop1.getLongitude(), stop2.getLongitude())
						&& stop.getLongitude() < Math.max(stop1.getLongitude(), stop2.getLongitude())
						&& stop.getLatitude() > Math.min(stop1.getLatitude(), stop2.getLatitude())
						&& stop.getLatitude() < Math.max(stop1.getLatitude(), stop2.getLatitude())) {

					incidentInfos.add(fetchIncidentInfo(stop, fromDate, toDate));
				}
			}
		}

		return (List<IncidentInfo>) incidentInfos.stream().filter(incidentInfo -> !incidentInfo.getReports().isEmpty())
				.collect(Collectors.toList());
	}

	/**
	 * Returns incidents regarding a given station. Incidents must have a
	 * publication date between from and toDate parameters.
	 * 
	 * @param stop
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private IncidentInfo fetchIncidentInfo(RailwayStop stop, Date fromDate, Date toDate) {
		IncidentInfo incidentInfo = new IncidentInfo(new StationHeader(stop.getId(), stop.getName()),
				stop.getLongitude(), stop.getLatitude(), new ArrayList<Report>());

		List<Incident> incidents = incidentRepository
				.findByMentionedRailwayStops_IdAndPublicationDateBetween(stop.getId(), fromDate, toDate);

		for (Incident in : incidents) {
			Report report = new Report(in.getId(), in.getTitle(), in.getLink(), in.getPublicationDate());
			incidentInfo.getReports().add(report);
		}
		return incidentInfo;
	}

	/**
	 * Returns the first railway line that has both station1 and station2 assigned
	 * to it.
	 * 
	 * @param stop1
	 * @param stop2
	 * @return
	 */
	private RailwayLine findCommonRailwayLine(RailwayStop stop1, RailwayStop stop2) {
		return stop1.getRailwayLines().stream().filter(line -> line.getRailwayStops().contains(stop2)).findFirst()
				.orElseThrow(() -> new RuntimeException("Stops don't have a common line."));
	}

}
