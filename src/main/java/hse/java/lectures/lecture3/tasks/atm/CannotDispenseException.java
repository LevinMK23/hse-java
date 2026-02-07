package hse.java.lectures.lecture3.tasks.atm;

public class CannotDispenseException extends RuntimeException {
    public CannotDispenseException(String message) {
        super(message);
    }
}
