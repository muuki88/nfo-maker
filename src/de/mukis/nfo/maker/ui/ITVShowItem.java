package de.mukis.nfo.maker.ui;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.mukis.nfo.maker.model.Episodedetails;
import de.mukis.nfo.maker.model.TVShow;

public interface ITVShowItem {

	public class TVShowItem implements ITVShowItem {
		
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public class Show extends TVShowItem {

		private TVShow tvShow;
		private final Map<Number, Season> seasons = new HashMap<>();
		private final Path path;

		public Show(Path path, String title) {
			this.path = path;
			this.tvShow = new TVShow();
			tvShow.setTitle(title);
		}

		public String getTitle() {
			return tvShow.getTitle();
		}

		public void setTitle(String title) {
			tvShow.setTitle(title);
		}
		
		public Path getPath() {
			return path;
		}
		
		public TVShow getTvShow() {
			return tvShow;
		}
		
		public Season get(Number season) {
			if (seasons.containsKey(season))
				return seasons.get(season);
			return new Season(season, this);
		}

		public Season put(Number season, Season value) {
			return seasons.put(season, value);
		}

		public Map<Number, Season> getSeasons() {
			return seasons;
		}

	}

	public class Season extends TVShowItem {

		private Number season;
		private final Map<Number, Episode> episodes = new HashMap<>();
		private final Show show;

		public Season(Number season, Show show) {
			this.show = show;
			this.season = season;
			show.put(season, this);
		}

		public Number getSeason() {
			return season;
		}
		
		public Show getShow() {
			return show;
		}

		public Episode get(Number episode) {
			return episodes.get(episode);
		}

		public Episode put(Number episode, Episode value) {
			return episodes.put(episode, value);
		}

		public Map<Number, Episode> getEpisodes() {
			return episodes;
		}

	}

	public class Episode extends TVShowItem {

		private final Episodedetails details;
		private final Season season;
		private Path path;

		public Episode(Path path, Episodedetails details, Season season) {
			this.path = path;
			this.details = details;
			this.season = season;
			season.put(details.getEpisode(), this);
		}

		public Episodedetails getDetails() {
			return details;
		}
		
		public Season getSeason() {
			return season;
		}
		
		public Path getPath() {
			return path;
		}
	}
}
