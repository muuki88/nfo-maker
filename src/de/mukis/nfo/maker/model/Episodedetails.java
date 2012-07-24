package de.mukis.nfo.maker.model;

import java.beans.Transient;
import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "episodedetails")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Episodedetails implements Comparable<Episodedetails> {

	private final StringProperty title = new SimpleStringProperty("");
	private final IntegerProperty season = new SimpleIntegerProperty(-1);
	private final IntegerProperty episode = new SimpleIntegerProperty(-1);

	/* ======= TRANSIENT ====== */
	private Path path;

	@Transient
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	/* ======================== */

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String value) {
		title.set(value);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public int getSeason() {
		return season.get();
	}

	public void setSeason(int value) {
		season.set(value);
	}

	public IntegerProperty seasonProperty() {
		return season;
	}

	public int getEpisode() {
		return episode.get();
	}

	public void setEpisode(int value) {
		episode.set(value);
	}

	public IntegerProperty episodeProperty() {
		return episode;
	}

	@Override
	public int compareTo(Episodedetails o) {
		if (season.greaterThan(o.seasonProperty()).get())
			return 1;
		else if (!season.greaterThanOrEqualTo(o.seasonProperty()).get())
			return -1;
		else { //Equal season
			if (episode.greaterThan(o.episodeProperty()).get())
				return 1;
			else if (!episode.greaterThanOrEqualTo(o.episodeProperty()).get())
				return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Episodedetails [");
		if (getPath() != null) {
			builder.append("getPath()=");
			builder.append(getPath());
			builder.append(", ");
		}
		if (getTitle() != null) {
			builder.append("getTitle()=");
			builder.append(getTitle());
			builder.append(", ");
		}
		builder.append("getSeason()=");
		builder.append(getSeason());
		builder.append(", getEpisode()=");
		builder.append(getEpisode());
		builder.append("]");
		return builder.toString();
	}

}
/*
 * <episodedetails> <title>My TV Episode</title> <rating>10.00</rating>
 * <season>2</season> <episode>1</episode> <plot>he best episode in the
 * world</plot>
 * <thumb>http://thetvdb.com/banners/episodes/164981/2528821.jpg</thumb>
 * <playcount>0</playcount> <lastplayed></lastplayed> <credits>Writer</credits>
 * <director>Mr. Vision</director> <aired>2000-12-31</aired>
 * <premiered>2010-09-24</premiered> <studio>Production studio or
 * channel</studio> <mpaa>MPAA certification</mpaa> <epbookmark>200</epbookmark>
 * <!-- For media files containing multiple episodes, where value is the time
 * where the next episode begins in seconds --> <displayseason>3</displayseason>
 * <!-- For TV show specials, determines how the episode is sorted in the series
 * --> <displayepisode>4096</displayepisode> <actor> <name>Little Suzie</name>
 * <role>Pole Jumper/Dancer</role> </actor> <fileinfo> <streamdetails> <audio>
 * <channels>6</channels> <codec>ac3</codec> </audio> <video>
 * <aspect>1.778</aspect> <codec>h264</codec>
 * <durationinseconds>587</durationinseconds> <height>720</height>
 * <language>eng</language> <longlanguage>English</longlanguage>
 * <scantype>Progressive</scantype> <width>1280</width> </video>
 * </streamdetails> </fileinfo> </episodedetails>
 */