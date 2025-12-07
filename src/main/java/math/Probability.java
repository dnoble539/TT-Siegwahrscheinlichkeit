package math;

import java.math.BigDecimal;

public record Probability(BigDecimal winProb) {

    public BigDecimal looseProb() {
        return BigDecimal.ONE.subtract(winProb);
    }
}
