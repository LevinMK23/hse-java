package hse.java.exprtree;

public non-sealed class Operation implements Expression {
    private final Expression l, r;
    private final ExprType type;

    public Operation(Expression l, Expression r, ExprType type) {
        this.l = l;
        this.r = r;
        this.type = type;
    }

//    @Override
//    public int getPriority() {
//        return 0;
//    }

    @Override
    public int eval() {
        return type.operation(l.eval(),r.eval());
    }


}
