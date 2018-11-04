package me.mren.fileloader.filevisitor;

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

	private final Set<VisitResultView> resultViews;

	public FileVisitor(final Set<VisitResultView> resultViews) {
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

	private VisitResultView createVisitResultView(final Path file, final BasicFileAttributes attrs) {
		final FileTime creationTime = attrs.creationTime();
		final FileTime lastModifiedTime = attrs.lastModifiedTime();
		final FileTime fileTime = creationTime.compareTo(lastModifiedTime) > 0 ? creationTime : lastModifiedTime;
		return new VisitResultView(file.toAbsolutePath().toString(), attrs.size(), new Date(fileTime.toMillis()));
	}
}
