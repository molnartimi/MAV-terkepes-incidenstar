package hu.bme.aut.vasutvonalak.VasutvonalLekeredezo;

import java.util.ArrayList;
import java.util.List;

public class Line {
	public final String id;
	public final String name;
	public List<Station> stations;

	public Line(String id, String name) {
		super();
		this.id = id;
		this.name = name;
		stations = new ArrayList<>();
	}
}
