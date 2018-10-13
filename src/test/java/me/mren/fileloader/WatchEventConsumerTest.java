package me.mren.fileloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WatchEventConsumerTest {

	private WatchEventConsumer watchEventConsumer;

	@Mock
	private BlockingQueue<WatchEvent<Path>> eventQueue;

	@Mock
	private FileTreeWalker fileTreeWalker;

	@Mock
	private WatchEvent<Path> watchEvent;

	@Mock
	private Path file, baseDir, resolved, parent;

	@Test
	public void whenWatchEventConsumerRunsThenProcessAllSteps()
			throws IOException, InterruptedException, IllegalAccessException {
		givenANewWatchEventConsumer();
		givenFileTreeWalkerIsInitialized();
		givenEventQueueHasOneEvent();
		givenWatchEventContextIsAFile();
		givenBaseDirCanResolveFile();
		givenResolvedFileHasAParent();

		whenWatchEventConsumerRun();

		thenFileTreeWalkerIsCalled();
	}

	private void givenANewWatchEventConsumer() {
		watchEventConsumer = new WatchEventConsumer();
	}

	private void givenFileTreeWalkerIsInitialized() throws IllegalAccessException {
		FieldUtils.writeDeclaredField(watchEventConsumer, "fileTreeWalker", fileTreeWalker, true);
	}

	private void givenEventQueueHasOneEvent() throws InterruptedException, IllegalAccessException {
		Mockito.when(eventQueue.take()).thenAnswer(invocation -> {
			watchEventConsumer.shutdown();
			return watchEvent;
		});
		FieldUtils.writeDeclaredField(watchEventConsumer, "eventQueue", eventQueue, true);
	}

	private void givenWatchEventContextIsAFile() {
		Mockito.when(watchEvent.context()).thenReturn(file);
	}

	private void givenBaseDirCanResolveFile() throws IllegalAccessException {
		Mockito.when(baseDir.resolve(file)).thenReturn(resolved);
		FieldUtils.writeDeclaredField(watchEventConsumer, "baseDir", baseDir, true);
	}

	private void givenResolvedFileHasAParent() {
		Mockito.when(resolved.getParent()).thenReturn(parent);
	}

	private void whenWatchEventConsumerRun() {
		watchEventConsumer.run();
	}

	private void thenFileTreeWalkerIsCalled() throws IllegalAccessException {
		Mockito.verify(fileTreeWalker, new Times(1)).process(parent);
	}

}
