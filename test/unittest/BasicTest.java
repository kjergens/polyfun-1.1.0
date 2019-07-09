package unittest;

import org.dalton.polyfun.*;

/**
 * Test Polynomial
 */
public class BasicTest {

    public static void main(String[] args) {
        /**
         * In this sample code we will instantiate a polynomial, p(x)=2x^3-3x+1,
         * in two different ways.  The first way will create an array of doubles which
         * we will then pass through the constructor.
         * The second method will create 3 single term polynomials and add them.
         */

        //First create the coefficient array:
        double[] coefficients = {1, -3, 0, 2};  //note the order of the numbers, and that 0 place holders must be included.
        Polynomial p = new Polynomial(coefficients);

        System.out.print("p(x) = " + p); //prints the polynomial to the console window

        //Now we will create the same polynomial in a different way.
        Polynomial a = new Polynomial(2.0, 3); //creates the "polynomial" 2x^3
        Polynomial b = new Polynomial(-3.0, 1); //creates the "polynomial" -3x
        Polynomial c = new Polynomial(1.0);     //creates the constant "polynomial" 1

        /**
         * Note that in the above three lines that care was taken to write 2.0 (instead of just 2)
         * to force the number to be interpreted as a double and not an int
         */

        // Create our polynomial by adding the three we have just created.
        Polynomial P = a.plus(b.plus(c));

        System.out.print("P(x) = " + P);

        /**
         * In the next code chunk we will find the value of p (or P) at x=3
         */

        Coef C = p.evaluate(3); //plugs in 3 and returns the answer as a org.dalton.polyfun.Coef which we call C
        Term T = C.getTerms()[0]; //pulls the first org.dalton.polyfun.Term (index 0) off the array of Terms and calls it T
        double y = T.getNumericalCoefficient(); //finds the number associated with that org.dalton.polyfun.Term, which is our answer.

        System.out.println("p(3) = " + y);  //prints the value of the polynomial when x=3 to the console

        /**
         * Note: p is a concrete polynomial (no variables other than x) so we know that when
         * evaluated at a number (double) the answer is just a number (double).
         * That means that the array of Terms C is of length 1.  It also means that the
         * org.dalton.polyfun.Term T contains only a double, and no other variable.
         */

        //This process could have been streamlined by calling multiple methods in one line:
        double Y = P.evaluate(3).getTerms()[0].getNumericalCoefficient(); //as P and p are the same Y and y will also be the same

        System.out.println("P(3) = " + Y);

    }

}