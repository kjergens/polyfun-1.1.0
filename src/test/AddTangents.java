import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * Randomly generate 1000 polynomials in the v6 library and in the v11 library and make sure they match.
 */

@RunWith(value = Parameterized.class)
public class AddTangents {
    private String poly_v6;
    private String polly_v11;

    // Inject via constructor
    public AddTangents(String poly_v6, String polly_v11) {
        this.poly_v6 = poly_v6;
        this.polly_v11 = polly_v11;
    }

    @Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {
        // Create list of random polynomials
        String[][] polyPairs = new String[1000][2];

        Random random = new Random();

        for (int i = 0; i < polyPairs.length; i++) {
            // Get random length from 2 - 6 (so they will be at least a 1 degree poly)
            int numCoefficients = random.nextInt(5) + 2;

            // Create 2 identical Coef arrays.
            polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
            Coef[] newCoefs = new Coef[numCoefficients];

            // Fill them using randomly selected numericalCoefficients.
            for (int j = 0; j < oldCoefs.length; j++) {
                double numericalCoefficient = random.nextDouble() * random.nextInt(10);
                oldCoefs[j] = new polyfun.Coef(numericalCoefficient);
                newCoefs[j] = new Coef(numericalCoefficient);
            }

            // Finally, create 2 (hopefully) identical Polynomials
            polyfun.Polynomial polynomial_v6 = new polyfun.Polynomial(oldCoefs);
            Polynomial polynomial_v11 = new Polynomial(newCoefs);

            // Get the string

            // Point System.out to another output stream so I can capture the print() output.
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            polynomial_v6.addTangent().print();
            polyPairs[i][0] = outContent.toString();

            polyPairs[i][1] = polynomial_v11.addTangent().toString();

            // Point System.out back to console.
            System.setOut(originalOut);
        }

        return Arrays.asList(polyPairs);
    }

    @Test
    public void addTangents_Compare_v6_v11() {
        Assert.assertEquals(poly_v6, polly_v11);
    }

}
