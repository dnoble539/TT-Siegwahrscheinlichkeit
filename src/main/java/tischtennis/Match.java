package tischtennis;

import math.Polynom;
import math.Probability;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static math.StochasticFunctions.probabilityForMHitsInNTries;
import static math.StochasticFunctions.probabilityForMOrLessHitsInNTries;

public class Match {

    private final int setsToWin;
    private final int pointsToWin;

    public Match(int setsToWin, int pointsToWin) {
        this.setsToWin = setsToWin;
        this.pointsToWin = pointsToWin;
    }

    public Probability chanceToWinMatchGiven(Probability probability) {
        BigDecimal probabilityForLoosingSet = calculateProbabilityForLoosingSetGivenMatchWinPercentage(probability);
        BigDecimal probabilityToWinMatch = probabilityForMOrLessHitsInNTries(setsToWin - 1, 2 * setsToWin - 1, new Probability(probabilityForLoosingSet));
        return new Probability(probabilityToWinMatch);
    }

    public Probability chanceToWinBallwechselGivenMatchWinPercentage(Probability probability) {
        Polynom polynom = new Polynom(List.of(probability.winProb(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.valueOf(15L), BigDecimal.valueOf(6L)));
        BigDecimal approximatedChancePerSetToWin = polynom.approximateNullstelleWithNewton();
        return null;
    }

    private BigDecimal calculateProbabilityForLoosingSetGivenMatchWinPercentage(Probability probability) {
        BigDecimal probabilityToLooseSetInRegularBallwechsel = probabilityForMOrLessHitsInNTries(pointsToWin, 2 * pointsToWin - 2, probability);
        BigDecimal probabilityForVerlaengerung = probabilityForMHitsInNTries(pointsToWin - 1, 2 * pointsToWin - 2, probability);
        BigDecimal probabilityForLoosingVerlaengerung = loosingVerlaengerung(probability);
        return probabilityToLooseSetInRegularBallwechsel.add(probabilityForVerlaengerung.multiply(probabilityForLoosingVerlaengerung));
    }

    private BigDecimal loosingVerlaengerung(Probability probability) {
        // sum_0_inf q^n = 1/1-q für q < 0 q= prob für 1:1 in zwei ballwechseln
        // das multiplizieren mit prob für 0/2 nach zwei Ballwechseln
        return probabilityForMHitsInNTries(0, 2, probability)
                .divide(
                        BigDecimal.ONE.subtract(probabilityForMHitsInNTries(1, 1, probability)),
                        MathContext.DECIMAL32
                );
    }
}
