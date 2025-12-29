package sg.account.exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(){
        super("Withdrawal amount is greater than accounts balance");
    }

}
