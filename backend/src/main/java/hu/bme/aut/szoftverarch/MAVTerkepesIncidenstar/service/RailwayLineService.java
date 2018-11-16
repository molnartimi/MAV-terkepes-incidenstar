package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.repository.RailwayLineRepository;

@Service
public class RailwayLineService {

	@Autowired
	RailwayLineRepository railwayLineRepository;

}
