package me.mren.fileloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileTreeWalker {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileTreeWalker.class);

	@Autowired
	private FileVisitor fileFinder;

	public void process(final Path parent) {
		try {
			Files.walkFileTree(parent, fileFinder);
		} catch (final IOException ioException) {
			LOGGER.warn("IO Error.", ioException);
		}
	}
}
