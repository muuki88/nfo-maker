package de.mukis.nfo.maker.ui;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.mukis.nfo.maker.model.MusicVideo;

public interface IMusicVideoItem {

	public class MusicVideoItem implements IMusicVideoItem {

		private final Map<String, Artist> artists;

		public MusicVideoItem() {
			artists = new HashMap<>();
		}

		public Map<String, Artist> getArtists() {
			return artists;
		}

		public Artist put(String key, Artist value) {
			return artists.put(key, value);
		}

		public Artist get(String artist) {
			Artist value = artists.get(artist);
			if (value == null) {
				value = new Artist(artist);
				put(artist, value);
			}
			return value;
		}

	}

	public class Artist implements IMusicVideoItem {

		private final String name;

		private final Map<String, Video> videos;

		public Artist(String name) {
			this.name = name;
			videos = new HashMap<>();
		}

		public String getName() {
			return name;
		}

		public Video put(String key, Video value) {
			return videos.put(key, value);
		}

		public Map<String, Video> getVideos() {
			return videos;
		}

	}

	public class Video implements IMusicVideoItem {

		private final Path file;
		private final MusicVideo video;
		private final Artist artist;

		public Video(Path file, MusicVideo video, String artist) {
			this(file, video, new Artist(artist));
		}

		public Video(Path file, MusicVideo video, Artist artist) {
			this.file = file;
			this.video = video;
			this.artist = artist;
			artist.put(video.getTitle(), this);
		}

		public Path getFile() {
			return file;
		}

		public MusicVideo getVideo() {
			return video;
		}

		public Artist getArtist() {
			return artist;
		}
	}
}
