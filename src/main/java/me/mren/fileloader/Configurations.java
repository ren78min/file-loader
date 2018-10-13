package me.mren.fileloader;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:file-loader.properties")
@ComponentScan(basePackages = { "me.mren.fileloader" })
@Import({ me.mren.filepoller.Configurations.class })
public class Configurations {

	private static final Logger LOGGER = LoggerFactory.getLogger(Configurations.class);

	@Qualifier("fileloader.fileBuffer")
	private BlockingQueue<Path> fileQueue;

	@Bean("fileloader.fileBuffer")
	@Value("${fileloader.filebuffer.size}")
	public BlockingQueue<Path> fileBuffer(final int capacity) {
		LOGGER.info("File buffer size [{}].", capacity);
		return new LinkedBlockingQueue<>(capacity);
	}
}
