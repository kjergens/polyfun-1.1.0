import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Coef;
import org.dalton.polyfun.Term;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CoefTest {

    public Coef coef;
    String expected;

    @Before
    public void setUp() {
        // Create a coef using Coef(Term[]) constructor
        // Uses 3 terms with 3 atoms each.

        Term[] terms = new Term[3];
        double numCoef = 1.0D;

        for (int i = 0; i < terms.length; i++) {
            Atom[] atoms = new Atom[3];
            char letter = 'a';
            int subscript = 1;
            int power = 1;

            for (int j = 0; j < atoms.length; j++) {
                atoms[j] = new Atom(letter++, subscript++, power++);
            }

            terms[i] = new Term(numCoef++, atoms);
        }

        coef = new Coef(terms);
        expected = "6.0a_1b_2^2c_3^3";
    }

    @After
    public void tearDown() {
        coef = null;
    }

    @Test
    public void setTermsWithTermArray() {
        Term[] terms = new Term[3];
        double numCoef = 1.0D;
        int subscript = 1;

        for (int i = 0; i < terms.length; i++) {
            Atom[] atoms = new Atom[3];
            char letter = 'a';
            int power = 1;

            for (int j = 0; j < atoms.length; j++) {
                atoms[j] = new Atom(letter++, subscript, power++);
            }

            subscript++;
            terms[i] = new Term(numCoef++, atoms);
        }

        coef.setTerms(terms);

        String expected = "a_1b_1^2c_1^3+2.0a_2b_2^2c_2^3+3.0a_3b_3^2c_3^3";

        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void setTermsWithTerm() {
        double numCoef = 1.0D;
        Atom[] atoms = new Atom[3];

        int subscript = 1;
        int power = 1;

        char letter = 'a';

        for (int j = 0; j < atoms.length; j++) {
            atoms[j] = new Atom(letter++, subscript++, power++);
        }

        coef.setTerms(new Term(numCoef, atoms));

        String expected = "a_1b_2^2c_3^3";

        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void getTerms() {
        String[] expectedTerms = expected.split("\\+");

        Term[] terms = coef.getTerms();

        for (int i = 0; i < terms.length; i++) {
            assertThat(terms[i].toString(), is(expectedTerms[i]));
        }
    }

    @Test
    public void print() {
        // Point System.out to another output stream so I can capture the print() output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run test
        coef.print();
        assertThat(outContent.toString(), is(expected));

        // Put stream back
        System.setOut(System.out);
    }

    @Test
    public void printConstantCoef() {
        // Point System.out to another output stream so I can capture the print() output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run test
        coef = new Coef(1.0);
        coef.print();
        assertThat(outContent.toString(), is("1.0"));

        // Put stream back
        System.setOut(System.out);
    }

    @Test
    public void printOneTerm() {
        // Point System.out to another output stream so I can capture the print() output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Run test
        Atom atom = new Atom('a', 1, 2);
        Term term = new Term(atom);
        coef = new Coef(term);
        coef.print();
        assertThat(outContent.toString(), is("a_1^2"));

        // Put stream back
        System.setOut(System.out);
    }

    @Test
    public void coefToString() {
        expected = "6.0a_1b_2^2c_3^3";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void snip() {
        coef = coef.snip();
        assertThat(coef.toString(), is(""));
    }

    @Test
    public void pop() {
        coef.pop();
        assertThat(coef.toString(), is(""));
    }

    @Test
    public void paste() {
        Atom atom = new Atom('a', 1, 2);
        Term term = new Term(atom);
        coef = coef.paste(term);

        expected = "a_1^2+" + expected;
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void push() {
        Atom atom = new Atom('a', 1, 2);
        Term term = new Term(atom);
        coef.push(term);

        expected = "a_1^2+" + expected;
        assertThat(coef.toString(), is(expected));
    }


    @Test
    public void placeAndCombine() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 1, 1)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = "a_1b_1+6.0a_1b_2^2c_3^3";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void placeInFrontSmallerLetters() {
        Atom[] atoms = {new Atom('a', 0, 1),
                new Atom('b', 0, 1),
                new Atom('c', 0, 1)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = "a_0b_0c_0+" + expected;
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void placeAddCoefsSameAtoms() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 2, 2),
                new Atom('c', 3, 3)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = expected.replace("6.0", "7.0");
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void insertEndMoreAtoms() {
        Atom[] atoms = {new Atom('a', 0, 1),
                new Atom('b', 0, 1),
                new Atom('c', 0, 1),
                new Atom('d', 0, 1)};
        Term term = new Term(1.0, atoms);
        coef.insert(term);

        expected = "a_0b_0c_0d_0+" + expected;
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void insertAddCoefsSameAtoms() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 2, 2),
                new Atom('c', 3, 3)};
        Term term = new Term(1.0, atoms);
        coef.insert(term);


        expected = expected.replace("6", "7");
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void insertInFrontFewerAtoms() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 1, 1)};
        Term term = new Term(1.0, atoms);
        coef.insert(term);

        expected = "a_1b_1+" + expected;
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void insertInFrontSmallerLetters() {
        Atom[] atoms = {new Atom('a', 0, 1),
                new Atom('b', 0, 1),
                new Atom('c', 0, 1)};
        Term term = new Term(1.0, atoms);
        coef.insert(term);

        expected = "a_0b_0c_0+" + expected;
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void simplifyParamPoly38_3Terms() {
        // b_3^2
        Term term1 = new Term(1.0, new Atom[]{
                new Atom('b', 3, 2)});

        // d_1^5e_1^3
        Term term2 = new Term(1.0, new Atom[]{
                new Atom('d', 1, 5),
                new Atom('e',1, 3)});

        // e_3^2
        Term term3 = new Term(1.0, new Atom[]{
                new Atom('e', 3, 2)});

        Coef coef = new Coef(new Term[]{term3, term2, term1});
        Coef expected = new Coef(new Term[]{term1, term2, term3});

        coef.simplify();

        assertThat(coef.toString(), is(expected.toString()));
    }

    @Test
    public void reduceSortWithConstants_TestFailureParaPoly151() {
//        Expected :8.65c_2^3+6.69
//        Actual   :6.69+8.65c_2^3

        // b_3^2
        Term term1 = new Term(8.65, new Atom[]{
                new Atom('c', 2, 3)});

        // 6.69
        Term term2 = new Term(6.69);

        Coef coef = new Coef(new Term[]{term2, term1});

        Coef expected = new Coef(new Term[]{term1, term2});

        coef.simplify();

        assertThat(coef.toString(), is(expected.toString()));
        assertThat(coef.toString(), is("8.65c_2^3+6.69"));
    }

    @Test
    public void reduceParamPoly38_3Terms() {
        // b_3^2
        Term term1 = new Term(1.0, new Atom[]{
                new Atom('b', 3, 2)});

        // d_1^5e_1^3
        Term term2 = new Term(1.0, new Atom[]{
                new Atom('d', 1, 5),
                new Atom('e',1, 3)});

        // e_3^2
        Term term3 = new Term(1.0, new Atom[]{
                new Atom('e', 3, 2)});

        Coef coef = new Coef(new Term[]{term3, term2, term1});
        Coef expected = new Coef(new Term[]{term1, term2, term3});

        coef.reduce();

        assertThat(coef.toString(), is(expected.toString()));
    }

    @Test
    public void reduceParamPolyTestFailure109() {
        // d_2^4+c_3^3+e_1^3

        // d_2^4
        Term term1 = new Term(new Atom('d', 2, 4));

        // c_3^3
        Term term2 = new Term(new Atom('c', 3, 3));

        // e_1^3
        Term term3 = new Term(new Atom('e', 1, 3));

        Coef coef = new Coef(new Term[]{term1, term2, term3});

        Coef expected = new Coef(new Term[]{term2, term1, term3});

        coef.reduce();

        assertThat(coef.toString(), is(expected.toString()));
        assertThat(coef.toString(), is("c_3^3+d_2^4+e_1^3"));
    }

    @Test
    public void simplifyParamPolyTestFailure109() {
        // d_2^4+c_3^3+e_1^3

        // d_2^4
        Term term1 = new Term(new Atom('d', 2, 4));

        // c_3^3
        Term term2 = new Term(new Atom('c', 3, 3));

        // e_1^3
        Term term3 = new Term(new Atom('e', 1, 3));

        Coef coef = new Coef(new Term[]{term1, term2, term3});

        Coef expected = new Coef(new Term[]{term2, term1, term3});

        coef.reduce();

        assertThat(coef.toString(), is(expected.toString()));
        assertThat(coef.toString(), is("c_3^3+d_2^4+e_1^3"));
    }

    @Test
    public void simplifyCombineTerms() {
        Atom atomA = new Atom('a', 1, 1);
        Atom atomB = new Atom('b', 2, 2);
        Atom atomC = new Atom('c', 3, 3);
        Atom[] atoms = {atomA, atomB, atomC};

        Term term = new Term(1.0, atoms);
        coef = new Coef(term);
        coef = coef.paste(term);
        coef = coef.simplify();

        expected = "2.0a_1b_2^2c_3^3";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void simplifyReorder() {
        Atom[] atoms = new Atom[3];
        double numCoef = 1.0D;
        char letter = 'd';

        for (int j = 0; j < atoms.length; j++) {
            atoms[j] = new Atom(letter++, 1, 1);
        }

        // Paste the bigger one in front
        coef = coef.paste(new Term(1.0, atoms));

        // Reorder to put term in back
        coef = coef.simplify();
        expected = "6.0a_1b_2^2c_3^3+d_1e_1f_1";

        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void simplifyCombine() {
        coef = coef.simplify();
        expected = "6.0a_1b_2^2c_3^3";

        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void reducePushReorder() {
        Atom[] atoms = new Atom[3];
        double numCoef = 1.0D;
        char letter = 'd';

        for (int j = 0; j < atoms.length; j++) {
            atoms[j] = new Atom(letter++, 1, 1);
        }

        // Paste the bigger one in front
        coef.push(new Term(1.0, atoms));

        // Reorder to put term in back
        coef.reduce();
        expected = "6.0a_1b_2^2c_3^3+d_1e_1f_1";

        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void reduceCombine() {
        coef.reduce();
        expected = "6.0a_1b_2^2c_3^3";

        assertThat(coef.toString(), is(expected));
    }


    @Test
    public void reduceCombineTerms() {
        Atom atomA = new Atom('a', 1, 1);
        Atom atomB = new Atom('b', 2, 2);
        Atom atomC = new Atom('c', 3, 3);
        Atom[] atoms = {atomA, atomB, atomC};

        Term term = new Term(1.0, atoms);
        coef = new Coef(term);
        coef = coef.paste(term);
        coef.reduce();

        expected = "2.0a_1b_2^2c_3^3";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void simplifyOld() {
        // DEBUG - try this with the original polyfun
        polyfun.Atom atomAo = new polyfun.Atom('a', 1, 1);
        polyfun.Atom atomBo = new polyfun.Atom('b', 2, 2);
        polyfun.Atom atomCo = new polyfun.Atom('c', 3, 3);
        polyfun.Atom[] atomso = {atomAo, atomBo, atomCo};

        polyfun.Term termo = new polyfun.Term(1.0, atomso);
        polyfun.Coef coefo = new polyfun.Coef(termo);

        coefo = coefo.paste(termo);

        expected = "2.0a_1b_2^2c_3^3";

        // Point System.out to another output stream so I can capture the print() output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        coefo = coefo.simplify();
        coefo.print();
        assertThat(outContent.toString(), is(expected));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void timesScalar() {
        double scalar = 2.0;

        Coef product = coef.times(scalar);
        expected = expected.replace("6.0", "12.0");

        assertThat(product.toString(), is(expected));
    }

    @Test
    public void timesCoef() {
        Coef product = coef.times(coef);
        expected = "36.0a_1^2b_2^4c_3^6";

        assertThat(product.toString(), is(expected));

    }

    @Test
    public void timesAbstractCoefSelf() {
        // a_3^2
        Atom[] atoms = new Atom[]{new Atom('a', 3, 2)};
        Term term = new Term(1.0, atoms);
        Coef coef = new Coef(term);

        assertThat(coef.getTerms().length, is(1));

        // Expected: a_3^4
        // all the coef and exp squared bc poly is 2-degrees
        Coef product = coef.times(coef);

        assertThat(product.toString(), is("a_3^4"));

        assertThat(product.getTerms().length, is(1));
    }

    @Test
    public void isZeroEmptyTerm() {
        Coef coef = new Coef(new Term[0]);

        boolean isZero = coef.isZero();
        assertThat(isZero, is(true));
    }

    @Test
    public void plus() {
        Coef sum = coef.plus(coef);
        expected = "12.0a_1b_2^2c_3^3";

        assertThat(sum.toString(), is(expected));
    }


    @Test
    public void isZero() {
        assertThat(coef.isZero(), is(false));
    }

    @Test
    public void isDouble() {
        assertThat(coef.isDouble(), is(false));
    }

    @Test
    public void isConstantCoef() {
        assertThat(coef.isConstantCoef(), is(false));
    }
}