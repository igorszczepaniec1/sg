package sg.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationDto(LocalDateTime date,
                           BigDecimal amount,
                           BigDecimal balance,
                           OperationType operationType) {


}
