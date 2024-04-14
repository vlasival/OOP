package org.pizzeria.worker;

import org.pizzeria.io.logger.ILogger;

/**
 * The Worker class is an abstract class that represents a worker in a pizzeria.
 * 
 * Each worker has a working time, a logger, a name, and a flag indicating if they can work.
 * 
 * This class extends the Thread class, allowing workers to run as separate threads.
 */
public abstract class Worker extends Thread {

    protected long workingTime;
    
    protected final ILogger logger;
    
    protected final String name;
    
    protected volatile boolean canWork;

    /**
     * Constructs a new Worker object with the given name, workingTime, and logger.
     *
     * @param name         the name of the worker
     * @param workingTime  the working time of the worker
     * @param logger       the logger used for logging
     */
    public Worker(String name, long workingTime, ILogger logger) {
        this.logger = logger;
        this.workingTime = workingTime;
        this.name = name;
        this.canWork = true;
    }

    /**
     * Retrieves the working time of the worker.
     *
     * @return the working time of the worker
     */
    public long getWorkingTime() {
        return workingTime;
    }

    /**
     * Retrieves the name of the worker.
     *
     * @return the name of the worker
     */
    public String getWorkerName() {
        return name;
    }

    /**
     * Stops the worker from working.
     */
    public void stopWorking() {
        this.canWork = false;
    }

    /**
     * Overrides the run() method from the Thread class.
     * 
     * This method represents the main logic of the worker, and should be implemented by subclasses.
     */
    @Override
    abstract public void run();
}