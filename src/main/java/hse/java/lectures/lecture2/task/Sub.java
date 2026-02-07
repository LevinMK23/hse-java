package hse.java.lectures.lecture2.task;

public class Sub extends Operation
{
    protected Sub(IntExpression left, IntExpression right)
    {
        super(left, right, "-");
    }

    @Override
    public int eval()
    {
        return left.eval() - right.eval();
    }
}
