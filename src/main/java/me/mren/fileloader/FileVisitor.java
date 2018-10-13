package me.mren.fileloader;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FileVisitor extends SimpleFileVisitor<Path> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileVisitor.class);

	@Autowired
	@Qualifier("fileloader.fileBuffer")
	private BlockingQueue<Path> fileQueue;

	@Override
	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
		try {
			fileQueue.put(file);
			return FileVisitResult.CONTINUE;
		} catch (final InterruptedException interruptedException) {
			LOGGER.info("Interrupted.", interruptedException);
			Thread.currentThread().interrupt();
			return FileVisitResult.TERMINATE;
		}
	}

	@Override
	public FileVisitResult visitFileFailed(final Path file, final IOException ioException) throws IOException {
		LOGGER.warn("Visit file failed.", ioException);
		return FileVisitResult.CONTINUE;

	}
}
