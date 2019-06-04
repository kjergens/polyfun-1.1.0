import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.dalton.polyfun.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AtomTest {

    Atom atom = new Atom('a', 1, 2);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() throws Exception {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLetterConstructor() {
        Atom a = new Atom('a');
        a.setPower(2);
        a.setSubscript(1);

        Assert.assertTrue(a.equals(this.atom));
    }

    @Test
    public void setAtom() {
        Atom a = new Atom();
        a.setAtom('a', 1, 2);

        Assert.assertTrue(atom.equals(a));
    }

    @Test
    public void getLetter() {
        Assert.assertEquals('a', atom.getLetter());
    }

    @Test
    public void getSubscript() {
        Assert.assertEquals(1, atom.getSubscript());
    }

    @Test
    public void getPower() {
        Assert.assertEquals(2, atom.getPower());
    }

    @Test
    public void print() {
        atom.print();
        Assert.assertEquals("a_1^2", outContent.toString());
    }

    @Test
    public void toString1() {
        Assert.assertEquals("a_1^2", atom.toString());
    }

    @Test
    public void timesLikeAtom() {
        Atom likeAtom = new Atom('a', 1, 4);
        Atom expectedAtom = new Atom('a', 1, 6);
        Assert.assertEquals(expectedAtom.toString(), atom.timesLikeAtom(likeAtom).toString());
    }

    @Test
    public void identicalTo() {
        Atom a = new Atom('a', 1, 2);
        Assert.assertTrue(atom.identicalTo(a));
    }

    @Test
    public void equals1() {
        Atom a = new Atom('a', 1, 2);
        Assert.assertTrue(atom.equals(a));
    }

    @Test
    public void like() {
        Atom a = new Atom('a', 1, 4);
        Assert.assertTrue(atom.like(a));
    }

    @Test
    public void lessThanOrEqual() {
        Atom a = new Atom('a', 1, 2);
        Assert.assertTrue(atom.lessThanOrEqual(a));
    }

    @Test
    public void lessThanOrEqual_Letter() {
        Atom a = new Atom('b', 1, 2);
        Assert.assertTrue(atom.lessThanOrEqual(a));
    }

    @Test
    public void lessThan() {
        Atom a = new Atom('a', 0, 1);
        System.err.println(atom + " < " + a);
        Assert.assertTrue(a.lessThan(atom));
    }
}