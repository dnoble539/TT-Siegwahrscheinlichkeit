package tischtennis;

import math.Probability;

public class Player {

    private final int ttrWert;

    public Player(int ttrWert) {
        this.ttrWert = ttrWert;
    }

    public Probability chanceToWinVs(Match match, Player opponent) {
        Probability probability = TtrCalculator.calculateWinPercentage(ttrWert - opponent.ttrWert);
        return match.chanceToWinMatchGiven(probability);
    }
}
