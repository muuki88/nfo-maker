package de.mukis.nfo.maker.model;

import java.net.URL;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Represents a thumb element. For example:
 * <pre>
 * {@code
 *  <thumb type="season" season="1">http://thetvdb.com/banners/seasons/73388-1.jpg</thumb>
 * }
 * </pre>
 * 
 * @author Nepomuk Seiler
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "thumb")
public class Thumb {

	@XmlValue
	private URL url;
	
	@XmlAttribute
	private String type;
	
	@XmlAttribute
	private Integer season;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
	
	
}

