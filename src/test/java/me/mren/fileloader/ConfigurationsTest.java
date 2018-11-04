package me.mren.fileloader;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.mren.fileloader.config.Configurations;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Configurations.class)
public class ConfigurationsTest {

	@Autowired
	@Qualifier("fileloader.fileBuffer")
	private BlockingQueue<Path> fileBuffer;

	@Test
	public void fileBufferIsInitialized() {
		assertEquals(10, fileBuffer.remainingCapacity());
	}

}
