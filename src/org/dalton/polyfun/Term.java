package org.dalton.polyfun;

/**
 * An array of Atoms and a number. The Atoms are understood to be multiplied.
 * For example: In the polynomial in x:
 *
 *     P(x) = 2(a_1)^3(b)x^4 - (a_2)(b_4)x^2 + 7ab + b_2
 *
 *  2(a_1)^3(b) is a term, and 7ab + b_2 is two terms: 7ab and b_2
 *
 * @Author David Gomprecht
 * @Author Katie Jergens
 */
public class Term {
    private double numericalCoefficient;
    private Atom[] atoms;

    /**
     * Default constructor.
     */
    public Term() {
    }

    /**
     * Construct a constant term.
     *
     * @param constant The constant that will be the entire term.
     */
    public Term(double constant) {
        this.numericalCoefficient = constant;
        this.atoms = new Atom[0]; // TODO: Maybe set this.atoms to null?
    }

    /**
     * Construct a Term that consists of one Atom.
     *
     * @param atom This will be the first and only Atom in the atoms array
     */
    public Term(Atom atom) {
        this.numericalCoefficient = 1.0;
        this.atoms = new Atom[]{atom};
    }

    /**
     * Constructor sets numericalCoefficient to 1 and initializes atoms as an array of length 1.
     *
     * @param letter The will be the letter
     */
    public Term(char letter) {
        this.numericalCoefficient = 1.0;
        this.atoms = new Atom[]{new Atom(letter)};
    }

    /**
     * Construct a Term with a new number and an array of Atoms.
     *
     * @param numericalCoefficient The numericalCoefficient attribute
     * @param atoms Atom[] attribute
     */
    public Term(double numericalCoefficient, Atom[] atoms) {
        this.numericalCoefficient = numericalCoefficient;
        this.atoms = new Atom[atoms.length];
        System.arraycopy(atoms, 0, this.atoms, 0, atoms.length);
    }

    /**
     * @deprecated use {@link #Term(double, Atom[])} ()} instead.
     */
    @Deprecated
    public void setTerm(double num, Atom[] atoms) {
        this.numericalCoefficient = num;
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }

    /**
     * @deprecated use {@link #setNumericalCoefficient(double)} ()} instead.
     */
    @Deprecated
    public void setTermDouble(double num) {
        this.numericalCoefficient = num;
    }

    /**
     * @deprecated use {@link #setAtoms(Atom[])} ()} instead.
     */
    @Deprecated
    public void setTerm(Atom[] atoms) {
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }

    /**
     * @deprecated use {@link #getNumericalCoefficient()} ()} instead.
     */
    @Deprecated
    public double getTermDouble() {
        return this.numericalCoefficient;
    }

    /**
     * @deprecated use {@link #getAtoms()} ()} instead.
     */
    @Deprecated
    public Atom[] getTermAtoms() {
        return this.atoms;
    }

    public double getNumericalCoefficient() {
        return numericalCoefficient;
    }

    public void setNumericalCoefficient(double numericalCoefficient) {
        this.numericalCoefficient = numericalCoefficient;
    }

    public Atom[] getAtoms() {
        return atoms;
    }

    public void setAtoms(Atom[] atoms) {
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }

    /**
     * @deprecated use {@link #toString()} instead.
     */
    @Deprecated
    public void print() {
        if (this.atoms.length == 0 && this.numericalCoefficient != 0.0D) {
            System.out.print(this.numericalCoefficient);
        } else {
            int i;
            if (this.numericalCoefficient == 1.0D) {
                for (i = 0; i < this.atoms.length; ++i) {
                    this.atoms[i].print();
                }
            } else if (this.numericalCoefficient == -1.0D) {
                System.out.print("-");

                for (i = 0; i < this.atoms.length; ++i) {
                    this.atoms[i].print();
                }
            } else {
                System.out.print(this.numericalCoefficient);

                for (i = 0; i < this.atoms.length; ++i) {
                    this.atoms[i].print();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (this.atoms.length == 0 && this.numericalCoefficient != 0.0D) {
            string = new StringBuilder(String.valueOf(this.numericalCoefficient));
        } else {
            int i;
            if (this.numericalCoefficient == 1.0D) {
                for (i = 0; i < this.atoms.length; ++i) {
                    string.append(this.atoms[i].toString());
                }
            } else if (this.numericalCoefficient == -1.0D) {
                string.append("-");

                for (i = 0; i < this.atoms.length; ++i) {
                    string.append(this.atoms[i].toString());
                }
            } else {
                string = new StringBuilder(String.valueOf(this.numericalCoefficient));

                for (i = 0; i < this.atoms.length; ++i) {
                    string.append(this.atoms[i].toString());
                }
            }
        }

        return string.toString();
    }

    /**
     * Make a new Term with the first Atom removed. Will fail with Term that has no Atoms.
     *
     * TODO: Actually update this Term and return the popped Atom.
     * TODO: rename pop()
     * @return New term with the first Atom removed
     */
    public Term snip() {
        Atom[] atoms = new Atom[this.getAtoms().length - 1];

        for (int i = 0; i < atoms.length; ++i) {
            atoms[i] = this.getAtoms()[i + 1];
        }

        return new Term(this.numericalCoefficient, atoms);
    }

    /**
     * Creates a new Term with the a new Atom at the front of Term
     *
     * TODO: Actually update this Term.
     * TODO: rename insertInFront() or push()
     *
     * @param atom
     * @return New Term with one new Atom at the beginning of the array
     */
    public Term paste(Atom atom) {
        Atom[] atoms = new Atom[this.atoms.length + 1];
        atoms[0] = atom;

        System.arraycopy(this.atoms, 0, atoms, 1, atoms.length - 1);

        return new Term(this.numericalCoefficient, atoms);
    }

    /**
     * Places a new Atom in the array of Atoms that make up the term. Atoms go in alphabetical order,
     * then by subscript. It "myatom" is "like" (same letter & subscript) the Atoms are combined.
     *
     * example: place b^2 in the Term 3abd will result in 3ab^3d
     * example: place c_1 in the Term 3abd will result in 3abc_1d
     *
     * TODO: rename insert()
     *
     * @param atom
     * @return
     */
    public Term place(Atom atom) {
        Term term = new Term(this.numericalCoefficient, this.atoms);

        if (atom.lessThan(this.atoms[0])) {
            return term.paste(atom);
        } else {
            Atom atom1;

            if (atom.like(this.atoms[0])) {
                atom1 = this.atoms[0];
                return term.snip().paste(atom1.timesLikeAtom(atom));
            } else if (this.atoms.length == 1) {
                atom1 = new Atom(atom.getLetter(), atom.getSubscript(), atom.getPower());
                Atom[] atoms1 = new Atom[]{atom1};
                Term term1 = new Term(this.numericalCoefficient, atoms1);
                return term1.paste(this.atoms[0]);
            } else {
                atom1 = new Atom();
                atom1.setAtom(this.atoms[0].getLetter(), this.atoms[0].getSubscript(), this.atoms[0].getPower());
                return term.snip().place(atom).paste(atom1);
            }
        }
    }

    /**
     * Orders Atoms and combines like Atoms in the Array of atoms.
     * example: 3baca^2 becomes 3a^3bc
     *
     * Permanently alters the Term.
     *
     * @return Term Permanently alters the Term
     */
    public Term simplify() {
        if (this.atoms.length > 1) {
            Atom atom = new Atom(this.atoms[0].getLetter(), this.atoms[0].getSubscript(), this.atoms[0].getPower());
            Term term = new Term(this.numericalCoefficient, this.atoms);

            term = term.snip().simplify().place(atom);
            this.setAtoms(term.getAtoms());
        }

        return this;
    }

    /**
     * Multiply Term by another Term.
     *
     * @param term The Term to multiply to this one
     * @return Term
     */
    public Term times(Term term) {
        Atom[] atoms = new Atom[this.atoms.length + term.getAtoms().length];

        for (int i = 0; i < atoms.length; ++i) {

            if (i < this.atoms.length) {
                // Assign the first array of Atoms into atoms
                atoms[i] = this.atoms[i];
            } else {
                // Assign the second array of Atoms into atoms
                atoms[i] = term.getAtoms()[i - this.atoms.length];
            }
        }

        Term termProduct = new Term(this.numericalCoefficient * term.getNumericalCoefficient(), atoms);
        return termProduct.simplify();
    }

    /**
     * @deprecated use {@link #equals(Term)} instead.
     */
    @Deprecated
    public boolean identicalTo(Term term) {
        Term term1 = new Term(this.simplify().getNumericalCoefficient(), this.simplify().getAtoms());
        Term term2 = new Term(term.simplify().getNumericalCoefficient(), term.simplify().getAtoms());

        int len = 0;

        if (term1.getAtoms().length != term2.getAtoms().length) {
            return false;
        } else {

            for (int i = 0; i < term1.getAtoms().length; ++i) {
                if (term1.getAtoms()[i].equals(term2.getAtoms()[i])) {
                    ++len;
                }
            }

            return len == term1.getAtoms().length;
        }
    }

    /**
     * Check equality between two Terms.
     *
     * @param term Term to check
     * @return boolean True if they are equal
     */
    public boolean equals(Term term) {
        this.simplify();
        term.simplify();

        if (this.getAtoms().length == term.getAtoms().length) {

            for (int i = 0; i < this.getAtoms().length; ++i) {
                if (!this.getAtoms()[i].equals(term.getAtoms()[i])) {
                    return false;
                }
            }

            // If got this far, they must be equal
            return true;
        }

        return false;
    }

    /**
     * Tests to see if two terms have "like" (same letter & subscript) Atoms
     *
     * @param term Term to compare "this" to
     * @return true or false if they are "like" or not
     */
    public boolean like(Term term) {
        Term term1 = new Term(this.simplify().getNumericalCoefficient(), this.simplify().getAtoms());
        Term term2 = new Term(term.simplify().getNumericalCoefficient(), term.simplify().getAtoms());
        int len = 0;

        if (term1.getAtoms().length != term2.getAtoms().length) {
            return false;
        } else {
            for (int i = 0; i < term1.getAtoms().length; ++i) {
                if (term1.getAtoms()[i].like(term2.getAtoms()[i])) {
                    ++len;
                }
            }

            return len == term1.getAtoms().length;
        }
    }

    public boolean lessThan(Term term) {
        int len = 0;
        Term term1 = new Term(this.simplify().getNumericalCoefficient(), this.simplify().getAtoms());
        Term term2 = new Term(term.simplify().getNumericalCoefficient(), term.simplify().getAtoms());

        if (term1.getAtoms().length <= term2.getAtoms().length && !this.equals(term)) {

            for (int i = 0; i < term1.getAtoms().length; ++i) {
                if (term1.getAtoms()[i].lessThanOrEqual(term2.getAtoms()[i])) {
                    ++len;
                }
            }

            return len == term1.getAtoms().length;
        } else {
            return false;
        }
    }

    /**
     * Multiply Term by a scalar value.
     *
     * @param scalar Value to multiply this Term by
     * @return the product
     */
    public Term times(double scalar) {
        return new Term(scalar * this.getNumericalCoefficient(), this.getAtoms());
    }

    /**
     * Checks to see of the double (and hence the Term) is zero.
     *
     * @return
     */
    public boolean isZero() {
        return this.numericalCoefficient == 0.0D;
    }

    /**
     * @deprecated use {@link #isConstantTerm()} instead.
     */
    @Deprecated
    public boolean isDouble() {
        return this.atoms.length == 0;
    }

    /**
     * Checks if this is a constant term, i.e. no variables.
     * @return
     */
    public boolean isConstantTerm() {
        return this.atoms.length == 0;
    }
}
