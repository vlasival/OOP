package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Stack;

import org.junit.jupiter.api.Test;

import task.OperationsClasses.Operation;
import task.OperationsClasses.OprationFactory;


class CalculatorTest {
    @Test 
    void calculateTest() {
        assertEquals(0, Calculator.calculate("- sqrt 32 pow * 8 4 0.5"));
    }

    @Test
    void incorrectOperatorTest() {
        assertThrows(CalculationException.class, () -> Calculator.calculate("sqpt 0.34"));
    }

    @Test
    void additionTest() {
        Operation add = OprationFactory.create("+");
        Stack<Double> stack = new Stack<>();
        stack.push(728.5453d);
        stack.push(435.232d);
        assertEquals(1163.7773d, add.apply(stack));
    }
    
    @Test
    void additionFailTest() {
        Operation add = OprationFactory.create("+");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(CalculationException.class, () -> add.apply(stack));
    }

    @Test
    void substractionTest() {
        Operation sub = OprationFactory.create("-");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        stack.push(453d);
        assertEquals(-275d, sub.apply(stack));
    }

    @Test
    void SubstractionFailTest() {
        Operation sub = OprationFactory.create("-");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(CalculationException.class, () -> sub.apply(stack));
    }

    @Test
    void multiplicationTest() {
        Operation mul = OprationFactory.create("*");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        stack.push(453d);
        assertEquals(329784d, mul.apply(stack));
    }

    @Test
    void multiplicationFailTest() {
        Operation mul = OprationFactory.create("*");
        Stack<Double> stack = new Stack<>();
        stack.push(728d);
        assertThrows(CalculationException.class, () -> mul.apply(stack));
    }

    @Test
    void divisionTest() {
        Operation div = OprationFactory.create("/");
        Stack<Double> stack = new Stack<>();
        stack.push(798d);
        stack.push(399d);
        assertEquals(0.5d, div.apply(stack));
    }

    @Test
    void divisionByZeroTest() {
        assertThrows(CalculationException.class, () -> Calculator.calculate("/ 5 0"));
    }

    @Test
    void divisionEmptyStackFailTest() {
        Operation div = OprationFactory.create("/");
        Stack<Double> stack = new Stack<>();
        stack.push(798d);
        assertThrows(CalculationException.class, () -> div.apply(stack));
    }

    @Test
    void logTest() {
        Operation log = OprationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.pow(Math.E, 2.67d));
        assertEquals(2.67d, log.apply(stack));
    }

    @Test
    void logEmptyStackFailTest() {
        Operation log = OprationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        assertThrows(CalculationException.class, () -> log.apply(stack));
    }

    @Test
    void logZeroArgumentFailTest() {
        Operation log = OprationFactory.create("log");
        Stack<Double> stack = new Stack<>();
        stack.push(0d);
        assertThrows(CalculationException.class, () -> log.apply(stack));
    }

    @Test
    void sinTest() {
        Operation sin = OprationFactory.create("sin");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.PI * 1.5);
        assertEquals(-1d, sin.apply(stack));
    }

    @Test
    void sinEmptyStackFailTest() {
        Operation sin = OprationFactory.create("sin");
        Stack<Double> stack = new Stack<>();
        assertThrows(CalculationException.class, () -> sin.apply(stack));
    }

    @Test
    void cosTest() {
        Operation cos = OprationFactory.create("cos");
        Stack<Double> stack = new Stack<>();
        stack.push(Math.PI);
        assertEquals(-1d, cos.apply(stack));
    }

    @Test
    void cosEmptyStackFailTest() {
        Operation cos = OprationFactory.create("cos");
        Stack<Double> stack = new Stack<>();
        assertThrows(CalculationException.class, () -> cos.apply(stack));
    }

    @Test
    void sqrtTest() {
        Operation sqrt = OprationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        stack.push(65 * 65d);
        assertEquals(65d, sqrt.apply(stack));
    }

    @Test
    void sqrtEmptyStackFailTest() {
        Operation sqrt = OprationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        assertThrows(CalculationException.class, () -> sqrt.apply(stack));
    }

    @Test
    void sqrtZeroArgumentFailTest() {
        Operation sqrt = OprationFactory.create("sqrt");
        Stack<Double> stack = new Stack<>();
        stack.push(-12d);
        assertThrows(CalculationException.class, () -> sqrt.apply(stack));
    }

    @Test
    void powTest() {
        Operation pow = OprationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        stack.push(16d);
        assertEquals(Math.pow(2, 16), pow.apply(stack));
    }

    @Test
    void powEmptyStackFailTest() {
        Operation pow = OprationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        assertThrows(CalculationException.class, () -> pow.apply(stack));
    }

    @Test
    void powZeroBaseFailTest() {
        Operation pow = OprationFactory.create("pow");
        Stack<Double> stack = new Stack<>();
        stack.push(4d);
        stack.push(0d);
        assertThrows(CalculationException.class, () -> pow.apply(stack));
    }

    @Test
    void factoryNullOutTest() {
        Operation pop = OprationFactory.create("pop");
        assertNull(pop);
    }
}
