package me.mren.fileloader;

import java.io.IOException;
import java.nio.file.FileVisitResult;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoadFileControllerImplTest {

	private LoadFileControllerImpl loadFileControllerImpl;

	@Mock
	private FileVisitorSupplier fileVisitorSupplier;

	@Mock
	private FileVisitor fileVisitor;

	@Before
	public void before() {
		loadFileControllerImpl = new LoadFileControllerImpl();
	}

	@Test
	public void testLoadFromPath() throws IllegalAccessException, IOException {
		givenFileVisitorIsFunctionable();
		givenFileVisitorSupplierIsInitialized();

		loadFileControllerImpl.loadFromPath("src/main/java/me/mren/fileloader");

		thenVisitFileCount(5);
		thenVisitFileFailedCount(0);
		thenPreVisitDirectoryCount(2);
		thenPostVisitDirectoryCount(2);
	}

	private void givenFileVisitorSupplierIsInitialized() throws IllegalAccessException {
		Mockito.when(fileVisitorSupplier.apply(Mockito.any())).thenReturn(fileVisitor);
		FieldUtils.writeDeclaredField(loadFileControllerImpl, "fileVisitorSupplier", fileVisitorSupplier, true);
	}

	private void givenFileVisitorIsFunctionable() throws IOException {
		Mockito.when(fileVisitor.preVisitDirectory(Mockito.any(), Mockito.any())).thenReturn(FileVisitResult.CONTINUE);
		Mockito.when(fileVisitor.visitFile(Mockito.any(), Mockito.any())).thenReturn(FileVisitResult.CONTINUE);
		Mockito.when(fileVisitor.postVisitDirectory(Mockito.any(), Mockito.any())).thenReturn(FileVisitResult.CONTINUE);
	}

	private void thenVisitFileCount(final int count) throws IOException {
		Mockito.verify(fileVisitor, Mockito.times(count)).visitFile(Mockito.any(), Mockito.any());
	}

	private void thenVisitFileFailedCount(final int count) throws IOException {
		Mockito.verify(fileVisitor, Mockito.times(count)).visitFileFailed(Mockito.any(), Mockito.any());
	}

	private void thenPreVisitDirectoryCount(final int count) throws IOException {
		Mockito.verify(fileVisitor, Mockito.times(count)).preVisitDirectory(Mockito.any(), Mockito.any());
	}

	private void thenPostVisitDirectoryCount(final int count) throws IOException {
		Mockito.verify(fileVisitor, Mockito.times(count)).postVisitDirectory(Mockito.any(), Mockito.any());
	}

}
