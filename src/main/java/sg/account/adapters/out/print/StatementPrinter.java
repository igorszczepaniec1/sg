package sg.account.adapters.out.print;

import sg.account.dto.OperationDto;
import sg.account.ports.out.StatementPrinterPort;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class StatementPrinter implements StatementPrinterPort {

    private static final String HEADER = "DATE | AMOUNT | BALANCE | TYPE";
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String print(List<OperationDto> operations) {
        var operationsFromNewestToOldest = operations.reversed();

        var sb = new StringBuilder();
        sb.append(HEADER);

        for (var operation : operationsFromNewestToOldest) {
            sb.append(System.lineSeparator());
            sb.append(String.format(
                    Locale.ROOT,
                    "%-10s | %12s | %12s | %12s",
                    operation.date().format(DATE_FMT),
                    format(operation.amount()),
                    format(operation.balance()),
                    operation.operationType().toString()
            ));
        }
        return sb.toString();
    }

    private String format(BigDecimal v) {
        return v.setScale(2).toPlainString();
    }
}
