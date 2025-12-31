package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.dto.AmountDto;
import sg.account.exceptions.AccountNotFoundException;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.DatePort;
import sg.account.ports.out.FxRatePort;


import static sg.account.domain.AccountMapper.fromDto;
import static sg.account.domain.AccountMapper.toDto;

class DepositService {

    private final AccountRepositoryPort accountRepositoryPort;
    private final DatePort datePort;
    private final CurrencyConverter currencyConverter;

    public DepositService(AccountRepositoryPort accountRepositoryPort, DatePort datePort, FxRatePort fxRatePort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.datePort = datePort;
        this.currencyConverter = new CurrencyConverter(fxRatePort);
    }

    void deposit(AccountIdDto accountId, AmountDto amount) {
        var account = fromDto(fetchAccount(accountId));
        var dateOfTransaction = datePort.now();

        account.deposit(retrieveAmount(account, amount), dateOfTransaction);
        accountRepositoryPort.save(toDto(account));
    }

    private AmountDto retrieveAmount(Account account, AmountDto amount){
        return account.getCurrency().equals(amount.currency())
                ? amount
                : currencyConverter.convert(account.getCurrency(), amount);
    }

    private AccountDto fetchAccount(AccountIdDto accountId) {
        return accountRepositoryPort.fetchAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId.id().toString()));
    }

}
