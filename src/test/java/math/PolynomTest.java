package math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PolynomTest {

    public static Stream<Arguments> getPolynomAndDerivate() {
        return Stream.of(
                Arguments.of(new Polynom(List.of(BigDecimal.TEN)), new Polynom(List.of())),
                Arguments.of(new Polynom(List.of(BigDecimal.TEN, BigDecimal.TWO)), new Polynom(List.of(BigDecimal.TWO))),
                Arguments.of(new Polynom(List.of(BigDecimal.TEN, BigDecimal.TWO, BigDecimal.TWO)),
                        new Polynom(List.of(BigDecimal.TWO, BigDecimal.valueOf(4L)))),
                Arguments.of(new Polynom(List.of(BigDecimal.TEN, BigDecimal.TWO, BigDecimal.TWO, BigDecimal.TEN)),
                        new Polynom(List.of(BigDecimal.TWO, BigDecimal.valueOf(4L), BigDecimal.valueOf(30L)))
                ));
    }

    public static Stream<Arguments> getPolynomialFunctionTestData() {
        return Stream.of(
                Arguments.of(new Polynom(List.of(BigDecimal.TWO)),
                        List.of(BigDecimal.valueOf(-1), BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TWO),
                        List.of(BigDecimal.TWO, BigDecimal.TWO, BigDecimal.TWO, BigDecimal.TWO)
                ),
                Arguments.of(new Polynom(List.of(BigDecimal.TWO, BigDecimal.TEN)),
                        List.of(BigDecimal.valueOf(-1), BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TWO),
                        List.of(BigDecimal.valueOf(-8), BigDecimal.TWO, BigDecimal.valueOf(12), BigDecimal.valueOf(22))
                ),
                Arguments.of(new Polynom(List.of(BigDecimal.TWO, BigDecimal.ZERO, BigDecimal.TWO)),
                        List.of(BigDecimal.valueOf(-1), BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TWO),
                        List.of(BigDecimal.valueOf(4), BigDecimal.TWO, BigDecimal.valueOf(4), BigDecimal.TEN)
                )
        );
    }

    @MethodSource("getPolynomAndDerivate")
    @ParameterizedTest
    void derive(Polynom basePolynom, Polynom expectedDerivate) {
        Polynom derivedPolynom = basePolynom.derive();

        assertThat(derivedPolynom).isEqualTo(expectedDerivate);
    }

    @MethodSource("getPolynomialFunctionTestData")
    @ParameterizedTest
    void getPolynomialFunction(Polynom basePolynom, List<BigDecimal> parameters, List<BigDecimal> expectedResults) {
        for (int i = 0; i < parameters.size(); i++) {
            BigDecimal result = basePolynom.getPolynomialFunction().apply(parameters.get(i));

            assertThat(result).isEqualTo(expectedResults.get(i));
        }
    }
}