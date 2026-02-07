package hse.java.lectures.lecture2.task;


public class Const implements IntExpression
{

    private final int value;

    public Const(int value)
    {
        this.value = value;
    }


    @Override
    public int eval()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return Integer.toString(value);
    }


}
