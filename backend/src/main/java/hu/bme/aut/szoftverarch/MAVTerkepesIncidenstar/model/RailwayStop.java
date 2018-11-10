package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "railway_stop")
public class RailwayStop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer geoId;
	
	private String city;
	
	private String name;
	
	private Double latitude;
	
	private Double longitude;
	
	@ManyToMany(mappedBy = "railwayStops")
	private List<RailwayLine> railwayLines = new ArrayList<RailwayLine>();
	
	@ElementCollection
	@CollectionTable(name="routes")
	@MapKeyColumn(name="id")
	private Map<RailwayStop, String> routes = new HashMap<RailwayStop, String>();
}
