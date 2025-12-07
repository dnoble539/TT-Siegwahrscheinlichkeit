package magic;

import java.util.Map;

public class MH3ArenaDirect extends MagicEvent {
    public MH3ArenaDirect() {
        super(7, 2, 8000, Map.of(
                0, 0,
                1, 0,
                2, 0,
                3, 3600,
                4, 7200,
                5, 10800,
                6, 65000,
                7, 130000
        ));
    }
}
