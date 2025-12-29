package sg.account.dto;

public record DepositCommand(AccountIdDto accountId, AmountDto amount) {
}
