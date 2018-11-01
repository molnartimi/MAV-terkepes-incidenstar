package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "vasutallomas")
public class RailwayStop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private Long latitude;
	
	private Long longitude;
	
	@ManyToOne
    @JoinColumn(name="vasutvonal_id", nullable=false)
	private List<RailwayLine> railwayLines;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "elerheto_allomasok", joinColumns = @JoinColumn(name = "honnan_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "hova_id", referencedColumnName = "id"))
	private List<RailwayStop> accessibleRailwayStops;
	
	

}
