package sg.account.adapters.out.time;

import sg.account.ports.out.DatePort;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateProvider implements DatePort {

    private final Clock clock;

    public DateProvider(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault());
    }
}
