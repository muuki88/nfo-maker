package de.mukis.nfo.maker.scraper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Optional;

public class Scraper {

	public static final String MEDIA_VIDEO_REGEX = ".mp4$|.mkv$|.avi$|.flv$|.wmv$|.ogv$|.mpeg$";
	public static final String MEDIA_MUSIC_REGEX = ".mp3$|.wav$|.ogg$|.acc$";

	public static <T> T find(Path searchPath, IScraper<T> scraper, Class<T> model) {
		return null;
	}

	public static <T> List<T> findList(Path searchPath, final IScraper<T> scraper, Class<T> model) throws IOException {
		final ArrayList<T> returns = new ArrayList<>();
		scraper.setCurrentDirectory(searchPath);
		Files.walkFileTree(searchPath, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				scraper.setCurrentDirectory(dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Optional<T> result = scraper.scrap(file);
				if (result.isPresent())
					returns.add(result.get());
				return FileVisitResult.CONTINUE;
			}
		});
		return returns;
	}

	public static String clean(String filename) {
		return filename.toLowerCase().replaceAll(" ", "");
	}

	public static boolean isVideoFile(String filename) {
		Pattern p = Pattern.compile(MEDIA_VIDEO_REGEX);
		Matcher m = p.matcher(clean(filename));
		if (m.find())
			return true;
		return false;
	}

	public static String removeFileEnding(String filename) {
		return filename.replaceAll(MEDIA_VIDEO_REGEX + "|" + MEDIA_MUSIC_REGEX, "");
	}
}
