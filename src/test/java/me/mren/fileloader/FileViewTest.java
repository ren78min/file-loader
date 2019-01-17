package me.mren.fileloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class FileViewTest {

	private FileView fileView, fileView111, fileView211, fileView121, fileView112;

	private static final String PATH1 = "PATH1";

	private static final String PATH2 = "PATH2";

	private static final long SIZE1 = 1l;

	private static final long SIZE2 = 2l;

	private static final Date DATE1 = new Date(1l);

	private static final Date DATE2 = new Date(DATE1.getTime() + 1l);

	@Before
	public void before() {
		fileView = new FileView(PATH1, SIZE1, DATE1);
		fileView111 = new FileView(PATH1, SIZE1, DATE1);
		fileView211 = new FileView(PATH2, SIZE1, DATE1);
		fileView121 = new FileView(PATH1, SIZE2, DATE1);
		fileView112 = new FileView(PATH1, SIZE1, DATE2);
	}

	@Test
	public void testHashCode() {
		assertEquals(-73457109, fileView.hashCode());
	}

	@Test
	public void testGetAbsolutPath() {
		assertEquals(PATH1, fileView.getAbsolutPath());
	}

	@Test
	public void testGetSize() {
		assertEquals(SIZE1, fileView.getSize());
	}

	@Test
	public void testGetDate() {
		assertEquals(DATE1, fileView.getDate());
	}

	@Test
	public void testEqualsObject() {
		assertTrue(fileView.equals(fileView));
		assertTrue(fileView.equals(fileView111));

		assertFalse(fileView.equals(fileView211));
		assertFalse(fileView.equals(fileView121));
		assertFalse(fileView.equals(fileView112));

		assertFalse(fileView.equals(null));
		assertFalse(fileView.equals(new Object()));
	}

	@Test
	public void testToString() {
		assertEquals("VisitResultView [absolutPath=" + PATH1 + ", size=" + SIZE1 + ", date=" + DATE1 + "]",
				fileView.toString());
	}

}
