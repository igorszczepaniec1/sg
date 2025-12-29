package sg.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountFakes {

    public static final UUID _0_BALANCE_ACCOUNT = UUID.fromString("9600c2a8-98b2-4a39-921c-29708256a5ab");
    public static final UUID _100_BALANCE_ACCOUNT = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID ACCOUNT_WITH_ONE_OPERATION = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID NON_EXISTENT_ACCOUNT = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID ACCOUNT_WITH_NO_OPERATIONS = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID _1000_BALANCE_ACCOUNT = UUID.fromString("55555555-5555-5555-5555-555555555555");

    public static final LocalDateTime TIME = LocalDateTime.of(2020, 10, 25, 10, 10);
    public static final LocalDateTime TIME_2 = LocalDateTime.of(2020, 10, 26, 10, 10);
    public static final LocalDateTime TIME_3 = LocalDateTime.of(2020, 10, 27, 10, 10);
    public static final LocalDateTime TIME_4 = LocalDateTime.of(2020, 10, 28, 10, 10);
    public static final LocalDateTime TIME_5 = LocalDateTime.of(2020, 10, 29, 10, 10);

    public static final BigDecimal NEGATIVE_50 = BigDecimal.valueOf(-50);
    public static final BigDecimal _50 = BigDecimal.valueOf(50);
    public static final BigDecimal _50_50c = BigDecimal.valueOf(50.50);
    public static final BigDecimal _100 = BigDecimal.valueOf(100);
    public static final BigDecimal _150 = BigDecimal.valueOf(150);
    public static final BigDecimal _500 = BigDecimal.valueOf(500);
    public static final BigDecimal _550 = BigDecimal.valueOf(550);
    public static final BigDecimal _600_50c = BigDecimal.valueOf(600.50);
    public static final BigDecimal _650 = BigDecimal.valueOf(650);
    public static final BigDecimal _1000 = BigDecimal.valueOf(1000);


}
