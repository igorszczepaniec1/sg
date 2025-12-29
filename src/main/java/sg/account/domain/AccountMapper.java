package sg.account.domain;

import sg.account.dto.AccountDto;
import sg.account.dto.OperationDto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class AccountMapper {

    static Account fromDto(AccountDto accountDto) {
        return new Account(
                new AccountId(accountDto.id()),
                new Balance(accountDto.balance()),
                fromOperationsDto(accountDto.operations()));
    }

    static AccountDto toDto(Account account) {
        return new AccountDto(
                account.getAccountId().getId(),
                account.getBalance().getAmount(),
                operationsToDto(account.getOperations()));
    }

    static List<OperationDto> toOperationsDto(Account account) {
        return operationsToDto(account.getOperations());
    }

    private static List<Operation> fromOperationsDto(List<OperationDto> operations) {
        return operations.stream()
                .map(AccountMapper::createOperation)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<OperationDto> operationsToDto(List<Operation> operations) {
        return operations.stream()
                .map(AccountMapper::createOperationDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static Operation createOperation(OperationDto operationDto) {
        return new Operation(
                operationDto.date(),
                operationDto.amount(),
                operationDto.balance(),
                operationDto.operationType());
    }

    private static OperationDto createOperationDto(Operation operation) {
        return new OperationDto(
                operation.getDate(),
                operation.getAmount(),
                operation.getBalance(),
                operation.getOperationType());
    }

}
