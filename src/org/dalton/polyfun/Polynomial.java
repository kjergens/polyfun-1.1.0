package org.dalton.polyfun;

/**
 * A Polynomial is a polynomial in X with a certain degree and a set of coefficients. These coefficients may be
 * specific values (doubles) or abstract variables depending on the situation. In the documentation for this class,
 * "term" refers to a term of a polynomial in the mathematical sense, while "Term" refers to the java object.
 * The coefficients, whether numbers or letters or a combination, are always stored as Coefs in an array of Coefs
 * called "coeffs." The index of the array corresponds to the degree of the term for which that Coef belongs.
 * <p>
 * Example: mathematical object
 * <p>
 * p(x) = (3a+1)x^3 + (ab-ac)x + c_2
 * <p>
 * Java object degree = 3
 * Coefs = {c_2, ab-ac, 0, 3a+1} (an array of Coefs. Note the order of the Coefs in the array)
 * <p>
 * Students will use this code and expand on it when developing the VDM method(s) and other Polynomial
 * manipulation methods.
 *
 * @Author David Gomprecht
 * @Author Katie Jergens
 */
public class Polynomial {
    private int degree;
    private Coef[] coefs;

    /**
     * Default constructor.
     */
    public Polynomial() {
    }

    /**
     * Construct a Polynomial by specifying an array of Coefs
     *
     * @param coefs The array of Coefs.
     */
    public Polynomial(Coef[] coefs) {
        this.degree = coefs.length - 1;
        this.coefs = new Coef[coefs.length];

        System.arraycopy(coefs, 0, this.coefs, 0, coefs.length);
    }

    /**
     * Constructor where you pass an array of numerical coefficients used to initialize Terms,
     * which creates the Coef array.
     *
     * @param numericalCoefficients array of numerical coefficients
     */
    public Polynomial(double[] numericalCoefficients) {
        this.degree = numericalCoefficients.length - 1;
        this.coefs = new Coef[numericalCoefficients.length];

        for (int i = 0; i < numericalCoefficients.length; ++i) {
            this.coefs[i] = new Coef(numericalCoefficients[i]);
        }
    }

    /**
     * Construct a Polynomial by setting the degree.
     *
     * @param degree The degree of the polynomial
     */
    public Polynomial(int degree) {
        this.degree = degree;
        this.coefs = new Coef[this.degree + 1];
    }

    /**
     * Constructs a zeroth degree or constant polynomial equal to the constant parameter.
     * Note: This has a very different behavior than Polynomial(int degree)
     *
     * @param constant The constant term of the zeroth degree polynomial.
     */
    public Polynomial(double constant) {
        this.coefs = new Coef[1];
        this.coefs[0] = new Coef(constant);
        this.degree = 0;
    }

    /**
     * Constructs a polynomial with one term and a specific degree.
     * Example: coefficient = 2.0 deg = 3 creates p(x) = 2.0x^3.
     *
     * @param numericalCoefficient The coefficient
     * @param degree               The degree of the term and the polynomial
     */
    public Polynomial(double numericalCoefficient, int degree) {
        this.coefs = new Coef[degree + 1];
        this.coefs[degree] = new Coef(numericalCoefficient);

        for (int i = 0; i < degree; ++i) {
            this.coefs[i] = new Coef(0.0D);
        }

        this.degree = degree;
    }

    /**
     * Constructs a zeroth degree polynomial which consists of an abstract constant.
     * For example: with atom = a_0 this creates p(x) = a_0.
     *
     * @param atom The Atom which becomes the polynomial
     */
    public Polynomial(Atom atom) {
        this.coefs = new Coef[1];
        this.coefs[0] = new Coef(atom);
        this.degree = 0;
    }

    /**
     * Constructs a polynomial of a certain degree with only one term whose coefficient is an abstract constant.
     * For example: with atom = a_2 and degree = 2 this creates p(x) = (a_2)x^2
     *
     * @param atom   The Atom which becomes the coefficient
     * @param degree The degree of the polynomial
     */
    public Polynomial(Atom atom, int degree) {
        this.coefs = new Coef[degree + 1];
        this.coefs[degree] = new Coef(atom);

        for (int i = 0; i < degree; ++i) {
            this.coefs[i] = new Coef(0.0D);
        }

        this.degree = degree;
    }

    /**
     * Constructs a zeroth degree polynomial which consists of an abstract constant(s) and
     * a double (i.e. a Term). For example: with term = 3ab this creates p(x) = 3ab.
     *
     * @param term Term is an abstract constant and a coefficient.
     */
    public Polynomial(Term term) {
        this.coefs = new Coef[1];
        this.coefs[0] = new Coef(term);
        this.degree = 0;
    }

    /**
     * Constructs a polynomial of a certain degree with only one term whose coefficient is an abstract constant(s)
     * and a double (i.e. a Term). For example: with term = 3ab and deg = 2 this creates p(x) = (3ab)x^2
     *
     * @param term
     * @param degree
     */
    public Polynomial(Term term, int degree) {
        this.coefs = new Coef[degree + 1];
        this.coefs[degree] = new Coef(term);

        for (int i = 0; i < degree; ++i) {
            this.coefs[i] = new Coef(0.0D);
        }

        this.degree = degree;
    }

    /**
     * Constructs a zeroth degree polynomial which consists of one Coef.
     * For example: with coef = 3ab+c this creates p(x) = 3ab+c.
     *
     * @param coef A Coef object is an array of Terms, which becomes the coefficient.
     */
    public Polynomial(Coef coef) {
        this.coefs = new Coef[1];
        this.coefs[0] = coef;
        this.degree = 0;
    }

    /**
     * Constructs a polynomial of a certain degree with only one term whose coefficient is a Coef.
     * For example: with newcoef = 3ab+c and deg = 2 this creates p(x) = (3ab+c)x^2
     *
     * @param coef   A Coef object is an array of Terms, which becomes the coefficient.
     * @param degree The degree of the polynomial
     */
    public Polynomial(Coef coef, int degree) {
        this.coefs = new Coef[degree + 1];
        this.coefs[degree] = coef;

        for (int i = 0; i < degree; ++i) {
            this.coefs[i] = new Coef(0.0D);
        }

        this.degree = degree;
    }

    /**
     * Construct a polynomial with generic coefficients.
     * Example: letter = a, deg = 3 creates p(x) = (a_3)x^3 + (a_2)x^2 + (a_1)x + (a_0)
     *
     * @param letter The letter for the base of each coefficient. Subscripts are automatically applied.
     * @param degree The degree of the polynomial
     */
    public Polynomial(char letter, int degree) {
        this.coefs = new Coef[degree + 1];
        Atom[] atoms = new Atom[degree + 1];
        Term[] terms = new Term[degree + 1];

        for (int i = 0; i < atoms.length; ++i) {
            atoms[i] = new Atom(letter, i, 1);
            terms[i] = new Term(atoms[i]);
            this.coefs[i] = new Coef(terms[i]);
        }

        this.degree = degree;
    }

    /**
     * Gets the degree of the Polynomial.
     *
     * @return degree The degree of the polynomial
     */
    public int getDegree() {
        return this.degree;
    }

    /**
     * Doesn't follow naming convention.
     *
     * @deprecated use {@link #getCoefAt(int)} instead.
     */
    @Deprecated
    public Coef getCoefficient(int index) {
        return this.coefs[index];
    }

    /**
     * Retrieve a specific coefficient by specifying the x which corresponds with that
     * org.dalton.polyfun.Coef. Returns a org.dalton.polyfun.Coef.
     *
     * @param index
     * @return org.dalton.polyfun.Coef
     */
    public Coef getCoefAt(int index) {
        return this.coefs[index];
    }

    /**
     * Doesn't follow naming convention.
     *
     * @deprecated use {@link #getCoefs()} instead.
     */
    @Deprecated
    public Coef[] getCoefficients() {
        return this.coefs;
    }

    /**
     * Get the Coef array.
     *
     * @return the array of Coefs.
     */
    public Coef[] getCoefs() {
        return this.coefs;
    }

    /**
     * Sets the degree of the Polynomial.
     *
     * @param degree The degree to set (also specifies length of coeffs array)
     */
    public void setDegree(int degree) {
        this.degree = degree;
        this.coefs = new Coef[degree + 1];
    }

    /**
     * Doesn't follow naming convention.
     *
     * @deprecated use {@link #setCoefs(Coef[])} ()} instead.
     */
    @Deprecated
    public void setCoefficients(Coef[] coefs) {
        this.degree = coefs.length - 1;
        this.coefs = new Coef[this.degree + 1];

        System.arraycopy(coefs, 0, this.coefs, 0, this.degree + 1);

    }

    /**
     * Doesn't follow naming convention.
     *
     * @deprecated use {@link #setCoefs(double[])} instead.
     */
    @Deprecated
    public void setCoefficients(double[] nums) {
        this.degree = nums.length - 1;
        this.coefs = new Coef[this.degree + 1];

        for (int i = 0; i <= this.degree; ++i) {
            this.coefs[i] = new Coef(nums[i]);
        }

    }

    /**
     * Set the Coefs array of the polynomial.
     *
     * @param coefs Coef array to copy into the polynomial. Also changes the degree.
     */
    public void setCoefs(Coef[] coefs) {
        this.degree = coefs.length - 1;
        this.coefs = new Coef[this.degree + 1];

        System.arraycopy(coefs, 0, this.coefs, 0, this.degree + 1);

    }

    /**
     * Set the Coefs array of the polynomial.
     *
     * @param coefficients Constants to make Coef objects out of.
     */
    public void setCoefs(double[] coefficients) {
        this.degree = coefficients.length - 1;
        this.coefs = new Coef[this.degree + 1];

        for (int i = 0; i <= this.degree; ++i) {
            this.coefs[i] = new Coef(coefficients[i]);
        }

    }

    /**
     * Add two GenPolynomials by adding the coefficients of the corresponding terms.
     *
     * @param polynomial Polynomial  to add
     * @return the sum
     */
    public Polynomial plus(Polynomial polynomial) {
        int biggerDegree = Math.max(this.getDegree(), polynomial.getDegree());
        int smallerDegree = Math.min(this.getDegree(), polynomial.getDegree());

        Coef[] coefs = new Coef[biggerDegree + 1];

        // Add the co-responding coefficients
        for (int i = 0; i <= smallerDegree; i++) {
            coefs[i] = this.getCoefAt(i).plus(polynomial.getCoefAt(i));
        }

        // Get the rest of the coefficients
        if (this.getDegree() > polynomial.getDegree()) {
            for (int i = smallerDegree + 1; i <= biggerDegree; i++) {
                coefs[i] = this.getCoefAt(i);
            }
        } else {
            for (int i = smallerDegree + 1; i <= biggerDegree; i++) {
                coefs[i] = polynomial.getCoefAt(i);
            }
        }

        return new Polynomial(coefs);
    }

    /**
     * Subtracts two GenPolynomials.
     *
     * @param polynomial Polynomial to substract
     * @return The difference
     */
    public Polynomial minus(Polynomial polynomial) {
        return this.plus(polynomial.times(-1.0D));
    }

    /**
     * Multiply a polynomial by a scalar by multiplying all the Coefs by the scalar.
     *
     * @param scalar to multiply
     * @return the product
     */
    public Polynomial times(double scalar) {
        Coef[] coefs = new Coef[this.getDegree() + 1];

        for (int i = 0; i < this.getDegree() + 1; ++i) {
            coefs[i] = this.getCoefAt(i).times(scalar);
        }

        return new Polynomial(coefs);
    }

    /**
     * Multiply a polynomial by a Coef by multiplying all the Coefs by the Coef.
     *
     * @param coef to multiply
     * @return the product
     */
    public Polynomial times(Coef coef) {
        Coef[] coefs = new Coef[this.getDegree() + 1];

        for (int i = 0; i < this.getDegree() + 1; ++i) {
            coefs[i] = this.getCoefAt(i).times(coef);
        }

        return new Polynomial(coefs);
    }

    /**
     * Multiply a polynomial by a polynomial.
     *
     * @param polynomial to multiply
     * @return the product
     */
    public Polynomial times(Polynomial polynomial) {
        Coef[] coefs = new Coef[this.getDegree() + polynomial.getDegree() + 1];

        int i;
        for (i = 0; i < coefs.length; ++i) {
            coefs[i] = new Coef(0.0D);
        }

        for (i = 0; i < coefs.length; ++i) {
            for (int j = 0; j <= i; ++j) {
                if (j <= this.getDegree() && i - j <= polynomial.getDegree()) {
                    coefs[i] = coefs[i].plus(this.getCoefAt(j).times(polynomial.getCoefAt(i - j)));
                }
            }
        }

        return new Polynomial(coefs);
    }

    /**
     * Raise to a power.
     *
     * @param power to raise by
     * @return Polynomial the result.
     */
    public Polynomial to(int power) {
        Polynomial polynomial = new Polynomial(1.0D);

        if (power >= 1) {
            polynomial.setCoefs(this.times(this.to(power - 1)).getCoefs());
        }

        return polynomial;
    }

    /**
     * Adds the Polynomial p(x) = mx + b to a Polynomial
     *
     * @return The new GenPoynomial with mx + b added to it.
     */
    public Polynomial addTangent() {
        Atom atomSlope = new Atom('m');
        Polynomial mx = new Polynomial(atomSlope, 1);

        Atom atomBias = new Atom('b');
        Polynomial b = new Polynomial(atomBias);

        Polynomial tangent = mx.plus(b);

        return this.plus(tangent);
    }

    /**
     * Composes two GenPolynomials.
     * Example: if this = p(x) and poly = q(x), this.of(poly) returns p[q(x)]
     *
     * @param polynomial The inner polynomial
     * @return The new polynomial which is the composition
     */
    public Polynomial of(Polynomial polynomial) {
        Polynomial result = new Polynomial(0.0D);

        for (int i = 0; i <= this.getDegree(); ++i) {
            Polynomial temp = polynomial.to(i).times(this.getCoefAt(i));
            result.setCoefs(result.plus(temp).getCoefs());
        }

        return result;
    }

    /**
     * Think of this method as plugging in a numeric value into a polynomial function.
     * For example, if p(x) = x2 + 5, and x = 2, then p.evaluate(2) would essentially
     * evaluate p(2) = 22 + 5 = 9. Although the answer will in many cases be a number,
     * a double, it is possible to have other variables as coefficients. This forces
     * the .evaluate(double x) to return a Coef. Use methods for Coefs to extract the
     * double from it.
     *
     * @param value The value to plug into the polynomial
     * @return org.dalton.polyfun.Coef the result
     */
    public Coef evaluate(double value) {
        Polynomial polynomial = new Polynomial(value);
        Coef coef = new Coef(0.0D);

        for (int i = 0; i < this.coefs.length; ++i) {
            coef.setTerms(coef.plus(polynomial.to(i).times(this.coefs[i]).getCoefAt(0)).getTerms());
        }

        return coef;
    }

    /**
     * Think of this method as plugging in a variable into a polynomial function. For example,
     * if p(x) = x2 + 5, and org.dalton.polyfun.Coef C = a2, then p.evaluate(C) would essentially evaluate
     * p(a^2) = (a^2)^2 + 5 = a^4 + 5
     *
     * @param coef The coef to plug into the polynomial.
     * @return org.dalton.polyfun.Coef object
     */
    public Coef evaluate(Coef coef) {
        Polynomial polynomial = new Polynomial(coef);
        Coef result = new Coef(0.0D);

        for (int i = 0; i < this.coefs.length; ++i) {
            result.setTerms(result.plus(polynomial.to(i).times(this.coefs[i]).getCoefAt(0)).getTerms());
        }

        return result;
    }

    public boolean isPlottable() {
        int degree = -1;

        for (int i = 0; i <= this.degree; ++i) {
            if (this.coefs[i].isDouble()) {
                ++degree;
            }
        }

        return degree == this.degree;
    }

    /**
     * Doesn't follow convention.
     *
     * @deprecated use {@link #toString()} instead.
     */
    @Deprecated
    public void print() {
        for (int i = this.degree; i > 1; --i) {
            if (!this.coefs[i].isZero()) {
                System.out.print("(");
                this.coefs[i].print();
                System.out.print(")X^" + i);

                int j;
                for (j = i - 1; j > 0 && this.coefs[j].isZero(); --j) {
                }

                if (j != 0) {
                    System.out.print("+");
                }
            }
        }

        if (this.degree > 0) {
            if (!this.coefs[1].isZero()) {
                System.out.print("(");
                this.coefs[1].print();
                System.out.print(")X");
            }

            if (!this.coefs[0].isZero()) {
                System.out.print("+");
                this.coefs[0].print();
            }
        }

        if (this.degree == 0 && !this.coefs[0].isZero()) {
            this.coefs[0].print();
        }

        System.out.println();
    }

    /**
     * Returns a printable string.
     *
     * @return String representing the polynomial.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        // Get terms with an exponent
        for (int i = this.getDegree(); i > 1; i--) {
            if (!this.getCoefAt(i).isZero()) {
                string.append("(").append(this.getCoefAt(i).toString()).append(")X^").append(i);

                // Skip zeros
                int j = i - 1;
                while(j > 0 && this.getCoefAt(j).isZero()) {
                    j--;
                }

                // If not at end append a "+"
                if (j != 0) {
                    string.append("+");
                }
            }
        }

        if (this.getDegree() > 0) {
            // Get term with no exponent (X exponent is 1)
            if (!this.getCoefAt(1).isZero()) {
                string.append("(").append(this.getCoefAt(1).toString()).append(")X");
            }

            // Get 0-degree term (no X invariate)
            if (!this.getCoefAt(0).isZero()) {
                string.append("+").append(this.getCoefAt(0).toString());
            }
        } else if (this.getDegree() == 0 && !this.getCoefAt(0).isZero()) {
            string.append(this.getCoefAt(0).toString());
        }

        return string.append("\n").toString();
    }
}
