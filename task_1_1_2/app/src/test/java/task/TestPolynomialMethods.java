
package task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestPolynomialMethods {
    @Test
    public void testToStringSimple() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, -2, 0, 1});
        assertEquals("x^3 - 2x^2 + 1", p1.toString());
    }

    @Test
    public void testToStringZeroPolynom() {
        Polynomial p2 = new Polynomial(new int[] {0, 0, 0});
        assertEquals("0", p2.toString());
    }
        
    @Test
    public void testToStringMinusCoeffs() {
        Polynomial p3 = new Polynomial(new int[] {-3, 1, -2});
        assertEquals("-3x^2 + x - 2", p3.toString());
    }

    @Test
    public void testPlus() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {-3, -2, 0, 1, 4});
        Polynomial expexted = new Polynomial(new int[] {-3, -1, 2, 1, 5});
        assertEquals(expexted, p1.plus(p2), "plus doesn't work");
    }

    @Test
    public void testPlusOtherway() {
        Polynomial p1 = new Polynomial(new int[] {2, 1, 2, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {5, 4, -3});
        Polynomial expexted = new Polynomial(new int[] {2, 1, 7, 4, -2});
        assertEquals(expexted, p1.plus(p2), "minus doesn't work");
    }

    @Test
    public void testMinus() {
        Polynomial p1 = new Polynomial(new int[] {2, 1, 2, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {-3, -2, 0, 2});
        Polynomial expexted = new Polynomial(new int[] {2, 4, 4, 0, -1});
        assertEquals(expexted, p1.minus(p2), "minus doesn't work");
    }

    @Test
    public void testMinusOtherway() {
        Polynomial p1 = new Polynomial(new int[] {2, 1, 2});
        Polynomial p2 = new Polynomial(new int[] {-3, -2, 0, 2});
        Polynomial expexted = new Polynomial(new int[] {3, 4, 1, 0});
        assertEquals(expexted, p1.minus(p2), "minus doesn't work");
    }

    @Test
    public void testEquals() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial p2 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial p3 = new Polynomial(new int[] {-3, -2, 0, 1});

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    @Test
    public void testEqualsSameObjects() {
        Polynomial p1 = new Polynomial(new int[] {3, -2, 5, -7});
        assertEquals(p1, p1);
    }

    @Test
    public void testEqualsNullObject() {
        Polynomial p1 = new Polynomial(new int[] {3, -2, 5, -7});
        assertNotEquals(p1, null);
    }

    @Test
    public void testEqualsDifferentCounts() {
        Polynomial p1 = new Polynomial(new int[] {3, -2, 5, -7});
        Polynomial p2 = new Polynomial(new int[] {3, -2, 5});
        assertNotEquals(p1, p2);
    }

    @Test
    public void testEqualsDifferentClasses() {
        Polynomial p1 = new Polynomial(new int[] {3, -2, 5, -7});
        Object p2 = new Object();
        assertNotEquals(p1, p2);
    }

    @Test
    public void testTimes() {
        Polynomial p1 = new Polynomial(new int[] {2, 1, 2});
        Polynomial p2 = new Polynomial(new int[] {-1, 2}); 
        Polynomial expexted = new Polynomial(new int[] {-2, 3, 0, 4});
        assertEquals(expexted, p1.times(p2), "multiplication doesn't work");
    }

    @Test
    public void testDifferentiateFirst() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial expexted = new Polynomial(new int[] {3, 4, 0});
        assertEquals(expexted, p1.differentiate(1), "differentiate doesn't work");
    }

    @Test
    public void testDifferentiateSecond() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        Polynomial expexted = new Polynomial(new int[] {0, 6, 4});
        assertEquals(expexted, p1.differentiate(2), "differentiate doesn't work");
    }

    @Test
    public void testDifferentiateMore() {
        Polynomial p1 = new Polynomial(new int[] {4, 15, -23, 0, -1, 9, 2, -4});
        Polynomial expexted = new Polynomial(new int[] {0});
        assertEquals(expexted, p1.differentiate(8), "differentiate doesn't work");
    }

    @Test
    public void testEvaluate() {
        Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
        long result = p1.evaluate(3);
        assertEquals(46, result);
    }

    @Test
    public void testBigTest() {
        Polynomial p1 = new Polynomial(new int[] {3, 5, -2, -6, 7, 5, 3, -2});
        p1 = p1.differentiate(1);
        assertEquals(p1, new Polynomial(new int[] {21, 30, -10, -24, 21, 10, 3}), "Big test, 1");
        p1 = p1.minus(new Polynomial(new int[] {0, 21, 0, 0, 0, 21, 10, 0}));
        assertEquals(p1, new Polynomial(new int[] {30, -10, -24, 0, 0, 3}), "Big test, 2");
        p1 = p1.plus(new Polynomial(new int[] {-20, 5, 12, 2, 3, 2}));
        assertEquals(p1, new Polynomial(new int[] {10, -5, -12, 2, 3, 5}), "Big test, 3");
        p1 = p1.differentiate(2);
        assertEquals(p1, new Polynomial(new int[] {200, -60, -72, 4}), "Big test, 4");
        assertEquals(11556, p1.evaluate(4), "Big test, 5");
    }
    
    @Test
    public void testHashCode() {
        Polynomial p1 = new Polynomial(new int[] {2, 3, -4, -6, 23, -29});
        Polynomial p2 = new Polynomial(new int[] {0, 0, 0, 2, 3, -4, -6, 23, -29}); 
        assertEquals(p1.hashCode(), p2.hashCode(), "HashCode doesn't work");
    }
}

