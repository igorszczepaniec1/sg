package sg.account.adapters.out.print;

import sg.account.dto.OperationDto;
import sg.account.ports.out.StatementPrinterPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class StatementPrinter implements StatementPrinterPort {

    private static final int DATE_WIDTH = 19;
    private static final int NUM_WIDTH = 12;
    private static final int TYPE_WIDTH = 12;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String ROW_FMT = "%-" + DATE_WIDTH + "s | %" + NUM_WIDTH + "s | %" + NUM_WIDTH + "s | %" + TYPE_WIDTH + "s";
    private static final String HEADER = String.format(Locale.ROOT, ROW_FMT, "DATE", "AMOUNT", "BALANCE", "TYPE");

    @Override
    public String print(List<OperationDto> operations) {
        var operationsFromNewestToOldest = operations.reversed();

        var sb = new StringBuilder();
        sb.append(HEADER);

        for (var operation : operationsFromNewestToOldest) {
            sb.append(System.lineSeparator());
            sb.append(String.format(
                    Locale.ROOT,
                    ROW_FMT,
                    operation.date().format(DATE_FMT),
                    format(operation.amount()),
                    format(operation.balance()),
                    operation.operationType().toString()
            ));
        }
        return sb.toString();
    }

    private String format(BigDecimal v) {
        return v.setScale(2, RoundingMode.HALF_DOWN).toPlainString();
    }
}
