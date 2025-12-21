package magic;

import finance.CashAmount;
import finance.Currency;
import math.Probability;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static math.MathFunctions.sum;
import static math.StochasticFunctions.probabilityForMHitsInNTries;

public class MagicEvent {
    private final int maxWins;
    private final int maxLosses;
    private final CashAmount cost;
    private final Map<Integer, Set<EventPrice>> priceStructure;
    private final Currency baseCurrency;

    public MagicEvent(int maxWins, int maxLosses, CashAmount cost, Map<Integer, Set<EventPrice>> priceStructure) {
        if (maxWins < 0 || maxLosses < 0) {
            throw new IllegalArgumentException();
        }
        this.priceStructure = priceStructure;
        this.cost = cost;
        this.baseCurrency = cost.currency();
        this.maxWins = maxWins;
        this.maxLosses = maxLosses;
    }

    public CashAmount calculateWinningForGivenPercentage(Probability gameWinPercentage) {
        Map<Integer, BigDecimal> likeliHoodPerWinTable = calculateLikeliHoodPerWinForGivenGameWinPercentage(gameWinPercentage);
        BigDecimal overallWinning = BigDecimal.ZERO;
        for (int i = 0; i <= maxWins; i++) {
            BigDecimal likeliHood = likeliHoodPerWinTable.get(i);
            var price = calculatePriceFromAll(priceStructure.get(i));
            BigDecimal winOnAverage = likeliHood.multiply(price);
            overallWinning = overallWinning.add(winOnAverage);
        }
        return new CashAmount(overallWinning, baseCurrency);
    }

    private BigDecimal calculatePriceFromAll(Set<EventPrice> priceEvents) {
        return priceEvents.stream()
                .map((eventPrice) -> eventPrice.toAmountIn(baseCurrency).amount())
                .reduce(BigDecimal::add)
                .map(price -> price.subtract(cost.amount()))
                .orElseThrow();
    }

    public Map<Integer, BigDecimal> calculateLikeliHoodPerWinForGivenGameWinPercentage(Probability gameWinPercentage) {
        HashMap<Integer, BigDecimal> likeliHoodPerWinTable = new HashMap<>();
        for (int i = 0; i <= maxWins; i++) {
            likeliHoodPerWinTable.put(i, calculateLikeliHoodForNWinsGivenGameWinPercentage(i, gameWinPercentage));
        }
        return likeliHoodPerWinTable;
    }

    private BigDecimal calculateLikeliHoodForNWinsGivenGameWinPercentage(int n, Probability gameWinPercentage) {
        if (n == 0) {
            return gameWinPercentage.looseProb().pow(maxLosses);
        }
        if (n > maxWins) {
            return BigDecimal.ZERO;
        }
        if (n == maxWins) {
            return sum(0, maxLosses - 1, i -> probabilityForMHitsInNTries(n + i-1, n-1, gameWinPercentage)).multiply(gameWinPercentage.winProb());
        }
        return probabilityForMHitsInNTries(n + maxLosses - 1, n, gameWinPercentage)
                .multiply(gameWinPercentage.looseProb());
    }
}
