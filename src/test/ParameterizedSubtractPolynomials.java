import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import testlib.ParameterCreator;
import testlib.PolyPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * Randomly generate 1000 polynomials in the v6 library and in the v11 library and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedSubtractPolynomials {
    private String poly_v6;
    private String polly_v11;

    // Inject via constructor
    public ParameterizedSubtractPolynomials(String poly_v6, String polly_v11) {
        this.poly_v6 = poly_v6;
        this.polly_v11 = polly_v11;
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
                polyPair = ParameterCreator.createRandomPolyPairWithCoefArray();
                polyPair2 = ParameterCreator.createRandomPolyPairWithCoefArray();
            } else if (i % 5 == 1) {
                /* Create 20% with Polynomial(double constant) constructor */
                polyPair = ParameterCreator.createRandomPolyPairWithConstant();
                polyPair2 = ParameterCreator.createRandomPolyPairWithConstant();
            } else if (i % 5 == 2) {
                /* Create 20% with Polynomial(double[] numericalCoefficients) constructor */
                polyPair = ParameterCreator.createRandomPolyPairWithDoubleArray();
                polyPair2 = ParameterCreator.createRandomPolyPairWithDoubleArray();
            } else {
                /* Create 40% with Polynomial(Term term, int degree) constructor */
                polyPair = ParameterCreator.createRandomPolyPairWithTermDegree();
                polyPair2 = ParameterCreator.createRandomPolyPairWithTermDegree();
            }

            /* Subtract the polys */
            polyfun.Polynomial difference_v6 = polyPair.polynomial_v6.minus(polyPair2.polynomial_v6);
            Polynomial difference_v11 = polyPair.polynomial_v11.minus(polyPair2.polynomial_v11);

            // Get the strings

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            difference_v6.print();
            polyParams[i][0] = outContent.toString();

            // Point System.out back to console.
            System.setOut(originalOut);

            polyParams[i][1] = difference_v11.toString();
        }

        return Arrays.asList(polyParams);
    }

    @Test
    public void test_RandomPolynomials_Compare_v6_v11() {
        Assert.assertEquals(poly_v6, polly_v11);
    }

}