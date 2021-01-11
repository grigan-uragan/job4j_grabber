package ru.grigan.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AlertRabbit {

    private static Properties properties;
    private static int interval;

    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream(
                "src/main/resources/rabbit.properties")) {
            properties = new Properties();
            properties.load(inputStream);
            interval = Integer.parseInt(properties.getProperty("rabbit.interval"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = JobBuilder.newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder
                    .simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
