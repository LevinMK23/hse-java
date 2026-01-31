package hse.java.exprtree;

import java.util.function.BiFunction;

public enum ExprType {
    SUM(Integer::sum),
    PRODUCT((Integer x, Integer y) -> x * y);
    private final BiFunction<Integer, Integer, Integer> operation;

    ExprType(BiFunction<Integer, Integer, Integer> operation) {
        this.operation = operation;
    }

    public int operation(int x, int y) {
        return operation.apply(x, y);
    }
}
