import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import testlib.PolyPairFactory;
import testlib.PolyPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;

/**
 * Randomly generate 1000 polynomials in the original library and in the refactored library and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedEvaluateScalar {
    private String polyOrig;
    private String polyRefactored;
    private static final int NUM_TESTS = 1000;

    // Inject via constructor
    public ParameterizedEvaluateScalar(String poly_orig, String poly_refactored) {
        this.polyOrig = poly_orig;
        this.polyRefactored = poly_refactored;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyParams = new String[NUM_TESTS][2];

        for (int i = 0; i < polyParams.length; i++) {
            PolyPair polyPair;

            if (i % 5 == 0) {
                /* Create 20% of the polynomials with the Polynomial(Coef[] coefs) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithNumericalCoefArray();
            } else if (i % 5 == 1) {
                /* Create 20% with Polynomial(double constant) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithConstant();
            } else if (i % 5 == 2) {
                /* Create 20% with Polynomial(double[] numericalCoefficients) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithDoubleArray();
            } else if (i % 5 == 3) {
                /* Create 20% with Polynomial(Term term, int degree) constructor */
                polyPair = PolyPairFactory.createRandomPolyPairWithTermDegree();
            } else {
                /* Create 20% with Polynomial(Coef[] coefs) and Coef(Term[] terms) constructors */
                polyPair = PolyPairFactory.createRandomPolyPairWithAbstractCoefArray();
            }

            // Create scalar (even spread of positive and negative numbers), make precision 2 so easier to read tests
            double scalar = ((double) i * .123) - ((double) NUM_TESTS * .123)/2;
            BigDecimal bd = new BigDecimal(scalar).setScale(2, RoundingMode.HALF_EVEN);
            scalar = bd.doubleValue();

            // Get the string

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            polyPair.polynomialOrig.evaluate(scalar).print();
            polyParams[i][0] = outContent.toString();

            polyParams[i][1] = polyPair.polynomialRefactored.evaluate(scalar).toString();

            // Point System.out back to console.
            System.setOut(originalOut);
        }

        return Arrays.asList(polyParams);
    }

    @Test
    public void testCompareOrigRefactored() {
        PolyPairFactory.compareAllTermsExistOrigRefactored(polyOrig, polyRefactored);
    }

}
