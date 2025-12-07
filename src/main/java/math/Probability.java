package math;

import java.math.BigDecimal;

public record Probability(BigDecimal winProb) {

    public Probability {
        if (winProb.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal looseProb() {
        return BigDecimal.ONE.subtract(winProb);
    }
}
