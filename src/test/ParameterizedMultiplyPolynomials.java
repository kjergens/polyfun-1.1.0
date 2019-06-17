import org.dalton.polyfun.Polynomial;
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
 * Randomly generate 1000 polynomials in the orig library and in the refactored library and make sure they match.
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
            PolyPair polyPair = PolyPairFactory.createPolyPairBasedOnIndex(i);
            PolyPair polyPair2 = PolyPairFactory.createPolyPairBasedOnIndex(i);

            // Mutiply the polys
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
    public void compareByStrings() {
        PolyPairFactory.compareByStrings(polyOrig, polyRefactored);
    }

    @Test
    public void testPolynomialsCompareOrigRefactored() {
        PolyPairFactory.compareByPartsIgnoringOrder(polyOrig, polyRefactored);
    }

}