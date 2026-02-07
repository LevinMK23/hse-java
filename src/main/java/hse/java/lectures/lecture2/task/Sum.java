package hse.java.lectures.lecture2.task;



public class Sum extends Operation
{

    protected Sum(IntExpression left, IntExpression right)
    {
        super(left, right, "+");
    }

    @Override
    public int eval()
    {
        return left.eval() + right.eval();
    }
}
