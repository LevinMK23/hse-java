package hse.java;

public class Main {
    public static void main(String[] args) {
        IntExpression expr = new Mul(
            new Sum(new Const(1), new Const(2)),
            new Sub(new Const(5), new Const(3))
        );
        System.out.println(expr.toString());
        System.out.println(expr.eval());
    }
}
