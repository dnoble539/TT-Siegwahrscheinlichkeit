package magic;

import finance.CashAmount;
import finance.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class PremierDraft extends MagicEvent {
    public PremierDraft() {
        super(7, 3, new CashAmount(BigDecimal.valueOf(1500L), Currency.GEMS), Map.of(
                0, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(50L), Currency.GEMS))),
                1, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(100L), Currency.GEMS))),
                2, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(250L), Currency.GEMS))),
                3, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1000L), Currency.GEMS))),
                4, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1400L), Currency.GEMS))),
                5, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1600L), Currency.GEMS))),
                6, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1800L), Currency.GEMS))),
                7, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(2200L), Currency.GEMS)))
        ));
    }
}
