package unittest;

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

import unittest.testlib.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

// TODO: add test cases with negative coefficients.
public class PolynomialTest {

    // Create output streams to capture .print() output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        // Point System.out back to console.
        System.setOut(System.out);
    }

    @Test
    public void printPolynomials_CompareVersions() {
        PolyPair polyPair = new PolyPair();

        polyPair.polynomialOrig.print();
        Assert.assertThat(polyPair.polynomialRefactored.toString(), is(outContent.toString().replace("\n", "")));
    }

    @Test
    public void addPolynomialsToSelf_CompareVersions() {
        PolyPair polyPair = new PolyPair();

        polyfun.Polynomial sum_orig = polyPair.polynomialOrig.plus(polyPair.polynomialOrig);
        Polynomial sum_refactored = polyPair.polynomialRefactored.plus(polyPair.polynomialRefactored);
        sum_orig.print();
        Assert.assertEquals(outContent.toString().replace("\n", ""), sum_refactored.toString());
    }

    @Test
    public void addPolynomialsTest25() {
        // 3.15+6.78d_2^7 + 3.5a_2c_2^3c_3+7.94d_2^4
        // 3.15+7.94d_2^4+6.78d_2^7+3.5a_2c_2^3c_3

        // Polynomial A: 3.15+6.78d_2^7
        // 3.15
        Atom[] atoms = new Atom[0];
        Term termA = new Term(3.15, atoms);

        // 6.78d_2^7
        atoms = new Atom[]{new Atom('d', 2, 7)};
        Term termB = new Term(6.78, atoms);

        Coef[] coefs = new Coef[]{new Coef(new Term[]{termA, termB})};
        Polynomial polynomialA = new Polynomial(coefs);

        // Polynomial B: 3.5a_2c_2^3c_3+7.94d_2^4
        // 3.5a_2c_2^3c_3
        atoms = new Atom[]{new Atom('a', 2, 1),
                new Atom('c', 2, 3),
                new Atom('c', 3, 1)
        };
        termA = new Term(3.5, atoms);

        // 7.94d_2^4
        atoms = new Atom[]{new Atom('d', 4, 2)};
        termB = new Term(7.94, atoms);

        coefs = new Coef[]{new Coef(new Term[]{termA, termB})};
        Polynomial polynomialB = new Polynomial(coefs);

        Polynomial sum = polynomialA.plus(polynomialB);

        assertThat(sum.toString(), is("3.5a_2c_2^3c_3+6.78d_2^7+7.94d_4^2+3.15"));
    }

    @Test
    public void multiplyPolynomialsToSelf_CompareVersions() {
        PolyPair polyPair = new PolyPair();
        PolyPair productPair = new PolyPair(polyPair.polynomialOrig.times(polyPair.polynomialOrig),
                polyPair.polynomialRefactored.times(polyPair.polynomialRefactored));

        productPair.polynomialOrig.print();
        assertThat(productPair.polynomialRefactored.toString(), is(outContent.toString().replace("\n", "").replace("\n", "")));
    }

    @Test
    public void addTangentDegree2() {
        // (0.9310232355416748)X^2+(4.597562722187815)X+0.8491604427928394
        // Expected: (0.9310232355416748)X^2+(m+4.597562722187815)X+b+0.8491604427928394
        Polynomial polynomial = new Polynomial(new double[]{
                0.8491604427928394,
                4.597562722187815,
                0.9310232355416748});
        Polynomial tangent = polynomial.addTangent();
        assertThat(tangent.toString(), is("(0.9310232355416748)X^2+(m+4.597562722187815)X+b+0.8491604427928394"));
    }

    @Test
    public void testEvaluate_CompareToPolyfunOld() {
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);

        double old_result = polyPair.polynomialOrig.evaluate(3).getTerms()[0].getTermDouble();
        double new_result = polyPair.polynomialRefactored.eval(3);

        assertTrue(old_result == new_result);
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

        double y = poly.eval(3);

        assertTrue(46.0 == y);
    }

    @Test
    public void testTo_CompareVersions() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);

        // Raise both to the power of 3
        polyfun.Polynomial old_result = polyPair.polynomialOrig.to(3);
        Polynomial new_result = polyPair.polynomialRefactored.to(3);

        // Compare strings
        old_result.print();
        assertThat(new_result.toString(), is(outContent.toString().replace("\n", "")));
    }

    @Test
    public void raiseTo3Degree3() {
        // (2.0)X^3+(-3.0)X+1.0
        double[] coefficients = {1, -3, 0, 2};

        Polynomial polynomial = new Polynomial(coefficients);

        // Raise to the power of 3
        polynomial = polynomial.raiseTo(3);

        assertThat(polynomial.toString(), is("(8.0)X^9+(-36.0)X^7+(12.0)X^6+(54.0)X^5+(-36.0)X^4+(-21.0)X^3+(27.0)X^2+(-9.0)X+1.0"));
    }

    @Test
    public void raiseTo5() {
        // (3.0259470350715567)X^2+3.117598099675224
        //(253.6919053370493)X^10+(1306.879123816304)X^8+(2692.924777395241)X^6+(2774.4891339042215)X^4+(1429.2619717358689)X^2+294.5104032144064
        Polynomial polynomial = new Polynomial(new double[]{3.117598099675224, 0,3.0259470350715567 });
        polynomial = polynomial.raiseTo(5);
        assertThat(polynomial.toString(), is(
                "(253.6919053370493)X^10+(1306.879123816304)X^8+(2692.924777395241)X^6+(2774.4891339042215)X^4+(1429.2619717358689)X^2+294.5104032144064"));
    }

    @Test
    public void to_0_CompareToPolyfunOld() {
        // Create 2 identical polynomials
        double[] coefficients = {1, -3, 0, 2};

        PolyPair polyPair = new PolyPair(coefficients);
        PolyPair raisedPolyPair = new PolyPair(polyPair.polynomialOrig.to(0),
                polyPair.polynomialRefactored.to(0));

        // Compare both
        comparePolynomials(raisedPolyPair.polynomialOrig, raisedPolyPair.polynomialRefactored);
    }

    @Test
    public void plusSelfDegree3CompareToPolyfunOld() {
        // Create 2 identical polynomials
        // (2.0)X^3-3.0X+1
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Add each to themselves
        polyfun.Polynomial oldResult = oldPoly.plus(oldPoly);
        Polynomial newResult = newPoly.plus(newPoly);

        oldResult.print();

        // Compare strings
        assertThat(newResult.toString(), is(outContent.toString().replace("\n", "")));
    }

    @Test
    public void plusSelfDegree1CompareToPolyfunOld() {
        // X
        double[] coefficients = {0, 1};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Add each to themselves
        polyfun.Polynomial oldResult = oldPoly.plus(oldPoly);
        Polynomial newResult = newPoly.plus(newPoly);

        oldResult.print();

        // Compare strings
        assertThat(newResult.toString(), is(outContent.toString().replace("\n", "")));
        assertThat(newResult.toString(), is("(2.0)X"));
    }

    @Test
    public void testTimes_CompareToPolyfunOld() {
        // (2.0)X^3+(-3.0)X+1.0
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Raise both to the power of 0
        polyfun.Polynomial oldResult = oldPoly.times(oldPoly);
        Polynomial newResult = newPoly.times(newPoly);

        // Compare strings
        // Expected: (4.0)X^6+(-12.0)X^4+(4.0)X^3+(9.0)X^2+(-6.0)X+1.0
        oldResult.print();
        assertThat(newResult.toString(), is(outContent.toString().replace("\n", "")));
    }

    @Test
    public void constructorCoefDegree() {
        // (a_1^2b_2^3b_3^4+b_3^4d_1^2d_3+c_1^4e_2)X

        // a_1^2b_2^3b_3^4
        Atom[] atoms = new Atom[]{new Atom('a', 1, 2),
                new Atom('b', 2, 3),
                new Atom('b', 3, 4)
        };
        Term termA = new Term(1, atoms);

        // b_3^4d_1^2d_3
        atoms = new Atom[]{new Atom('b', 3, 4),
                new Atom('d', 1, 2),
                new Atom('d', 3, 1)
        };
        Term termB = new Term(1, atoms);

        // c_1^4e_2
        atoms = new Atom[]{new Atom('c', 1, 4),
                new Atom('e', 2, 1)
        };
        Term termC = new Term(1, atoms);

        Coef coef = new Coef(new Term[]{termA, termC, termB});
        Polynomial poly = new Polynomial(coef, 1);

        assertThat(poly.toString(), is("(a_1^2b_2^3b_3^4+b_3^4d_1^2d_3+c_1^4e_2)X"));
    }

    @Test
    public void constructorDegree() {
        Polynomial polynomial = new Polynomial(2);
        assertThat(polynomial.toString(), is("X^2"));
    }

    @Test
    public void constructorCoefArray() {
        // (a_1^2b_2^3b_3^4+b_3^4d_1^2d_3+c_1^4e_2)X
        Term[] terms = new Term[3];

        // a_1^2b_2^3b_3^4
        Atom[] atoms = new Atom[]{new Atom('a', 1, 2),
                new Atom('b', 2, 3),
                new Atom('b', 3, 4)
        };
        terms[2] = new Term(1, atoms);

        // b_3^4d_1^2d_3
        atoms = new Atom[]{new Atom('b', 3, 4),
                new Atom('d', 1, 2),
                new Atom('d', 3, 1)
        };
        terms[1] = new Term(1, atoms);

        // c_1^4e_2
        atoms = new Atom[]{new Atom('c', 1, 4),
                new Atom('e', 2, 1)
        };
        terms[0] = new Term(1, atoms);

        Coef coef = new Coef(terms);

        // Implies a one-degree with a 0 in the 0-deg spot
        Coef[] coefs = new Coef[]{new Coef(0), coef};

        Polynomial poly = new Polynomial(coefs);

        assertThat(poly.toString(), is("(a_1^2b_2^3b_3^4+b_3^4d_1^2d_3+c_1^4e_2)X"));
    }

    @Test(expected = AssertionError.class)
    public void getCoefficientAtTermTermWithAtoms() {
        // (a_1^2b_2^3b_3^4+b_3^4d_1^2d_3+c_1^4e_2)X
        Term[] terms = new Term[3];

        // a_1^2b_2^3b_3^4
        Atom[] atoms = new Atom[]{new Atom('a', 1, 2),
                new Atom('b', 2, 3),
                new Atom('b', 3, 4)
        };
        terms[2] = new Term(1, atoms);

        // b_3^4d_1^2d_3
        atoms = new Atom[]{new Atom('b', 3, 4),
                new Atom('d', 1, 2),
                new Atom('d', 3, 1)
        };
        terms[1] = new Term(1, atoms);

        // c_1^4e_2
        atoms = new Atom[]{new Atom('c', 1, 4),
                new Atom('e', 2, 1)
        };
        terms[0] = new Term(1, atoms);

        Coef coef = new Coef(terms);

        // Implies a one-degree with a 0 in the 0-deg spot
        Coef[] coefs = new Coef[]{new Coef(0), coef};

        Polynomial poly = new Polynomial(coefs);

        poly.getCoefficientAtTerm(1);
    }

    @Test
    public void getCoefficientAtTermMultipleTerms() {
        Atom[] atoms = {new Atom('a', 1, 2)};
        Coef[] coefs = {new Coef(new Term(3, atoms))};
        Polynomial polynomial = new Polynomial(coefs);
        assertThat(polynomial.getCoefficientAtTerm(0), is(1.0));
    }

    @Test
    public void getCoefficientAtTermBigDegree() {
        double[] coefficients = {1, -3, 0, 2};
        Polynomial polynomial = new Polynomial(coefficients);
        assertThat(polynomial.getCoefficientAtTerm(13), is(0.0));
    }

    @Test
    public void getCoefficientAtTerm() {
        double[] coefficients = {1, -3, 0, 2};
        Polynomial polynomial = new Polynomial(coefficients);
        assertThat(polynomial.getCoefficientAtTerm(3), is(2.0));
    }

    @Test
    public void getCoefficientAtTermTermTooBig() {
        double[] coefficients = {1, -3, 0, 2};
        Polynomial polynomial = new Polynomial(coefficients);
        assertThat(polynomial.getCoefficientAtTerm(13), is(0.0));
    }

    @Test
    public void orderOfAtomArrays0Degree() {
        // a_1^2b_2^3b_3^4 + c_1^4e_2

        // a_1^2b_2^3b_3^4
        Atom[] atoms = new Atom[]{new Atom('a', 1, 2),
                new Atom('b', 2, 3),
                new Atom('b', 3, 4)
        };
        Term termA = new Term(1, atoms);

        // c_1^4e_2
        atoms = new Atom[]{new Atom('c', 1, 4),
                new Atom('e', 2, 1)
        };
        Term termB = new Term(1, atoms);

        Coef coef = new Coef(new Term[]{termB, termA});
        Polynomial poly = new Polynomial(coef, 0);

        assertThat(poly.toString(), is("a_1^2b_2^3b_3^4+c_1^4e_2"));
    }

    @Test
    public void evaluateToNumber() {
        Polynomial polynomial = new Polynomial(2);
        assertThat(polynomial.eval(3), is(9.0));
    }

    @Test
    public void evaluateToNumberDoubleArray() {
        Polynomial polynomial = new Polynomial(new double[]{0, 3, 2});
        assertThat(polynomial.eval(3), is(27.0));
    }

    @Test
    public void minusCompareToPolyfunOld() {
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

        // Compare strings
        oldResult.print();
        assertThat(newResult.toString(), is(outContent.toString().replace("\n", "").replace("+-", "-")));
    }

    @Test
    public void of() {
        // First polynomial
        Atom atom = new Atom('a', 1, 2);
        Term term = new Term(atom);
        Coef coef = new Coef(term);
        Polynomial polynomial = new Polynomial(coef);

        // Second polynomial
        double[] coefficients = {1, -3, 0, 2};
        Polynomial newPoly = new Polynomial(coefficients);

        Polynomial composition = newPoly.of(polynomial);

        assertThat(composition.toString(), is("-3.0a_1^2+2.0a_1^6+1.0"));
    }

    @Test
    public void ofConstants() {
        // First polynomial 9.11
        Polynomial polynomial = new Polynomial(9.11);

        // Second polynomial 4.87
        Polynomial newPoly = new Polynomial(4.87);

        Polynomial composition = polynomial.of(newPoly);

        assertThat(composition.toString(), is("9.11"));
    }

    @Test
    public void ofAbstractCoefs() {
        // (1.4a_2^3)X^2
        Atom[] atoms = new Atom[]{new Atom('a', 2, 3)};
        Term term = new Term(1.4, atoms);
        Polynomial poly = new Polynomial(term, 2);

        // 2.75a_3^2c_1^3c_3^2
        atoms = new Atom[]{new Atom('a', 3, 2),
                new Atom('c', 1, 3),
                new Atom('c', 3, 2),
        };
        term = new Term(2.75, atoms);
        Polynomial polyB = new Polynomial(term);

        // Expected: 10.587499999999999a_2^3a_3^4c_1^6c_3^4
        // all the coef and exp squared bc poly is 2-degrees, then times poly.
        // bug in poly times coef. this has term 0 and term 1 but coef has just term 0
        Polynomial comp = poly.of(polyB);

        assertThat(comp.toString(), is("10.587499999999999a_2^3a_3^4c_1^6c_3^4"));
    }

    @Test
    public void ofDegree2OfAbstractCoef() {
        // (1.4)X^2
        Term term = new Term(1.4);
        Polynomial poly = new Polynomial(term, 2);

        // a_3^2
        Atom[] atoms = new Atom[]{new Atom('a', 3, 2)};
        term = new Term(1.0, atoms);
        Polynomial polyB = new Polynomial(term);

        assertThat(polyB.getCoefAt(0).getTerms().length, is(1));

        // Expected: 1.4a_2^4
        // all the coef and exp squared bc poly is 2-degrees, then times poly.
        Polynomial comp = poly.of(polyB);

        assertThat(comp.toString(), is("1.4a_3^4"));
    }

    @Test
    public void timesAbstractCoefSelf() {
        // a_3^2
        Atom[] atoms = new Atom[]{new Atom('a', 3, 2)};
        Term term = new Term(1.0, atoms);
        Polynomial poly = new Polynomial(term);

        assertThat(poly.getCoefAt(0).getTerms().length, is(1));

        // Expected: a_3^4
        Polynomial product = poly.times(poly);

        assertThat(product.toString(), is("a_3^4"));

        // bug in poly times coef. this has term 0 and term 1 but coef has just term 0
        assertThat(product.getCoefAt(0).getTerms().length, is(1));
    }

    @Test
    public void ofAbstractCoefsNoNum() {
        // (1.4a_2^3)X^2
        Atom[] atoms = new Atom[]{new Atom('a', 2, 3)};
        Term term = new Term(1.0, atoms);
        Polynomial poly = new Polynomial(term, 2);

        // 2.75a_3^2c_1^3c_3^2
        atoms = new Atom[]{new Atom('a', 3, 2),
                new Atom('c', 1, 3),
                new Atom('c', 3, 2),
        };
        term = new Term(1.0, atoms);
        Polynomial polyB = new Polynomial(term);

        // Expected: a_2^3a_3^4c_1^6c_3^4
        Polynomial comp = poly.of(polyB);

        assertThat(comp.toString(), is("a_2^3a_3^4c_1^6c_3^4"));
    }

    @Test
    public void constructorParamPolyTestFailure109() {
        // d_2^4+c_3^3+e_1^3

        // d_2^4
        Term term1 = new Term(new Atom('d', 2, 4));

        // c_3^3
        Term term2 = new Term(new Atom('c', 3, 3));

        // e_1^3
        Term term3 = new Term(new Atom('e', 1, 3));

        Coef coef = new Coef(new Term[]{term1, term2, term3});

        Polynomial polynomial = new Polynomial(coef);

        Coef expectedCoef = new Coef(new Term[]{term2, term1, term3});
        Polynomial expectedPolynomial = new Polynomial(expectedCoef);

        assertThat(polynomial.toString(), is(expectedPolynomial.toString()));
        comparePolynomials(polynomial, expectedPolynomial);
    }

    @Test
    public void constructorWithConstants_TestFailureParaPoly515() {
//        Expected :8.65c_2^3+6.69
//        Actual   :6.69+8.65c_2^3

        // b_3^2
        Term term1 = new Term(8.65, new Atom[]{
                new Atom('c', 2, 3)});

        // 6.69
        Term term2 = new Term(6.69, new Atom[0]);

        Coef coef = new Coef(new Term[]{term2, term1});
        Polynomial polynomial = new Polynomial(new Coef[]{coef});

        Coef expectedC = new Coef(new Term[]{term1, term2});
        Polynomial expectedPolynomial = new Polynomial((new Coef[]{expectedC}));

        assertThat(polynomial.toString(), is(expectedPolynomial.toString()));
        assertThat(polynomial.toString(), is("8.65c_2^3+6.69"));
        comparePolynomials(polynomial, expectedPolynomial);
    }

    @Test
    public void ofOld() {
        // First polynomial
        polyfun.Atom atom = new polyfun.Atom('a', 1, 2);
        polyfun.Term term = new polyfun.Term(atom);
        polyfun.Coef coef = new polyfun.Coef(term);
        polyfun.Polynomial polynomial = new polyfun.Polynomial(coef);

        // Second polynomial
        double[] coefficients = {1, -3, 0, 2};
        polyfun.Polynomial newPoly = new polyfun.Polynomial(coefficients);

        polyfun.Polynomial composition = newPoly.of(polynomial);
    }

    @Test
    public void ofCompareToPolyfunOld() {
        // (2.0)X^2-3.0X+1.0
        double[] coefficients = {1, -3, 0, 2};

        polyfun.Polynomial oldPoly = new polyfun.Polynomial(coefficients);
        Polynomial newPoly = new Polynomial(coefficients);

        // Compose new poly of the poly and itself
        polyfun.Polynomial oldResult = oldPoly.of(oldPoly);
        Polynomial newResult = newPoly.of(newPoly);

        // Compare strings
        oldResult.print();
        assertThat(newResult.toString(), is(outContent.toString().replace("\n", "")));
    }

    @Test
    public void addTangentCompareToPolyfunOld() {
        // (2.0)X^3+(m-3.0)X+b+1.0
        double[] coefficients = {1, -3, 0, 2};

        // (2.0)X^3+(-3.0)X+1.0
        Polynomial polynomial = new Polynomial(coefficients);

        // Add tangent
        Polynomial newResult = polynomial.addTangent();

        assertThat(newResult.toString(), is("(2.0)X^3+(m-3.0)X+b+1.0"));
    }

    @Test
    public void addTangentDegree2NoNum() {
        // Create 2 identical polynomials
        double[] coefficients = {0, 1, 1};

        Polynomial polynomial = new Polynomial(coefficients);

        Polynomial sum = polynomial.addTangent();

        // Compare strings
        assertThat(sum.toString(), is("X^2+(m+1.0)X+b"));
    }

    @Test
    public void toStringMinusSignsManyDegrees() {
        String expected = "(-5.0)X^8+(-4.0)X^7+(-2.0)X^6+(-7.0)X^5+(-6.0)X^4+(-5.0)X^3+(-4.0)X^2+(-3.0)X-2.0";
        double[] coefs = {-2,-3,-4,-5,-6,-7,-2,-4,-5};
        Polynomial polynomial = new Polynomial(coefs);

        // Compare strings
        assertThat(polynomial.toString(), is (expected));
    }

    @Test
    public void toStringMinusSignsOneManyDegrees() {
        String expected = "(-1.0)X^8+(-1.0)X^7+(-1.0)X^6+(-1.0)X^5+(-1.0)X^4+(-1.0)X^3+(-1.0)X^2+(-1.0)X-1.0";
        double[] coefs = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        Polynomial polynomial = new Polynomial(coefs);

        // Compare strings
        assertThat(polynomial.toString(), is (expected));
    }

    @Test
    public void toStringSignsOneManyDegrees() {
        String expected = "X^8+X^7+X^6+X^5+X^4+X^3+X^2+X+1.0";
        double[] coefs = {1,1,1,1,1,1,1,1,1};
        Polynomial polynomial = new Polynomial(coefs);

        // Compare strings
        assertThat(polynomial.toString(), is (expected));
    }

    @Test
    public void getCoefficientAtTermFix() {
        Polynomial fx = new Polynomial(new double[]{0, 3, 0, 2});

        assertThat(fx.getCoefficientAtTerm(0), is(0.0));
        assertThat(fx.getCoefficientAtTerm(1), is(3.0));
        assertThat(fx.getCoefficientAtTerm(2), is(0.0));
        assertThat(fx.getCoefficientAtTerm(3), is(2.0));
    }

    @Test
    public void getCoefficientArray() {
        Polynomial fx = new Polynomial(new double[]{0, 3, 0, 2});

        double[] coefs = fx.getCoefficientArray();

        assertThat(coefs[0], is(0.0));
        assertThat(coefs[1], is(3.0));
        assertThat(coefs[2], is(0.0));
        assertThat(coefs[3], is(2.0));
    }


    public static void comparePolynomials(polyfun.Polynomial oldPoly, Polynomial newPoly) {
        // Compare number of coefficients.
        polyfun.Coef[] oldCoefs = oldPoly.getCoefficients();
        Coef[] newCoefs = newPoly.getCoefs();

        Assert.assertEquals(oldCoefs.length, newCoefs.length);

        // Compare Coef by Coef.
        for (int i = 0; i < oldCoefs.length; i++) {
            compareCoefs(oldCoefs[i], newCoefs[i]);
        }
    }

    private static void compareCoefs(polyfun.Coef oldCoef, Coef newCoef) {
        polyfun.Term[] oldTerms = oldCoef.getTerms();
        Term[] newTerms = newCoef.getTerms();

        assertThat(newTerms.length, is(oldTerms.length));

        // For each Term array, compare Term by Term
        for (int i = 0; i < newTerms.length; i++) {
            compareTerms(oldTerms[i], newTerms[i]);
        }
    }

    private static void compareTerms(polyfun.Term oldTerm, Term newTerm) {
        // Compare term coefficients
        assertThat(newTerm.getNumericalCoefficient(), is(oldTerm.getTermDouble()));

        // For each Term, compare Atom array by Atom array
        polyfun.Atom[] oldAtoms = oldTerm.getTermAtoms();
        Atom[] newAtoms = newTerm.getAtoms();

        if (oldAtoms != null && newAtoms != null) {
            Assert.assertEquals(oldAtoms.length, newAtoms.length);

            // For each Atom, compare the parts.
            for (int i = 0; i < newAtoms.length; i++) {
                Assert.assertEquals(oldAtoms[i].getLetter(), newAtoms[i].getLetter());
                Assert.assertEquals(oldAtoms[i].getPower(), newAtoms[i].getPower());
                Assert.assertEquals(oldAtoms[i].getSubscript(), newAtoms[i].getSubscript());
            }
        }
    }

    public static void comparePolynomials(Polynomial actual, Polynomial expected) {
        // Compare number of coefficients.
        Coef[] expectedCoefs = expected.getCoefs();
        Coef[] actualCoefs = actual.getCoefs();

        Assert.assertEquals(expectedCoefs.length, actualCoefs.length);

        // Compare Coef by Coef.
        for (int i = 0; i < expectedCoefs.length; i++) {
            compareCoefs(actualCoefs[i], expectedCoefs[i]);
        }
    }

    private static void compareCoefs(Coef actual, Coef expected) {
        Term[] expectedTerms = expected.getTerms();
        Term[] actualTerms = actual.getTerms();

        assertThat(actualTerms.length, is(expectedTerms.length));

        // For each Term array, compare Term by Term
        for (int j = 0; j < expectedTerms.length; j++) {
            compareTerms(actualTerms[j], expectedTerms[j]);
        }
    }

    private static void compareTerms(Term actual, Term expected) {
        // Compare term coefficients
        assertThat(actual.getNumericalCoefficient(), is(expected.getNumericalCoefficient()));

        // For each Term, compare Atom array by Atom array
        Atom[] expectedAtoms = expected.getAtoms();
        Atom[] actualAtoms = actual.getAtoms();

        if (expectedAtoms != null && actualAtoms != null) {
            Assert.assertEquals(expectedAtoms.length, actualAtoms.length);

            // For each Atom, compare the parts.
            for (int i = 0; i < actualAtoms.length; i++) {
                Assert.assertEquals(expectedAtoms[i].getLetter(), actualAtoms[i].getLetter());
                Assert.assertEquals(expectedAtoms[i].getPower(), actualAtoms[i].getPower());
                Assert.assertEquals(expectedAtoms[i].getSubscript(), actualAtoms[i].getSubscript());
            }
        }
    }
}
