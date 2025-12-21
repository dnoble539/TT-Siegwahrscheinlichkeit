import magic.*;
import math.Probability;

import java.math.BigDecimal;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        MagicEvent premierDraft = new LimitedChampionShip(new Probability(BigDecimal.valueOf(0.6)));
        String gewinnwahrscheinlichkeit = "0.55";
        Probability gameWinPercentage = new Probability(new BigDecimal(gewinnwahrscheinlichkeit));
        Map<Integer, BigDecimal> winToPercentageMap = premierDraft.calculateLikeliHoodPerWinForGivenGameWinPercentage(gameWinPercentage);
        winToPercentageMap.forEach(
                (integer, percentage) -> System.out.println("Bei einem Premierdraft mit Siegwahrscheinlichkeit pro Spiel von " + gewinnwahrscheinlichkeit + " beträgt die Wahrscheinlichkeit für " + integer + " Siege " + percentage)
        );
        System.out.println("Der Gewinn beträgt bei einer Wahrscheinlichkeit von " + gewinnwahrscheinlichkeit + " " + premierDraft.calculateWinningForGivenPercentage(gameWinPercentage).amount());
    }
}
