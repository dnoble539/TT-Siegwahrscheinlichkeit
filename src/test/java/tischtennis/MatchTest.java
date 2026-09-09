package tischtennis;

import math.Probability;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MatchTest {

    /**
     * Characterization test — pinnt das aktuelle (fehlerhafte) Verhalten, das aus
     * dem Monorepo übernommen wurde: {@code chanceToWinMatchGiven} ruft
     * {@code probabilityForMOrLessHitsInNTries(n, m, …)} mit {@code m > n} auf und
     * verletzt damit die Bedingung {@code tries <= hits} in
     * {@code StochasticFunctions}. Sobald {@code Match} korrigiert ist, diesen Test
     * durch eine echte Wahrscheinlichkeits-Assertion ersetzen.
     */
    @Test
    void chanceToWinMatchGiven_isCurrentlyNonFunctional() {
        Match match = new Match(4, 11);
        Probability perBallwechsel = new Probability(new BigDecimal("0.55"));

        assertThatThrownBy(() -> match.chanceToWinMatchGiven(perBallwechsel))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
