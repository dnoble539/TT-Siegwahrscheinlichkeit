package math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StochasticFunctionsTest {

    @CsvSource({
            "3, 0, 0.5, 0.125",
            "3, 1, 0.5, 0.375",
            "3, 2, 0.5, 0.375",
            "3, 3, 0.5, 0.125",
            "3, 0, 0, 1",
            "3, 1, 0, 0",
            "3, 2, 0, 0",
            "3, 3, 0, 0",
            "3, 0, 1, 0",
            "3, 1, 1, 0",
            "3, 2, 1, 0",
            "3, 3, 1, 1",
    })
    @ParameterizedTest
    void probabilityForMHitsInNTries(int hits, int tries, BigDecimal prob, BigDecimal expectedResult) {
        BigDecimal result = StochasticFunctions.probabilityForMHitsInNTries(hits, tries, new Probability(prob));

        assertThat(result).isEqualTo(expectedResult);
    }
}