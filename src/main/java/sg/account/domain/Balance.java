package sg.account.domain;

import sg.account.exceptions.NegativeAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Balance {

    private BigDecimal balance;

    Balance(BigDecimal balance) {
        this.balance = balance.setScale(2, RoundingMode.HALF_DOWN);
    }

    void add(BigDecimal amount) {
        requirePositiveAmount(amount);
        balance = balance.add(amount);
    }

    void subtract(BigDecimal amount) {
        requirePositiveAmount(amount);
        balance = balance.subtract(amount);
    }

    BigDecimal getAmount() {
        return balance;
    }


    private static void requirePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new NegativeAmountException(amount);
    }
}
