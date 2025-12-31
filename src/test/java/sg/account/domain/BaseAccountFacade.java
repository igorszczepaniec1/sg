package sg.account.domain;

import sg.account.adapters.out.fx.RateKey;
import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.dto.Currency;
import sg.account.dto.OperationDto;
import sg.account.ports.out.AccountRepositoryPort;
import sg.account.ports.out.DatePort;
import sg.account.ports.out.FxRatePort;
import sg.account.ports.out.StatementPrinterPort;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static sg.account.domain.AccountFacadeFakes.accounts;
import static sg.account.dto.Currency.EUR;
import static sg.account.dto.Currency.USD;

class BaseAccountFacade {

    protected final FakeStatementPrinter fakeStatementPrinter = new FakeStatementPrinter();
    protected final DatePort datePort = mock(DatePort.class);

    protected final AccountFacade accountFacade = new AccountFacade(
            new InMemoryAccountRepository(),
            datePort,
            fakeStatementPrinter,
            new FakeFxRate()
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

    protected static class FakeFxRate implements FxRatePort {

        Map<RateKey, BigDecimal> map = Map.ofEntries(
                Map.entry(new RateKey(EUR, USD), new BigDecimal("0.9000000000")),
                Map.entry(new RateKey(USD, EUR), new BigDecimal("1.1111111111"))
        );

        @Override
        public BigDecimal rate(Currency from, Currency to) {
            return map.get(new RateKey(from, to));
        }
    }

}
