
package task2;

import java.util.Arrays;

/**
 * Main class, which contain methods makes able to work with polynoms.
 */
public class Polynomial {
    public int count;
    public int[] coefficients;

    /**
     * Class constructor
     */
    public Polynomial(int[] args) {
        count = args.length;

        int i = 0;
        while ((i < count) && (args[i] == 0)) {
            i++;
        }
        coefficients = Arrays.copyOfRange(args, i, count);
        count = args.length - i;
    }

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
                if (coeff < 0){
                    result.append("-");
                    coeff = -coeff;
                }
            }
            else {
                if (coeff > 0) {
                    result.append(" + ");
                }
                else {
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


    public Polynomial plus(Polynomial poly) {
        int maxLen = Math.max(poly.count, count);
        int[] coeffs = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            coeffs[i] = coefficients[i] + poly.coefficients[i];
        }

        return new Polynomial(coeffs);
    }

    public Polynomial minus(Polynomial poly) {
        int maxLen = Math.max(poly.count, count);
        int[] coeffs = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            coeffs[i] = coefficients[i] - poly.coefficients[i];
        }

        return new Polynomial(coeffs);
    }

    public boolean equals(Polynomial poly) {
        if (count != poly.count){
            return false;
        }
        for (int i = 0; i < count; i++){
            if (coefficients[i] != poly.coefficients[i]){
                return false;
            }
        }
        return true;
    }

    public Polynomial times(Polynomial poly) {
        int[] mult = new int[poly.count + count - 1];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < poly.count; j++) {
                mult[i + j] += coefficients[i] * poly.coefficients[j];
            }
        }

        return new Polynomial(mult);
    }

    public Polynomial differentiate(int deg) {
        int[] newCoeffs = coefficients;
        int newCount = count;
        for (int i = 0; i < deg; i++) {
            for (int j = 0; j < newCount - 1; j++) {
                newCoeffs[j] *= newCount - j - 1;
            }
            newCount -= 1;
            newCoeffs = Arrays.copyOfRange(newCoeffs, 0, newCount);
        }

        return new Polynomial(newCoeffs);
    }

    public long evaluate(long x) {
        long res = 0;
        for (int i = 0; i < count - 1; i++) {
            res += coefficients[i] * Math.pow(x, count - i - 1);
        }
        res += coefficients[count - 1];
        return res;
    }

    // public static void main(String[] args) {
    //     Polynomial p1 = new Polynomial(new int[] {0, 1, 2, 0, 1});
    //     System.out.println(p1.toString());

    //     Polynomial p2 = new Polynomial(new int[] {0, 0, 0});
    //     System.out.println(p2.toString());
    // }

}


