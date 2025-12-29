package sg.account.adapters.out.persistence;

import sg.account.dto.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record OperationEntity(LocalDateTime date, BigDecimal amount, BigDecimal balance, OperationType operationType) {

}
