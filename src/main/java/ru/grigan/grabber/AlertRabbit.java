package ru.grigan.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AlertRabbit {

    public static final Logger LOG = LoggerFactory.getLogger(AlertRabbit.class.getName());
    private static Properties properties;
    private static int interval;

    public static void main(String[] args) {
        //read properties
        try (FileInputStream inputStream = new FileInputStream(
                "src/main/resources/rabbit.properties")) {
            properties = new Properties();
            properties.load(inputStream);
            interval = Integer.parseInt(properties.getProperty("rabbit.interval"));
            Class.forName(properties.getProperty("driver-class-name"));
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Some trouble with properties read ", e);
        }

        try (Connection connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password")
        )) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = JobBuilder.newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder
                    .simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException | SQLException e) {
            LOG.error("Some trouble with scheduler or Thread or connection ", e);
        }
    }
}
