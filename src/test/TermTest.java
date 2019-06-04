import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Term;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TermTest {

    Term term = new Term();

    // Create output streams to capture .print() output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));

        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        term.setAtoms(atoms);
        term.setNumericalCoefficient(3);
    }

    @After
    public void restoreStreams() {
        // Point System.out back to console.
        System.setOut(originalOut);
    }

    @Test
    public void setAtoms_setNumericalCoefficient() {
        term.setNumericalCoefficient(3);

        Assert.assertEquals("3.0a_1^2", term.toString());
        Assert.assertTrue(3 == term.getNumericalCoefficient());
        Assert.assertEquals('a', term.getAtoms()[0].getLetter());
        Assert.assertEquals(1, term.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, term.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void setTermDouble() {
        term.setTermDouble(3);

        Assert.assertTrue(3 == term.getTermDouble());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void setTerm() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        term.setTerm(3, atoms);

        Assert.assertEquals("3.0a_1^2", term.toString());
        Assert.assertTrue(3 == term.getNumericalCoefficient());
        Assert.assertEquals('a', term.getAtoms()[0].getLetter());
        Assert.assertEquals(1, term.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, term.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void getTermDouble() {
        Assert.assertTrue(3 == term.getTermDouble());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void getTermAtoms() {
        Assert.assertEquals('a', term.getTermAtoms()[0].getLetter());
        Assert.assertEquals(1, term.getTermAtoms()[0].getSubscript());
        Assert.assertEquals(2, term.getTermAtoms()[0].getPower());
        Assert.assertEquals(1, term.getTermAtoms().length);
    }

    @Test
    public void getNumericalCoefficient() {
        Assert.assertTrue(3 == term.getNumericalCoefficient());
    }

    @Test
    public void setNumericalCoefficient() {
        term.setNumericalCoefficient(4);
        Assert.assertTrue(4 == term.getNumericalCoefficient());

        // Put back to 3
        term.setNumericalCoefficient(3);
    }

    @Test
    public void set_getAtoms() {
        Assert.assertEquals('a', term.getAtoms()[0].getLetter());
        Assert.assertEquals(1, term.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, term.getAtoms()[0].getPower());
        Assert.assertEquals(1, term.getAtoms().length);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void print() {
        term.print();
        Assert.assertEquals(outContent.toString(), term.toString());
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals("3.0a_1^2", term.toString());
    }

    @Test
    public void snip() {
        Term snipped = term.snip();
        Assert.assertEquals(0, snipped.getAtoms().length);
        Assert.assertTrue(3.0 == snipped.getNumericalCoefficient());
    }

    @Test
    public void paste() {
        Atom atom = new Atom('b', 1, 3);
        Term newTerm = term.paste(atom);

        Assert.assertEquals(2, newTerm.getAtoms().length);
        Assert.assertTrue(3.0 == newTerm.getNumericalCoefficient());
        Assert.assertEquals('b', newTerm.getAtoms()[0].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(3, newTerm.getAtoms()[0].getPower());
        Assert.assertEquals('a', newTerm.getAtoms()[1].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, newTerm.getAtoms()[1].getPower());
    }

    @Test
    public void place() {
        Atom atom = new Atom('b', 1, 3);
        Term newTerm = term.place(atom);

        Assert.assertEquals(2, newTerm.getAtoms().length);
        Assert.assertTrue(3.0 == newTerm.getNumericalCoefficient());
        Assert.assertEquals('a', newTerm.getAtoms()[0].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, newTerm.getAtoms()[0].getPower());
        Assert.assertEquals('b', newTerm.getAtoms()[1].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(3, newTerm.getAtoms()[1].getPower());
    }

    @Test
    public void simplify_TestReordering() {
        Atom atom = new Atom('b', 1, 3);
        Term newTerm = term.paste(atom);

        newTerm.simplify();

        Assert.assertEquals(2, newTerm.getAtoms().length);
        Assert.assertTrue(3.0 == newTerm.getNumericalCoefficient());
        Assert.assertEquals('a', newTerm.getAtoms()[0].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, newTerm.getAtoms()[0].getPower());
        Assert.assertEquals('b', newTerm.getAtoms()[1].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(3, newTerm.getAtoms()[1].getPower());
    }

    @Test
    public void simplify_TestMultiplying() {
        Atom atom = new Atom('a', 1, 3);
        Term newTerm = term.paste(atom);

        newTerm.simplify();

        Assert.assertEquals(1, newTerm.getAtoms().length);
        Assert.assertTrue(3.0 == newTerm.getNumericalCoefficient());
        Assert.assertEquals('a', newTerm.getAtoms()[0].getLetter());
        Assert.assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        Assert.assertEquals(5, newTerm.getAtoms()[0].getPower());
    }

    @Test
    public void timesTerm() {
        Atom atom = new Atom('a', 1, 3);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        Term product = term.times(newTerm);


        Assert.assertEquals(1, product.getAtoms().length);
        Assert.assertTrue(6.0 == product.getNumericalCoefficient());
        Assert.assertEquals('a', product.getAtoms()[0].getLetter());
        Assert.assertEquals(1, product.getAtoms()[0].getSubscript());
        Assert.assertEquals(5, product.getAtoms()[0].getPower());
    }

    @Test
    public void timesScalar() {
        Term product = term.times(10);


        Assert.assertEquals(1, product.getAtoms().length);
        Assert.assertTrue(30.0 == product.getNumericalCoefficient());
        Assert.assertEquals('a', product.getAtoms()[0].getLetter());
        Assert.assertEquals(1, product.getAtoms()[0].getSubscript());
        Assert.assertEquals(2, product.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void identicalTo() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        Assert.assertTrue(term.identicalTo(newTerm));
    }

    @Test
    public void equals1() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        Assert.assertTrue(term.equals(newTerm));
    }

    @Test
    public void like() {
        Atom atom = new Atom('a', 1, 3);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        Assert.assertTrue(term.like(newTerm));
    }

    @Test
    public void isLessThan_MoreAtoms() {
        // TODO: Which one is less than the other?
        // Greater length but lower letters
        Atom[] atomsA = {
                new Atom('a', 1, 1),
                new Atom('b', 1, 1),
                new Atom('c', 1, 1)
        };

        // Smaller length but higher letters
        Atom[] atomsB = {
                new Atom('d', 1, 1),
                new Atom('e', 1, 1)
        };

        Term termA = new Term(1, atomsA);
        Term termB = new Term(1, atomsB);

        Assert.assertFalse(termB.isLessThan(termA));
    }

    @Test
    public void times1() {
    }

    @Test
    public void isZero() {
    }

    @Test
    public void isDouble() {
    }

    @Test
    public void isConstantTerm() {
    }
}