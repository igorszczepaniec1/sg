package sg.account.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sg.account.dto.AccountIdDto;
import sg.account.dto.AmountDto;
import sg.account.dto.Currency;
import sg.account.dto.DepositCommand;
import sg.account.dto.WithdrawCommand;
import sg.account.exceptions.AccountNotFoundException;
import sg.account.exceptions.InsufficientFundsException;
import sg.account.exceptions.NegativeAmountException;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static sg.account.AccountFakes.*;
import static sg.account.dto.OperationType.DEPOSIT;
import static sg.account.dto.OperationType.WITHDRAWAL;

class AccountFacadeTest extends BaseAccountFacade {

    @BeforeEach
     void setup() {
        when(datePort.now()).thenReturn(TIME);
    }

    @Test
    void deposit_adds_operation_and_updates_balance() {
        //given
        var accountId = new AccountIdDto(_0_BALANCE_ACCOUNT);
        var amount = new AmountDto(_100, Currency.EUR);
        var command = new DepositCommand(accountId, amount);

        //when
        accountFacade.deposit(command);

        //then
        accountFacade.printStatement(accountId);
        var operation = fakeStatementPrinter.operations.getFirst();
        assertEquals(_100, operation.balance());
        assertEquals(_100, operation.amount());
        assertEquals(DEPOSIT, operation.operationType());
        assertEquals(TIME, operation.date());
    }


    @Test
    void withdrawal_adds_operation_and_updates_balance() {
        //given
        var accountId = new AccountIdDto(_100_BALANCE_ACCOUNT);
        var amount = new AmountDto(_50, Currency.EUR);
        var command = new WithdrawCommand(accountId, amount);

        //when
        accountFacade.withdraw(command);

        //then
        accountFacade.printStatement(accountId);
        var operation = fakeStatementPrinter.operations.getFirst();
        assertEquals(_50, operation.balance());
        assertEquals(_50, operation.amount());
        assertEquals(WITHDRAWAL, operation.operationType());
        assertEquals(TIME, operation.date());
    }

    @Test
    void printStatement_shows_operations_with_date_amount_balance_and_type() {
        //given
        var accountId = new AccountIdDto(ACCOUNT_WITH_ONE_OPERATION);

        //when
        accountFacade.printStatement(accountId);

        //then
        var operation = fakeStatementPrinter.operations.getFirst();
        assertEquals(_1000, operation.balance());
        assertEquals(_500, operation.amount());
        assertEquals(DEPOSIT, operation.operationType());
        assertEquals(TIME, operation.date());
    }

    @Test
    void printStatement_shows_all_operations_accordingly_after_multiple_deposits_and_withdrawals() {
        //given
        when(datePort.now()).thenReturn(TIME, TIME_2, TIME_3, TIME_4);
        var accountId = new AccountIdDto(_1000_BALANCE_ACCOUNT);

        var amount_50 = new AmountDto(_50, Currency.EUR);
        var amount_100 = new AmountDto(_100, Currency.EUR);
        var amount_500 = new AmountDto(_500, Currency.EUR);

        var withdraw_1 = new WithdrawCommand(accountId, amount_500);
        var deposit_1 = new DepositCommand(accountId, amount_50);
        var deposit_2 = new DepositCommand(accountId, amount_100);
        var withdraw_2 = new WithdrawCommand(accountId, amount_500);

        //when
        accountFacade.withdraw(withdraw_1);
        accountFacade.deposit(deposit_1);
        accountFacade.deposit(deposit_2);
        accountFacade.withdraw(withdraw_2);

        //and
        accountFacade.printStatement(accountId);

        //then
        assertEquals(4, fakeStatementPrinter.operations.size());
        var operation_1 = fakeStatementPrinter.operations.getFirst();
        var operation_2 = fakeStatementPrinter.operations.get(1);
        var operation_3 = fakeStatementPrinter.operations.get(2);
        var operation_4 = fakeStatementPrinter.operations.getLast();

        assertEquals(_500, operation_1.balance());
        assertEquals(_500, operation_1.amount());
        assertEquals(WITHDRAWAL, operation_1.operationType());
        assertEquals(TIME, operation_1.date());

        assertEquals(_550, operation_2.balance());
        assertEquals(_50, operation_2.amount());
        assertEquals(DEPOSIT, operation_2.operationType());
        assertEquals(TIME_2, operation_2.date());

        assertEquals(_650, operation_3.balance());
        assertEquals(_100, operation_3.amount());
        assertEquals(DEPOSIT, operation_3.operationType());
        assertEquals(TIME_3, operation_3.date());

        assertEquals(_150, operation_4.balance());
        assertEquals(_500, operation_4.amount());
        assertEquals(WITHDRAWAL, operation_4.operationType());
        assertEquals(TIME_4, operation_4.date());
    }

    @ParameterizedTest
    @MethodSource
    void deposit_should_convert_deposit_amount_to_account_currency(DepositCommand command, BigDecimal expectedBalance, BigDecimal expectedAmount) {
        //when
        accountFacade.deposit(command);

        //then
        accountFacade.printStatement(command.accountId());
        var operation = fakeStatementPrinter.operations.getFirst();
        assertEquals(expectedAmount, operation.amount());
        assertEquals(expectedBalance, operation.balance());
    }

    private static Stream<Arguments> deposit_should_convert_deposit_amount_to_account_currency() {
        return Stream.of(
                Arguments.of(new DepositCommand(
                        new AccountIdDto(_0_EUR_ACCOUNT),
                        new AmountDto(_100, Currency.USD)),
                        _90,
                        _90),

                Arguments.of(new DepositCommand(
                         new AccountIdDto(_0_USD_ACCOUNT),
                         new AmountDto(_100, Currency.EUR)),
                        _111_11c,
                        _111_11c)
        );
    }

    @ParameterizedTest
    @MethodSource
    void withdraw_should_convert_withdraw_amount_to_account_currency(WithdrawCommand command, BigDecimal expectedBalance, BigDecimal expectedAmount) {
        //when
        accountFacade.withdraw(command);

        //then
        accountFacade.printStatement(command.accountId());
        var operation = fakeStatementPrinter.operations.getFirst();
        assertEquals(expectedAmount, operation.amount());
        assertEquals(expectedBalance, operation.balance());
    }

    private static Stream<Arguments> withdraw_should_convert_withdraw_amount_to_account_currency() {
        return Stream.of(
                Arguments.of(new WithdrawCommand(
                        new AccountIdDto(_1000_EUR_ACCOUNT),
                        new AmountDto(_100, Currency.USD)),
                        _910,
                        _90),

                Arguments.of(new WithdrawCommand(
                        new AccountIdDto(_1000_USD_ACCOUNT),
                        new AmountDto(_100, Currency.EUR)),
                        _888_89c,
                        _111_11c)
        );
    }

    @Test
    void print_statement_does_not_show_any_operations_if_account_has_no_operations() {
        //given
        var accountId = new AccountIdDto(ACCOUNT_WITH_NO_OPERATIONS);

        //when
        accountFacade.printStatement(accountId);

        //then
        assertEquals(0, fakeStatementPrinter.operations.size());
    }

    @Test
    void deposit_throws_exception_when_deposit_amount_is_0_or_negative() {
        var accountId = new AccountIdDto(_100_BALANCE_ACCOUNT);
        var amount = new AmountDto(NEGATIVE_50, Currency.EUR);
        var command = new DepositCommand(accountId, amount);

        assertThrows(NegativeAmountException.class, () -> accountFacade.deposit(command));
    }

    @Test
    void withdraw_throws_exception_when_withdraw_amount_is_0_or_negative() {
        var accountId = new AccountIdDto(_100_BALANCE_ACCOUNT);
        var amount = new AmountDto(NEGATIVE_50, Currency.EUR);
        var command = new WithdrawCommand(accountId, amount);

        assertThrows(NegativeAmountException.class, () -> accountFacade.withdraw(command));
    }


    @Test
    void withdraw_throws_exception_when_withdraw_amount_greater_than_balance() {
        var accountId = new AccountIdDto(_100_BALANCE_ACCOUNT);
        var amount = new AmountDto(_500, Currency.EUR);
        var command = new WithdrawCommand(accountId, amount);

        assertThrows(InsufficientFundsException.class, () -> accountFacade.withdraw(command));
    }

    @Test
    void deposit_throws_exception_when_account_was_not_found() {
        var accountId = new AccountIdDto(NON_EXISTENT_ACCOUNT);
        var amount = new AmountDto(_500, Currency.EUR);
        var command = new DepositCommand(accountId, amount);

        assertThrows(AccountNotFoundException.class, () -> accountFacade.deposit(command));
    }

    @Test
    void withdraw_throws_exception_when_account_was_not_found() {
        var accountId = new AccountIdDto(NON_EXISTENT_ACCOUNT);
        var amount = new AmountDto(_500, Currency.EUR);
        var command = new WithdrawCommand(accountId, amount);

        assertThrows(AccountNotFoundException.class, () -> accountFacade.withdraw(command));
    }

    @Test
    void print_statement_throws_exception_when_account_was_not_found() {
        var accountId = new AccountIdDto(NON_EXISTENT_ACCOUNT);

        assertThrows(AccountNotFoundException.class, () -> accountFacade.printStatement(accountId));
    }

}





