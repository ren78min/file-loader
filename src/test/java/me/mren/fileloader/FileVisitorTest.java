package me.mren.fileloader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileVisitorTest {

	private FileVisitor fileVisitor;

	private FileVisitResult result;

	@Mock
	private BlockingQueue<Path> fileQueue;

	@Mock
	private Path file;

	@Test
	public void whenVisitFileThenFileQueued() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();
		givenFileQueueIsInitialized();

		whenVisitFile();

		thenFileIsQueued();
		thenResultIsContinue();
	}

	@Test
	public void whenVisitFileFailedThenContinue() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();

		whenVisitFileFailed();

		thenResultIsContinue();
	}

	private void givenANewFileVisitor() {
		fileVisitor = new FileVisitor();
	}

	private void givenFileQueueIsInitialized() throws IllegalAccessException {
		FieldUtils.writeDeclaredField(fileVisitor, "fileQueue", fileQueue, true);
	}

	private void whenVisitFile() throws IOException {
		result = fileVisitor.visitFile(file, null);
	}

	private void whenVisitFileFailed() throws IOException {
		result = fileVisitor.visitFileFailed(file, new IOException());
	}

	private void thenFileIsQueued() throws InterruptedException {
		Mockito.verify(fileQueue, new Times(1)).put(file);
	}

	private void thenResultIsContinue() {
		assertEquals(FileVisitResult.CONTINUE, result);
	}

}
