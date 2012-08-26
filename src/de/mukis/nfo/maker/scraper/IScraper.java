package de.mukis.nfo.maker.scraper;

import java.io.IOException;
import java.nio.file.Path;

import com.google.common.base.Optional;

public interface IScraper<T> {

	public void setCurrentDirectory(Path dir);

	public Optional<T> scrap(Path file) throws IOException;
}
