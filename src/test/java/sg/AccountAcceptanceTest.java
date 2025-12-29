package sg;

import org.junit.jupiter.api.Test;
import sg.account.adapters.out.persistence.AccountRepository;
import sg.account.adapters.out.print.StatementPrinter;
import sg.account.adapters.out.time.DateProvider;
import sg.account.domain.AccountFacade;
import sg.account.dto.AccountIdDto;
import sg.account.dto.AmountDto;
import sg.account.dto.DepositCommand;
import sg.account.dto.WithdrawCommand;

import java.time.Duration;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sg.account.AccountFakes.TIME;
import static sg.account.AccountFakes._0_BALANCE_ACCOUNT;
import static sg.account.AccountFakes._100;
import static sg.account.AccountFakes._50_50c;

class AccountAcceptanceTest {


    private static final MutableClock clock = new MutableClock(TIME.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

    AccountFacade accountFacade = new AccountFacade(
            new AccountRepository(),
            new DateProvider(clock),
            new StatementPrinter()
    );

    @Test
    void acceptanceTest() {
        //given
        var accountId = new AccountIdDto(_0_BALANCE_ACCOUNT);
        var amount = new AmountDto(_100);
        var depositCommand = new DepositCommand(accountId, amount);

        var amount_withdrawal = new AmountDto(_50_50c);
        var withdrawalCommand = new WithdrawCommand(accountId, amount_withdrawal);

        //when as a bank client I want to make a deposit in my account
        accountFacade.deposit(depositCommand);

        //then as a bank client I see my operation printed
        var statement_1 = accountFacade.printStatement(accountId);
        var expectedResult_1 =
                """
                        DATE | AMOUNT | BALANCE | TYPE
                        2020-10-25T10:10:00 |       100.00 |       100.00 |      DEPOSIT""";

        assertEquals(expectedResult_1, statement_1);

        //and when later as a bank client I want to make another deposit to my bank account
        clock.advance(Duration.ofSeconds(10));
        accountFacade.deposit(depositCommand);

        //then I can see all of my operations printed from newest to oldest
        var statement_2 = accountFacade.printStatement(accountId);
        var expectedResult_2 =
                """
                        DATE | AMOUNT | BALANCE | TYPE
                        2020-10-25T10:10:10 |       100.00 |       200.00 |      DEPOSIT
                        2020-10-25T10:10:00 |       100.00 |       100.00 |      DEPOSIT""";

        assertEquals(expectedResult_2, statement_2);

        //and when later as a bank client I want to withdraw my money
        clock.advance(Duration.ofSeconds(10));
        accountFacade.withdraw(withdrawalCommand);

        //then I can see all of my operations printed from newest to oldest
        var statement_3 = accountFacade.printStatement(accountId);
        var expectedResult_3 =
                """
                        DATE | AMOUNT | BALANCE | TYPE
                        2020-10-25T10:10:20 |        50.50 |       149.50 |   WITHDRAWAL
                        2020-10-25T10:10:10 |       100.00 |       200.00 |      DEPOSIT
                        2020-10-25T10:10:00 |       100.00 |       100.00 |      DEPOSIT""";

        assertEquals(expectedResult_3, statement_3);

    }


}
