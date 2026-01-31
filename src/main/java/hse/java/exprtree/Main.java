package hse.java.exprtree;

import java.beans.Expression;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Operation z = new Operation(new Const(5), new Const(7),ExprType.PRODUCT);
        System.out.println(z.eval());

    }
}