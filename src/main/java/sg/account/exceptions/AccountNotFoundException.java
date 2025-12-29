package sg.account.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String accountId) {
        super("Account not found | accountId=" + accountId);
    }
}
