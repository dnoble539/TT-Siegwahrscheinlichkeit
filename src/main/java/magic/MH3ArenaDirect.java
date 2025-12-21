package magic;

import finance.CashAmount;
import finance.Currency;

import java.math.BigDecimal;
import java.util.Map;

public class MH3ArenaDirect extends MagicEvent {
    public MH3ArenaDirect() {
        super(7, 2, new CashAmount(BigDecimal.valueOf(8000),Currency.GEMS), Map.of(
                0, new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS)),
                1, new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS)),
                2, new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS)),
                3, new CashPrice(new CashAmount(BigDecimal.valueOf(3600L), Currency.GEMS)),
                4, new CashPrice(new CashAmount(BigDecimal.valueOf(7200L), Currency.GEMS)),
                5, new CashPrice(new CashAmount(BigDecimal.valueOf(10800L), Currency.GEMS)),
                6, new CashPrice(new CashAmount(BigDecimal.valueOf(65000L), Currency.GEMS)),
                7, new CashPrice(new CashAmount(BigDecimal.valueOf(130000L), Currency.GEMS))
        ));
    }
}
