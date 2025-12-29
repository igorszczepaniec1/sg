package sg.account.adapters.out.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.ZoneId;

import static sg.account.AccountFakes.TIME;

class DateProviderTest {

    private final Clock clock = Clock.fixed(TIME.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    //sut
    private final DateProvider dateProvider = new DateProvider(clock);


    @Test
    void now_should_return_current_time() {
        var now = dateProvider.now();
        Assertions.assertEquals(TIME, now);
    }


}