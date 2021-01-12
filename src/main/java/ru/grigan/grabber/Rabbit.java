package ru.grigan.grabber;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

public class Rabbit implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long currentTime = System.currentTimeMillis();
        Date date = Date.from(Instant.ofEpochMilli(currentTime));
        Connection connection = (Connection) jobExecutionContext
                .getJobDetail()
                .getJobDataMap()
                .get("connection");
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into rabbit (visit_date) values (?)")) {
            statement.setString(1, date.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("I was here " + date);
    }
}
