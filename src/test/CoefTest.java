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
        expected = "a_1b_2^2c_3^3+2.0a_1b_2^2c_3^3+3.0a_1b_2^2c_3^3";
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
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void snip() {
        coef = coef.snip();
        expected = "2.0a_1b_2^2c_3^3+3.0a_1b_2^2c_3^3";

        assertThat(coef.toString(), is(expected));
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
    public void placeInFrontFewerAtoms() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 1, 1)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = "a_1b_1+" + expected;
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
    public void placeEndMoreAtoms() {
        Atom[] atoms = {new Atom('a', 0, 1),
                new Atom('b', 0, 1),
                new Atom('c', 0, 1),
                new Atom('d', 0, 1)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = expected + "+a_0b_0c_0d_0";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void placeEndBiggerLastAtom() {
        Atom[] atoms = {new Atom('a', 0, 1),
                new Atom('b', 0, 1),
                new Atom('c', 3, 4)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected += "+a_0b_0c_3^4";
        assertThat(coef.toString(), is(expected));
    }

    @Test
    public void placeAddCoefsSameAtoms() {
        Atom[] atoms = {new Atom('a', 1, 1),
                new Atom('b', 2, 2),
                new Atom('c', 3, 3)};
        Term term = new Term(1.0, atoms);
        coef = coef.place(term);

        expected = "2.0" + expected;
        assertThat(coef.toString(), is(expected));
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
    public void simplyReorder() {
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
    public void simplyCombine() {
        coef = coef.simplify();
        expected = "6.0a_1b_2^2c_3^3";

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
    public void placeEndBiggerAtomOld() {
        // Set up the same as the refactored one
        polyfun.Atom[] atoms = {new polyfun.Atom('a', 1, 1),
                new polyfun.Atom('b', 2, 2),
                new polyfun.Atom('c', 3, 3)};
        polyfun.Term termo1 = new polyfun.Term(1.0, atoms);
        polyfun.Term termo2 = new polyfun.Term(2.0, atoms);
        polyfun.Term termo3 = new polyfun.Term(3.0, atoms);
        polyfun.Term[] termos = {termo1, termo2, termo3};

        polyfun.Coef coefo = new polyfun.Coef(termos);

        // Create new one to place. It has smaller a, c but bigger b
        polyfun.Atom[] atomsAdd = {new polyfun.Atom('a', 1, 1),
                new polyfun.Atom('b', 2, 1),
                new polyfun.Atom('c', 3, 4)};
        polyfun.Term termo = new polyfun.Term(1.0, atomsAdd);
        coefo = coefo.place(termo);

        expected += "+a_1b_2c_3^4";

        // Point System.out to another output stream so I can capture the print() output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        coefo.print();
        assertThat(outContent.toString(), is(expected));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void timesScalar() {
        double scalar = 2.0;

        Coef product = coef.times(scalar);
        expected = "2.0a_1b_2^2c_3^3+4.0a_1b_2^2c_3^3+6.0a_1b_2^2c_3^3";

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
        System.err.println(coef.toString());

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