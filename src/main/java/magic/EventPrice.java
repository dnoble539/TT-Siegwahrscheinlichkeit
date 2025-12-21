package magic;

import finance.CashAmount;
import finance.Currency;

public interface EventPrice {

    CashAmount toAmountIn(Currency currency);
}
