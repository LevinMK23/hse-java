package hse.java.exprtree;


public non-sealed class Const implements Expression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

//    @Override
//    public int getPriority() {
//        return 10;
//    }

    @Override
    public int eval() {
        return value;
    }

    @Override
    public String toString(){
        return Integer.toString(value);
    }
}
