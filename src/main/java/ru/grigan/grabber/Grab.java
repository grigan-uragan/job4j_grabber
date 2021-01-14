package ru.grigan.grabber;

import org.quartz.Scheduler;

public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler);
}
