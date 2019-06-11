package testlib;

import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;

import java.util.Random;

public class PolyPairFactory {
    public static PolyPair createRandomPolyPairWithCoefArray() {
        PolyPair polyPair = new PolyPair();
        Random random = new Random();

        // Get random length from 2 - 6 (so they will be at least a 1 degree poly)
        int numCoefficients = random.nextInt(5) + 2;

        // Create 2 identical Coef arrays.
        polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
        Coef[] newCoefs = new Coef[numCoefficients];

        // Fill them using randomly selected numericalCoefficients.
        for (int j = 0; j < oldCoefs.length; j++) {
            double numericalCoefficient = random.nextDouble() * random.nextInt(10);
            oldCoefs[j] = new polyfun.Coef(numericalCoefficient);
            newCoefs[j] = new Coef(numericalCoefficient);
        }

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomial_orig = new polyfun.Polynomial(oldCoefs);
        polyPair.polynomial_refactored = new Polynomial(newCoefs);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithConstant() {
        PolyPair polyPair = new PolyPair();
        Random random = new Random();

        double constant = random.nextDouble() * 10;
        polyPair.polynomial_orig = new polyfun.Polynomial(constant);
        polyPair.polynomial_refactored = new Polynomial(constant);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithDoubleArray() {
        PolyPair polyPair = new PolyPair();
        Random random = new Random();

        int arrayLen = random.nextInt(10) + 1;

        double[] numericalCoefficients = new double[arrayLen];

        for (int j = 0; j < arrayLen; j++) {
            numericalCoefficients[j] = random.nextDouble() * 10;
        }

        polyPair.polynomial_orig = new polyfun.Polynomial(numericalCoefficients);
        polyPair.polynomial_refactored = new Polynomial(numericalCoefficients);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithTermDegree() {
        PolyPair polyPair = new PolyPair();
        Random random = new Random();

        // How many Atoms
        int atomArrayLen = random.nextInt(10) + 1;
        polyfun.Atom[] atoms_Orig = new polyfun.Atom[atomArrayLen];
        Atom[] atoms = new Atom[atomArrayLen];

        // Create Atom arrays for both original and refactored polyfun
        for (int j = 0; j < atomArrayLen; j++) {
            char letter = (char) (random.nextInt(5) + 97);
            int subscript = random.nextInt(5) + 1;
            int power = random.nextInt(5);

            atoms_Orig[j] = new polyfun.Atom(letter, subscript, power);
            atoms[j] = new Atom(letter, subscript, power);
        }

        // Create Term for both original and refactored
        double numericalCoefficient = random.nextDouble() * 10;
        polyfun.Term term_Orig = new polyfun.Term(numericalCoefficient, atoms_Orig);
        Term term = new Term(numericalCoefficient, atoms);

        // Finally, create Polynomial for both original and refactored
        int degree = random.nextInt(4);
        polyPair.polynomial_orig = new polyfun.Polynomial(term_Orig, degree);
        polyPair.polynomial_refactored = new Polynomial(term, degree);

        return polyPair;
    }
}
