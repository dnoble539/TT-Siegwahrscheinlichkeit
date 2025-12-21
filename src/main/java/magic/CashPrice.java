package magic;

import finance.CashAmount;
import finance.Currency;
import finance.ExchangeRateTable;

import java.math.BigDecimal;

public record CashPrice(CashAmount cashAmount) implements EventPrice {

    @Override
    public CashAmount toAmountIn(Currency currency) {
        BigDecimal exchangeRate = ExchangeRateTable.forCurrencyPair(new ExchangeRateTable.CurrencyPair(cashAmount.currency(), currency));
        return new CashAmount(cashAmount.amount().multiply(exchangeRate), currency);
    }
}
