import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.dalton.polyfun.*;
import static org.junit.Assert.*;

public class AtomTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConstructors() {
        Atom atom = new Atom('a');
        atom.setPower(2);
        atom.setSubscript(1);

        Atom atom1 = new Atom('a', 1, 2);

        Assert.assertTrue(atom.equals(atom1));
    }

    @Test
    public void setAtom() {
    }

    @Test
    public void getLetter() {
    }

    @Test
    public void getSubscript() {
    }

    @Test
    public void getPower() {
    }

    @Test
    public void print() {
    }

    @Test
    public void toString1() {
    }

    @Test
    public void timesLikeAtom() {
    }

    @Test
    public void identicalTo() {
    }

    @Test
    public void equals1() {
    }

    @Test
    public void like() {
    }

    @Test
    public void lessThanOrEqual() {
    }

    @Test
    public void lessThan() {
    }
}