package org.pizzeria;

import java.util.ArrayList;
import java.util.List;
import org.pizzeria.io.jsonReader.JsonFileReader;
import org.pizzeria.io.jsonReader.PizzeriaConfig;
import org.pizzeria.io.jsonReader.WorkerConfig;
import org.pizzeria.state.Order;
import org.pizzeria.state.State;
import org.pizzeria.worker.Worker;
import org.pizzeria.worker.WorkerFactory;
import org.pizzeria.worker.WorkerType;
import com.google.gson.Gson;

/**
 * Class Pizzeria.
 */
public class Pizzeria {

    private int storageSize;
    private int orderListSize;
    private long workingTime;
    private State state;
    private List<Worker> bakers;
    private List<Worker> couriers;
    private List<Thread> bakerThreads;
    private List<Thread> courierThreads;

    /**
     * Constructor for Pizzeria.
     *
     * @param configFile file
     */
    public Pizzeria(String configFile) {
        JsonFileReader reader = new JsonFileReader(configFile);
        PizzeriaConfig config = readConfigFile(reader.toJsonString());
        storageSize = config.getStorageSize();
        orderListSize = config.getOrderListSize();
        workingTime = config.getWorkingTime();
        state = new State(storageSize, orderListSize);
        bakerThreads = new ArrayList<>();
        courierThreads = new ArrayList<>();

        for (int i = 0; i < config.getStartOrdersCount(); i++) {
            try {
                state.getOrders().put(new Order(i, "pizza"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        bakers = new ArrayList<>();
        for (WorkerConfig bakerInfo : config.getBakers()) {
            bakers.add(makeWorkersFromConfig(WorkerType.Baker, bakerInfo));
        }

        couriers = new ArrayList<>();
        for (WorkerConfig courierInfo : config.getCouriers()) {
            couriers.add(makeWorkersFromConfig(WorkerType.Courier, courierInfo));
        }
    }

    /**
     * Run.
     */
    public void run() {

        for (Worker worker : bakers) {
            bakerThreads.add(new Thread(worker));
        }

        for (Worker worker : couriers) {
            courierThreads.add(new Thread(worker));
        }

        for (Thread thread : bakerThreads) {
            thread.start();
        }

        for (Thread thread : courierThreads) {
            thread.start();
        }
        
        waitForExit(workingTime);

        while (state.getOrders().getSize() > 0) { }

        for (Worker baker : bakers) {
            baker.stopWorking();
        }

        for (Thread thread : bakerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (state.getStorage().getSize() > 0) { }

        for (Worker courier : couriers) {
            courier.stopWorking();
        }

        for (Thread thread : courierThreads) {
            thread.interrupt();
        }

    }


    /**
     * Add order.
     *
     * @param count of orders
     * @throws InterruptedException if interrupted
     */
    public void addOrder(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            state.getOrders().put(new Order(i, "ord"));
        }
    }

    /**
     * Waiting.
     *
     * @param time time
     */
    private void waitForExit(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) { }
    }

    /**
     * Reader.
     *
     * @param configJson conf
     * @return PizzeriaConf
     */
    private PizzeriaConfig readConfigFile(String configJson) {
        Gson gson = new Gson();
        return gson.fromJson(configJson, PizzeriaConfig.class);
    }

    /**
     * Maker.
     *
     * @param type type
     * @param workerConfig conf
     * @return Worker
     */
    private Worker makeWorkersFromConfig(WorkerType type, WorkerConfig workerConfig) {
        WorkerFactory factory = new WorkerFactory();
        return factory.createWorker(
                type, 
                workerConfig.getName(),
                workerConfig.getWorkingTime(),
                workerConfig.getCapacity(), 
                state.getOrders(),
                state.getStorage());
    }


}
