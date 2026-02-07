package hse.java.lectures.lecture3.tasks.atm;

public class InvalidDepositException extends RuntimeException {
    public InvalidDepositException(String message) {
        super(message);
    }
}
