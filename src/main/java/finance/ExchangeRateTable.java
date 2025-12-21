package finance;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateTable {
    private static final Map<CurrencyPair, BigDecimal> exchangeRates = Map.of(
            new CurrencyPair(Currency.GEMS, Currency.GEMS), BigDecimal.ONE,
            new CurrencyPair(Currency.EURO, Currency.EURO), BigDecimal.ONE,
            new CurrencyPair(Currency.DOLLAR, Currency.DOLLAR), BigDecimal.ONE,
            new CurrencyPair(Currency.GOLD, Currency.GOLD), BigDecimal.ONE,
            new CurrencyPair(Currency.EURO, Currency.GEMS), BigDecimal.valueOf(194),
            new CurrencyPair(Currency.DOLLAR, Currency.EURO), BigDecimal.valueOf(0.85),
            new CurrencyPair(Currency.DOLLAR, Currency.GEMS), BigDecimal.valueOf(165)
    );

    public record CurrencyPair(Currency from, Currency to) {
    }

    public static BigDecimal forCurrencyPair(CurrencyPair currencyPair) {
        return exchangeRates.get(currencyPair);
    }
}
