package org.pizzeria.worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pizzeria.customQueue.IBlockingQueue;
import org.pizzeria.state.Order;
import org.pizzeria.io.jsonReader.WorkerConfig;
import org.pizzeria.worker.baker.Baker;
import org.pizzeria.worker.courier.Courier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Test class for WorkerFactory.
 */
public class WorkerFactoryTest {

    @Mock
    private WorkerConfig workerConfig;

    @Mock
    private IBlockingQueue<Order> mockOrdersQueue;

    @Mock
    private IBlockingQueue<Order> mockStorageQueue;

    private WorkerFactory workerFactory;

    /**
     * Set up method to initialize the test environment.
     */
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        workerFactory = new WorkerFactory();
    }

    /**
     * Test method for creating a Baker worker.
     */
    @Test
    public void createWorker_Baker_Type_Test() {
        when(workerConfig.getName()).thenReturn("John");
        when(workerConfig.getWorkingExperience()).thenReturn(5);
        when(workerConfig.getCapacity()).thenReturn(10);

        Worker worker = workerFactory.createWorker(WorkerType.Baker, 
                                            workerConfig,
                                            mockOrdersQueue, 
                                            mockStorageQueue);

        assertTrue(worker instanceof Baker);
        assertEquals("John", worker.getWorkerName());
        assertEquals(5, worker.getWorkingExperience());
    }

    /**
     * Test method for creating a Courier worker.
     */
    @Test
    public void createWorker_Courier_Type_Test() {
        when(workerConfig.getName()).thenReturn("Johannes");
        when(workerConfig.getWorkingExperience()).thenReturn(15);
        when(workerConfig.getCapacity()).thenReturn(10);

        Worker worker = workerFactory.createWorker(WorkerType.Courier, 
                                            workerConfig,
                                            mockOrdersQueue, 
                                            mockStorageQueue);

        assertTrue(worker instanceof Courier);
        assertEquals("Johannes", worker.getWorkerName());
        assertEquals(15, worker.getWorkingExperience());
    }

    /**
     * Test method for creating an unknown worker type.
     */
    @Test
    public void createWorker_Unknown_Type_Test() {
        when(workerConfig.getName()).thenReturn("John");
        when(workerConfig.getWorkingExperience()).thenReturn(5);
        when(workerConfig.getCapacity()).thenReturn(10);

        Worker worker = workerFactory.createWorker(null, 
                                            workerConfig,
                                            mockOrdersQueue, 
                                            mockStorageQueue);

        assertTrue(worker == null);
    }

}