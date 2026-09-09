import math.Probability;
import tischtennis.Match;

import java.math.BigDecimal;

public class Application {

    public static void main(String[] args) {
        // Best-of-7: erster bei 4 Sätzen, Sätze bis 11 Punkte.
        Match match = new Match(4, 11);

        String ballwechselSiegWahrscheinlichkeit = "0.55";
        Probability perBallwechsel = new Probability(new BigDecimal(ballwechselSiegWahrscheinlichkeit));

        try {
            Probability matchWin = match.chanceToWinMatchGiven(perBallwechsel);
            System.out.println("Bei einer Ballwechsel-Siegwahrscheinlichkeit von "
                    + ballwechselSiegWahrscheinlichkeit
                    + " beträgt die Matchsiegwahrscheinlichkeit (Best-of-7, Sätze bis 11) "
                    + matchWin.winProb());
        } catch (RuntimeException e) {
            // Bekannter, aus dem Monorepo übernommener Defekt: Match reicht (n, m)
            // mit m > n an probabilityForMOrLessHitsInNTries, was die Bedingung
            // tries <= hits in StochasticFunctions verletzt. Zusätzlich sind
            // TtrCalculator.calculateWinPercentage und
            // Match.chanceToWinBallwechselGivenMatchWinPercentage noch Stubs.
            System.out.println("Die Tischtennis-Berechnung ist noch nicht funktionsfähig: "
                    + e);
        }
    }
}
