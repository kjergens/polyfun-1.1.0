import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import lib.PolyPair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;


/**
 * Randomly generate 20 polynomials in the original library and in the refactored library and make sure they match.
 * This test takes a long time so only 20 iterations.
 */

@RunWith(value = Parameterized.class)
public class ParameterizedRaiseTo {
    private String polyOrig;
    private String polyRefactored;
    private static final int NUM_TESTS = 20;

    // Inject via constructor
    public ParameterizedRaiseTo(String polyOrig, String polyRefactored) {
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
            int exponent = i % 5 - (i - NUM_TESTS / 2) % 5;

            // Get the string

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            polyPair.polynomialOrig.to(exponent).print();
            polyParams[i][0] = outContent.toString();

            polyParams[i][1] = polyPair.polynomialRefactored.raiseTo(exponent).toString();

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
