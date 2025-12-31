package sg.account.domain;

import sg.account.dto.AccountIdDto;
import sg.account.dto.DepositCommand;
import sg.account.dto.WithdrawCommand;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.DatePort;
import sg.account.ports.out.FxRatePort;
import sg.account.ports.out.StatementPrinterPort;

public class AccountFacade {

    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final StatementService statementService;

    public AccountFacade(AccountRepositoryPort accountRepositoryPort, DatePort datePort, StatementPrinterPort statementPrinterPort, FxRatePort fxRatePort) {
        this.depositService = new DepositService(accountRepositoryPort, datePort, fxRatePort);
        this.withdrawService = new WithdrawService(accountRepositoryPort, datePort, fxRatePort);
        this.statementService = new StatementService(accountRepositoryPort, statementPrinterPort);
    }

    public void deposit(DepositCommand depositCommand) {
        depositService.deposit(depositCommand.accountId(), depositCommand.amount());
    }

    public void withdraw(WithdrawCommand withdrawCommand) {
        withdrawService.withdraw(withdrawCommand.accountId(), withdrawCommand.amount());
    }

    public String printStatement(AccountIdDto accountIdDto) {
        return statementService.printStatement(accountIdDto);
    }

}
