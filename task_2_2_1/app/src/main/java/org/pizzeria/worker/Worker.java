package org.pizzeria.worker;

import org.pizzeria.io.logger.ILogger;

/**
 * Worker class.
 */
public abstract class Worker extends Thread {

    protected long workingTime;

    protected final ILogger logger;

    protected final String name;

    protected boolean canWork;

    /**
     * Constructor.
     *
     * @param name name
     * @param workingTime time
     * @param logger logger
     */
    public Worker(String name, long workingTime, ILogger logger) {
        this.logger = logger;
        this.workingTime = workingTime;
        this.name = name;
        this.canWork = true;
    }

    /**
     * Getter.
     *
     * @return working time
     */
    public long getWorkingTime() {
        return workingTime;
    }

    /**
     * Getter.
     *
     * @return name
     */
    public String getWorkerName() {
        return name;
    }

    /**
     * Stop.
     */
    public void stopWorking() {
        this.canWork = false;
    }

    /**
     * Run.
     */
    @Override
    abstract public void run();
}
