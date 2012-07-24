package de.mukis.nfo.maker.ui;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import de.mukis.nfo.maker.model.Episodedetails;

public interface ITVShowItem {

	public class TVShowItem implements ITVShowItem {
		
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public class Show extends TVShowItem {

		private String name;
		private final Map<Integer, Season> seasons = new HashMap<>();

		public Show(String tvshow) {
			this.name = tvshow;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public Season get(Integer season) {
			if (seasons.containsKey(season))
				return seasons.get(season);
			return new Season(season, this);
		}

		public Season put(Integer season, Season value) {
			return seasons.put(season, value);
		}

		public Map<Integer, Season> getSeasons() {
			return seasons;
		}

	}

	public class Season extends TVShowItem {

		private int season;
		private final Map<Integer, Episode> episodes = new HashMap<>();
		private final Show show;

		public Season(int season, Show show) {
			this.show = show;
			this.season = season;
			show.put(season, this);
		}

		public int getSeason() {
			return season;
		}
		
		public Show getShow() {
			return show;
		}

		public Episode get(Integer episode) {
			return episodes.get(episode);
		}

		public Episode put(Integer episode, Episode value) {
			return episodes.put(episode, value);
		}

		public Map<Integer, Episode> getEpisodes() {
			return episodes;
		}

	}

	public class Episode extends TVShowItem {

		private final Episodedetails details;
		private final Season season;

		public Episode(Path path, Episodedetails details, Season season) {
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
	}
}
