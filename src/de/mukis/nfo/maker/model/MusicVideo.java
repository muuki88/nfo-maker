package de.mukis.nfo.maker.model;

import java.nio.file.Path;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/*
 <musicvideo>
 <title>Bestsongintheworld</title>
 <artist>Bestartistintheworld</artist>
 <album>Me</album>
 <genre>Pop</genre>
 <runtime>3:20</runtime>
 <plot>Scantly clad women hoing about</plot>
 <year>2000</year>
 <director>and I</director>
 <studio>Ego prod.</studio>
 </musicvideo>*/

/**
 * 
 * @author Nepomuk Seiler
 * 
 */
@XmlRootElement(name = "musicvideo")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "title", "artist", "album", "genre", "runtime", "plot", "year", "director", "studio" })
public class MusicVideo {

	private final StringProperty title = new SimpleStringProperty("");
	private final StringProperty artist = new SimpleStringProperty("");
	private final StringProperty album = new SimpleStringProperty("");
	private String genre;
	private String runtime;
	private String plot;
	private Integer year;
	private String director;
	private String studio;

	private Path file;

	public MusicVideo() {
	}

	public MusicVideo(String title, String artist, String album, String genre) {
		this();
		this.genre = genre;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist);
	}

	public StringProperty artistProperty() {
		return artist;
	}

	public String getAlbum() {
		return album.get();
	}

	public void setAlbum(String album) {
		this.album.set(album);
	}

	public StringProperty albumProperty() {
		return album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	@XmlTransient
	public Path getFile() {
		return file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicVideo [");
		if (artist != null) {
			builder.append("artist=");
			builder.append(artist);
			builder.append(", ");
		}
		if (title != null) {
			builder.append("title=");
			builder.append(title);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicVideo other = (MusicVideo) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (artist == null) {
			if (other.artist != null)
				return false;
		} else if (!artist.equals(other.artist))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
