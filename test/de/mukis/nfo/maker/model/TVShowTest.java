package de.mukis.nfo.maker.model;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

public class TVShowTest {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	@Before
	public void setUp() throws Exception {
		context = JAXBContext.newInstance(TVShow.class);
		marshaller = context.createMarshaller();
		unmarshaller = context.createUnmarshaller();
	}
	
	@Test
	public void testAll() throws MalformedURLException {
		TVShow tvShow = new TVShow();
		tvShow.setTitle("Scrubs");
		
		//Thumb
		Thumb thumb = new Thumb();
		thumb.setSeason(1);
		thumb.setType("season");
		thumb.setUrl(new URL("http://thetvdb.com/banners/seasons/73388-1.jpg"));
		tvShow.getThumbs().add(thumb);
		tvShow.getThumbs().add(thumb);
		
		//Actor
		Actor actor = new Actor();
		actor.setName("Will Smith");
		actor.setRole("J");
		actor.setThumb(thumb);
		tvShow.getActors().add(actor);
		
		JAXB.marshal(tvShow, System.out);
	}

}
