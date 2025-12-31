package sg.account.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record AmountDto(BigDecimal amount, Currency currency) {
    public AmountDto{
        amount = amount.setScale(2, RoundingMode.HALF_DOWN);
    }
}
