package me.mren.fileloader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadFileControllerImpl implements LoadFileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileVisitor.class);

	@Autowired
	private FileVisitorSupplier fileVisitorSupplier;

	@Override
	@GetMapping("/loadFromPath")
	public Set<FileView> loadFromPath(@RequestParam final String directory) {
		final Set<FileView> result = new HashSet<>();
		try {
			final Path baseDir = Paths.get(directory);
			Files.walkFileTree(baseDir, fileVisitorSupplier.apply(result));
		} catch (final Exception ioException) {
			LOGGER.warn("IO Error.", ioException);
		}
		return result;
	}

}
