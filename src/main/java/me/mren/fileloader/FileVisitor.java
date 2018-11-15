package me.mren.fileloader;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileVisitor extends SimpleFileVisitor<Path> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileVisitor.class);

	private final Set<FileView> resultViews;

	FileVisitor(final Set<FileView> resultViews) {
		this.resultViews = resultViews;
	}

	@Override
	public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) throws IOException {
		resultViews.add(createVisitResultView(path, attrs));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(final Path path, final IOException ioException) throws IOException {
		LOGGER.warn("Visit file failed.", ioException);
		return FileVisitResult.CONTINUE;
	}

	private FileView createVisitResultView(final Path path, final BasicFileAttributes attrs) {
		final FileTime fileTime = attrs.lastModifiedTime();
		return new FileView(path.toAbsolutePath().toString(), attrs.size(), new Date(fileTime.toMillis()));
	}
}
