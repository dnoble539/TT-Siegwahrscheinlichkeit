package finance;

import java.math.BigDecimal;

public record CashAmount(BigDecimal amount, Currency currency) {

    public CashAmount minus(CashAmount subtrahent) {
        BigDecimal exchangeRate = ExchangeRateTable.forCurrencyPair(new ExchangeRateTable.CurrencyPair(subtrahent.currency, currency));
        BigDecimal subtrahentInCurrencyOfAmount = subtrahent.amount.multiply(exchangeRate);
        return new CashAmount(amount.subtract(subtrahentInCurrencyOfAmount), currency);
    }
}
