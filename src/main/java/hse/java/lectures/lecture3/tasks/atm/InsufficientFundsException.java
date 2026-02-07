package hse.java.lectures.lecture3.tasks.atm;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
