package de.mukis.nfo.maker.scraper;

import java.io.IOException;
import java.nio.file.Path;

import com.google.common.base.Optional;

import de.mukis.nfo.maker.model.MusicVideo;

/**
 * Trying to identify music files with standard pattern </p>
 * <code>artist - title</code> </p>
 * 
 * @author muki
 * 
 */
public class MusicVideoScraper implements IScraper<MusicVideo> {

	private Path currentDir;

	@Override
	public void setCurrentDirectory(Path dir) {
		this.currentDir = dir;
	}

	@Override
	public Optional<MusicVideo> scrap(Path file) throws IOException {
		String filename = file.getFileName().toString();
		if (!Scraper.isVideoFile(filename))
			return Optional.absent();
		String[] split = filename.split("-");
		MusicVideo video = new MusicVideo();
		video.setFile(file);
		if (split.length == 0) {
			video.setTitle(filename);
		} else if (split.length == 1) {
			video.setArtist(currentDir.getFileName().toString().trim());
			video.setTitle(Scraper.removeFileEnding(split[0]).trim());
		} else if (split.length == 2) {
			video.setArtist(split[0].trim());
			video.setTitle(Scraper.removeFileEnding(split[1]).trim());
		} else {
			// Guessings here
			video.setArtist(split[0].trim());
			video.setTitle(Scraper.removeFileEnding(split[1]).trim());
		}

		return Optional.of(video);
	}

}
