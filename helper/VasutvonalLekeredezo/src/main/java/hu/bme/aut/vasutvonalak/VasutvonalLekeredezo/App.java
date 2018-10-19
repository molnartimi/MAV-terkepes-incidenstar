package hu.bme.aut.vasutvonalak.VasutvonalLekeredezo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {
	
	public static void main(String[] args) {
		try {
			List<VasutVonal> vonalak = vasutVonalakOlvasasa();
			allomasokOlvasasa(vonalak);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<VasutVonal> vasutVonalakOlvasasa()
			throws MalformedURLException, ProtocolException, IOException {
		List<VasutVonal> vonalak = new ArrayList<VasutVonal>();
		BufferedReader rd = readBodyOfRequest("http://www.vasutallomasok.hu/vonalak.php");
		String line;

		// atugorjuk a sorokat amikre nincs szuksesunk
		while ((line = rd.readLine()) != null && !line.contains("Vasútvonalak vonalszám szerint")) {}

		// A "vasutvonalak vonalszam szerint" cimtol a "megszunt vasutvonalak" cimig atnezzuk a sorokat
		while ((line = rd.readLine()) != null && !line.contains("Megszűnt vasútvonalak")) {
			if (line.contains("index.php?o=vonkep&num=")) {
				line = line.substring(line.indexOf("</a>") + 5);
				String id = line.substring(line.indexOf("num=") + 4, line.indexOf("'>"));
				String name = line.substring(line.indexOf("'>") + 2, line.indexOf("</a>"));
				vonalak.add(new VasutVonal(id, name));
			}
		}

		rd.close();
		return vonalak;
	}
	

	private static void allomasokOlvasasa(List<VasutVonal> vonalak) throws MalformedURLException, ProtocolException, IOException {
		for (VasutVonal vonal: vonalak) {
			List<String> allomasok = new ArrayList<String>();
			
			BufferedReader rd = readBodyOfRequest("http://www.vasutallomasok.hu/index.php?o=vonkep&num=" + vonal.id);
			String line;
			
			while ((line = rd.readLine()) != null) {
				if (line.contains("<tr class='sorki'>") && line.contains("<b>")) {
					String allomas = line.substring(line.indexOf("<b>") + 3, line.indexOf("</b>"));
					allomasok.add(allomas);
				}
			}

			vonal.setAllomasok(allomasok);
		}	
	}

	private static BufferedReader readBodyOfRequest(String urlString)
			throws MalformedURLException, IOException, ProtocolException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		return rd;
	}
}
