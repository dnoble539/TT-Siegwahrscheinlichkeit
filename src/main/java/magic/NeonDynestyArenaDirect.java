package magic;

import java.util.Map;

public class NeonDynestyArenaDirect extends MagicEvent {
    public NeonDynestyArenaDirect() {
        super(7, 2, 6000, Map.of(
                0, 0,
                1, 0,
                2, 0,
                3, 2700,
                4, 5400,
                5, 8100,
                6, 20000,
                7, 40000
        ));
    }
}
