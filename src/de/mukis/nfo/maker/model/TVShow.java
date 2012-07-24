package de.mukis.nfo.maker.model;

import java.beans.Transient;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Nepomuk Seiler
 * 
 */
@XmlRootElement(name = "tvshow")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TVShow {

	private Long id;

	private final StringProperty title = new SimpleStringProperty("");
	private Double rating;
	private Integer year;
	private Integer season;
	private Integer episode;
	private Integer displayseason;
	private Integer displayepisode;

	// votes
	private String outline;
	private String plot;
	private String tagline;
	private Integer runtime; // in minutes

	private List<Thumb> thumbs = new ArrayList<>();

	private Fanart fanart;
	private String mpaa;

	/*
	 * <episodeguide> <url>
	 * cache="73388.xml">http://www.thetvdb.com/api/1D62F2F90030
	 * C444/series/73388/all/en.zip</url> </episodeguide>
	 */
	private List episodeguide;

	private String genre;
	private String credits;
	private String directory;
	private String set;

	private Date premiered;
	private Date aired;

	private String studio;
	private String code;
	private URL trailer;

	private List<Actor> actors = new ArrayList<>();

	private List<Artist> artists = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	@Transient
	public final StringProperty titleProperty() {
		return title;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public Integer getEpisode() {
		return episode;
	}

	public void setEpisode(Integer episode) {
		this.episode = episode;
	}

	public Integer getDisplayseason() {
		return displayseason;
	}

	public void setDisplayseason(Integer displayseason) {
		this.displayseason = displayseason;
	}

	public Integer getDisplayepisode() {
		return displayepisode;
	}

	public void setDisplayepisode(Integer displayepisode) {
		this.displayepisode = displayepisode;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	@XmlElement(name = "thumb")
	public List<Thumb> getThumbs() {
		return thumbs;
	}

	public void setThumbs(List<Thumb> thumbs) {
		this.thumbs = thumbs;
	}

	/*
	 * public Thumb getThumb() { return thumb; }
	 * 
	 * public void setThumb(Thumb thumb) { this.thumb = thumb; }
	 */

	public Fanart getFanart() {
		return fanart;
	}

	public void setFanart(Fanart fanart) {
		this.fanart = fanart;
	}

	public String getMpaa() {
		return mpaa;
	}

	public void setMpaa(String mpaa) {
		this.mpaa = mpaa;
	}

	public List getEpisodeguide() {
		return episodeguide;
	}

	public void setEpisodeguide(List episodeguide) {
		this.episodeguide = episodeguide;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public Date getPremiered() {
		return premiered;
	}

	public void setPremiered(Date premiered) {
		this.premiered = premiered;
	}

	public Date getAired() {
		return aired;
	}

	public void setAired(Date aired) {
		this.aired = aired;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public URL getTrailer() {
		return trailer;
	}

	public void setTrailer(URL trailer) {
		this.trailer = trailer;
	}

	@XmlElement(name = "actor")
	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	@XmlElement(name = "artist")
	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

}

/*
 * <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
 */
