package org.dalton.polyfun;

/**
 * An Atom is a letter, a subscript, and a power which represents the basic part of
 * the coefficient of a polynomial.
 * <p>
 * For example: In the polynomial in x:
 * <p>
 * P(x) = 2(a_1)^3(b)x^4 - (a_2)(b_4)x^2 + 7ab
 * <p>
 * 2(a_1)^3(b) is a coefficient, with two atoms: (a_1)^3 and (b)
 *
 * @author David Gomprecht (wrote the original polyfun library)
 * @author Katie Jergens (wrote refactored version based on Dr. Gomprecht's library)
 */
public class Atom implements Comparable<Atom> {
    private char letter;
    private int subscript = -1;
    private int power = 1;

    /**
     * Default constructor.
     * @since 1.0.0
     */
    public Atom() {
    }

    /**
     * Construct a letter, subscript, power. E.g. 'a', 1, 2 will make the atom a_1^2.
     * @param letter
     * @param subscript
     * @param power
     * @since 1.0.0
     */
    public Atom(char letter, int subscript, int power) {
        this.letter = letter;
        this.subscript = subscript;
        this.power = power;
    }

    /**
     * Create an atom with just a letter.
     *
     * @param letter The new letter.
     * @since 1.0.0
     */
    public Atom(char letter) {
        this.letter = letter;
    }

    /**
     * Get letter
     *
     * @return  This atom's letter variable
     * @since 1.0.0
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Get subscript
     *
     * @return This atom's subscript
     * @since 1.0.0
     */
    public int getSubscript() {
        return this.subscript;
    }

    /**
     * Get power
     *
     * @return  This atom's exponent
     * @since 1.0.0
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Set letter.
     *
     * @param letter
     * @since 1.0.0
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Set subscript.
     *
     * @param subscript
     * @since 1.0.0
     */
    public void setSubscript(int subscript) {
        this.subscript = subscript;
    }

    /**
     * Set power.
     *
     * @param power
     * @since 1.0.0
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Create an Atom by entering a letter, a subscript, and a power
     *
     * @param letter    The letter
     * @param subscript The subscript
     * @param power     The power (i.e. exponent)
     * @since 1.0.0
     */
    public void setAtom(char letter, int subscript, int power) {
        this.letter = letter;
        this.subscript = subscript;
        this.power = power;
    }

    /**
     * Multiply two atoms which have the same letter and subscript.
     * For example:
     * (a_1)^3 * (a_1)^4 = (a_1)^7
     * <p>
     * The original atom remains unchanged
     *
     * @param atom the like atom to multiply by
     * @return the product
     * @since 1.0.0
     */
    public Atom timesLikeAtom(Atom atom) {
        return new Atom(this.getLetter(), this.subscript, this.getPower() + atom.getPower());
    }

    /**
     * Test to see if Atoms are "like" (same letter, subscript)
     *
     * @param atom Atom to compare this to.
     * @return true if the two Atoms have same letter and subscript.
     * @since 1.0.0
     * @deprecated Use {@link #isLike(Atom)} instead.
     */
    @Deprecated
    public boolean like(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript();
    }

    /**
     * Test to see if Atoms are "like" (same letter, subscript)
     *
     * @param atom Atom to compare this to.
     * @return true if the two Atoms have same letter and subscript.
     * @since 1.1.0
     */
    public boolean isLike(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript();
    }

    /**
     * Defines a way of comparing two atoms. If letters are in alphabetical order it's true. If
     * letters are the same, but subscripts are in increasing order it's true. If letters are the same,
     * and subscripts are the same, and powers are equal or in increasing order it's true. Otherwise
     * it's false.
     *
     * @param atom Atom to compare this to.
     * @return true if this is less than or equal to the atom passed in.
     * @since 1.0.0
     * @deprecated Use {@link #isLessThanOrEquals(Atom)} instead.
     */
    @Deprecated
    public boolean lessThanOrEqual(Atom atom) {
        if (this.getLetter() < atom.getLetter()) return true;

        if (this.getLetter() == atom.getLetter()) {
            if (this.getSubscript() < atom.getSubscript()) return true;

            if (this.getSubscript() == atom.getSubscript()
                    && this.getPower() < atom.getPower()) {
                return true;
            }
        }

        return this.equals(atom);
    }

    /**
     * Defines a way of comparing two atoms. If letters are in alphabetical order it's true. If
     * letters are the same, but subscripts are in increasing order it's true. If letters are the same,
     * and subscripts are the same, and powers are equal or in increasing order it's true. Otherwise
     * it's false.
     *
     * @param atom Atom to compare this to.
     * @return true if this is less than or equal to the atom passed in.
     * @since 1.1.0
     */
    public boolean isLessThanOrEquals(Atom atom) {
        // First check for equals
        if (this.equals(atom)) return true;

        // For comparison, first precedence is to the letter
        if ((int) this.getLetter() < (int) atom.getLetter()) return true;

        // If the letter is the same, the next precedence is to the subscript.
        if ((int) this.getLetter() == (int) atom.getLetter()) {
            if (this.getSubscript() < atom.getSubscript()) return true;

            // If the letter and the subscript are the same, the next precedence is to the power.
            if (this.getSubscript() == atom.getSubscript()
                    && this.getPower() < atom.getPower()) {
                return true;
            }
        }

        // If the  equals and "less-than" checks failed, return false.
        return false;
    }

    /**
     * Compares two atoms by letter and subscript only. If the letters are in alphabetical order it's true.
     * If the letters are the same, and the subscripts are in increasing order it's true. Otherwise
     * it's false.
     *
     * @param atom Atom to compare this to.
     * @return true if this is less than the atom passed in
     * @since 1.0.0
     * @deprecated Use {@link #isLessThan(Atom)} instead
     */
    @Deprecated
    public boolean lessThan(Atom atom) {
        if (this.getLetter() < atom.getLetter()) return true;

        return this.getLetter() == atom.getLetter() && this.getSubscript() < atom.getSubscript();
    }

    /**
     * Compares two atoms by letter and subscript only. If the letters are in alphabetical order it's true.
     * If the letters are the same, and the subscripts are in increasing order it's true. Otherwise
     * it's false.
     *
     * @param atom Atom to compare this to.
     * @return true if this is less than the atom passed in
     * @since 1.1.0
     */
    public boolean isLessThan(Atom atom) {
        if (this.getLetter() < atom.getLetter()) return true;

        return this.getLetter() == atom.getLetter() && this.getSubscript() < atom.getSubscript();
    }

    /**
     * Test to see if Atoms are exactly identical (same letter, subscript, and power)
     *
     * @param atom
     * @return true if same
     * @since 1.0.0
     * @deprecated Use {@link #equals(Atom)} instead.
     */
    @Deprecated
    public boolean identicalTo(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript()
                && this.getPower() == atom.getPower();
    }

    /**
     * Prints the Atom.
     *
     * @since 1.0.0
     * @deprecated Use {@link #toString()} instead
     */
    @Deprecated
    public void print() {
        if (this.subscript == -1) {
            if (this.power == 1) {
                System.out.print(this.letter);
            } else if (this.power != 0) {
                System.out.print(this.letter + "^" + this.power);
            }
        } else if (this.power == 1) {
            System.out.print(this.letter + "_" + this.subscript);
        } else if (this.power != 0) {
            System.out.print(this.letter + "_" + this.subscript + "^" + this.power);
        }
    }

    /**
     * Checks equality between Atoms.
     *
     * @param atom - The org.dalton.polyfun.Atom to compare to this one.
     * @return boolean
     * @since 1.1.0
     */
    public boolean equals(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript()
                && this.getPower() == atom.getPower();
    }

    /**
     * Returns a printable string of the Atom.
     *
     * @return A string.
     * @since 1.1.0
     */
    @Override
    public String toString() {
        String string = "";

        if (this.subscript == -1) {
            if (this.power == 1) {
                string = String.valueOf(this.letter);
            } else if (this.power != 0) {
                string = this.letter + "^" + this.power;
            }
        } else if (this.power == 1) {
            string = this.letter + "_" + this.subscript;
        } else if (this.power != 0) {
            string = this.letter + "_" + this.subscript + "^" + this.power;
        }

        return string;
    }

    /**
     *
     * @param a
     * @return  -1 for less than, 0 for equal, 1 for greater than
     * @since 1.1.0
     */
    public int compareTo(Atom a) {
        if (this.equals(a))
            return 0;
        else if (this.isLessThan(a))
            return -1;
        else
            return 1;
    }
}
