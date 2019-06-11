import org.junit.*;
import org.dalton.polyfun.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AtomTest {

    Atom atom = new Atom('a', 1, 2);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        // Point System.out to another output stream so I can test the print() outputs.
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        // Point System.out back to console.
        System.setOut(System.out);
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
    @SuppressWarnings("deprecation")
    public void print() {
        atom.print();
        Assert.assertEquals("a_1^2", outContent.toString());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void print_vs_toString() {
        atom.print();
        Assert.assertEquals(outContent.toString(), atom.toString());
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
    @SuppressWarnings("deprecation")
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
    @SuppressWarnings("deprecation")
    public void identicalTo_vs_Equals(){
        Atom a = new Atom('a', 1, 2);
        Assert.assertEquals(atom.identicalTo(a), atom.equals(a));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void like() {
        Atom a = new Atom('a', 1, 4);
        Assert.assertTrue(atom.like(a));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThanOrEqual() {
        Atom a = new Atom('a', 1, 2);
        Assert.assertTrue(atom.lessThanOrEqual(a));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThanOrEqual_HigherLetter() {
        Atom a = new Atom('b', 1, 2);
        Assert.assertTrue(atom.lessThanOrEqual(a));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThanOrEqual_HigherLetterLowerSubscript() {
        Atom a = new Atom('b', 0, 0);
        Assert.assertFalse(a.lessThanOrEqual(atom));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThanOrEqual_SameLetterLowerSubscript() {
        Atom a = new Atom('a', 0, 0);
        Assert.assertTrue(a.lessThanOrEqual(atom));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThan_SameLetterLowerSubscript() {
        Atom a = new Atom('a', 0, 1);
        Assert.assertTrue(a.lessThan(atom));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThan_HigherLetterLowerSubscript() {
        Atom a = new Atom('b', 0, 1);
        Assert.assertTrue(atom.lessThan(a));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void lessThan_SameLetterSameSubscript() {
        Atom a = new Atom('a', 1, 1);
        Assert.assertFalse(atom.lessThan(a));
    }
}