package hse.java;

import javax.swing.plaf.nimbus.State;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;

import static java.util.Collections.copy;
import static java.util.Collections.max;

public class Task {
    /**
     * The operator combines all values in the given range into one value
     * using combiner and initial value (seed)
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = new BiFunction<Integer, IntBinaryOperator, IntBinaryOperator>() {
        @Override
        public IntBinaryOperator apply(Integer integer, IntBinaryOperator intBinaryOperator) {
            return (start, end) -> {
                Integer sum = integer;
                for (int x = start; x <= end; x++) {
                    sum = intBinaryOperator.applyAsInt(x, sum);
                }
                return sum;
            };
        }
    };

    /**
     * The operator calculates the sum in the given range (inclusively)
     */
    static IntBinaryOperator multiply = (x, y) -> x * y;
    static IntBinaryOperator sum = (x, y) -> x + y;
    public static final IntBinaryOperator sumOperator = reduceIntOperator.apply(0, sum);

    /**
     * The operator calculates the product in the given range (inclusively)
     */
    public static final IntBinaryOperator productOperator = reduceIntOperator.apply(1, multiply);// write your code here
}
class Transaction {
    enum State {CANCELED;}
    State state;
    public Long getSum() {return new Long(0);}
}
class Account {
    List<Transaction> transactions;
}

class Task2 {
    public static long calcSumOfCanceledTransOnNonEmptyAccounts(List<Account> accounts) {
        OptionalLong vall = accounts.stream()
                .filter((account) -> {return account.balance > 0;})
                .map((account) -> {
                    OptionalLong val = account.transactions.stream().filter((t) -> {return t.state == State.CANCELED;})
                            .map((tr) -> {return tr.getSum();}).mapToLong(Number::longValue).reduce((g, h) -> {return g + h;});
                    return val.isPresent() ? val.getAsLong() : 0;
                })
                .reduce((g, h) -> {return g + h;});
        return vall.isPresent() ? vall.getAsLong() : 0;
    }
}
