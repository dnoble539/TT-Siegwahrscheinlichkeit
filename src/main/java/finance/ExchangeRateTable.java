package finance;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateTable {
    private static final Map<CurrencyPair, BigDecimal> exchangeRates = Map.of(
            new CurrencyPair(Currency.GEMS, Currency.GEMS), BigDecimal.ONE,
            new CurrencyPair(Currency.EURO, Currency.EURO), BigDecimal.ONE,
            new CurrencyPair(Currency.DOLLAR, Currency.DOLLAR), BigDecimal.ONE,
            new CurrencyPair(Currency.GOLD, Currency.GOLD), BigDecimal.ONE
    );

    public record CurrencyPair(Currency from, Currency to) {
    }

    public static BigDecimal forCurrencyPair(CurrencyPair currencyPair) {
        return exchangeRates.get(currencyPair);
    }
}
