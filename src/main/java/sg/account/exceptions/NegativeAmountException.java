package sg.account.exceptions;

import java.math.BigDecimal;

public class NegativeAmountException extends RuntimeException {

    public NegativeAmountException(BigDecimal amount) {
        super("Amount cannot be less or equal to 0 | Amount=" + amount.toString());
    }
}
