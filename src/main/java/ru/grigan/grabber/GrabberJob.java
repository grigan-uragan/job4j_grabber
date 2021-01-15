package ru.grigan.grabber;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrabberJob implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(GrabberJob.class);
    private String[] urls =
            {"https://www.sql.ru/forum/job-offers/1",
                    "https://www.sql.ru/forum/job-offers/2",
                    "https://www.sql.ru/forum/job-offers/3",
                    "https://www.sql.ru/forum/job-offers/4",
                    "https://www.sql.ru/forum/job-offers/5"};

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Parse parse = (Parse) jobExecutionContext
                .getJobDetail()
                .getJobDataMap()
                .get("parse");
        Store store = (Store) jobExecutionContext
                .getJobDetail()
                .getJobDataMap()
                .get("store");
        try {
            List<Post> posts = new ArrayList<>();
            for (String url : urls) {
               parse.getPostList(url).stream()
                       .filter(post -> !posts.contains(post))
                       .forEach(posts::add);
            }
            System.out.println(posts.size());
            posts.forEach(store::save);
        } catch (IOException e) {
            LOG.error("Some trouble in with parsing", e);
        }
    }
}
