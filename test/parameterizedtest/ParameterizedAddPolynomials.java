package parameterizedtest;

import org.dalton.polyfun.Polynomial;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import unittest.testlib.PolyPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import unittest.testlib.*;


/**
 * Randomly generate 1000 polynomials in the original and in the refactored polyfun and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedAddPolynomials {
    private String polyOrig;
    private String polyRefactored;

    private static final int NUM_TESTS = 1000;


    // Inject via constructor
    public ParameterizedAddPolynomials(String polyOrig, String polyRefactored) {
        this.polyOrig = polyOrig;
        this.polyRefactored = polyRefactored;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyParams = new String[NUM_TESTS][2];

        for (int i = 0; i < polyParams.length; i++) {
            PolyPair polyPair = PolyPairFactory.createPolyPairBasedOnIndex(i);
            PolyPair polyPair2 = PolyPairFactory.createPolyPairBasedOnIndex(i);

            /* Add the polys */
            polyfun.Polynomial sum_orig = polyPair.polynomialOrig.plus(polyPair2.polynomialOrig);
            Polynomial sum_refactored = polyPair.polynomialRefactored.plus(polyPair2.polynomialRefactored);

            // Get the strings

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;

            // DEBUG #25
            if (sum_refactored.toString().equals("3.15+3.5a_2c_2^3c_3+7.94d_2^4+6.78d_2^7")) {
                System.err.println(polyPair.polynomialRefactored.toString() + " + " + polyPair2.polynomialRefactored.toString());
                for (org.dalton.polyfun.Coef c : polyPair.polynomialRefactored.getCoefs()) {
                    for (int j = 0; j < c.getTerms().length; j++) {
                        System.err.println(j + ": " + c.getTerms()[j]);
                    }
                }

                sum_refactored = polyPair.polynomialRefactored.plus(polyPair2.polynomialRefactored);

            }
            System.setOut(new PrintStream(outContent));

            sum_orig.print();
            polyParams[i][0] = outContent.toString();

            // Point System.out back to console.
            System.setOut(originalOut);

            polyParams[i][1] = sum_refactored.toString();
        }

        return Arrays.asList(polyParams);
    }

    @Test
    @Ignore
    public void compareByStrings() {
        PolyPairFactory.compareByStrings(polyOrig, polyRefactored);
    }

    @Test
    public void testPolynomialsCompareAllTermsExistOrigRefactored() {
        PolyPairFactory.compareByPartsIgnoringOrder(polyOrig, polyRefactored);
    }
}