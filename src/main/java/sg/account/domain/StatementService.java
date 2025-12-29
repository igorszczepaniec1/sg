package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.exceptions.AccountNotFoundException;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.StatementPrinterPort;

import static sg.account.domain.AccountMapper.*;

class StatementService {

    private final AccountRepositoryPort accountRepositoryPort;
    private final StatementPrinterPort statementPrinterPort;

    public StatementService(AccountRepositoryPort accountRepositoryPort, StatementPrinterPort statementPrinterPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.statementPrinterPort = statementPrinterPort;
    }

    String printStatement(AccountIdDto accountIdDto){
        var account = fromDto(fetchAccount(accountIdDto));
        return statementPrinterPort.print(toOperationsDto(account));
    }

    private AccountDto fetchAccount(AccountIdDto accountId) {
        return accountRepositoryPort.fetchAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId.id().toString()));
    }
}
