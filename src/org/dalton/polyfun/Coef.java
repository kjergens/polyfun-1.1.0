package org.dalton.polyfun;

/**
 * An array of Terms. The Terms are understood to be added.
 * For example: In the polynomial in x:
 *
 *      P(x) = [2(a_1)^3(b) + 3b^2]x^4 - (a_2)(b_4)x^2 + 7ab + b_2
 *
 *  2(a_1)^3(b)+3b^2 is a Coef, as is -(a_2)(b_4) and 7ab + b_2. The first and third have
 *  length 2 (two Terms) the middle has length 1 (one term)
 *
 * @Author David Gomprecht
 * @Author Katie Jergens
 *
 *  TODO: In the setters, think through if they should assign an attribute to the Object passed in...
 *  TODO:  (cont) ... or first make a copy of the Object (so that it can't be altered from outside the instance).
 */
public class Coef {
    private Term[] terms;

    /**
     * Default constructor.
     */
    public Coef() {
    }

    /**
     * Constructor that takes an array of Terms and copies the values into the terms array.
     *
     * @param terms the Term array to copy
     */
    public Coef(Term[] terms) {
        this.terms = new Term[terms.length];

        for (int i = 0; i < terms.length; ++i) {
            Term term = new Term(terms[i].getNumericalCoefficient(), terms[i].getAtoms());
            this.terms[i] = term.simplify();
        }
    }

    /**
     * Construct a Coef with just 1 term. The Term is inserted into an array of length 1.
     *
     * @param term A single Term that becomes the Coef.
     */
    public Coef(Term term) {
        this.setTerms(new Term[]{term.simplify()});  // TODO: Better to make a copy of term first?
    }

    /**
     * Construct a Coef with just 1 Atom. The Atom is made into a Term and the Term is put into an array.
     *
     * @param atom Atom object used to initialize a term which will be the only element in the terms array.
     */
    public Coef(Atom atom) {
        Term term = new Term(atom);   // TODO: Better to make a copy of atom first?
        Term[] terms = new Term[]{term};
        this.setTerms(terms);
    }

    /**
     * Construct a Coef with just a constant.
     * The constant is converted into a Term and the Term is put into the array.
     *
     * @param constant
     */
    public Coef(double constant) {
        Term term = new Term(constant);
        Term[] terms = new Term[]{term};
        this.setTerms(terms);
    }

    /**
     * Construct a Coef with just one letter (no numerical coefficient or exponent).
     *
     * @param letter Used to create the term, which is the only element in the Term array.
     */
    public Coef(char letter) {
        Term term = new Term(letter);
        Term[] terms = new Term[]{term};
        this.setTerms(terms);
    }

    public void setTerms(Term[] terms) {
        this.terms = new Term[terms.length];

        for (int i = 0; i < terms.length; ++i) {
            this.terms[i] = new Term(terms[i].getNumericalCoefficient(), terms[i].getAtoms());
            this.terms[i].simplify();
        }

    }

    public void setTerms(Term term) {
        this.setTerms(new Term[]{term.simplify()}); // TODO: Better to make a copy of term first?
    }

    public Term[] getTerms() {
        return this.terms;
    }

    @Deprecated
    public void print() {
        if (this.terms[0].getNumericalCoefficient() != 0.0D) {
            this.terms[0].print();
        }

        for (int i = 1; i < this.terms.length; ++i) {
            if (this.terms[i].getNumericalCoefficient() > 0.0D) {
                System.out.print("+");
                this.terms[i].print();
            } else if (this.terms[i].getNumericalCoefficient() < 0.0D) {
                this.terms[i].print();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        if (this.terms[0].getNumericalCoefficient() != 0.0D) {
            string = new StringBuilder(this.terms[0].toString());
        }

        for (int i = 1; i < this.terms.length; ++i) {
            if (this.terms[i].getNumericalCoefficient() > 0.0D) {
                string.append("+");
                string.append(this.terms[i].toString());
            } else if (this.terms[i].getNumericalCoefficient() < 0.0D) {
                string.append(this.terms[i].toString());
            }
        }

        return string.toString();
    }

    /**
     * Create new Ceof array with the first Term removed. Meant for array > 1 only.
     *
     * TODO: Make this so it actually updates the coefs attribute and returns the popped Term.
     * TODO: Rename pop()
     *
     * @return New Coef with one less Term.
     */
    public Coef snip() {
        Term[] terms = new Term[this.getTerms().length - 1];

        for (int i = 0; i < this.getTerms().length - 1; ++i) {
            terms[i] = this.getTerms()[i + 1];
        }

        return new Coef(terms);
    }

    /**
     * Creates new Coef array with the new Term at the front of the array of Terms.
     *
     * @param term Term to place in front.
     * @return A new Coef with another Term (original Coef is not changed)
     *
     * TODO: Actually update this Term.
     * TODO: rename insertInFront() or push()
     */
    public Coef paste(Term term) {
        Term[] terms = new Term[this.getTerms().length + 1];
        terms[0] = term;

        for (int i = 1; i < this.getTerms().length + 1; ++i) {
            terms[i] = new Term(this.getTerms()[i - 1].getNumericalCoefficient(), this.getTerms()[i - 1].getAtoms());
        }

        return new Coef(terms);
    }

    /**
     * Creates a new Coef with the Term inserted in a convenient order or combines it with a like another like Term
     *
     * @param term
     * @return A new Coef with another Term (original Coef is not changed)
     *
     * TODO: Actually update this Coef
     * TODO: rename insert()
     */
    public Coef place(Term term) {
        Coef coef = new Coef(this.terms);

        if (!term.isZero() && term.lessThan(this.getTerms()[0])) {
            return coef.paste(term);
        } else if (term.equals(this.getTerms()[0])) {
            coef.getTerms()[0].setNumericalCoefficient(term.getNumericalCoefficient() + coef.getTerms()[0].getNumericalCoefficient());
        } else if (this.getTerms().length == 1) {
            Term[] terms = new Term[]{term};
            Coef coef1 = new Coef(terms);
            coef.setTerms(coef1.paste(this.getTerms()[0]).getTerms());
        } else {
            Term term1 = this.getTerms()[0];
            coef.setTerms(coef.snip().place(term).paste(term1).getTerms());
        }

        return coef;

    }

    /**
     * Combines like terms and writes them in order.
     *
     * @return this The original Coef is permanently altered.
     */
    public Coef simplify() {
        Coef coef = new Coef(this.getTerms());

        if (this.getTerms().length > 1) {
            Term term = new Term(this.getTerms()[0].simplify().getNumericalCoefficient(), this.getTerms()[0].simplify().getAtoms());
            this.setTerms(coef.snip().simplify().place(term).getTerms());
        } else {
            Term[] terms = new Term[]{this.getTerms()[0].simplify()};
            this.setTerms(terms);
        }

        return this;
    }

    /**
     * Multiply a Coefficient by another Coefficient.
     *
     * @param coef The Coef object to multiply to this one.
     * @return the product
     */
    public Coef times(Coef coef) {
        Term[] terms = new Term[this.getTerms().length * coef.getTerms().length];

        for (int i = 0; i < this.getTerms().length; ++i) {
            for (int j = 0; j < coef.getTerms().length; ++j) {
                double tempNum = this.getTerms()[i].times(coef.getTerms()[j]).getNumericalCoefficient();
                Atom[] atoms = this.getTerms()[i].times(coef.getTerms()[j]).getAtoms();
                Term term = new Term(tempNum, atoms);
                int index = i * coef.getTerms().length + j;
                terms[index] = term;
            }
        }

        Coef result = new Coef(terms);
        return result.simplify();
    }

    /**
     * Add Coefs by combining like Terms and adding unlike Terms
     *
     * @param coef Coef to be added to the first Coef
     * @return sumCoef The sum of this and that
     */
    public Coef plus(Coef coef) {
        Term[] terms = new Term[this.getTerms().length + coef.getTerms().length];

        for (int i = 0; i < terms.length; ++i) {
            if (i < this.getTerms().length) {
                terms[i] = this.getTerms()[i];
            } else {
                terms[i] = coef.getTerms()[i - this.getTerms().length];
            }
        }

        Coef coef1 = new Coef(terms);
        return coef1.simplify();
    }

    /**
     * Multiplies each Term in the Coef by a scalar (double)
     *
     * @param scalar The double to multiply it by
     * @return Coef that's been multiplied by a scalar
     */
    public Coef times(double scalar) {
        Term[] terms = new Term[this.getTerms().length];

        for (int i = 0; i < this.getTerms().length; ++i) {
            terms[i] = this.getTerms()[i].times(scalar);
        }

        return new Coef(terms);
    }

    /**
     * If the Coef is zero, it returns true.
     *
     * @return true if the Coeff is 0
     */
    public boolean isZero() {
        this.simplify();

        for (int i = 0; i < this.getTerms().length; ++i) {
            if (!this.getTerms()[i].isZero()) {
                return false;
            }
        }

        // If got this far it all Terms are zero.
        return true;
    }

    /**
     * True if Coef consists only of a double, false otherwise
     *
     * @return True if Coef consists only of a double, false otherwise
     * @deprecated use {@link #isConstantCoef()} instead.
     */
    public boolean isDouble() {
        this.simplify();
        return this.terms.length == 1 && this.terms[0].isDouble();
    }

    /**
     * True if Coef consists only of a double, false otherwise
     * @return True if Coef consists only of a double, false otherwise
     */
    public boolean isConstantCoef() {
        this.simplify();
        return this.terms.length == 1 && this.terms[0].isConstantTerm();
    }
}

