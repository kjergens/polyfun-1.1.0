import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Polynomial;
import org.dalton.polyfun.Term;
import org.junit.Assert;
import org.junit.Test;


public class PolynomialTest {

    @Test
    public void testEvaluate_CompareToPolyfunOld() {
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        double old_result = oldPoly.evaluate(3).getTerms()[0].getTermDouble();
        double new_result = newPoly.evaluate(3).getTerms()[0].getNumericalCoefficient();

        Assert.assertEquals(old_result, new_result);
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

        Assert.assertEquals(46.0, y);
    }

    @Test
    public void testTo_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Raise both to the power of 3
        polyfun.Polynomial old_result = oldPoly.to(3);
        Polynomial new_result = newPoly.to(3);

        // Compare both
        comparePolynomials(old_result, new_result);
    }

    @Test
    public void testTo_0_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Raise both to the power of 0
        polyfun.Polynomial old_result = oldPoly.to(0);
        Polynomial new_result = newPoly.to(0);

        // Compare both
        comparePolynomials(old_result, new_result);
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
        Assert.assertEquals(oldTerm.getTermDouble(), newTerm.getNumericalCoefficient());

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
}
