package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;
import org.junit.jupiter.api.Test;
import task.exceptions.IncorrectExpressionException;
import task.exceptions.LogLessZeroArgumentException;
import task.exceptions.PowLessZeroArgumentException;
import task.exceptions.SqrtLessZeroArgumentException;
import task.exceptions.ZeroDivisionException;
import task.factory.OperationFactory;
import task.operations.Operation;


class CalculatorTest {
    @Test 
    void calculateTest() {
        assertEquals(0, Calculator.calculate("- sqrt 32 pow * 8 4 0.5"));
    }

    @Test
    void incorrectOperatorTest() {
        assertThrows(IncorrectExpressionException.class, () -> Calculator.calculate("sqpt 0.34"));
    }

    @Test
    void additionTest() {
        Operation add = OperationFactory.create("+");
        Stack<Double> stack = new Stack<>();
        stack.push(728.5453d);
        stack.push(435.232d);
        assertEquals(1163.7773d, add.apply(stack));
    }
    
    @Test
    void additionFailTest() {
        Operation add = OperationFactory.create("+");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(IncorrectExpressionException.class, () -> add.apply(stack));
    }

    @Test
    void substractionTest() {
        Operation sub = OperationFactory.create("-");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        stack.push(453d);
        assertEquals(-275d, sub.apply(stack));
    }

    @Test
    void substractionFailTest() {
        Operation sub = OperationFactory.create("-");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(IncorrectExpressionException.class, () -> sub.apply(stack));
    }

    @Test
    void multiplicationTest() {
        Operation mul = OperationFactory.create("*");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        stack.push(453d);
        assertEquals(329784d, mul.apply(stack));
    }

    @Test
    void multiplicationFailTest() {
        Operation mul = OperationFactory.create("*");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(IncorrectExpressionException.class, () -> mul.apply(stack));
    }

    @Test
    void divisionTest() {
        Operation div = OperationFactory.create("/");
        Stack<Double> stack = new Stack<>();
        stack.push(798d);
        stack.push(399d);
        assertEquals(0.5d, div.apply(stack));
    }

    @Test
    void divisionByZeroTest() {
        assertThrows(ZeroDivisionException.class, () -> Calculator.calculate("/ 5 0"));
    }

    @Test
    void divisionEmptyStackFailTest() {
        Operation div = OperationFactory.create("/");
        Stack<Double> stack = new Stack<>();
        stack.push(798d);
        assertThrows(IncorrectExpressionException.class, () -> div.apply(stack));
    }

    @Test
    void logTest() {
        Operation log = OperationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.pow(Math.E, 2.67d));
        assertEquals(2.67d, log.apply(stack));
    }

    @Test
    void logEmptyStackFailTest() {
        Operation log = OperationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        assertThrows(IncorrectExpressionException.class, () -> log.apply(stack));
    }

    @Test
    void logZeroArgumentFailTest() {
        Operation log = OperationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        stack.push(0d);
        assertThrows(LogLessZeroArgumentException.class, () -> log.apply(stack));
    }

    @Test
    void sinTest() {
        Operation sin = OperationFactory.create("sin");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.PI * 1.5);
        assertEquals(-1d, sin.apply(stack));
    }

    @Test
    void sinEmptyStackFailTest() {
        Operation sin = OperationFactory.create("sin");
        Stack<Double> stack = new Stack<>();
        assertThrows(IncorrectExpressionException.class, () -> sin.apply(stack));
    }

    @Test
    void cosTest() {
        Operation cos = OperationFactory.create("cos");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.PI);
        assertEquals(-1d, cos.apply(stack));
    }

    @Test
    void cosEmptyStackFailTest() {
        Operation cos = OperationFactory.create("cos");
        Stack<Double> stack = new Stack<>();
        assertThrows(IncorrectExpressionException.class, () -> cos.apply(stack));
    }

    @Test
    void sqrtTest() {
        Operation sqrt = OperationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        stack.push(65 * 65d);
        assertEquals(65d, sqrt.apply(stack));
    }

    @Test
    void sqrtEmptyStackFailTest() {
        Operation sqrt = OperationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        assertThrows(IncorrectExpressionException.class, () -> sqrt.apply(stack));
    }

    @Test
    void sqrtLessZeroArgumentFailTest() {
        Operation sqrt = OperationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        stack.push(-12d);
        assertThrows(SqrtLessZeroArgumentException.class, () -> sqrt.apply(stack));
    }

    @Test
    void powTest() {
        Operation pow = OperationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        stack.push(16d);
        assertEquals(Math.pow(2, 16), pow.apply(stack));
    }

    @Test
    void powEmptyStackFailTest() {
        Operation pow = OperationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        assertThrows(IncorrectExpressionException.class, () -> pow.apply(stack));
    }

    @Test
    void powZeroBaseFailTest() {
        Operation pow = OperationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        stack.push(0d);
        assertThrows(PowLessZeroArgumentException.class, () -> pow.apply(stack));
    }

    @Test
    void factoryNullOutTest() {
        Operation pop = OperationFactory.create("pop");
        assertNull(pop);
    }
}
