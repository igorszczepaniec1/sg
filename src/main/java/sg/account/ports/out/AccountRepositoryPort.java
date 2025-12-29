package sg.account.ports.out;

import sg.account.dto.AccountDto;
import sg.account.dto.AccountIdDto;

import java.util.Optional;

public interface AccountRepositoryPort {
    Optional<AccountDto> fetchAccountById(AccountIdDto accountIdDto);
    void save(AccountDto accountDto);

}
