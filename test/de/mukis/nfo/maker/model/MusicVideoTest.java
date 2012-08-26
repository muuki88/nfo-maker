package de.mukis.nfo.maker.model;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

public class MusicVideoTest {

	private JAXBContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	@Before
	public void setUp() throws Exception {
		context = JAXBContext.newInstance(MusicVideo.class);
		marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		unmarshaller = context.createUnmarshaller();
	}

	@Test
	public void testMarshalling() throws JAXBException {
		MusicVideo video = new MusicVideo();
		video.setTitle("Sacrifice");
		video.setArtist("Chrisis");
		video.setAlbum("10");
		video.setDirector("Christian Valenta");
		video.setGenre("Metal");
		video.setStudio("Homestudio");
		video.setYear(2010);
		marshaller.marshal(video, System.out);
	}
}
