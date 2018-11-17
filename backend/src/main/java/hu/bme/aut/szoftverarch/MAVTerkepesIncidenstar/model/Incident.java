package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "incident")
public class Incident {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
	private String link;
	
    private Date publicationDate;

	@OneToMany
	@JoinTable(name = "incident_lines", 
			joinColumns = @JoinColumn(name = "incident_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "railway_line_id", referencedColumnName = "id"))
    private List<RailwayLine> mentionedRailwayLines = new ArrayList<RailwayLine>();

	@OneToMany
	@JoinTable(name = "incident_stops", 
			joinColumns = @JoinColumn(name = "incident_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "railway_stop_id", referencedColumnName = "id"))
    private List<RailwayStop> mentionedRailwayStops = new ArrayList<RailwayStop>();
}
