package magic;

import math.Probability;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static math.MathFunctions.sum;
import static math.StochasticFunctions.probabilityForMHitsInNTries;

public class MagicEvent {
    private final int maxWins;
    private final int maxLosses;
    private final Integer cost;
    private final Map<Integer, Integer> priceStructure;

    public MagicEvent(int maxWins, int maxLosses, Integer cost, Map<Integer, Integer> priceStructure) {
        if (maxWins < 0 || maxLosses < 0) {
            throw new IllegalArgumentException();
        }
        this.priceStructure = priceStructure;
        this.cost = cost;
        this.maxWins = maxWins;
        this.maxLosses = maxLosses;
    }

    public BigDecimal calculateWinningForGivenPercentage(Probability gameWinPercentage) {
        Map<Integer, BigDecimal> likeliHoodPerWinTable = calculateLikeliHoodPerWinForGivenGameWinPercentage(gameWinPercentage);
        BigDecimal overallWinning = BigDecimal.ZERO;
        for (int i = 0; i <= maxWins; i++) {
            BigDecimal likeliHood = likeliHoodPerWinTable.get(i);
            var price = priceStructure.get(i) - cost;
            BigDecimal winOnAverage = likeliHood.multiply(BigDecimal.valueOf(price));
            overallWinning = overallWinning.add(winOnAverage);
        }
        return overallWinning;
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
