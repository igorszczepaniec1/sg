package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.dto.AmountDto;
import sg.account.exceptions.AccountNotFoundException;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.DatePort;

import static sg.account.domain.AccountMapper.fromDto;
import static sg.account.domain.AccountMapper.toDto;

class WithdrawService {

    private final AccountRepositoryPort accountRepositoryPort;
    private final DatePort datePort;

    public WithdrawService(AccountRepositoryPort accountRepositoryPort, DatePort datePort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.datePort = datePort;
    }

    void withdraw(AccountIdDto accountId, AmountDto amount) {
        var account = fromDto(fetchAccount(accountId));
        var dateOfTransaction = datePort.now();
        account.withdraw(amount, dateOfTransaction);
        accountRepositoryPort.save(toDto(account));
    }

    private AccountDto fetchAccount(AccountIdDto accountId) {
        return accountRepositoryPort.fetchAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId.id().toString()));
    }

}
