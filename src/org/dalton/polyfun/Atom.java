package org.dalton.polyfun;

/**
 * An Atom is a letter, a subscript, and a power which represents the basic part of
 * the coefficient of a polynomial.
 *
 * For example: In the polynomial in x:
 *
 *       P(x) = 2(a_1)^3(b)x^4 - (a_2)(b_4)x^2 + 7ab
 *
 *  2(a_1)^3(b) is a coefficient, with two atoms: (a_1)^3 and (b)
 *
 * @Author David Gomprecht
 * @Author Katie Jergens
 */
public class Atom {
    private char letter;
    private int subscript = -1;
    private int power = 1;

    public Atom() {
    }

    public Atom(char letter, int subscript, int power) {
        this.letter = letter;
        this.subscript = subscript;
        this.power = power;
    }

    public Atom(char letter) {
        this.letter = letter;
    }

    public void setAtom(char letter, int subscript, int power) {
        this.letter = letter;
        this.subscript = subscript;
        this.power = power;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getSubscript() {
        return this.subscript;
    }

    public int getPower() {
        return this.power;
    }

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

    public Atom timesLikeAtom(Atom atom) {
        return new Atom(this.getLetter(), this.subscript, this.getPower() + atom.getPower());
    }

    public boolean identicalTo(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript()
                && this.getPower() == atom.getPower();
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

    public boolean like(Atom atom) {
        return this.getLetter() == atom.getLetter() && this.getSubscript() == atom.getSubscript();
    }

    public boolean lessThanOrEqual(Atom atom) {
        if (this.getLetter() < atom.getLetter()) {
            return true;
        } else if (this.getLetter() == atom.getLetter() && this.getSubscript() < atom.getSubscript()) {
            return true;
        } else {
            return this.equals(atom);
        }
    }

    public boolean lessThan(Atom atom) {
        if (this.getLetter() < atom.getLetter()) {
            return true;
        } else {
            return this.getLetter() == atom.getLetter() && this.getSubscript() < atom.getSubscript();
        }
    }
}
