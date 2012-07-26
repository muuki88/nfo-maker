package de.mukis.nfo.maker.scraper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;

import de.mukis.nfo.maker.model.Episodedetails;
import de.mukis.nfo.maker.ui.ITVShowItem;
import de.mukis.nfo.maker.ui.ITVShowItem.Season;
import de.mukis.nfo.maker.ui.ITVShowItem.Show;

public class TVShowScraper {

	private final Path searchPath;
	
	private static final String SEASON_REGEX = "(s(eason)?\\d{2})";
	private static final String EPISODE_REGEX = "(e(pisode)?\\d{2})";
	
	private static final String MEDIA_REGEX = ".mp4$|.mkv$|.avi$|.flv$|.wmv$|.ogv$";

	public TVShowScraper(Path searchPath) {
		this.searchPath = searchPath;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Show findShow() throws IOException {
		final Show show = new ITVShowItem.Show(searchPath, "Unknown");
		
		Files.walkFileTree(searchPath, new SimpleFileVisitor<Path>() {
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				if(dir.equals(searchPath)) {
					if(!findSeason(dir.getFileName().toString()).isPresent()) 
						show.setTitle(dir.getFileName().toString());
				}
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				String filename = file.getFileName().toString();
				
				if(!isVideoFile(filename))
					return FileVisitResult.CONTINUE;
				
				Optional<Integer> season = findSeason(filename)
						.or(findSeason(file.getParent().getFileName().toString())); //check foldername
				
				
				Optional<Integer> episode = findEpisode(filename);
				Episodedetails e = new Episodedetails();
				e.setSeason(season.or(-1));
				e.setEpisode(episode.or(-1));
				e.setTitle(findEpisodeName(filename, show.getTitle()));
				
				Season s = show.get(e.getSeason());
				new ITVShowItem.Episode(file, e, s);
				
				return FileVisitResult.CONTINUE;
			}
		});
		
		return show;
	}
	
	public Optional<Integer> findSeason(String filename) {
		Pattern p = Pattern.compile(SEASON_REGEX);
		Matcher m = p.matcher(clean(filename));
		if(m.find())
			return Optional.of(new Integer(m.group().replaceAll("[a-z]", "")));
		return Optional.absent();
	}
	
	public Optional<Integer>  findEpisode(String filename) {
		Pattern p = Pattern.compile(EPISODE_REGEX);
		Matcher m = p.matcher(clean(filename));
		if(m.find())
			return Optional.of(new Integer(m.group().replaceAll("[a-z]", "")));
		return Optional.absent();

	}
	
	public String findEpisodeName(String filename, String tvshow) {
		String regex = SEASON_REGEX + "|" + EPISODE_REGEX + "|\\.\\w{3}$|[-]|" + ((tvshow != null) ? clean(tvshow) : "");
		return clean(filename).replaceAll(regex, "");
	}
	
	public boolean isVideoFile(String filename) {
		Pattern p = Pattern.compile(MEDIA_REGEX);
		Matcher m = p.matcher(clean(filename));
		if(m.find())
			return true;
		return false;
	}
	
	protected String clean(String filename) {
		return filename.toLowerCase().replaceAll(" ", "");
	}
	
	public Path getSearchPath() {
		return searchPath;
	}
}
