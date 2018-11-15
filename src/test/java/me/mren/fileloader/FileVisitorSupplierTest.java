package me.mren.fileloader;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class FileVisitorSupplierTest {
	private FileVisitorSupplier fileVisitorSupplier;

	@Before
	public void before() {
		fileVisitorSupplier = new FileVisitorSupplier();
	}

	@Test
	public void testAppy() {
		assertNotNull(fileVisitorSupplier.apply(new HashSet<>(0)));
	}
}
