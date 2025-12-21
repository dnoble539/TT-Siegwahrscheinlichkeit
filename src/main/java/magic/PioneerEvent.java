package magic;

import finance.CashAmount;
import finance.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class PioneerEvent extends MagicEvent {
    public PioneerEvent() {
        super(7,3, new CashAmount(BigDecimal.valueOf(375), Currency.GEMS), Map.of(
                0, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(25L), Currency.GEMS))),
                1, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(50L), Currency.GEMS))),
                2, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(75L), Currency.GEMS))),
                3, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(200L), Currency.GEMS))),
                4, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(300L), Currency.GEMS))),
                5, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(400L), Currency.GEMS))),
                6, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(450L), Currency.GEMS))),
                7, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(500L), Currency.GEMS)))
        ));
    }
}
