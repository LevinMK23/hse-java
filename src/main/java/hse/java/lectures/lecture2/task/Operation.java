package hse.java.lectures.lecture2.task;


public abstract class Operation implements IntExpression
{

    protected final IntExpression left;
    protected final IntExpression right;
    private final String operationSymbol;

    protected Operation(IntExpression left, IntExpression right, String operationSymbol)
    {
        this.left = left;
        this.right = right;
        this.operationSymbol = operationSymbol;
    }


    @Override
    public String toString()
    {
        return "(" + left.toString() + operationSymbol + right.toString() + ")";
    }
}
