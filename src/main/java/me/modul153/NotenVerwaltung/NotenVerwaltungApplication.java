package me.modul153.NotenVerwaltung;

import net.myplayplanet.services.ServiceCluster;
import net.myplayplanet.services.config.ConfigService;
import net.myplayplanet.services.connection.ConnectionService;
import net.myplayplanet.services.logger.LoggerService;
import net.myplayplanet.services.logger.sinks.MySQLSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NotenVerwaltungApplication {

	public NotenVerwaltungApplication() {
		MySQLSink.setDisable(true);
		ServiceCluster.addServices(true, new LoggerService());
		File configPath = new File("C:\\temp\\nv-backend");
		configPath.mkdirs();
		ServiceCluster.addServices(true, new ConfigService(configPath));
		ServiceCluster.addServices(true, new ConnectionService());
	}

	public static void main(String[] args) {
		SpringApplication.run(NotenVerwaltungApplication.class, args);
	}

}
