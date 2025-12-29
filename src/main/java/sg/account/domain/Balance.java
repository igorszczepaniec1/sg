package sg.account.domain;

import sg.account.exceptions.NegativeAmountException;

import java.math.BigDecimal;

class Balance {

    private BigDecimal balance;

    Balance(BigDecimal balance) {
        this.balance = balance;
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
