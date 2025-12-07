package magic;

import math.Probability;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MagicEventTest {

    @Test
    void calculateLikeliHoodPerWinForGivenGameWinPercentage() {
        PremierDraft premierDraft = new PremierDraft();

        Map<Integer, BigDecimal> likeliHoodTable = premierDraft.calculateLikeliHoodPerWinForGivenGameWinPercentage(new Probability(BigDecimal.valueOf(0.5)));
        Map<Integer, BigDecimal> expected = Map.of(
                0, new BigDecimal("0.125"),
                1, new BigDecimal("0.1875"),
                2, new BigDecimal("0.1875"),
                3, new BigDecimal("0.15625"),
                4, new BigDecimal("0.1171875"),
                5, new BigDecimal("0.08203125"),
                6, new BigDecimal("0.0546875"),
                7, new BigDecimal("0.08984375")
        );

        expected.forEach((key, expectedValue) ->
                assertThat(likeliHoodTable.get(key))
                        .isEqualByComparingTo(expectedValue)
        );
    }
}