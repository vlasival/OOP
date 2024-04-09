package org.pizzeria.io.jsonReader;

/**
 * WorkerConfig class.
 */
public class WorkerConfig {
    private String name;
    private int capacity;
    private long workingTime;

    /**
     * Getter.
     *
     * @return name
     */
    public String getName() {
        return name;
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
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }
}
