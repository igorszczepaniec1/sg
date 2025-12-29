package sg.account.adapters.out.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

record AccountEntity(UUID id, BigDecimal balance, List<OperationEntity> operations) {

}
