package math;

import java.math.BigDecimal;
import java.util.function.Function;

public class MathFunctions {

    public static Long faculty(int naturalNumber) {
        if (naturalNumber < 0) {
            throw new IllegalArgumentException();
        }
        if (naturalNumber == 0) {
            return 1L;
        }
        return naturalNumber * faculty(naturalNumber - 1);
    }

    public static Long binomialCoefficient(int upper, int lower) {
        if (lower > upper) {
            throw new IllegalArgumentException();
        }
        if (lower < 0) {
            throw new IllegalArgumentException();
        }
        return faculty(upper) / (faculty(lower) * faculty(upper - lower));
    }

    public static BigDecimal sum(int von, int bis, Function<Integer, BigDecimal> function) {
        BigDecimal sumValue = BigDecimal.ZERO;
        for (int i = von; i<=bis; i++) {
            sumValue = sumValue.add(function.apply(i));
        }
        return sumValue;
    }
}
