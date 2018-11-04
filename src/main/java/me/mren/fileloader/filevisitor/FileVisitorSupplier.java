package me.mren.fileloader.filevisitor;

import java.util.Set;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class FileVisitorSupplier implements Function<Set<VisitResultView>, FileVisitor> {

	@Override
	public FileVisitor apply(final Set<VisitResultView> visitResultViews) {
		return new FileVisitor(visitResultViews);
	}

}
