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
    private String poly_orig;
    private String poly_refactored;

    // Inject via constructor
    public ParameterizedMultiplyPolynomials(String poly_orig, String poly_refactored) {
        this.poly_orig = poly_orig;
        this.poly_refactored = poly_refactored;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyParams = new String[1000][2];

        for (int i = 0; i < polyParams.length; i++) {
            PolyPair polyPair;
            PolyPair polyPair2;

            if (i % 5 == 0) {
                /* Create 20% of the polynomials with the Polynomial(Coef[] coefs) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithCoefArray();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithCoefArray();
            } else if (i % 5 == 1) {
                /* Create 20% with Polynomial(double constant) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithConstant();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithConstant();
            } else if (i % 5 == 2) {
                /* Create 20% with Polynomial(double[] numericalCoefficients) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithDoubleArray();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithDoubleArray();
            } else {
                /* Create 40% with Polynomial(Term term, int degree) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithTermDegree();
                polyPair2 = PolyPairFactory.createRandomPolyPairWithTermDegree();
            }

            /* Mutiply the polys */
            polyfun.Polynomial product_orig = polyPair.polynomial_orig.times(polyPair2.polynomial_orig);
            Polynomial product_refactored = polyPair.polynomial_refactored.times(polyPair2.polynomial_refactored);

            // Get the strings

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            product_orig.print();
            polyParams[i][0] = outContent.toString();

            // Point System.out back to console.
            System.setOut(originalOut);

            polyParams[i][1] = product_refactored.toString();
        }

        return Arrays.asList(polyParams);
    }

    @Test
    public void test_RandomPolynomials_Compare_orig_refactored() {
        Assert.assertEquals(poly_orig, poly_refactored);
    }

}