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
 * @Author David Gomprecht
 * @Author Katie Jergens
 */
public class Atom {
    private char letter;
    private int subscript = -1;
    private int power = 1;

    /**
     * Default constructor.
     */
    public Atom() {
    }

    public Atom(char letter, int subscript, int power) {
        this.letter = letter;
        this.subscript = subscript;
        this.power = power;
    }

    /**
     * Create an atom with just a letter.
     *
     * @param letter The new letter.
     */
    public Atom(char letter) {
        this.letter = letter;
    }

    /**
     * Get letter
     *
     * @return
     */
    public char getLetter() {
        return this.letter;
    }

    /**
     * Get subscript
     *
     * @return
     */
    public int getSubscript() {
        return this.subscript;
    }

    /**
     * Get power
     *
     * @return
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Set letter.
     *
     * @param letter
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * Set subscript.
     *
     * @param subscript
     */
    public void setSubscript(int subscript) {
        this.subscript = subscript;
    }

    /**
     * Set power.
     *
     * @param power
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
     */
    public Atom timesLikeAtom(Atom atom) {
        return new Atom(this.getLetter(), this.subscript, this.getPower() + atom.getPower());
    }

    /**
     * Test to see if Atoms are "like" (same letter, subscript)
     *
     * @param atom Atom to compare this to.
     * @return true if the two Atoms have same letter and subscript.
     * @deprecated Use {@link #isLike(Atom)} instead.
     */
    public boolean like(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript();
    }

    /**
     * Test to see if Atoms are "like" (same letter, subscript)
     *
     * @param atom Atom to compare this to.
     * @return true if the two Atoms have same letter and subscript.
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
     * @deprecated Use {@link #isLessThanOrEquals(Atom)} instead.
     */
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
     */
    public boolean isLessThanOrEquals(Atom atom) {
        // First precedence is to the letter
        if (this.getLetter() < atom.getLetter()) return true;

        // If the letter is the same, the next precedence is to the subscript.
        if (this.getLetter() == atom.getLetter()) {
            if (this.getSubscript() < atom.getSubscript()) return true;

            // If the letter and the subscript are the same, the next precedence is to the power.
            if (this.getSubscript() == atom.getSubscript()
                && this.getPower() < atom.getPower()) {
                return true;
            }
        }

        // The the "less-than" checks failed, return true if equal. Otherwise, return false.
        return this.equals(atom);
    }

    /**
     * Compares two atoms by letter and subscript only. If the letters are in alphabetical order it's true.
     * If the letters are the same, and the subscripts are in increasing order it's true. Otherwise
     * it's false.
     *
     * @param atom Atom to compare this to.
     * @return true if this is less than the atom passed in
     * @deprecated Use {@link #isLessThan(Atom)} instead
     */
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
     * @deprecated Use {@link #equals(Atom)} instead.
     */
    public boolean identicalTo(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript()
                && this.getPower() == atom.getPower();
    }

    /**
     * Prints the Atom.
     *
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
     */
    public boolean equals(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript()
                && this.getPower() == atom.getPower();
    }

    /**
     * Returns a printable string of the Atom.
     *
     * @return A string.
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
}
