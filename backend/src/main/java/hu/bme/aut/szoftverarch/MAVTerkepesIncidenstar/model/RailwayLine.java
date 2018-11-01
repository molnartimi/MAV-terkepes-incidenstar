package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "railway_line")
public class RailwayLine {

	@Id
	private String id;
	
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "railway_line_stops",
			joinColumns = @JoinColumn(name = "railway_line_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "railway_stop_id", referencedColumnName = "id"))
	private List<RailwayStop> railwayStops = new ArrayList<RailwayStop>();

}
