package hse.java.lectures.lecture3.tasks.atm;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
