package org.pizzeria.worker;

import org.pizzeria.logger.ILogger;

public abstract class Worker extends Thread {

    protected long workingTime;

    protected final ILogger logger;

    protected final String name;

    protected boolean canWork;

    public Worker(String name, long workingTime, ILogger logger) {
        this.logger = logger;
        this.workingTime = workingTime;
        this.name = name;
        this.canWork = true;
    }

    public long getWorkingTime() {
        return workingTime;
    }

    public String getWorkerName() {
        return name;
    }

    public void stopWorking() {
        this.canWork = false;
    }

    @Override
    abstract public void run();
}
