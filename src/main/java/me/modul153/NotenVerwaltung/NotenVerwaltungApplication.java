package me.modul153.NotenVerwaltung;

import me.modul153.NotenVerwaltung.data.model.School;
import me.modul153.NotenVerwaltung.managers.*;
import me.modul153.NotenVerwaltung.services.SqlSetup;
import net.myplayplanet.services.ServiceCluster;
import net.myplayplanet.services.config.ConfigService;
import net.myplayplanet.services.connection.ConnectionService;
import net.myplayplanet.services.logger.LoggerService;
import net.myplayplanet.services.logger.sinks.MockSink;
import net.myplayplanet.services.schedule.ScheduleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class NotenVerwaltungApplication {

    public NotenVerwaltungApplication() {
        File configPath = new File("C:\\temp\\nv-backend");
        LoggerService.setDefaultSink(new MockSink());
        ServiceCluster.addServices(true, new LoggerService());
        ServiceCluster.addServices(true, new ConfigService(configPath));
        ServiceCluster.addServices(true, new ConnectionService());
        ServiceCluster.addServices(true, new ScheduleService());



        //the order is important because of dependency between the objects
        CityManager.getInstance();
        AdressManager.getInstance();
        UserManager.getInstance();
        TeacherManager.getInstance();
        StudentManager.getInstance();
        CredentialManager.getInstance();
        SchoolManager.getInstance();
        ClassManager.getInstance();

        ExamManager.getInstance();
        //end


        new SqlSetup().setup();
    }

    public static void main(String[] args) {
        SpringApplication.run(NotenVerwaltungApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ServiceCluster.shutdownCluster();
            UserManager.getInstance().clearCache();
            CityManager.getInstance().clearCache();
            AdressManager.getInstance().clearCache();
        }));
    }

}
