package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.dto.OperationDto;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.DatePort;
import sg.account.ports.out.StatementPrinterPort;

import java.util.*;

import static org.mockito.Mockito.mock;
import static sg.account.domain.AccountFacadeFakes.accounts;

class BaseAccountFacade {

    protected final FakeStatementPrinter fakeStatementPrinter = new FakeStatementPrinter();
    protected final DatePort datePort = mock(DatePort.class);

    protected final AccountFacade accountFacade = new AccountFacade(
            new InMemoryAccountRepository(),
            datePort,
            fakeStatementPrinter
    );

    protected static class InMemoryAccountRepository implements AccountRepositoryPort {

        protected Map<UUID, AccountDto> database;

        public InMemoryAccountRepository() {
            database = new HashMap<>();
            for (AccountDto account : accounts()) {
                database.put(account.id(), account);
            }
        }

        @Override
        public Optional<AccountDto> fetchAccountById(AccountIdDto accountIdDto) {
            return Optional.ofNullable(database.get(accountIdDto.id()));
        }

        @Override
        public void save(AccountDto accountDto) {
            database.put(accountDto.id(), accountDto);
        }

    }

    protected static class FakeStatementPrinter implements StatementPrinterPort {

        List<OperationDto> operations;

        @Override
        public String print(List<OperationDto> operations) {
            this.operations = List.copyOf(operations);
            return "ignore";
        }
    }

}
