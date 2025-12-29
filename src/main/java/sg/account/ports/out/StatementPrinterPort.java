package sg.account.ports.out;

import sg.account.dto.AccountIdDto;
import sg.account.dto.OperationDto;

import java.util.List;

public interface StatementPrinterPort {
    String print(List<OperationDto> dto);
}
