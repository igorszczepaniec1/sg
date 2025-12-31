package sg.account.adapters.out.persistence;

import org.junit.jupiter.api.Test;
import sg.account.dto.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sg.account.AccountFakes.*;

class AccountRepositoryTest {

    //sut
    private final AccountRepository accountRepository = new AccountRepository();

    @Test
    void repository_should_save_entity_and_then_retrieve_it() {
        //given
        var accountDto = createAccountDto();

        //when
        accountRepository.save(accountDto);
        //and
        var retrievedAccount = accountRepository.fetchAccountById(new AccountIdDto(accountDto.id()));

        //then
        assertEquals(retrievedAccount.get(), accountDto);

    }

    @Test
    void repository_should_return_empty_optional_if_no_account_found() {
        //expect
        assertTrue(accountRepository.fetchAccountById(new AccountIdDto(NON_EXISTENT_ACCOUNT)).isEmpty());
    }

    private static AccountDto createAccountDto() {
        return new AccountDto(_0_BALANCE_ACCOUNT, BigDecimal.ZERO, Currency.EUR, List.of(new OperationDto(TIME, BigDecimal.TEN, BigDecimal.TEN, OperationType.WITHDRAWAL)));
    }


}