package me.mren.fileloader.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:file-loader.properties")
@ComponentScan(basePackages = { "me.mren.fileloader.controller", "me.mren.fileloader.filevisitor" })
public class Configurations {

	private static final Logger LOGGER = LoggerFactory.getLogger(Configurations.class);

	@Bean
	CommandLineRunner postConstruct(final ApplicationContext context) {
		return args -> {
			for (final String name : context.getBeanDefinitionNames()) {
				LOGGER.info(name);
			}
		};
	}

}
