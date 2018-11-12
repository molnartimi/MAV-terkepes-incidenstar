package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayLine;
import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.RailwayStop;

@Repository
public interface RailwayLineRepository extends JpaRepository<RailwayLine, Integer> {
	@Override
	List<RailwayLine> findAll();
	
	RailwayLine findOneById(String id);
}