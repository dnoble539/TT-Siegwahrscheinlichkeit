package magic;

import finance.CashAmount;
import finance.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class NeonDynestyArenaDirect extends MagicEvent {
    public NeonDynestyArenaDirect() {
        super(7, 2, new CashAmount(BigDecimal.valueOf(6000), Currency.GEMS), Map.of(
                0, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS))),
                1, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS))),
                2, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0L), Currency.GEMS))),
                3, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(2700L), Currency.GEMS))),
                4, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(5400L), Currency.GEMS))),
                5, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(8100L), Currency.GEMS))),
                6, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(20000L), Currency.GEMS))),
                7, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(40000L), Currency.GEMS)))
        ));
    }

}
