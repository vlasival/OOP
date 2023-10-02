
package task;

import java.util.Arrays;

/**
 * Represents a polynomial and provides methods for polynomial operations.
 */
public class Polynomial {
    public int count; // Contains number of coefficients.
    public int[] coefficients; // Array of coefficients.

    /**
     * Constructs a polynomial from an array of coefficients.
     *
     * @param args An array of coefficients.
     */
    public Polynomial(int[] args) {
        count = args.length;
    
        int firstNonZeroIndex = 0;
        while (firstNonZeroIndex < count && args[firstNonZeroIndex] == 0) {
            firstNonZeroIndex++;
        }
    
        coefficients = Arrays.copyOfRange(args, firstNonZeroIndex, count);
        count = coefficients.length;
    }
    
    /**
     * Redefinición of standart method toString().
     * Represents polynom in string form.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (count == 0) {
            return "0";
        }

        for (int i = 0; i < count; i++) {
            int coeff = coefficients[i];

            if (coeff == 0) {
                continue;
            }

            if (i == 0) {
                if (coeff < 0) {
                    result.append("-");
                    coeff = -coeff;
                }
            } else {
                if (coeff > 0) {
                    result.append(" + ");
                } else {
                    result.append(" - "); 
                    coeff = -coeff;  
                }
            }

            if ((coeff != 1) || (i == count - 1)) {
                result.append(coeff);
            }

            if (i < count - 2) {
                result.append("x^").append(count - i - 1);
            }
            if (i == count - 2) {
                result.append("x");   
            }
        }

        return result.toString();
    }

    /**
     * Adds two polynomials and returns the result as a new polynomial.
     * 
     * @param poly is the polynomial to be added.
     * @return A new polynomial representing the sum of this polynomial and the provided polynomial.
     */
    public Polynomial plus(Polynomial poly) {
        int maxLen = poly.count > count ? poly.count : count;
        int minLen = poly.count < count ? poly.count : count;
        int[] coeffs = new int[maxLen];

        for (int i = 1; i <= minLen; i++) {
            coeffs[maxLen - i] = coefficients[this.count - i] + poly.coefficients[poly.count - i];
        }

        if (this.count == minLen) {
            for (int i = minLen + 1; i <= maxLen; i++) {
                coeffs[maxLen - i] = poly.coefficients[maxLen - i];
            }
        } else {
            for (int i = minLen + 1; i <= maxLen; i++) {
                coeffs[maxLen - i] = this.coefficients[maxLen - i];
            }
        }

        return new Polynomial(coeffs);
    }

    /**
     * Subtracts another polynomial from this polynomial and returns the result as a new polynomial.
     * 
     * @param poly is the polynomial to be subtracted.
     * @return A new polynomial representing the difference 
     * between this polynomial and the provided polynomial.
     */
    public Polynomial minus(Polynomial poly) {
        int maxLen = poly.count > count ? poly.count : count;
        int minLen = poly.count < count ? poly.count : count;
        int[] coeffs = new int[maxLen];

        for (int i = 1; i <= minLen; i++) {
            coeffs[maxLen - i] = coefficients[this.count - i] - poly.coefficients[poly.count - i];
        }

        if (this.count == minLen) {
            for (int i = minLen + 1; i <= maxLen; i++) {
                coeffs[maxLen - i] = -poly.coefficients[maxLen - i];
            }
        } else {
            for (int i = minLen + 1; i <= maxLen; i++) {
                coeffs[maxLen - i] = this.coefficients[maxLen - i];
            }
        }


        return new Polynomial(coeffs);
    }

    /**
     * Checks if two polynomials are equal.
     * Redefinicion of the standart method.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if ((obj == null) || (getClass() != obj.getClass())) {return false;}
        Polynomial that = (Polynomial) obj;
        if (this.count != that.count) {return false;}
        for (int i = 0; i < count; i++) {
            if (this.coefficients[i] != that.coefficients[i]) {return false;}
        }
        return true;
    }

    /**
     * Redefinicion hashCode method.
     * Returns a hash code value for the polynomial.
     * Depends on polynomial length and coefficients.
     */
    @Override
    public int hashCode() {
        int result = 17; 
        result = 31 * result + count;
        result = 31 * result + Arrays.hashCode(coefficients);
        return result;
    }
    
    /**
     * Multiplies two polynomials and returns the result as a new polynomial.
     * @param poly is the polynomial to be multiplied.
     * @return A new polynomial representing the products of polynomials.
     */
    public Polynomial times(Polynomial poly) {
        int[] mult = new int[poly.count + count - 1];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < poly.count; j++) {
                mult[i + j] += coefficients[i] * poly.coefficients[j];
            }
        }

        return new Polynomial(mult);
    }

    /**
     * Computes the derivative of the polynomial with a specified degree.
     * @param deg is the degree of the derivative to compute.
     * @return A new polynomial representing the derivative of this polynomial.
     */
    public Polynomial differentiate(int deg) {
        if (deg >= count){
            return new Polynomial(new int[] {0});
        }
        int[] newCoeffs = new int[count];
        System.arraycopy(coefficients, 0, newCoeffs, 0, count);
        for (int i = 0; i < deg; i++) {
            for (int j = count - 1; j > i; j--) {
                newCoeffs[j] = newCoeffs[j - 1] * (count - j);
            }
            newCoeffs[i] = 0;
        }

        return new Polynomial(newCoeffs);
    }

    /**
     * Evaluates the polynomial at a given value of x.
     * @param x is the value of x at which to evaluate the polynomial.
     * @return The result of evaluating the polynomial at the given value of x.
     */
    public long evaluate(long x) {
        long res = 0;
        long xPower = 1;
    
        for (int i = count - 1; i >= 0; i--) {
            res += coefficients[i] * xPower;
            xPower *= x;
        }
    
        return res;
    }
}
