package org.dalton.polyfun;

/**
 * An array of Atoms and a number. The Atoms are understood to be multiplied.
 * For example: In the polynomial in x:
 * <p>
 * P(x) = 2(a_1)^3(b)x^4 - (a_2)(b_4)x^2 + 7ab + b_2
 * <p>
 * 2(a_1)^3(b) is a term, and 7ab + b_2 is two terms: 7ab and b_2
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
     * @param atoms                Atom[] attribute
     */
    public Term(double numericalCoefficient, Atom[] atoms) {
        this.numericalCoefficient = numericalCoefficient;

        if (atoms != null) {
            this.atoms = new Atom[atoms.length];
            System.arraycopy(atoms, 0, this.atoms, 0, atoms.length);
        }
    }

    /**
     * Get atoms array.
     *
     * @deprecated use {@link #getNumericalCoefficient()} ()} instead.
     */
    @Deprecated
    public double getTermDouble() {
        return this.numericalCoefficient;
    }

    /**
     * Get the numerical coefficient.
     *
     * @return the numerical coefficient
     */
    public double getNumericalCoefficient() {
        return numericalCoefficient;
    }

    /**
     * @deprecated use {@link #getAtoms()} ()} instead.
     */
    @Deprecated
    public Atom[] getTermAtoms() {
        return this.atoms;
    }

    /**
     * Get the array of Atoms
     *
     * @return atoms
     */
    public Atom[] getAtoms() {
        return atoms;
    }

    /**
     * Set Term numerical coefficient and atoms array.
     *
     * @deprecated use {@link #Term(double, Atom[])} ()} instead.
     */
    @Deprecated
    public void setTerm(double num, Atom[] atoms) {
        this.numericalCoefficient = num;
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }

    /**
     * Set numerical coeffiecient.
     *
     * @deprecated use {@link #setNumericalCoefficient(double)} ()} instead.
     */
    @Deprecated
    public void setTermDouble(double num) {
        this.numericalCoefficient = num;
    }

    /**
     * Set atoms array.
     *
     * @deprecated use {@link #setAtoms(Atom[])} ()} instead.
     */
    @Deprecated
    public void setTerm(Atom[] atoms) {
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }


    /**
     * Set the numericalCoefficient.
     *
     * @param numericalCoefficient
     */
    public void setNumericalCoefficient(double numericalCoefficient) {
        this.numericalCoefficient = numericalCoefficient;
    }

    /**
     * Set the array of Atoms. Note this array gets assigned, not copied.
     *
     * @param atoms Will become the Atoms array for this Term
     */
    public void setAtoms(Atom[] atoms) {
        this.atoms = atoms; // TODO: should this be an arraycopy?
    }


    /**
     * Remove first Atom and return it. Will fail with Term that has no Atoms.
     * <p>
     *
     * @return the popped Atom
     */
    public Atom pop() {
        if (this.getAtoms() == null && this.getAtoms().length == 0) {
            return null;
        } else {
            Atom[] atoms = new Atom[this.getAtoms().length - 1];
            Atom removed = this.getAtoms()[0];

            for (int i = 0; i < atoms.length; i++) {
                atoms[i] = this.getAtoms()[i + 1];
            }

            this.setAtoms(atoms);
            return removed;
        }
    }

    /**
     * Make a new Term with the first Atom removed. Will fail with Term that has no Atoms.
     * <p>
     * TODO: Actually update this Term and return the popped Atom.
     * TODO: rename pop()
     *
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
     * <p>
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
     * Inserts new Atom at the front of Term
     * <p>
     *
     * @param atom
     * @return nothing
     */
    public void push(Atom atom) {
        Atom[] atoms = new Atom[this.atoms.length + 1];
        atoms[0] = atom;

        System.arraycopy(this.atoms, 0, atoms, 1, atoms.length - 1);

        this.setAtoms(atoms);
    }

    /**
     * Inserts new Atom at the end of Term
     * <p>
     *
     * @param atom
     * @return nothing
     */
    public void append(Atom atom) {
        Atom[] atoms = new Atom[this.atoms.length + 1];

        System.arraycopy(this.atoms, 0, atoms, 0, atoms.length - 1);
        atoms[atoms.length - 1] = atom;

        this.setAtoms(atoms);
    }

    /**
     * Places a new Atom in the array of Atoms that make up the term. Atoms go in alphabetical order,
     * then by subscript. It "myatom" is "like" (same letter & subscript) the Atoms are combined.
     * <p>
     * example: place b^2 in the Term 3abd will result in 3ab^3d
     * example: place c_1 in the Term 3abd will result in 3abc_1d
     * <p>
     * TODO: rename insert()
     *
     * @param atom
     * @return
     */
    public Term place(Atom atom) {
        Term term = new Term(this.numericalCoefficient, this.atoms);

        if (atom.isLessThan(this.atoms[0])) {
            return term.paste(atom);
        } else {
            Atom atom1;

            if (atom.isLike(this.atoms[0])) {
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
     * Places a new Atom in the array of Atoms that make up the term. Atoms go in alphabetical order,
     * then by subscript. It "myatom" is "like" (same letter & subscript) the Atoms are combined.
     * <p>
     * example: place b^2 in the Term 3abd will result in 3ab^3d
     * example: place c_1 in the Term 3abd will result in 3abc_1d
     * <p>
     *
     * @param atom
     * @return nothing
     * @author Katie Jergens
     */
    public void smartInsert(Atom atom) {
        if (this.getAtoms() == null || this.getAtoms().length == 0) {
            // If there are no atoms make this the atom
            this.setAtoms(new Atom[]{atom});
        } else if (atom.isLessThan(this.atoms[0])) {
            // If this atom is smaller then it goes in front
            this.push(atom);
        } else if (atom.isLike(this.atoms[0])) {
            // Replace the first atom with the product of the first atom and this atom
            Atom head = this.pop();
            this.push(head.timesLikeAtom(atom));
        } else if (this.atoms.length == 1) {
            // Otherwise put it at the end
            this.append(atom);
        } else {
            // Recursive calls
            Atom head = this.pop();
            this.smartInsert(atom);
            this.push(head);
        }
    }

    /**
     * Orders Atoms and combines like Atoms in the Array of atoms.
     * example: 3baca^2 becomes 3a^3bc
     * <p>
     * Permanently alters the Term.
     *
     * @return Term Permanently alters the Term
     */
    public Term simplify() {
        if (this.atoms != null && this.atoms.length > 1) {
            Atom atom = new Atom(this.atoms[0].getLetter(), this.atoms[0].getSubscript(), this.atoms[0].getPower());
            Term term = new Term(this.numericalCoefficient, this.atoms);

            term = term.snip().simplify().place(atom);
            this.setAtoms(term.getAtoms());
        }

        return this;
    }

    /**
     * Orders Atoms and combines like Atoms in the Array of atoms.
     * example: 3baca^2 becomes 3a^3bc
     * <p>
     * Permanently alters the Term.
     *
     * @return nothing. Permanently alters the Term
     */
    public void reduce() {
        if (this.getAtoms() == null) {
            return;
        }

        // Shallow copy
        Atom[] unorderedAtoms = this.getAtoms();

        // Remove existing atoms
        this.setAtoms(null);

        // Put them back in order
        for (int i = 0; i < unorderedAtoms.length; i++) {
            this.smartInsert(unorderedAtoms[i]);
        }
    }

    /**
     * Multiply Term by another Term.
     *
     * @param term The Term to multiply to this one
     * @return Term
     */
    public Term times(Term term) {
        if (this.atoms == null || term.atoms == null) return term;

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
     * Multiply Term by a scalar value.
     *
     * @param scalar Value to multiply this Term by
     * @return the product
     */
    public Term times(double scalar) {
        return new Term(scalar * this.getNumericalCoefficient(), this.getAtoms());
    }

    /**
     * Tests to see if two terms have "like" (same letter & subscript) Atoms
     *
     * @param term Term to compare "this" to
     * @return true or false if they are "like" or not
     * @deprecated Use {@link #isLike(Term)} instead.
     */
    @Deprecated
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

    /**
     * Tests to see if two terms have "like" (same letter & subscript) Atoms
     *
     * @param term Term to compare "this" to
     * @return true or false if they are "like" or not
     */
    public boolean isLike(Term term) {
        this.reduce();
        term.reduce();

        if (this.getAtoms().length != term.getAtoms().length) return false;

        for (int i = 0; i < this.getAtoms().length; ++i) {
            if (!this.getAtoms()[i].isLike(term.getAtoms()[i])) {
                return false;
            }
        }

        // If got this far, have the same number of Atoms each atom pair has same letter and subscript.
        return true;
    }

    /**
     * Detects if the term passed in is less than this term.
     *
     * @param term term to compare
     * @return true if this is less than the param
     * @deprecated Use {@link #isLessThan(Term)} instead.
     */
    @Deprecated
    public boolean lessThan(Term term) {
        int len = 0;
        term.simplify();
        this.simplify();

        Term term1 = new Term(this.getNumericalCoefficient(), this.getAtoms());
        Term term2 = new Term(term.getNumericalCoefficient(), term.getAtoms());

        if (term1.getAtoms().length <= term2.getAtoms().length && !this.equals(term)) {

            for (int i = 0; i < term1.getAtoms().length; ++i) {
                if (term1.getAtoms()[i].lessThanOrEqual(term2.getAtoms()[i])) {
                    ++len;
                }
            }

            return len == term1.getAtoms().length;
        }

        return false;
    }

    /**
     * Detects if the term passed in is less than this term.
     *
     * @param term term to compare
     * @return true if this is less than the param
     */
    public boolean isLessThan(Term term) {
        this.reduce();
        term.reduce();

        if (term == null || term.getAtoms() == null || this.getAtoms() == null) return false;

        if (this.equals(term)) return false;

        // More atoms makes it "greater than"
        if (this.getAtoms().length > term.getAtoms().length) return false;

        // Atom-atom place pairs must be less than for the whole term to be "less than"
        // This means that fewer atoms can still be greater than.
        for (int i = 0; i < this.getAtoms().length; i++) {
            if (!this.getAtoms()[i].isLessThanOrEquals(term.getAtoms()[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks to see of the double (and hence the Term) is zero.
     *
     * @return true if numericalCoefficient is zero.
     */
    public boolean isZero() {
        return this.numericalCoefficient == 0.0D;
    }

    /**
     * Non-descriptive method name.
     *
     * @deprecated use {@link #isConstantTerm()} instead.
     */
    @Deprecated
    public boolean isDouble() {
        return this.atoms.length == 0;
    }

    /**
     * Checks if this is a constant term, i.e. no variables.
     *
     * @return true if a constant term
     */
    public boolean isConstantTerm() {
        return this.atoms.length == 0;
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

    /**
     * Check equality between two Terms.
     *
     * @param term Term to check
     * @return boolean True if they are equal
     */
    public boolean equals(Term term) {
        this.reduce();
        term.reduce();

        if (term == null) return false;
        if (this.getAtoms() == null && term.getAtoms() == null) return true;
        if (this.getAtoms() == null) return false;
        if (term.getAtoms() == null) return false;

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
     * Composes a printable string of this term.
     *
     * @return a printable string
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        if (this.numericalCoefficient == 0) return "";
        if (this.atoms == null || this.atoms.length == 0) return String.valueOf(this.numericalCoefficient);

        // Prepend with the numerical coef (unless it's 1, which is implied)
        if (this.numericalCoefficient == -1.0D) string.append("-");
        else if (this.numericalCoefficient != 1.0D) string.append(String.valueOf(this.numericalCoefficient));

        // Append all the atoms
        for (int i = 0; i < this.atoms.length; i++) {
            string.append(this.atoms[i].toString());
        }

        return string.toString();
    }
}
