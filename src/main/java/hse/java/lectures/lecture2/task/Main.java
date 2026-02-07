package hse.java.lectures.lecture2.task;




public class Main {



    public static void main(String[] args)
    {
        IntExpression expr1 = new Sum(new Sum(new Const(2), new Const(3)), new Const(100));


        System.out.println(expr1.toString() + "=" + expr1.eval());


    }
}