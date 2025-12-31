package sg.account.adapters.out.persistence;

import sg.account.dto.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

record AccountEntity(UUID id,
                     BigDecimal balance,
                     Currency currency,
                     List<OperationEntity> operations) {

}
