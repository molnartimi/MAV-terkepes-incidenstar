package hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.component;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import hu.bme.aut.szoftverarch.MAVTerkepesIncidenstar.service.RssReadService;

@Component
public class RssReadComponent implements ApplicationRunner {
	
	@Autowired
	RssReadService rssReadService;

	/**
	 * Time in milliseconds between two RSS feed reads.
	 */
	private static int period = 1800000;
	
	/**
	 * Schedules an RSS read periodically.
	 * @param args
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				consumeRssFeed("https://www.mavcsoport.hu/mavinform/rss.xml");
			}
		}, 0, period);
	}
	
	/**
	 * Consumes RSS feed from given url.
	 * @param url
	 */
	private void consumeRssFeed(String url) {
		try {
			SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));			
			for (SyndEntry entry : feed.getEntries()) {
				rssReadService.addEntry(entry);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("Last read performed at " + sdf.format(new Date()));
		} catch (IllegalArgumentException | FeedException | IOException e) {
			e.printStackTrace();
		}
	}
}
