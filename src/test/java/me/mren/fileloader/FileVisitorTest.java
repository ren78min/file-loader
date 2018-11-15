package me.mren.fileloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileVisitorTest {

	private FileVisitor fileVisitor;

	private FileVisitResult result;

	private long now;

	private Set<FileView> files;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Path path;

	@Mock
	private BasicFileAttributes basicFileAttributes;

	@Before
	public void before() {
		now = new Date().getTime();
		files = new HashSet<>();
	}

	@Test
	public void testOneFileVisited() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();
		givenPath();
		givenBasicFileAttributes();

		whenVisitFileSucceded();

		thenResultIsContinue();
		thenFilesVisited(1);
		thenFileIsExpected();
	}

	@Test
	public void testNoFileVisited() throws InterruptedException, IOException, IllegalAccessException {
		givenANewFileVisitor();

		whenVisitFileFailed();

		thenResultIsContinue();
		thenFilesVisited(0);
	}

	private void givenANewFileVisitor() {
		fileVisitor = new FileVisitor(files);
	}

	private void givenPath() {
		Mockito.when(path.toAbsolutePath().toString()).thenReturn("thepath");
	}

	private void givenBasicFileAttributes() {
		Mockito.when(basicFileAttributes.lastModifiedTime()).thenReturn(FileTime.fromMillis(now));
		Mockito.when(basicFileAttributes.size()).thenReturn(now);
	}

	private void whenVisitFileSucceded() throws IOException {
		result = fileVisitor.visitFile(path, basicFileAttributes);
	}

	private void whenVisitFileFailed() throws IOException {
		result = fileVisitor.visitFileFailed(path, new IOException());
	}

	private void thenResultIsContinue() {
		assertEquals(FileVisitResult.CONTINUE, result);
	}

	private void thenFilesVisited(final int count) {
		assertEquals(count, files.size());
	}

	private void thenFileIsExpected() {
		final FileView expected = new FileView(path.toAbsolutePath().toString(), now, new Date(now));
		assertTrue(files.contains(expected));
	}

}
