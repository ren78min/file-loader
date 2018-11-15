package me.mren.fileloader.app;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

public class ApplicationTest {

	private Application application;

	@Before
	public void before() {
		application = new Application();
	}

	@Test
	public void testPostConstruct() {
		application.postConstruct();

		assertEquals(TimeZone.getTimeZone("UTC"), TimeZone.getDefault());
	}

}
