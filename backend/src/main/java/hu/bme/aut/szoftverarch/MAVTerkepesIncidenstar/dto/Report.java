package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Report {	
	private Integer id;
	
	private String title;
	
	private String link;
	
    private Date publicationDate;
    
    public Report(Integer id, String title, String link, Date publicationDate) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.publicationDate = publicationDate;
	}
}
