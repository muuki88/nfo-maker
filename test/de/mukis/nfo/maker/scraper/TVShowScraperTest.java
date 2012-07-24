package de.mukis.nfo.maker.scraper;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

public class TVShowScraperTest {
	
	private TVShowScraper scraper;
	
	private String f01 = "scrubs - s01e01 - my title.avi";
	private String f02 = "ScrUbs - season 02 episode 02 - my title";
	
	@Before
	public void setUp() throws Exception {
		Path searchPath = Paths.get(System.getProperty("user.home"), "Development", "workspace_e4", "nfo-maker", "test", "scrubs");
		scraper = new TVShowScraper(searchPath);
	}

	@Test
	public void testFindSeason() {
		Optional<Integer> s01 = scraper.findSeason(f01);
		assertEquals(new Integer(1), s01.get());
		
		Optional<Integer> s02 = scraper.findSeason(f02);
		assertEquals(new Integer(2), s02.get());
	}

	@Test
	public void testFindEpisode() {
		Optional<Integer> s01 = scraper.findEpisode(f01);
		assertEquals(new Integer(1), s01.get());
		
		Optional<Integer> s02 = scraper.findEpisode(f02);
		assertEquals(new Integer(2), s02.get());
	}

	@Test
	public void testFindEpisodeName() {
		String n01 = scraper.findEpisodeName(f01, "scrubs");
		assertEquals("mytitle", n01);
		
		String n02 = scraper.findEpisodeName(f02, "scrubs");
		assertEquals("mytitle", n02);
	}

}
