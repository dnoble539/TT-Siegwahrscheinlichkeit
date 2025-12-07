package Magic;

import java.util.Map;

public class PioneerEvent extends MagicEvent {
    public PioneerEvent() {
        super(7,3, 375, Map.of(
                0, 25,
                1, 50,
                2, 75,
                3, 200,
                4, 300,
                5, 400,
                6, 450,
                7, 500
        ));
    }
}
