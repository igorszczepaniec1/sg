package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.OperationDto;
import sg.account.dto.OperationType;

import java.math.BigDecimal;
import java.util.List;

import static sg.account.AccountFakes.*;

class AccountFacadeFakes {

    static List<AccountDto> accounts() {
        return List.of(
                new AccountDto(_0_BALANCE_ACCOUNT, BigDecimal.ZERO, List.of()),
                new AccountDto(_100_BALANCE_ACCOUNT, _100, List.of()),
                new AccountDto(ACCOUNT_WITH_ONE_OPERATION, BigDecimal.valueOf(1000), List.of(new OperationDto(TIME, _500, _1000, OperationType.DEPOSIT))),
                new AccountDto(ACCOUNT_WITH_NO_OPERATIONS, BigDecimal.valueOf(1000), List.of()),
                new AccountDto(_1000_BALANCE_ACCOUNT, BigDecimal.valueOf(1000), List.of())
        );
    }


}
