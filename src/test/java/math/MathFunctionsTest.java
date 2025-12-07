package math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MathFunctionsTest {

    @Test
    void facultyNegativNumber() {
        assertThatThrownBy(() -> MathFunctions.faculty(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void facultyZero() {
        Long result = MathFunctions.faculty(0);

        assertThat(result).isEqualTo(1L);
    }

    @CsvSource({
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "5, 120",
            "6, 720"
    })
    @ParameterizedTest
    void facultyNaturalNumber(int input, Long expectedResult) {
        Long result = MathFunctions.faculty(input);

        assertThat(result).isEqualTo(expectedResult);
    }

    @CsvSource({
            "0, 1",
            "1, 2",
            "-1, 0",
            "0, -1",
            "-1, -1"
    })
    @ParameterizedTest
    void binomialCoefficient_failureCases(int upper, int lower) {
        assertThatThrownBy(() -> MathFunctions.binomialCoefficient(upper, lower)).isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource({
            "7, 0, 1",
            "7, 1, 7",
            "7, 2, 21",
            "7, 3, 35",
            "7, 4, 35",
            "7, 5, 21",
            "7, 6, 7",
            "7, 7, 1"
    })
    @ParameterizedTest
    void binomialCoefficient(int upper, int lower, Long expectedResult) {
        Long result = MathFunctions.binomialCoefficient(upper, lower);

        assertThat(result).isEqualTo(expectedResult);
    }

    @CsvSource({
            "0, 0, 0",
            "0, 1, 1",
            "0, 100, 5050",
            "10, 12, 33",
    })
    @ParameterizedTest
    void sum_identity(int von, int bis, BigDecimal expectedResult) {
        BigDecimal result = MathFunctions.sum(von, bis, BigDecimal::valueOf);

        assertThat(expectedResult).isEqualTo(result);
    }
}