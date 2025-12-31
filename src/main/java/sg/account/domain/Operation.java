package sg.account.domain;

import sg.account.dto.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class Operation {

    private final LocalDateTime date;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final OperationType operationType;

    Operation(LocalDateTime date, BigDecimal amount, BigDecimal balance, OperationType operationType) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.operationType = operationType;
    }

    static Operation deposit(LocalDateTime date, BigDecimal amount, BigDecimal balance){
        return new Operation(date, amount, balance, OperationType.DEPOSIT);
    }

    static Operation withdraw(LocalDateTime date, BigDecimal amount, BigDecimal balance){
        return new Operation(date, amount, balance, OperationType.WITHDRAWAL);
    }

    LocalDateTime getDate() {
        return date;
    }

    BigDecimal getAmount() {
        return amount;
    }

    BigDecimal getBalance() {
        return balance;
    }

    OperationType getOperationType() {
        return operationType;
    }
}
