import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

// TODO: add test cases with negative coefficients.
public class PolynomialTest {

    // Create output streams to capture .print() output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Before
    public void setUp() {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        // Point System.out back to console.
        System.setOut(originalOut);
    }

    @Test
    public void createAndCompareRandomPolyPairs() {
        PolyPair polyPair = new PolyPair();

        // Print to System.err
        System.err.println(polyPair.polynomial_v11);

        // Compare each part
        comparePolynomials(polyPair.polynomial_v6, polyPair.polynomial_v11);

        // Compare printed string
        polyPair.polynomial_v6.print();
        Assert.assertEquals(outContent.toString(), polyPair.polynomial_v11.toString());

    }

    @Test
    public void printPolynomials_CompareV6V11() {
        PolyPair polyPair = new PolyPair();

        polyPair.polynomial_v6.print();
        System.err.println(outContent.toString());
        Assert.assertEquals(outContent.toString(), polyPair.polynomial_v11.toString());
    }

    @Test
    public void polynomialParts_CompareV6V11() {
        PolyPair polyPair = new PolyPair();

        comparePolynomials(polyPair.polynomial_v6, polyPair.polynomial_v11);
    }

    @Test
    public void addPolynomialsToSelf_CompareV6V11() {
        PolyPair polyPair = new PolyPair();

        polyfun.Polynomial sum_v6 = polyPair.polynomial_v6.plus(polyPair.polynomial_v6);
        Polynomial sum_v11 = polyPair.polynomial_v11.plus(polyPair.polynomial_v11);
        sum_v6.print();
        Assert.assertEquals(outContent.toString(), sum_v11.toString());
    }

    @Test
    public void multiplyPolynomialsToSelf_CompareV6V11() {
        PolyPair polyPair = new PolyPair();
        PolyPair productPair = new PolyPair(polyPair.polynomial_v6.times(polyPair.polynomial_v6),
                polyPair.polynomial_v11.times(polyPair.polynomial_v11));

        productPair.polynomial_v6.print();
        Assert.assertEquals(outContent.toString(), productPair.polynomial_v11.toString());
    }

    @Test
    public void addTangent_CompareV6V11() {
        PolyPair polyPair = new PolyPair();
        PolyPair sumPair = new PolyPair(polyPair.polynomial_v6.addTangent(),
                polyPair.polynomial_v11.addTangent());

        sumPair.polynomial_v6.print();
        Assert.assertEquals(outContent.toString(), sumPair.polynomial_v11.toString());
    }

    @Test
    public void testEvaluate_CompareToPolyfunOld() {
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);

        double old_result = polyPair.polynomial_v6.evaluate(3).getTerms()[0].getTermDouble();
        double new_result = polyPair.polynomial_v11.evaluate(3).getTerms()[0].getNumericalCoefficient();

        Assert.assertTrue(old_result == new_result);
    }

    @Test
    public void testPlus() {
        double[] coefficients = {1, -3, 0, 2};
        Polynomial poly = new Polynomial(coefficients);

        // Create the same polynomial in a different way.
        Polynomial a = new Polynomial(2.0, 3); // creates the "polynomial" 2x^3
        Polynomial b = new Polynomial(-3.0, 1); // creates the "polynomial" -3x
        Polynomial c = new Polynomial(1.0);     // creates the constant "polynomial" 1

        //Create our polynomial by adding the three we have just created.
        Polynomial P = a.plus(b.plus(c));

        Assert.assertEquals(poly.toString(), P.toString());
    }

    @Test
    public void testEvaluate() {
        double[] coefficients = {1, -3, 0, 2};
        Polynomial poly = new Polynomial(coefficients);

        double y = poly.evaluate(3).getTerms()[0].getNumericalCoefficient();

        Assert.assertTrue(46.0 == y);
    }

    @Test
    public void testTo_CompareV6V11() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);

        // Raise both to the power of 3
        polyfun.Polynomial old_result = polyPair.polynomial_v6.to(3);
        Polynomial new_result = polyPair.polynomial_v11.to(3);

        // Compare both
        comparePolynomials(old_result, new_result);
    }

    @Test
    public void testToRandom_CompareV6V11() {
        PolyPair polyPair = new PolyPair();
        PolyPair raisePolys = new PolyPair(polyPair.polynomial_v6.to(5), polyPair.polynomial_v11.to(5));

        // Compare parts
        comparePolynomials(raisePolys.polynomial_v6, raisePolys.polynomial_v11);

        // Also test printed versions
        raisePolys.polynomial_v6.print();
        Assert.assertEquals(outContent.toString(), raisePolys.polynomial_v11.toString());
    }

    @Test
    public void testTo_0_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);
        PolyPair raisedPolyPair = new PolyPair(polyPair.polynomial_v6.to(0),
                polyPair.polynomial_v11.to(0));

        // Compare both
        comparePolynomials(raisedPolyPair.polynomial_v6, raisedPolyPair.polynomial_v11);
    }

    @Test
    public void testPlus_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Raise both to the power of 0
        polyfun.Polynomial oldResult = oldPoly.plus(oldPoly);
        Polynomial newResult = newPoly.plus(newPoly);

        // Compare both
        comparePolynomials(oldResult, newResult);
    }

    @Test
    public void testTimes_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Raise both to the power of 0
        polyfun.Polynomial oldResult = oldPoly.times(oldPoly);
        Polynomial newResult = newPoly.times(newPoly);

        // Compare both
        comparePolynomials(oldResult, newResult);
    }

    @Test
    public void testMinus_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};
        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Create another 2 identical polynomials
        double[] coefficients2 = {4, -3, 2};
        polyfun.Polynomial oldPoly2 = new polyfun.Polynomial(coefficients2);
        Polynomial newPoly2 = new Polynomial(coefficients2);

        // Subtract from self.
        polyfun.Polynomial oldResult = oldPoly.minus(oldPoly2);
        Polynomial newResult = newPoly.minus(newPoly2);

        // Compare both
        comparePolynomials(oldResult, newResult);
    }

    @Test
    public void testOf_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Compose new poly of the poly and itself
        polyfun.Polynomial oldResult = oldPoly.of(oldPoly);
        Polynomial newResult = newPoly.of(newPoly);

        // Compare both
        comparePolynomials(oldResult, newResult);
    }

    @Test
    public void testAddTangent_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Compose new poly of the poly and itself
        polyfun.Polynomial oldResult = oldPoly.addTangent();
        Polynomial newResult = newPoly.addTangent();

        // Compare both
        comparePolynomials(oldResult, newResult);
    }

    private void comparePolynomials(polyfun.Polynomial oldPoly, Polynomial newPoly) {
        // Compare number of coefficients.
        polyfun.Coef[] oldCoefs = oldPoly.getCoefficients();
        Coef[] newCoefs = newPoly.getCoefs();

        Assert.assertEquals(oldCoefs.length, newCoefs.length);

        // Compare Coef by Coef.
        for (int i = 0; i < oldCoefs.length; i++) {
            compareCoefs(oldCoefs[i], newCoefs[i]);
        }
    }

    private void compareCoefs(polyfun.Coef oldCoef, Coef newCoef) {
        polyfun.Term[] oldTerms = oldCoef.getTerms();
        Term[] newTerms = newCoef.getTerms();

        Assert.assertEquals(oldTerms.length, newTerms.length);

        // For each Term array, compare Term by Term
        for (int j = 0; j < oldTerms.length; j++) {
            compareTerms(oldTerms[j], newTerms[j]);
        }
    }

    private void compareTerms(polyfun.Term oldTerm, Term newTerm) {
        // Compare term coefficients
        Assert.assertTrue(oldTerm.getTermDouble() == newTerm.getNumericalCoefficient());

        // For each Term, compare Atom array by Atom array
        polyfun.Atom[] oldAtoms = oldTerm.getTermAtoms();
        Atom[] newAtoms = newTerm.getAtoms();

        Assert.assertEquals(oldAtoms.length, newAtoms.length);

        // For each Atom, compare the parts.
        for (int i = 0; i < newAtoms.length; i++) {
            Assert.assertEquals(oldAtoms[i].getLetter(), newAtoms[i].getLetter());
            Assert.assertEquals(oldAtoms[i].getPower(), newAtoms[i].getPower());
            Assert.assertEquals(oldAtoms[i].getSubscript(), newAtoms[i].getSubscript());
        }
    }

    private class PolyPair {
        polyfun.Polynomial polynomial_v6;
        Polynomial polynomial_v11;

        /**
         * Default constructor creates a random pair of matching polynomials, one for v6 and one for v11.
         * Uses Coefs[] constructor to make Polynomials.
         */
        public PolyPair() {
            Random random = new Random();

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
            this.polynomial_v6 = new polyfun.Polynomial(oldCoefs);
            this.polynomial_v11 = new Polynomial(newCoefs);
        }

        /**
         * Construct with given numericalCoefficients array.
         * Uses double[] constructor to make Polynomials.
         */
        public PolyPair(double[] numericalCoefficients) {

            // Finally, create 2 (hopefully) identical Polynomials
            this.polynomial_v6 = new polyfun.Polynomial(numericalCoefficients);
            this.polynomial_v11 = new Polynomial(numericalCoefficients);
        }

        /**
         * Construct with given polyfun.Polynomial and Polynomial
         * Uses double[] constructor to make Polynomials.
         */
        public PolyPair(polyfun.Polynomial p6, Polynomial p11) {
            this.polynomial_v6 = p6;
            this.polynomial_v11 = p11;
        }
    }

}
