package sg.account.dto;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountDto(UUID id,
                         BigDecimal balance,
                         Currency currency,
                         List<OperationDto> operations) {
}
