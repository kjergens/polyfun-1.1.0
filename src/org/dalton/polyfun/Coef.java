package org.dalton.polyfun;

import java.util.Arrays;

/**
 * An array of Terms. The Terms are understood to be added.
 * For example: In the polynomial in x:
 * <p>
 * P(x) = [2(a_1)^3(b) + 3b^2]x^4 - (a_2)(b_4)x^2 + 7ab + b_2
 * <p>
 * 2(a_1)^3(b)+3b^2 is a Coef, as is -(a_2)(b_4) and 7ab + b_2. The first and third have
 * length 2 (two Terms) the middle has length 1 (one term)
 *
 *  @author David Gomprecht (wrote the original Coef object)
 *  @author Katie Jergens (wrote refactored version based on Dr. Gomprecht's library)
 * <p>
 * TODO: In the setters, think through if they should assign an attribute to the Object passed in...
 * TODO:  (cont) ... or first make a copy of the Object (so that it can't be altered from outside the instance).
 */
public class Coef {
    private Term[] terms;

    /**
     * Default constructor.
     * @since 1.0.0
     */
    public Coef() {
    }

    /**
     * Constructor that takes an array of Terms and copies the values into the terms array.
     *
     * @param terms the Term array to copy
     * @since 1.0.0
     */
    public Coef(Term[] terms) {
        this.terms = new Term[terms.length];

        for (int i = 0; i < terms.length; ++i) {
            Term term = new Term(terms[i].getNumericalCoefficient(), terms[i].getAtoms());
            term.reduce();
            this.terms[i] = term;
        }

        this.reduce();
    }

    /**
     * Construct a Coef with just 1 term. The Term is inserted into an array of length 1.
     *
     * @param term A single Term that becomes the Coef.
     * @since 1.0.0
     */
    public Coef(Term term) {
        term.reduce();
        this.setTerms(new Term[]{term});  // TODO: Better to make a copy of term first?
    }

    /**
     * Construct a Coef with just 1 Atom. The Atom is made into a Term and the Term is put into an array.
     *
     * @param atom Atom object used to initialize a term which will be the only element in the terms array.
     * @since 1.0.0
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
     * @since 1.0.0
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
     * @since 1.0.0
     */
    public Coef(char letter) {
        Term term = new Term(letter);
        Term[] terms = new Term[]{term};
        this.setTerms(terms);
    }

    /**
     * Get terms array.
     *
     * @return terms array
     * @since 1.0.0
     */
    public Term[] getTerms() {
        return this.terms;
    }

    /**
     * Set terms array. The array passed in is copied to the terms attribute.
     *
     * @param terms
     * @since 1.0.0
     */
    public void setTerms(Term[] terms) {
        this.terms = new Term[terms.length];

        for (int i = 0; i < terms.length; ++i) {
            this.terms[i] = new Term(terms[i].getNumericalCoefficient(), terms[i].getAtoms());
            this.terms[i].reduce();
        }

    }

    /**
     * Set terms array with this term as the only element
     *
     * @param term term that will make up the terms array
     * @since 1.0.0
     */
    public void setTerms(Term term) {
        term.reduce();
        this.setTerms(new Term[]{term}); // TODO: Better to make a copy of term first?
    }

    /**
     * Create new Ceof array with the first Term removed. Meant for array > 1 only.
     * <p>
     *
     * @return  a new Coef with one less Term.
     * @since 1.0.0
     */
    public Coef snip() {
        Term[] terms = new Term[this.getTerms().length - 1];

        for (int i = 0; i < this.getTerms().length - 1; ++i) {
            terms[i] = this.getTerms()[i + 1];
        }

        return new Coef(terms);
    }

    /**
     * Remove first Term.
     *
     * @return The popped Term or null
     * @since 1.1.0
     */
    public Term pop() {
        if (this.getTerms().length == 0) {
            return null;
        } else {
            // Save the first term before it's removed
            Term poppedTerm = this.getTerms()[0];

            // Create new shorter-by-one terms array
            Term[] terms = new Term[this.getTerms().length - 1];

            // Copy everything but first term into new array
            System.arraycopy(this.getTerms(), 1, terms, 0, this.getTerms().length - 1);

            // Set new array as the terms for this Coef
            this.setTerms(terms);

            // Return the popped Term
            return poppedTerm;
        }
    }

    /**
     * Creates new Coef array with the new Term at the front of the array of Terms.
     *
     * @param term Term to place in front.
     * @return A new Coef with another Term (original Coef is not changed)
     * @since 1.0.0
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
     * Inserts new Term at the front of the array of Terms.
     *
     * @param term Term to place in front.
     * @return
     * @since 1.1.0
     **/
    public void push(Term term) {
        Term[] terms = new Term[this.getTerms().length + 1];
        terms[0] = term;

        for (int i = 1; i < this.getTerms().length + 1; i++) {
            terms[i] = new Term(this.getTerms()[i - 1].getNumericalCoefficient(), this.getTerms()[i - 1].getAtoms());
        }

        this.setTerms(terms);
    }

    /**
     * Creates a new Coef with the Term inserted in a convenient order or combines it with a like another like Term
     *
     * @param term
     * @return A new Coef with another Term (original Coef is not changed)
     * @since 1.0.0
     */
    public Coef place(Term term) {
        Coef coef = new Coef(this.terms);

        if (!term.isZero() && (this.getTerms() == null
                || this.getTerms().length == 0
                || term.lessThan(this.getTerms()[0]))) {
            // If the given term is less than the Coef's first term, insert the new term at the front.
            return coef.paste(term);
        } else if (term.equals(this.getTerms()[0])) {
            // If the given term is the same as  the Coef's first term, add the numerical coefficients.
            coef.getTerms()[0].setNumericalCoefficient(term.getNumericalCoefficient() + coef.getTerms()[0].getNumericalCoefficient());
        } else if (this.getTerms().length == 1) {
            // If the Coef only has one term, append the given term at the end.
            Term[] terms = new Term[]{term};
            Coef coef1 = new Coef(terms);
            coef.setTerms(coef1.paste(this.getTerms()[0]).getTerms());
        } else {
            // Otherwise, call this again recursively
            Term term1 = this.getTerms()[0];
            coef.setTerms(coef.snip().place(term).paste(term1).getTerms());
        }

        return coef;
    }

    /**
     * Inserts given Term in a convenient order or combines it with a like another like Term
     *
     * @param term
     * @return
     * @since 1.1.0
     */
    public void insert(Term term) {

        if (term != null && !term.isZero()) {
            // If it's the first term, just add it
            if (this.getTerms() == null || this.getTerms().length == 0) {
                this.setTerms(term);
                return; // Quit once you've handled it.
            }

            // If the term is the same as an existing term, add the numerical coefficients.
            for (int i = 0; i < this.getTerms().length; i++) {
                if (term.equals(this.getTerms()[i])) {
                    double sum = term.getNumericalCoefficient() + this.getTerms()[i].getNumericalCoefficient();
                    this.getTerms()[i].setNumericalCoefficient(sum);
                    return; // Quit once you've handled it.
                }
            }

            // For all other cases, push the new term, then sort all terms alphanumerically
            this.push(term);
            Arrays.sort(this.getTerms());
        }
    }

    /**
     * Combines like terms and writes them in order.
     *
     * @return this The original Coef is permanently altered.
     * @since 1.0.0
     */
    public Coef simplify() {
        Coef coef = new Coef(this.getTerms());

        if (this.getTerms().length > 1) {
            Term term = new Term(this.getTerms()[0].simplify().getNumericalCoefficient(), this.getTerms()[0].simplify().getAtoms());
            this.setTerms(coef.snip().simplify().place(term).getTerms());
        } else if (this.getTerms().length == 1) {
            Term[] terms = new Term[]{this.getTerms()[0].simplify()};
            this.setTerms(terms);
        }

        return this;
    }

    /**
     * Combines like terms and writes them in order.
     *
     * @return nothing The original Coef is permanently altered.
     * @since 1.1.0
     */
    public void reduce() {
        // Save a copy of the current terms.
        Term[] termsUnordered = new Term[this.getTerms().length];
        System.arraycopy(this.getTerms(), 0, termsUnordered, 0, this.getTerms().length);

        // Wipe out the current terms
        this.setTerms(new Term[0]);

        // Put them back in smart order
        for (int i = 0; i < termsUnordered.length; i++) {
            termsUnordered[i].reduce();
            this.insert(termsUnordered[i]);
        }
    }


    /**
     * Multiply a Coefficient by another Coefficient.
     *
     * @param coef The Coef object to multiply to this one.
     * @return the product
     * @since 1.0.0
     */
    public Coef times(Coef coef) {
        Term[] terms = new Term[this.getTerms().length * coef.getTerms().length];

        // Multiply every term by every term.
        for (int i = 0; i < this.getTerms().length; i++) {
            for (int j = 0; j < coef.getTerms().length; j++) {
                Term product = this.getTerms()[i].times(coef.getTerms()[j]);

                int index = i * coef.getTerms().length + j;
                terms[index] = product;
            }
        }

        Coef productCoef = new Coef(terms);
        productCoef.reduce();
        return productCoef;
    }

    /**
     * Multiplies each Term in the Coef by a scalar (double)
     *
     * @param scalar The double to multiply it by
     * @return Coef that's been multiplied by a scalar
     * @since 1.0.0
     */
    public Coef times(double scalar) {
        Term[] terms = new Term[this.getTerms().length];

        for (int i = 0; i < this.getTerms().length; i++) {
            terms[i] = this.getTerms()[i].times(scalar);
        }

        return new Coef(terms);
    }

    /**
     * Add Coefs by combining like Terms and adding unlike Terms
     *
     * @param coef  Coef to be added to the first Coef
     * @return      The sum of this and that
     * @since 1.0.0
     */
    public Coef plus(Coef coef) {
        // Ignore zero terms.
        if (this.isZero() && coef.isZero()) return new Coef(0.0D);
        if (coef.isZero()) return this;
        if (this.isZero()) return coef;

        Term[] terms = new Term[this.getTerms().length + coef.getTerms().length];

        for (int i = 0; i < terms.length; i++) {
            if (i < this.getTerms().length) {
                terms[i] = this.getTerms()[i];
            } else {
                terms[i] = coef.getTerms()[i - this.getTerms().length];
            }
        }

        Coef sum = new Coef(terms);
        sum.reduce();
        return sum;
    }


    /**
     * If the Coef is zero, it returns true.
     *
     * @return  true if the Coeff is 0
     * @since 1.0.0
     */
    public boolean isZero() {
        this.reduce(); // TODO. It seems wrong to change a Coef in an isXXX method.

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
     * @since 1.0.0
     * @deprecated use {@link #isConstantCoef()} instead.
     */
    @Deprecated
    public boolean isDouble() {
        this.simplify();
        return this.terms.length == 1 && this.terms[0].isDouble();
    }

    /**
     * True if Coef consists only of a double, false otherwise.
     *
     * @return True if Coef consists only of a double, false otherwise
     * @since 1.1.0
     */
    public boolean isConstantCoef() {
        this.reduce();
        return this.terms.length == 1 && this.terms[0].isConstantTerm();
    }

    /**
     * @since 1.0.0
     * @deprecated Use {@link #toString()} instead.
     */
    @Deprecated
    public void print() {

        // No leading + sign
        if (this.terms[0].getNumericalCoefficient() != 0.0D) {
            this.terms[0].print();
        }

        for (int i = 1; i < this.terms.length; ++i) {
            // If positive, prepend with plus sign, then print term
            if (this.terms[i].getNumericalCoefficient() > 0.0D) {
                System.out.print("+");
                this.terms[i].print();
            } else if (this.terms[i].getNumericalCoefficient() < 0.0D) {
                // If negative, don't include "+" (the "-" comes with the negative number)
                this.terms[i].print();
            }
        }
    }

    /**
     * Compose a printable string of the Coef.
     *
     * @return a printable string
     * @since 1.1.0
     */
    @Override
    public String toString() {
        String string = "";

        // First term
        if (terms.length > 0) {
            string += this.getTerms()[0].toString();
        }

        // Rest of terms are "+" and the term.
        for (int i = 1; i < this.getTerms().length; i++) {
            String term = this.getTerms()[i].toString();
            if (term.length() > 0) {
                // If negative, don't include "+" (the "-" comes with the negative number)
                if (string.length() > 0 & this.getTerms()[i].getNumericalCoefficient() > 0) string += "+";
                string += term;
            }
        }

        return string;
    }
}