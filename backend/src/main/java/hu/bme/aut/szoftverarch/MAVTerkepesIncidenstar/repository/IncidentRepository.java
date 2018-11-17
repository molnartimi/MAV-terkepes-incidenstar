package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model.Incident;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Integer> {
	@Override
	List<Incident> findAll();
	
	Incident findOneByTitle(String title);
}