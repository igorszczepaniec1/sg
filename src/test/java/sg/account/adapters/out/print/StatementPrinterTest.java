package sg.account.adapters.out.print;

import org.junit.jupiter.api.Test;
import sg.account.dto.OperationDto;
import sg.account.dto.OperationType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sg.account.AccountFakes.*;

class StatementPrinterTest {

    //sut
    private final StatementPrinter statementPrinter = new StatementPrinter();

    @Test
    void print_should_return_formatted_string() {
        //given
        var operations = List.of(
                new OperationDto(TIME, _50, _50, OperationType.DEPOSIT),
                new OperationDto(TIME_2, _50, _100, OperationType.DEPOSIT),
                new OperationDto(TIME_3, _50, _50, OperationType.WITHDRAWAL),
                new OperationDto(TIME_4, _500, _550, OperationType.DEPOSIT),
                new OperationDto(TIME_5, _50_50c, _600_50c, OperationType.DEPOSIT));

        //when
        var result = statementPrinter.print(operations);

        //then
        var expectedResult = """
                DATE | AMOUNT | BALANCE | TYPE
                2020-10-29T10:10:00 |        50.50 |       600.50 |      DEPOSIT
                2020-10-28T10:10:00 |       500.00 |       550.00 |      DEPOSIT
                2020-10-27T10:10:00 |        50.00 |        50.00 |   WITHDRAWAL
                2020-10-26T10:10:00 |        50.00 |       100.00 |      DEPOSIT
                2020-10-25T10:10:00 |        50.00 |        50.00 |      DEPOSIT""";

        assertEquals(expectedResult, result);

    }

    @Test
    void print_should_return_headers_only_when_no_operations() {
        var result = statementPrinter.print(List.of());
        var expectedResult = "DATE | AMOUNT | BALANCE | TYPE";

        assertEquals(expectedResult, result);
    }


}