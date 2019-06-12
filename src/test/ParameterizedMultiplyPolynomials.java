import org.dalton.polyfun.Polynomial;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import testlib.PolyPairFactory;
import testlib.PolyPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * Randomly generate 1000 polynomials in the v6 library and in the v11 library and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedMultiplyPolynomials {
    private String polyOrig;
    private String polyRefactored;

    private static final int NUM_TESTS = 1000;

    // Inject via constructor
    public ParameterizedMultiplyPolynomials(String polyOrig, String polyRefactored) {
        this.polyOrig = polyOrig;
        this.polyRefactored = polyRefactored;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyParams = new String[NUM_TESTS][2];

        for (int i = 0; i < polyParams.length; i++) {
            PolyPair polyPair;
            PolyPair polyPair2;

            if (i % 5 == 0) {
                /* Create 20% of the polynomials with the Polynomial(Coef[] coefs) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithNumericalCoefArray();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithNumericalCoefArray();
            } else if (i % 5 == 1) {
                /* Create 20% with Polynomial(double constant) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithConstant();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithConstant();
            } else if (i % 5 == 2) {
                /* Create 20% with Polynomial(double[] numericalCoefficients) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithDoubleArray();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithDoubleArray();
            } else if (i % 5 == 3) {
                /* Create 20% with Polynomial(Term term, int degree) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithTermDegree();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithTermDegree();
            } else {
                /* Create 20% with Polynomials made with abstract coefs */
                polyPair = PolyPairFactory.createRandomPolyPairWithAbstractCoefArray();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithAbstractCoefArray();
            }

            /* Mutiply the polys */
            polyfun.Polynomial productOrig = polyPair.polynomialOrig.times(polyPair2.polynomialOrig);
            Polynomial productRefactored = polyPair.polynomialRefactored.times(polyPair2.polynomialRefactored);

            // Get the strings

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            productOrig.print();
            polyParams[i][0] = outContent.toString();

            // Point System.out back to console.
            System.setOut(originalOut);

            polyParams[i][1] = productRefactored.toString();
        }

        return Arrays.asList(polyParams);
    }

    @Test
    public void testPolynomialsCompareOrigRefactored() {
        PolyPairFactory.compareAllTermsExistOrigRefactored(polyOrig, polyRefactored);
    }

}