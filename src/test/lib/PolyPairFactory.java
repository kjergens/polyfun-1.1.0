import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;
import org.junit.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import lib.PolyPair;
import lib.CoefPair;

public class PolyPairFactory {

    static Random random = new Random(1);

    public static PolyPair createRandomPolyPairWithDegree() {
        PolyPair polyPair = new PolyPair();

        int degree = random.nextInt(5) + 1;

        polyPair.polynomialOrig = new polyfun.Polynomial(degree);
        polyPair.polynomialRefactored = new org.dalton.polyfun.Polynomial(degree);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithNumCoefDegree() {
        PolyPair polyPair = new PolyPair();

        int degree = random.nextInt(5) + 1;

        double numericalCoefficient = random.nextDouble() * random.nextInt(10);
        BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
        numericalCoefficient = bd.doubleValue();

        polyPair.polynomialOrig = new polyfun.Polynomial(numericalCoefficient, degree);
        polyPair.polynomialRefactored = new org.dalton.polyfun.Polynomial(numericalCoefficient, degree);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithLetterDegree() {
        PolyPair polyPair = new PolyPair();

        char letter = (char) (random.nextInt(5) + 97);
        int degree = random.nextInt(5) + 1;

        polyPair.polynomialOrig = new polyfun.Polynomial(letter, degree);
        polyPair.polynomialRefactored = new org.dalton.polyfun.Polynomial(letter, degree);

        return polyPair;
    }


    /**
     * Use Polynomial(Coef[]) and Coef(Term[]) and Term(numericalCoefficient, Atom[])
     * @return Poly pair - an old polyfun Polynomial and a new org.dalton.polyfun Polynomial
     */
    public static PolyPair createRandomPolyPairWithAbstractCoefArray() {
        PolyPair polyPair = new PolyPair();
        // Get random length from 2 - 4 (so they will be at least a 1 degree poly)
        int numCoefficients = random.nextInt(3) + 2;

        // Create 2 identical Coef arrays.
        polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
        Coef[] newCoefs = new Coef[numCoefficients];

        // Fill them using randomly selected Terms.
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

    public static PolyPair createRandomPolyPairWithAbstractCoef() {
        PolyPair polyPair = new PolyPair();

        // Fill them using randomly selected Terms.
        CoefPair coefPair = CoefPairFactory.createCoefPairWithTermArray();

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomialOrig = new polyfun.Polynomial(coefPair.coefOrig);
        polyPair.polynomialRefactored = new Polynomial(coefPair.coefRefactored);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithNumericalCoef() {
        PolyPair polyPair = new PolyPair();

        // Randomly selected numericalCoefficients. Precision 2 to make tests easier to read.
        double numericalCoefficient = random.nextDouble() * random.nextInt(10);
        BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
        numericalCoefficient = bd.doubleValue();

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomialOrig = new polyfun.Polynomial(new polyfun.Coef(numericalCoefficient));
        polyPair.polynomialRefactored = new Polynomial(new Coef(numericalCoefficient));

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithNumericalCoefDegree() {
        PolyPair polyPair = new PolyPair();

        int degree = random.nextInt(5) + 1;

        // Randomly selected numericalCoefficients. Precision 2 to make tests easier to read.
        double numericalCoefficient = random.nextDouble() * random.nextInt(10);
        BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
        numericalCoefficient = bd.doubleValue();

        // Finally, create 2 (hopefully) identical Polynomials
        polyPair.polynomialOrig = new polyfun.Polynomial(new polyfun.Coef(numericalCoefficient), degree);
        polyPair.polynomialRefactored = new Polynomial(new Coef(numericalCoefficient), degree);

        return polyPair;
    }

    public static PolyPair createRandomPolyPairWithNumericalCoefArray() {
        PolyPair polyPair = new PolyPair();
        // Get random length from 2 - 4 (so they will be at least a 1 degree poly)
        int numCoefficients = random.nextInt(3) + 2;

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

        int arrayLen = random.nextInt(3) + 1;

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
        int atomArrayLen = random.nextInt(3) + 1;
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

    public static void compareByStrings(String polyOrig, String polyRefactored) {
        // Leave imperfections that exist in both, to be fixed later.
        if (!polyRefactored.equals(polyOrig)) {
            // Get rid of trailing newline
            polyOrig = polyOrig.replace("\n", "");

            // The original sometimes has a bad triple +++ sign. Fixed in the refactored version.
            polyOrig = polyOrig.replace("+++", "+");

            // The original sometimes has a bad double ++ sign. Fixed in the refactored version.
            polyOrig = polyOrig.replace("++", "+");

            // Correct for the original sometimes having a bad leading + sign
            polyOrig = polyOrig.replaceAll("\\A\\+", "");
            polyOrig = polyOrig.replace("(+", "(");

            // Correct for the original sometimes having a bad trailing + sign
            polyOrig = polyOrig.replace("+)", ")");
            polyOrig = polyOrig.replaceAll("\\+\\Z", "");
        }

        Assert.assertThat(polyRefactored, is(polyOrig));
    }

    public static void compareByPartsIgnoringOrder(String polyOrig, String polyRefactored) {

        // Leave imperfections that exist in both, to be fixed later.
        if (!polyRefactored.equals(polyOrig)) {

            // The original sometimes has a bad triple +++ sign. Fixed in the refactored version.
            polyOrig = polyOrig.replace("+++", "+");

            // The original sometimes has a bad double ++ sign. Fix in the refactored version.
            polyOrig = polyOrig.replace("++", "+");

            // Correct for the original sometimes having a bad leading + sign
            polyOrig = polyOrig.replaceAll("\\A\\+", "");
            polyOrig = polyOrig.replace("(+", "(");

            // Correct for the original sometimes having a bad trailing + sign
            polyOrig = polyOrig.replaceAll("\\+\\Z", "+");

            // Get rid of trailing newline
            polyOrig = polyOrig.replace("\n", "");
        }

        // Get all the individual terms
        String[] orig = polyOrig.split("[\\(\\)\\+-]");
        String[] refactored = polyRefactored.split("[\\(\\)\\+-]");

        // Sort
        Arrays.sort(orig);
        Arrays.sort(refactored);

        // Turn into ArrayList to make removing blanks easier
        ArrayList<String> origList = new ArrayList<>(Arrays.asList(orig));
        ArrayList<String> refactoredList = new ArrayList<>(Arrays.asList(refactored));

        // Remove blanks from original
        while (origList.size() > 0 && (origList.get(0).equals(""))) {
            origList.remove(0);
        }

        // Remove blanks from refactored
        while (refactoredList.size() > 0 && refactoredList.get(0).equals("")) {
            refactoredList.remove(0);
        }

        // Assert that all terms are present, irrespective of their order
        Assert.assertThat(refactoredList.toArray(), is(origList.toArray()));
    }

    public static PolyPair createPolyPairBasedOnIndex(int i) {
        PolyPair polyPair;

        if (i % 10 == 0) {
            // Create 10% of the polynomials with the Polynomial(Coef[] coefs) and Coef(double[] nums) constructors
            polyPair = PolyPairFactory.createRandomPolyPairWithNumericalCoefArray();
        } else if (i % 10 == 1) {
            // Create 10% with Polynomial(double constant) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithConstant();
        } else if (i % 10 == 2) {
            // Create 10% with Polynomial(double[] numericalCoefficients) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithDoubleArray();
        } else if (i % 10 == 3) {
            // Create 10% with Polynomial(Term term, int degree) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithTermDegree();
        } else if (i % 10 == 4) {
            // Create 10% with the Polynomial(double numericalCoefficient, int degree) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithNumCoefDegree();
        } else if (i % 10 == 5) {
            // Create 10% with the Polynomial(Coef coef) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithAbstractCoef();
        } else if (i % 10 == 6) {
            // Create 10% with the Polynomial(char letter, int degree) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithLetterDegree();
        } else if (i % 10 == 7) {
            // Create 10% with the Polynomial(Coef coef) constructor
            polyPair = PolyPairFactory.createRandomPolyPairWithNumericalCoef();
        } else {
            // Create 20% with Polynomial(Coef[] coefs) and Coef(Term[] terms) constructors
            polyPair = PolyPairFactory.createRandomPolyPairWithAbstractCoefArray();
        }

        return polyPair;
    }
}
