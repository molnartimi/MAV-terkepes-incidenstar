package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;

@Repository
public interface RailwayStopRepository extends JpaRepository<RailwayStop, Integer> {
	@Override
	List<RailwayStop> findAll();
	
	RailwayStop findOneByGeoId(Integer geoId);
}