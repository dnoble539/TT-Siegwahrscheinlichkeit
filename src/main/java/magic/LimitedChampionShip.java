package magic;

import finance.CashAmount;
import finance.Currency;
import math.Probability;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class LimitedChampionShip extends MagicEvent {

    public LimitedChampionShip() {
        this(new LimitedChampionShipDay2());
    }

    public LimitedChampionShip(Probability probability) {
        this(new LimitedChampionShipDay2(probability));
    }

    private LimitedChampionShip(PriceEvent priceEvent) {
        super(7, 2, new CashAmount(BigDecimal.valueOf(5000L), Currency.GEMS), Map.of(
                0, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0), Currency.GEMS))),
                1, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0), Currency.GEMS))),
                2, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0), Currency.GEMS))),
                3, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(0), Currency.GEMS))),
                4, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(1000), Currency.GEMS))),
                5, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(2500), Currency.GEMS))),
                6, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(5000), Currency.GEMS))),
                7, Set.of(new CashPrice(new CashAmount(BigDecimal.valueOf(5500), Currency.GEMS)), priceEvent)
        ));
    }
}
