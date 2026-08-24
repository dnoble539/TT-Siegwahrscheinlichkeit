package math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Polynom {

    private final int grad;
    private final List<BigDecimal> koeffizienten;

    public Polynom(List<BigDecimal> koeffizienten) {
        this.grad = koeffizienten.size();
        this.koeffizienten = Collections.unmodifiableList(koeffizienten);
    }

    public Polynom derive() {
        if (grad == 0) {
            return new Polynom(koeffizienten);
        }
        ArrayList<BigDecimal> derivedKoeffizienten = new ArrayList<>(grad);
        for (int i = 1; i < koeffizienten.size(); i++) {
            BigDecimal derivedKoeffizient = koeffizienten.get(i).multiply(BigDecimal.valueOf(i));
            derivedKoeffizienten.add(derivedKoeffizient);
        }
        return new Polynom(derivedKoeffizienten);
    }

    public Function<BigDecimal, BigDecimal> getPolynomialFunction() {
        return x -> {
            BigDecimal result = BigDecimal.ZERO;
            for (int i = 0; i < grad; i++) {
                BigDecimal resultOfGrad = x.pow(i).multiply(koeffizienten.get(i));
                result = result.add(resultOfGrad);
            }
            return result;
        };
    }

    public BigDecimal approximateNullstelleWithNewton() {
        return newtonApproximationWithStartValue(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.001));
    }

    private BigDecimal newtonApproximationWithStartValue(BigDecimal startValue, BigDecimal toleranceValue) {
        BigDecimal calculatedValue = getPolynomialFunction().apply(startValue);
        if (calculatedValue.compareTo(BigDecimal.ZERO) == 0) {
            return startValue;
        }
        BigDecimal derivedValue = derive().getPolynomialFunction().apply(startValue);
        BigDecimal nextValue = startValue.subtract(calculatedValue.divide(derivedValue, MathContext.DECIMAL32));
        if (nextValue.subtract(startValue).compareTo(toleranceValue) < 0) {
            return nextValue;
        }
        return newtonApproximationWithStartValue(nextValue, toleranceValue);
    }

    @Override
    public String toString() {
        return "Polynom vom Grad " + grad +
                " mit koeffizienten=" + koeffizienten;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Polynom polynom = (Polynom) o;
        return grad == polynom.grad && Objects.equals(koeffizienten, polynom.koeffizienten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grad, koeffizienten);
    }
}
