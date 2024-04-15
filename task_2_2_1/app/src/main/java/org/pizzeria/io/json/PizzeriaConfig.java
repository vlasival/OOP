package org.pizzeria.io.json;

import java.util.List;

/**
 * Class  to store configuration for a pizzeria.
 */
public class PizzeriaConfig {
    private int storageSize;
    private int orderListSize;
    private int startOrdersCount;
    private long workingTime;
    private List<WorkerConfig> bakers;
    private List<WorkerConfig> couriers;

    /**
     * Getter.
     *
     * @return storage size
     */
    public int getStorageSize() {
        return storageSize;
    }

    /**
     * Getter.
     *
     * @return orderlist size
     */
    public int getOrderListSize() {
        return orderListSize;
    }

    /**
     * Getter.
     *
     * @return orders count
     */
    public int getStartOrdersCount() {
        return startOrdersCount;
    }

    /**
     * Getter.
     *
     * @return time
     */
    public long getWorkingTime() {
        return workingTime;
    }
    
    /**
     * Getter.
     *
     * @return bakers
     */
    public List<WorkerConfig> getBakers() {
        return bakers;
    }
    
    /**
     * Getter.
     *
     * @return couriers
     */
    public List<WorkerConfig> getCouriers() {
        return couriers;
    }
    
}
