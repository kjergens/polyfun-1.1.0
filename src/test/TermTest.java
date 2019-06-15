import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Term;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

public class TermTest {

    Term term;

    // Create output streams to capture .print() output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));

        // 3.0a_1^2
        term = new Term();
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        term.setAtoms(atoms);
        term.setNumericalCoefficient(3);
    }

    @After
    public void restoreStreams() {
        // Point System.out back to console.
        System.setOut(System.out);
    }

    @Test
    public void setAtoms_setNumericalCoefficient() {
        term.setNumericalCoefficient(3);

        assertEquals("3.0a_1^2", term.toString());
        assertThat(term.getNumericalCoefficient(), is(3.0));
        assertEquals('a', term.getAtoms()[0].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(2, term.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void setTermDouble() {
        term.setTermDouble(3);

        assertThat(term.getTermDouble(), is(3.0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void setTerm() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        term.setTerm(3, atoms);

        assertEquals("3.0a_1^2", term.toString());
        assertThat(term.getNumericalCoefficient(), is(3.0));
        assertEquals('a', term.getAtoms()[0].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(2, term.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void getTermDouble() {
        assertThat(term.getTermDouble(), is(3.0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void getTermAtoms() {
        assertEquals('a', term.getTermAtoms()[0].getLetter());
        assertEquals(1, term.getTermAtoms()[0].getSubscript());
        assertEquals(2, term.getTermAtoms()[0].getPower());
        assertEquals(1, term.getTermAtoms().length);
    }

    @Test
    public void getNumericalCoefficient() {
        assertThat(term.getNumericalCoefficient(), is(3.0));
    }

    @Test
    public void setNumericalCoefficient() {
        term.setNumericalCoefficient(4);
        assertThat(term.getNumericalCoefficient(), is(4.0));

        // Put back to 3
        term.setNumericalCoefficient(3);
    }

    @Test
    public void set_getAtoms() {
        assertEquals('a', term.getAtoms()[0].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(2, term.getAtoms()[0].getPower());
        assertEquals(1, term.getAtoms().length);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void print() {
        term.print();
        assertEquals(outContent.toString(), term.toString());
    }

    @Test
    public void toStringTest() {
        assertEquals("3.0a_1^2", term.toString());
    }

    @Test
    public void snip() {
        Term snipped = term.snip();
        assertEquals(0, snipped.getAtoms().length);
        assertThat(snipped.getNumericalCoefficient(), is(3.0));
    }

    @Test
    public void pop() {
        term.pop();
        assertEquals(0, term.getAtoms().length);
        assertThat(term.getNumericalCoefficient(), is(3.0));
    }

    @Test
    public void paste() {
        Atom atom = new Atom('b', 1, 3);
        Term newTerm = term.paste(atom);

        assertEquals(2, newTerm.getAtoms().length);
        assertThat(newTerm.getNumericalCoefficient(), is(3.0));
        assertEquals('b', newTerm.getAtoms()[0].getLetter());
        assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        assertEquals(3, newTerm.getAtoms()[0].getPower());
        assertEquals('a', newTerm.getAtoms()[1].getLetter());
        assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        assertEquals(2, newTerm.getAtoms()[1].getPower());
    }

    @Test
    public void push() {
        Atom atom = new Atom('b', 1, 3);
        term.push(atom);

        assertEquals(2, term.getAtoms().length);
        assertThat(term.getNumericalCoefficient(), is(3.0));
        assertEquals('b', term.getAtoms()[0].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(3, term.getAtoms()[0].getPower());
        assertEquals('a', term.getAtoms()[1].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(2, term.getAtoms()[1].getPower());
        assertThat(term.toString(), is("3.0b_1^3a_1^2"));
    }

    @Test
    public void place() {
        Atom atom = new Atom('b', 1, 3);
        Term newTerm = term.place(atom);

        assertEquals(2, newTerm.getAtoms().length);
        assertThat(newTerm.getNumericalCoefficient(), is(3.0));
        assertEquals('a', newTerm.getAtoms()[0].getLetter());
        assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        assertEquals(2, newTerm.getAtoms()[0].getPower());
        assertEquals('b', newTerm.getAtoms()[1].getLetter());
        assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        assertEquals(3, newTerm.getAtoms()[1].getPower());
    }

    @Test
    public void smartInsert() {
        Atom atom = new Atom('b', 1, 3);
        term.smartInsert(atom);

        assertEquals(2, term.getAtoms().length);
        assertThat(term.getNumericalCoefficient(), is(3.0));
        assertEquals('a', term.getAtoms()[0].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(2, term.getAtoms()[0].getPower());
        assertEquals('b', term.getAtoms()[1].getLetter());
        assertEquals(1, term.getAtoms()[0].getSubscript());
        assertEquals(3, term.getAtoms()[1].getPower());
        assertThat(term.toString(), is("3.0a_1^2b_1^3"));
    }

    @Test
    public void simplifyReordering() {
        Atom atom = new Atom('c', 1, 3);
        term = term.paste(atom);

        atom = new Atom('b', 1, 2);
        term = term.paste(atom);

        term.simplify();

        assertThat(term.toString(), is("3.0a_1^2b_1^2c_1^3"));
    }

    @Test
    public void simplifyCombineAtomsByAddingExponents() {
        Atom[] atoms = {new Atom('a', 1, 2),
                new Atom('b', 1, 3),
                new Atom('b', 1, 3)};

        for(Atom atom : atoms ) {
            term = term.paste(atom);
        }

        term = term.simplify();

        assertThat(term.toString(), is("3.0a_1^4b_1^6"));
    }

    @Test
    public void simplifyPaste() {
        Atom atom = new Atom('a', 1, 3);
        Term newTerm = term.paste(atom);

        newTerm.simplify();

        assertEquals(1, newTerm.getAtoms().length);
        assertThat(newTerm.getNumericalCoefficient(), is(3.0));
        assertEquals('a', newTerm.getAtoms()[0].getLetter());
        assertEquals(1, newTerm.getAtoms()[0].getSubscript());
        assertEquals(5, newTerm.getAtoms()[0].getPower());
    }

    @Test
    public void reduceCombineAtomsByAddingExponents() {
        Atom[] atoms = {new Atom('a', 1, 2),
                new Atom('b', 1, 3),
                new Atom('b', 1, 3)};

        for(Atom atom : atoms ) {
            term.push(atom);
        }

        term.reduce();

        assertThat(term.toString(), is("3.0a_1^4b_1^6"));
    }

    @Test
    public void reducePushReorder() {
        Atom atom = new Atom('b', 1, 3);
        term.push(atom);
        term.reduce();

        assertThat(term.toString(), is("3.0a_1^2b_1^3"));
    }

    @Test
    public void timesTerm() {
        Atom atom = new Atom('a', 1, 3);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        Term product = term.times(newTerm);

        assertEquals(1, product.getAtoms().length);
        assertThat(product.getNumericalCoefficient(), is(6.0));
        assertEquals('a', product.getAtoms()[0].getLetter());
        assertEquals(1, product.getAtoms()[0].getSubscript());
        assertEquals(5, product.getAtoms()[0].getPower());
    }

    @Test
    public void timesScalar() {
        Term product = term.times(10);

        assertEquals(1, product.getAtoms().length);
        assertThat(product.getNumericalCoefficient(), is(30.0));
        assertEquals('a', product.getAtoms()[0].getLetter());
        assertEquals(1, product.getAtoms()[0].getSubscript());
        assertEquals(2, product.getAtoms()[0].getPower());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void identicalTo() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        assertThat(term.identicalTo(newTerm), is(true));
    }

    @Test
    public void equals1() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        assertThat(term.equals(newTerm), is(true));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void like() {
        Atom atom = new Atom('a', 1, 3);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        assertThat(term.like(newTerm), is(true));
    }

    @Test
    public void isLike() {
        Atom atom = new Atom('a', 1, 3);
        Atom[] atoms = {atom};
        Term newTerm = new Term(2, atoms);

        assertThat(term.isLike(newTerm), is(true));
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

        assertThat(termB.isLessThan(termA), is(false));
    }

    @Test
    public void isZero() {
        Atom atom = new Atom('a', 1, 2);
        Atom[] atoms = {atom};
        Term newTerm = new Term(0, atoms);

        assertThat(newTerm.isZero(), is(true));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void isDouble() {
        Atom[] atoms = new Atom[0];
        Term newTerm = new Term(2, atoms);

        assertThat(newTerm.isDouble(), is(true));
    }

    @Test
    public void isConstantTerm() {
        Atom[] atoms = new Atom[0];
        Term newTerm = new Term(2, atoms);

        assertThat(newTerm.isConstantTerm(), is(true));
    }
}