package hu.bme.aut.vasutvonalak.VasutvonalLekeredezo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class App {
	// {Station name: station}
	private static Map<String, Station> allStations;
	// {City name: geonameid of station}
	private static Map<String, Station> validStations;
	private static Scanner scanner;

	public static void main(String[] args) {
		try {
			scanner = new Scanner(System.in);
			allStations = readAllStations();

			List<Line> lines = readLinesFromWeb();

			validStations = new HashMap<>();

			for (Line line: lines) {
				List<String> stationNames = readStationsOfLineFromWeb(line);
				List<Station> stations = createStationsFromNames(stationNames);
				if (stations.size() > 1) {
					line.stations = stations;
					writeLineToCsvFiles(line);
					System.out.println(line.id + " line wroted out successfully.");
				} else {
					System.out.println(line.id + " line removed, has not enough stations.");
				}
			}
			writeValidStationsToCsvFile();

			scanner.close();
			System.out.print("Done everything.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, Station> readAllStations() throws IOException {
		Map<String, Station> allStations = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader("geonames.csv"));

		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			String[] params = line.split(";");
			String geoid = params[0];
			String name = params[1];
			String ascii = params[2];
			Double latitude = Double.valueOf(params[3].replace(",", "."));
			Double longitude = Double.valueOf(params[4].replace(",", "."));
			Station station = new Station(geoid, name, ascii, latitude, longitude);
			allStations.put(name, station);
		}

		br.close();
		return allStations;
	}

	// Load and read railway ids and names from the web
	private static List<Line> readLinesFromWeb() throws IOException {
		List<Line> vonalak = new ArrayList<>();
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
				vonalak.add(new Line(id, name));
			}
		}

		rd.close();
		return vonalak;
	}

	// Load and read station names of a railway from the web
	private static List<String> readStationsOfLineFromWeb(Line vonal) throws IOException {
		List<String> allomasok = new ArrayList<>();
		BufferedReader rd = readBodyOfRequest("http://www.vasutallomasok.hu/index.php?o=vonkep&num=" + vonal.id);
		String line;

		while ((line = rd.readLine()) != null) {
			if (line.contains("<tr class='sorki'>") && line.contains("<b>")) {
				String allomas = line.substring(line.indexOf("<b>") + 3, line.indexOf("</b>"));
				allomasok.add(allomas);
			}
		}

		rd.close();
		return allomasok;
	}

	private static BufferedReader readBodyOfRequest(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}

	// Append the stations of one railway to allomasok_vonalankent.csv
	private static void writeLineToCsvFiles(Line line) throws FileNotFoundException {
		PrintWriter writer = openFileToWriteWithHeader("vasutvonalak.csv", "id;nev\n");
		writer.append(line.id + ";" + line.name + "\n");
		writer.close();

		writer = openFileToWriteWithHeader("vasutvonal_allomasok.csv", "vonalId;sorszam;geoid\n");
		int i = 1;
		for (Station station : line.stations) {
			writer.append(line.id + ";" + (i++) + ";" + station.geoId + "\n");
		}
		writer.close();
	}

	private static void writeValidStationsToCsvFile() throws FileNotFoundException {
		PrintWriter writer = openFileToWriteWithHeader("allomasok.csv", Station.getCsvHeader());
		for (Station station: validStations.values()) {
			writer.append(station.toCsvString());
		}
		writer.close();
	}

	private static PrintWriter openFileToWriteWithHeader(String file, String header) throws FileNotFoundException {
		File lineCsv = new File(file);
		boolean exist = lineCsv.exists();
		PrintWriter writer = new PrintWriter(new FileOutputStream(lineCsv, true));
		if (!exist) writer.append(header);
		return writer;
	}

	private static List<Station> createStationsFromNames(List<String> cityNames) {
		List<Station> stations = new ArrayList<>();

		for (String city : cityNames) {
			if (validStations.containsKey(city)) {
				stations.add(validStations.get(city));
				continue;
			}

			// If new, try stations started with name of the city
			List<String> matchedList = new ArrayList<>();
			for (String station : allStations.keySet()) {
				if (station.indexOf(city) == 0) {
					matchedList.add(station);
				}
			}

			// If nothing found, continue with next city
			if (matchedList.size() == 0) {
				continue;

			// If exactly 1 found, perfect, save it to valid stations and to the actual list
			} else if (matchedList.size() == 1) {
				saveNewValidStation(stations, city, matchedList.get(0));

			// If more matched, try stations started with "city Vasút..." or "city Pálya"
			} else {
				List<String> secondMatch = new ArrayList<>();
				for (String station : matchedList) {
					if (station.indexOf(city + " Vasút") == 0 || station.indexOf(city + " Pálya") == 0) {
						secondMatch.add(station);
					}
				}

				// If this way exactly 1 founded, perfect!
				if (secondMatch.size() == 1) {
					saveNewValidStation(stations, city, secondMatch.get(0));

				// If more founded also this way, user is needed to choose from first matching list
				} else {
					int neededIdx = getValidIdxFromUser(city, matchedList);

					if (neededIdx == 0) {
						continue;
					} else {
						saveNewValidStation(stations, city, matchedList.get(neededIdx - 1));
					}
				}
			}
		}
		return stations;
	}

	private static int getValidIdxFromUser(String city, List<String> matchedList) {
		System.out.println("Found " + matchedList.size() + " station for city " + city + ":");
		int idx = 1;
		for (String station : matchedList) {
			System.out.println(idx++ + ": " + station);
		}
		System.out.print("Type valid idx: ");
		int neededIdx = scanner.nextInt();

		while (neededIdx > matchedList.size() || neededIdx < 0) {
			System.out.print("Invalid number: ");
			neededIdx = scanner.nextInt();
		}
		return neededIdx;
	}

	private static void saveNewValidStation(List<Station> stations, String city, String stationName) {
		Station station = allStations.get(stationName);
		station.shortName = city;
		validStations.put(city, station);
		stations.add(station);
	}
}
