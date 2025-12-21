package magic;

import finance.CashAmount;
import finance.Currency;
import math.Probability;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class LimitedChampionShipDay2 extends PriceEvent {

    public LimitedChampionShipDay2() {
        this(new Probability(BigDecimal.valueOf(0.5)));
    }

    public LimitedChampionShipDay2(Probability probability) {
        super(6, 2, new CashAmount(BigDecimal.ZERO, Currency.GEMS), Map.of(
                0, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0), Currency.GEMS))),
                1, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(6500), Currency.GEMS))),
                2, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(7500), Currency.GEMS))),
                3, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(8500), Currency.GEMS))),
                4, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(10000), Currency.GEMS))),
                5, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1000), Currency.DOLLAR))),
                6, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(2000), Currency.DOLLAR)))
        ), probability);
    }

}
