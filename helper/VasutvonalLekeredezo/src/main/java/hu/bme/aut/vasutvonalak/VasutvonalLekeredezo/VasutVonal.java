package hu.bme.aut.vasutvonalak.VasutvonalLekeredezo;

import java.util.List;

public class VasutVonal {
	public final String id;
	public final String name;
	private List<String> allomasok;
	
	public VasutVonal(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public void setAllomasok(List<String> allomasok) {
		this.allomasok = allomasok;
	}
	
	public List<String> allomasok() {
		return allomasok;
	}
	
}
