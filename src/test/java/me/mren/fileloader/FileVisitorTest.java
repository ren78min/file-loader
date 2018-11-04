package me.mren.fileloader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.mren.fileloader.filevisitor.FileVisitor;

@RunWith(MockitoJUnitRunner.class)
public class FileVisitorTest {

	private FileVisitor fileVisitor;

	private FileVisitResult result;

	@Mock
	private Path file;

	@Test
	public void whenVisitFileThenFileQueued() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();

		whenVisitFile();

		thenResultIsContinue();
	}

	@Test
	public void whenVisitFileFailedThenContinue() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();

		whenVisitFileFailed();

		thenResultIsContinue();
	}

	private void givenANewFileVisitor() {
		fileVisitor = new FileVisitor(new HashSet<>());
	}

	private void whenVisitFile() throws IOException {
		result = fileVisitor.visitFile(file, null);
	}

	private void whenVisitFileFailed() throws IOException {
		result = fileVisitor.visitFileFailed(file, new IOException());
	}

	private void thenResultIsContinue() {
		assertEquals(FileVisitResult.CONTINUE, result);
	}

}
