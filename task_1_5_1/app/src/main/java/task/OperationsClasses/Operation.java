package task.operationsClasses;

import java.util.Stack;

/**
 * Sealed interface (contract) that brings together all operations.
 */
public sealed interface Operation permits Addition, Substraction, Multiplication, Division, Sin, Log, Cos, Sqrt, Pow {
    /**
     * Abstract method implies the implementation of the functionality of applying an operation.
     *
     * @param stack stack of input numbers.
     * @return result of operation.
     */
    public double apply(Stack<Double> stack);
}
