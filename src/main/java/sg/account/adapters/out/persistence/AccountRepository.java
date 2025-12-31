package sg.account.adapters.out.persistence;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;
import sg.account.dto.Currency;
import sg.account.ports.out.AccountRepositoryPort;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static sg.account.adapters.out.persistence.EntityMapper.toDto;
import static sg.account.adapters.out.persistence.EntityMapper.fromDto;

public class AccountRepository implements AccountRepositoryPort {

    private final Map<UUID, AccountEntity> database;
    private static final UUID ID = UUID.fromString("9600c2a8-98b2-4a39-921c-29708256a5ab");


    public AccountRepository() {
        this.database = new HashMap<>(Map.of(ID, createAccountEntity()));
    }

    private AccountEntity createAccountEntity() {
        return new AccountEntity(ID, BigDecimal.ZERO, Currency.EUR, new LinkedList<>());
    }

    @Override
    public Optional<AccountDto> fetchAccountById(AccountIdDto accountIdDto) {
        var entity = database.get(accountIdDto.id());
        return entity != null
                ? Optional.of(toDto(entity))
                : Optional.empty();
    }

    @Override
    public void save(AccountDto accountDto) {
        database.put(accountDto.id(), fromDto(accountDto));
    }
}
