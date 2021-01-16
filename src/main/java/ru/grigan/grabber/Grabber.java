package ru.grigan.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Grabber implements Grab {

    private Properties properties;

    public Grabber(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("parse", parse);
        dataMap.put("store", store);
        int interval = Integer.parseInt(properties.getProperty("rabbit.interval"));
        try {
            scheduler.start();
            JobDetail job = JobBuilder.newJob(GrabberJob.class)
                    .usingJobData(dataMap)
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
            Thread.sleep(15000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Scheduler getScheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    public static void main(String[] args) throws SchedulerException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(
                "src/main/resources/rabbit.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PSqlStore pSqlStore = new PSqlStore(properties);
        ParserSqlRu parserSqlRu = new ParserSqlRu();
        Grabber grabber = new Grabber(properties);
        Scheduler scheduler = grabber.getScheduler();
        grabber.init(parserSqlRu, pSqlStore, scheduler);
        pSqlStore.getAll().forEach(System.out::println);
    }

}
