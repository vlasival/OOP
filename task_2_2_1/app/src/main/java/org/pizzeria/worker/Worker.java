package org.pizzeria.worker;

import java.util.Random;
import org.pizzeria.io.logger.InLogger;

/**
 * The Worker class is an abstract class that represents a worker in a pizzeria.
 * Each worker has a working time, a logger, a name, and a flag indicating if they can work.
 * This class extends the Thread class, allowing workers to run as separate threads.
 */
public abstract class Worker extends Thread {

    protected int workingExperience;
    
    protected final InLogger logger;
    
    protected final String name;
    
    protected volatile boolean canWork;

    protected static int maxWorkingTime = 10000;

    protected Random random;

    /**
     * Constructs a new Worker object with the given name, workingExperience, and logger.
     *
     * @param name         the name of the worker
     * @param workingExperience  the working experience of the worker
     * @param logger       the logger used for logging
     */
    public Worker(String name, int workingExperience, InLogger logger) {
        this.logger = logger;
        this.workingExperience = workingExperience;
        this.name = name;
        this.canWork = true;
        this.random = new Random();
    }

    /**
     * Retrieves the working time of the worker.
     *
     * @return the working time of the worker
     */
    public int getWorkingExperience() {
        return workingExperience;
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
     * This method represents the main logic of the worker, and should be implemented by subclasses.
     */
    @Override
    public abstract void run();
}