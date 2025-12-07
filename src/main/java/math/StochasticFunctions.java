package math;

import java.math.BigDecimal;

import static math.MathFunctions.binomialCoefficient;
import static math.MathFunctions.sum;

public class StochasticFunctions {

    public static BigDecimal probabilityForMOrLessHitsInNTries(int n, int m, Probability probability) {
        return sum(0, m, (i) -> probabilityForMHitsInNTries(n, i, probability));
    }

    public static BigDecimal probabilityForMHitsInNTries(int n, int m, Probability probability) {
        if (probability.winProb().compareTo(BigDecimal.ONE) > 0 || probability.looseProb().compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException();
        }
        if (m < 0 || n < 0 || m > n) {
            throw new IllegalArgumentException();
        }
        return probability.winProb().pow(m)
                .multiply(probability.looseProb().pow(n - m))
                .multiply(BigDecimal.valueOf(binomialCoefficient(n, m)));
    }
}
