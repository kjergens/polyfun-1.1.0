package testlib;

import org.dalton.polyfun.Atom;
import org.dalton.polyfun.Term;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class CoefPairFactory {
    static Random random = new Random(1);

    public static CoefPair createCoefPairWithTermArray() {
        CoefPair coefPair = new CoefPair();

        // Generate number of Terms
        int numTerms = random.nextInt(3) + 1;
        polyfun.Term[] termOrigs = new polyfun.Term[numTerms];
        org.dalton.polyfun.Term[] termRefactoreds = new org.dalton.polyfun.Term[numTerms];

        for (int i = 0; i < numTerms; i++) {

            // Generate number of Atoms
            int numAtoms = random.nextInt(3) + 1;
            polyfun.Atom[] atomsOrig = new polyfun.Atom[numAtoms];
            Atom[] atomsRefactored = new org.dalton.polyfun.Atom[numAtoms];

            // Create Atom arrays for both original and refactored polyfun
            for (int j = 0; j < numAtoms; j++) {
                char letter = (char) (random.nextInt(5) + 97);
                int subscript = random.nextInt(3) + 1;
                int power = random.nextInt(5);

                atomsOrig[j] = new polyfun.Atom(letter, subscript, power);
                atomsRefactored[j] = new org.dalton.polyfun.Atom(letter, subscript, power);
            }

            // Create Term for both original and refactored
            double numericalCoefficient = random.nextDouble() * 10;
            BigDecimal bd = new BigDecimal(numericalCoefficient).setScale(2, RoundingMode.HALF_EVEN);
            numericalCoefficient = 1; // bd.doubleValue();

            termOrigs[i] = new polyfun.Term(numericalCoefficient, atomsOrig);
            termRefactoreds[i] = new Term(numericalCoefficient, atomsRefactored);
        }

        coefPair.coefOrig = new polyfun.Coef(termOrigs);
        coefPair.coefRefactored = new org.dalton.polyfun.Coef(termRefactoreds);

        return coefPair;
    }
}
