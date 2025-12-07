package math;

import java.math.BigDecimal;

import static math.MathFunctions.binomialCoefficient;
import static math.MathFunctions.sum;

public class StochasticFunctions {

    public static BigDecimal probabilityForMOrLessHitsInNTries(int n, int m, Probability probability) {
        return sum(0, m, (i) -> probabilityForMHitsInNTries(n, i, probability));
    }

    public static BigDecimal probabilityForMHitsInNTries(int hits, int tries, Probability probability) {
        if (tries < 0 || hits < 0 || tries > hits) {
            throw new IllegalArgumentException();
        }
        return probability.winProb().pow(tries)
                .multiply(probability.looseProb().pow(hits - tries))
                .multiply(BigDecimal.valueOf(binomialCoefficient(hits, tries)));
    }
}
