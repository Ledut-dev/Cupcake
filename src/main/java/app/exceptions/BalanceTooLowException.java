package app.exceptions;

public class BalanceTooLowException extends RuntimeException {
    public BalanceTooLowException(String message) {
        super(message);
    }
}
