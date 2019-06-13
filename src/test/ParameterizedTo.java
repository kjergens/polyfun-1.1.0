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
 * Randomly generate 1000 polynomials in the original library and in the refactored library and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedTo {
    private String polyOrig;
    private String polyRefactored;
    private static final int NUM_TESTS = 1000;

    // Inject via constructor
    public ParameterizedTo(String polyOrig, String polyRefactored) {
        this.polyOrig = polyOrig;
        this.polyRefactored = polyRefactored;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyParams = new String[NUM_TESTS][2];

        for (int i = 0; i < polyParams.length; i++) {
            PolyPair polyPair = PolyPairFactory.createPolyPairBasedOnIndex(i);

            // Create exponent (even spread of positive and negative numbers)
            int exponent = i - (NUM_TESTS /2);

            // Get the string

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            polyPair.polynomialOrig.evaluate(exponent).print();
            polyParams[i][0] = outContent.toString();

            polyParams[i][1] = polyPair.polynomialRefactored.evaluate(exponent).toString();

            // Point System.out back to console.
            System.setOut(originalOut);
        }

        return Arrays.asList(polyParams);
    }

    @Test
    public void compareOrigRefactored() {
        PolyPairFactory.compareAllTermsExistOrigRefactored(polyOrig, polyRefactored);
    }

}
