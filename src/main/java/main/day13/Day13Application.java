package main.day13;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.day13.utils.Contacts;

@SpringBootApplication
public class Day13Application {

	private static Logger logger = LoggerFactory.getLogger(Contacts.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Day13Application.class);
		ApplicationArguments appArgs = new DefaultApplicationArguments(args);

		List<String> dataDir = appArgs.getOptionValues("dataDir");
		if (dataDir == null || dataDir.isEmpty()) {
			logger.warn("No data directory specified.");
			System.exit(1);
		}
		app.run(args);
	}
}