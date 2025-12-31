package sg.account.ports.out;

import sg.account.dto.Currency;

import java.math.BigDecimal;

public interface FxRatePort  {
    BigDecimal rate(Currency from, Currency to);
}
