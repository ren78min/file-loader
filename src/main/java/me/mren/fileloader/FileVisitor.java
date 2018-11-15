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

class FileVisitor extends SimpleFileVisitor<Path> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileVisitor.class);

	private final Set<FileView> resultViews;

	public FileVisitor(final Set<FileView> resultViews) {
		if (resultViews == null) {
			throw new IllegalArgumentException("ResultViews can't be null");
		}
		this.resultViews = resultViews;
	}

	@Override
	public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
		resultViews.add(createVisitResultView(file, attrs));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(final Path file, final IOException ioException) throws IOException {
		LOGGER.warn("Visit file failed.", ioException);
		return FileVisitResult.CONTINUE;
	}

	private FileView createVisitResultView(final Path path, final BasicFileAttributes attrs) {
		final FileTime fileTime = attrs.lastModifiedTime();
		return new FileView(path.toAbsolutePath().toString(), attrs.size(), new Date(fileTime.toMillis()));
	}
}
