package sg.account.dto;

public record WithdrawCommand(AccountIdDto accountId, AmountDto amount) {
}
