package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "incidens")
public class Incident {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String title;
	
    private Date publicationDate;

	@ManyToOne
    @JoinColumn(name="vasutvonal_id", nullable=false)
    private List<RailwayLine> mentionedRailwayLines;

	@ManyToOne
    @JoinColumn(name="vasutallomas_id", nullable=false)
    private List<RailwayStop> mentionedRailwayStops;
}
