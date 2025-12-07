package Magic;

import java.util.Map;

public class PremierDraft extends MagicEvent {
    public PremierDraft() {
        super(7,3, 1500, Map.of(
                0, 50,
                1, 100,
                2, 250,
                3, 1000,
                4, 1400,
                5, 1600,
                6, 1800,
                7, 2200
        ));
    }
}
