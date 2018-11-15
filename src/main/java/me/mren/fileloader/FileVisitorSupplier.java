package me.mren.fileloader;

import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
class FileVisitorSupplier implements Function<Set<FileView>, FileVisitor> {

	@Override
	public FileVisitor apply(final Set<FileView> visitResultViews) {
		return new FileVisitor(visitResultViews);
	}

}
