package hse.java.exprtree;

public sealed interface Expression permits Operation, Const {
    String toString();
//    int getPriority();
    int eval();
}
