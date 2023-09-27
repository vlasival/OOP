
package task_1_1_2;

import java.util.Arrays;

public class Polynomial {
    public int n;
    public int[] coefficients;

    public Polynomial(int[] args) {
        this.n = args.length;

        int i = 0;
        while ((i < n) && (args[i] == 0)) {
            i++;
        }
        this.coefficients = Arrays.copyOfRange(args, i, this.n);
        this.n = args.length - i;
    }

   @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (n == 0){
            return "0";
        }

        for (int i = 0; i < n; i++) {
            int coeff = coefficients[i];

            if (coeff == 0) {
                continue;
            }

            if (i == 0){
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

            if ((coeff != 1) || (i == n - 1)) {
                result.append(coeff);
            }

            if (i < n - 2) {
                result.append("x^").append(n - i - 1);
            }
            if (i == n - 2) {
                result.append("x");   
            }
        }

        return result.toString();
    }


    public Polynomial plus(Polynomial poly) {
        int maxLen = Math.max(poly.n, this.n);
        int[] coeffs = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            coeffs[i] = this.coefficients[i] + poly.coefficients[i];
        }

        return new Polynomial(coeffs);
    }

    public Polynomial minus(Polynomial poly) {
        int maxLen = Math.max(poly.n, this.n);
        int[] coeffs = new int[maxLen];

        for (int i = 0; i < maxLen; i++) {
            coeffs[i] = this.coefficients[i] - poly.coefficients[i];
        }

        return new Polynomial(coeffs);
    }

    public boolean equals(Polynomial poly) {
        if (this.n != poly.n){
            return false;
        }
        for (int i = 0; i < this.n; i++){
            if (this.coefficients[i] != poly.coefficients[i]){
                return false;
            }
        }
        return true;
    }

    public Polynomial times(Polynomial poly) {
        int[] mult = new int[poly.n + this.n - 1];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < poly.n; j++) {
                mult[i + j] += this.coefficients[i] * poly.coefficients[j];
            }
        }

        return new Polynomial(mult);
    }

    public Polynomial differentiate(int deg) {
        for (int i = 0; i < deg; i++) {
            for (int j = 0; j < this.n - 1; j++) {
                this.coefficients[j] *= this.n - j - 1;
            }
            this.n -= 1;
            this.coefficients = Arrays.copyOfRange(this.coefficients, 0, this.n);
        }
        return this;
    }

    public long evaluate(long x) {
        long res = 0;
        for (int i = 0; i < this.n - 1; i++) {
            res += this.coefficients[i] * Math.pow(x, n - i - 1);
        }
        res += this.coefficients[n - 1];
        return res;
    }

}


