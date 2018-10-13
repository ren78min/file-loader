package me.mren.fileloader;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WatchEventConsumer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(WatchEventConsumer.class);

	@Autowired
	@Qualifier("filepoller.eventBuffer")
	private BlockingQueue<WatchEvent<Path>> eventQueue;

	@Autowired
	private FileTreeWalker fileTreeWalker;

	@Autowired
	private Path baseDir;

	private volatile boolean running = true;

	@Override
	public void run() {
		try {
			while (running) {
				final WatchEvent<Path> watchEvent = eventQueue.take();
				final Path file = watchEvent.context();
				final Path resolved = baseDir.resolve(file);
				LOGGER.debug("Received [{}]", resolved);
				final Path parent = resolved.getParent();
				LOGGER.debug("Processing [{}]", parent);
				fileTreeWalker.process(parent);
			}
		} catch (final InterruptedException interruptedException) {
			LOGGER.info("Interrupted.", interruptedException);
			Thread.currentThread().interrupt();
		}
	}

	void shutdown() {
		running = false;
	}

}
