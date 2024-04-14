package org.pizzeria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * Represents a pizzeria with workers and orders.
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
    private String nameOfSerializeFile = "app\\src\\main\\java\\org\\pizzeria\\temp\\pizzeria.ser";

    /**
     * Constructs a new Pizzeria object with the specified configuration file.
     *
     * @param configFile the path to the configuration file
     */
    public Pizzeria(String configFile) {
        JsonFileReader reader = new JsonFileReader(configFile);
        PizzeriaConfig config = readConfigFile(reader.toJsonString());

        storageSize = config.getStorageSize();
        orderListSize = config.getOrderListSize();
        workingTime = config.getWorkingTime();

        state = new State(storageSize, orderListSize);

        loadOldStateFromFile(nameOfSerializeFile);

        bakerThreads = new ArrayList<>();
        courierThreads = new ArrayList<>();

        try {
            addOrder(config.getStartOrdersCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bakers = createWorkers(WorkerType.Baker, config.getBakers());
        couriers = createWorkers(WorkerType.Courier, config.getCouriers());
    }

    private void loadOldStateFromFile(String filename) {
        try {
            State oldState = State.deserializeState(filename);
            if (oldState == null) {
                return;
            }

            if (oldState.getOrders().isEmpty() && oldState.getStorage().isEmpty()) {
                return;
            }

            while (!oldState.getOrders().isEmpty()) {
                try {
                    state.getOrders().put(oldState.getOrders().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            while (!oldState.getStorage().isEmpty()) {
                try {
                    state.getStorage().put(oldState.getStorage().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Runs the pizzeria simulation.
     */
    public void run() {
        createAndStartThreads(bakerThreads, bakers);

        createAndStartThreads(courierThreads, couriers);

        waitForExit(workingTime);

        stopWorkers(bakers);

        joinThreads(bakerThreads);

        waitUntilStorageIsEmpty();

        stopWorkers(couriers);

        joinThreads(courierThreads);

        serializeState();
    }

    private void serializeState() {
        if (state.getOrders().isEmpty() && state.getStorage().isEmpty()) {
            Path filePath = Paths.get(nameOfSerializeFile);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException ignored) { } 
            return;
        }

        try {
            State.serializeState(state, nameOfSerializeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current state of the pizzeria.
     *
     * @return the state of the pizzeria
     */
    public State getState() {
        return state;
    }

    /**
     * Adds a specified number of orders to the pizzeria.
     *
     * @param count the number of orders to add
     * @throws InterruptedException if the thread is interrupted while adding orders
     */
    public void addOrder(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            state.getOrders().put(new Order(i, "pizza"));
        }
    }

    /**
     * Waits for a specified amount of time.
     *
     * @param time the time to wait in milliseconds
     */
    private void waitForExit(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the configuration file into a PizzeriaConfig object.
     *
     * @param configJson the JSON string representing the configuration
     * @return the PizzeriaConfig object representing the configuration
     */
    private PizzeriaConfig readConfigFile(String configJson) {
        Gson gson = new Gson();
        return gson.fromJson(configJson, PizzeriaConfig.class);
    }

    /**
     * Creates a list of workers of the specified type.
     *
     * @param type          the type of worker to create
     * @param workerConfigs the list of worker configurations
     * @return the list of created workers
     */
    private List<Worker> createWorkers(WorkerType type, List<WorkerConfig> workerConfigs) {
        List<Worker> workers = new ArrayList<>();
        WorkerFactory factory = new WorkerFactory();

        for (WorkerConfig workerConfig : workerConfigs) {
            Worker worker = factory.createWorker(
                    type,
                    workerConfig.getName(),
                    workerConfig.getWorkingExperience(),
                    workerConfig.getCapacity(),
                    state.getOrders(),
                    state.getStorage());
            workers.add(worker);
        }

        return workers;
    }

    /**
     * Creates and starts threads for the specified workers.
     *
     * @param threads the list to store the created threads
     * @param workers the list of workers to create threads for
     */
    private void createAndStartThreads(List<Thread> threads, List<Worker> workers) {
        for (Worker worker : workers) {
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Stops the specified workers.
     *
     * @param workers the list of workers to stop
     */
    private void stopWorkers(List<Worker> workers) {
        for (Worker worker : workers) {
            worker.stopWorking();
        }
    }

    /**
     * Joins the specified threads.
     *
     * @param threads the list of threads to join
     */
    private void joinThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Waits until the storage is empty.
     */
    private void waitUntilStorageIsEmpty() {
        synchronized (state.getStorage()) {
            while (!state.getStorage().isEmpty()) {
                try {
                    state.getStorage().wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}