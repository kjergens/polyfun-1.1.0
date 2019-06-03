import java.util.Random;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;


public class PolynomialRandomTests {
    public static void main(String[] args) {
        Random random = new Random();

         // Create 2 identical polynomials

        // Create 2 identical Coef arrays
        int numCoefficients = random.nextInt(5) + 1;
        polyfun.Coef[] oldCoefs = new polyfun.Coef[numCoefficients];
        Coef[] newCoefs = new Coef[numCoefficients];

        // Fill them the same way, using numericalCoefficients
        for (int i = 0; i < oldCoefs.length; i++) {
            double numericalCoefficient = random.nextDouble() * random.nextInt(5);
            oldCoefs[i] = new polyfun.Coef(numericalCoefficient);
            newCoefs[i] = new Coef(numericalCoefficient);
        }

        // Finally, create 2 identical Polynomials
        polyfun.Polynomial oldPoly = new polyfun.Polynomial(oldCoefs);
        Polynomial newPoly = new Polynomial(newCoefs);

        // TODO: Put them through all the tests.
    }




}
