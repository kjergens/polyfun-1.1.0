package testlib;

import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.core.Is.is;

public class PolyPairFactory {

    static Random random = new Random(1);

    public static PolyPair createRandomPolyPairWithAbstractCoefArray() {
        PolyPair polyPair = new PolyPair();
        // Get random length from 2 - 6 (so they will be at least a 1 degree poly)
        int numCoefficients = random.nextInt(3) + 2;

        // Create 2 identical Coef arrays.
        polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
        Coef[] newCoefs = new Coef[numCoefficients];

        // Fill them using randomly selected numericalCoefficients.
        for (int j = 0; j < oldCoefs.length; j++) {
            CoefPair coefPair = CoefPairFactory.createCoefPairWithTermArray();
            oldCoefs[j] = coefPair.coefOrig;
            newCoefs[j] = coefPair.coefRefactored;
        }

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomialOrig = new polyfun.Polynomial(oldCoefs);
        polyPair.polynomialRefactored = new Polynomial(newCoefs);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithNumericalCoefArray() {
        PolyPair polyPair = new PolyPair();
        // Get random length from 2 - 6 (so they will be at least a 1 degree poly)
        int numCoefficients = random.nextInt(5) + 2;

        // Create 2 identical Coef arrays.
        polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
        Coef[] newCoefs = new Coef[numCoefficients];

        // Fill them using randomly selected numericalCoefficients. Precision 2 to make tests easier to read.
        for (int j = 0; j < oldCoefs.length; j++) {
            double numericalCoefficient = random.nextDouble() * random.nextInt(10);
            BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
            numericalCoefficient = bd.doubleValue();

            oldCoefs[j] = new polyfun.Coef(numericalCoefficient);
            newCoefs[j] = new Coef(numericalCoefficient);
        }

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomialOrig = new polyfun.Polynomial(oldCoefs);
        polyPair.polynomialRefactored = new Polynomial(newCoefs);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithConstant() {
        PolyPair polyPair = new PolyPair();

        double constant = random.nextDouble() * 10;
        BigDecimal bd = new BigDecimal(constant).setScale(2, RoundingMode.HALF_EVEN);
        constant = bd.doubleValue();

        polyPair.polynomialOrig = new polyfun.Polynomial(constant);
        polyPair.polynomialRefactored = new Polynomial(constant);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithDoubleArray() {
        PolyPair polyPair = new PolyPair();

        int arrayLen = random.nextInt(10) + 1;

        double[] numericalCoefficients = new double[arrayLen];

        for (int j = 0; j < arrayLen; j++) {
            double numericalCoefficient = random.nextDouble() * 10;
            BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
            numericalCoefficients[j] = bd.doubleValue();
        }

        polyPair.polynomialOrig = new polyfun.Polynomial(numericalCoefficients);
        polyPair.polynomialRefactored = new Polynomial(numericalCoefficients);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithTermDegree() {
        PolyPair polyPair = new PolyPair();

        // How many Atoms
        int atomArrayLen = random.nextInt(10) + 1;
        polyfun.Atom[] atomsOrig = new polyfun.Atom[atomArrayLen];
        Atom[] atoms = new Atom[atomArrayLen];

        // Create Atom arrays for both original and refactored polyfun
        for (int j = 0; j < atomArrayLen; j++) {
            char letter = (char) (random.nextInt(5) + 97);
            int subscript = random.nextInt(5) + 1;
            int power = random.nextInt(5);

            atomsOrig[j] = new polyfun.Atom(letter, subscript, power);
            atoms[j] = new Atom(letter, subscript, power);
        }

        // Create Term for both original and refactored. Coef is precision 2 to make tests easier to read.
        double numericalCoefficient = random.nextDouble() * 10;
        BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
        numericalCoefficient = bd.doubleValue();

        polyfun.Term termOrig = new polyfun.Term(numericalCoefficient, atomsOrig);
        Term term = new Term(numericalCoefficient, atoms);

        // Finally, create Polynomial for both original and refactored
        int degree = random.nextInt(4);
        polyPair.polynomialOrig = new polyfun.Polynomial(termOrig, degree);
        polyPair.polynomialRefactored = new Polynomial(term, degree);

        return polyPair;
    }

    public static void comparePolynomials(String polyOrig, String polyRefactored) {
        // FIXME Both the original & refactored sometimes have a bad triple +++ sign. Fix in the refactored version.
        polyOrig = polyOrig.replace("+++", "+");
        polyRefactored = polyRefactored.replace("+++", "+");

        // FIXME Both the original & refactored sometimes have a bad double ++ sign. Fix in the refactored version.
        polyOrig = polyOrig.replace("++", "+");
        polyRefactored = polyRefactored.replace("++", "+");

        // Correct for the original sometimes having a bad leading + sign
        polyOrig = polyOrig.replaceAll("\\A\\+", "");
        polyOrig = polyOrig.replace("(+", "(");

        // Correct for the original sometimes having a bad trailing + sign
        polyOrig = polyOrig.replaceAll("\\+\\Z", "+");

        Assert.assertThat(polyRefactored, is(polyOrig));
    }

    public static void compareAllTermsExistOrigRefactored(String polyOrig, String polyRefactored) {

        // Leave imperfections that exist in both, to be fixed later.
        if (!polyRefactored.equals(polyOrig)) {

            // FIXME Both the original & refactored sometimes have a bad triple +++ sign. Fix in the refactored version.
            polyOrig = polyOrig.replace("+++", "+");
            //polyRefactored = polyRefactored.replace("+++", "+");

            // FIXME Both the original & refactored sometimes have a bad double ++ sign. Fix in the refactored version.
            polyOrig = polyOrig.replace("++", "+");
            //polyRefactored = polyRefactored.replace("++", "+");

            // Correct for the original sometimes having a bad leading + sign
            polyOrig = polyOrig.replaceAll("\\A\\+", "");
            polyOrig = polyOrig.replace("(+", "(");

            // Correct for the original sometimes having a bad trailing + sign
            polyOrig = polyOrig.replaceAll("\\+\\Z", "+");
        }

        // Get all the individual terms
        String[] orig = polyOrig.split("\\(|\\)|\\+");
        String[] refactored = polyRefactored.split("\\(|\\)|\\+");

        // Sort
        Arrays.sort(orig);
        Arrays.sort(refactored);

        try {
            // Assert that all terms are present irrespective of their order
            Assert.assertThat(refactored, is(orig));
        } catch (AssertionError e) {
            System.err.println("Orig: " + polyOrig);
            System.err.println("Refac:" + polyRefactored);
            throw (e);
        }
    }
}
