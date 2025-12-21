package magic;

import finance.CashAmount;
import finance.Currency;
import math.Probability;

import java.util.Map;
import java.util.Set;

public class PriceEvent extends MagicEvent implements EventPrice {
    private final Probability assumedWinProbability;

    public PriceEvent(int maxWins, int maxLosses, CashAmount cost, Map<Integer, Set<EventPrice>> priceStructure, Probability assumedWinProbability) {
        super(maxWins, maxLosses, cost, priceStructure);
        this.assumedWinProbability = assumedWinProbability;
    }

    @Override
    public CashAmount toAmountIn(Currency currency) {
        return calculateWinningForGivenPercentage(assumedWinProbability);
    }
}
