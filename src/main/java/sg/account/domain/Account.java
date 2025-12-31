package sg.account.domain;

import sg.account.dto.AmountDto;
import sg.account.dto.Currency;
import sg.account.exceptions.InsufficientFundsException;

import java.time.LocalDateTime;
import java.util.List;

class Account {

    private final AccountId accountId;
    private final Balance balance;
    private final Currency currency;
    private final List<Operation> operations;

    Account(AccountId accountId, Balance balance, Currency currency, List<Operation> operations) {
        this.accountId = accountId;
        this.balance = balance;
        this.currency = currency;
        this.operations = operations;
    }

    void deposit(AmountDto amount, LocalDateTime time) {
        balance.add(amount.amount());
        operations.add(Operation.deposit(time, amount.amount(), balance.getAmount()));
    }

    void withdraw(AmountDto amount, LocalDateTime time) {
        requireSufficientFunds(amount);
        balance.subtract(amount.amount());
        operations.add(Operation.withdraw(time, amount.amount(), balance.getAmount()));
    }

    private void requireSufficientFunds(AmountDto amount) {
        if (balance.getAmount().compareTo(amount.amount()) < 0) {
            throw new InsufficientFundsException();
        }
    }

    AccountId getAccountId() {
        return accountId;
    }

    Balance getBalance() {
        return balance;
    }

    List<Operation> getOperations() {
        return operations;
    }

    Currency getCurrency() {
        return currency;
    }
}
