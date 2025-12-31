package sg.account.adapters.out.persistence;

import sg.account.dto.AccountDto;
import sg.account.dto.OperationDto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class EntityMapper {

    static AccountDto toDto(AccountEntity account) {
        return new AccountDto(account.id(), account.balance(), account.currency(), toOperationsDto(account.operations()));
    }

    static AccountEntity fromDto(AccountDto accountDto) {
        return new AccountEntity(accountDto.id(), accountDto.balance(), accountDto.currency(), fromOperationsDto(accountDto.operations()));
    }

    private static List<OperationEntity> fromOperationsDto(List<OperationDto> operations) {
        return operations.stream()
                .map(EntityMapper::createOperationEntity)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<OperationDto> toOperationsDto(List<OperationEntity> operations) {
        return operations.stream()
                .map(EntityMapper::createOperationDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static OperationEntity createOperationEntity(OperationDto operationsDto) {
        return new OperationEntity(operationsDto.date(),
                operationsDto.amount(),
                operationsDto.balance(),
                operationsDto.operationType());
    }

    private static OperationDto createOperationDto(OperationEntity operationsDto) {
        return new OperationDto(operationsDto.date(),
                operationsDto.amount(),
                operationsDto.balance(),
                operationsDto.operationType());
    }
}
