package hse.java.lectures.lecture3.tasks.atm;

import java.io.BufferedReader;
import java.util.*;

public class Atm {
    public enum Denomination {
        D50(50),
        D100(100),
        D500(500),
        D1000(1000),
        D5000(5000);

        private final int value;

        Denomination(int value) {
            this.value = value;
        }

        int value() {
            return value;
        }


        @Override
        public String toString() {
            return value + " ₽";  // или просто Integer.toString(value)
        }
    }

    final private Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    public Atm() {
        for (Denomination d : Denomination.values()) {
            banknotes.put(d, 0);
        }
    }

    public void  printBanknotes() {
        for (Denomination i : this.banknotes.keySet()) {
            System.out.println(" nominal : "+ i + " --> count :  " + this.banknotes.get(i));
        }
    }

    public Boolean atmEmpty() {
        Integer[] a = banknotes.values().toArray(new Integer[0]);
        Integer result = 0 ;
        for (Integer el : a) {
            result += el ;
        }
        return result == 0 ;
    }

    public void deposit(Map<Denomination, Integer> banknotes){ // переделать
        if (banknotes == null) {
            throw  new InvalidDepositException("null") ;
        }
        for (Denomination i : banknotes.keySet()) {
            if ( banknotes.get(i) <=  0 ) {
                throw  new InvalidDepositException(" 0 or -1 count banknotes");
            }
            if (!this.banknotes.containsKey(i)) {
                throw new InvalidDepositException("Invalid denomination: " + i);
            }
        }
        for (Denomination i : banknotes.keySet()) {
            this.banknotes.put(i , (this.banknotes.get(i ) + banknotes.get(i)) ) ;
        }

    }


    public Map<Denomination, Integer> withdraw(int amount) {
        Map<Denomination, Integer> result = new EnumMap<>(Denomination.class);
        Denomination[] d = Denomination.values();
        Map<Denomination, Integer> banknotesCopy = new EnumMap<>(banknotes);
//        for (Denomination el : d) {
//            System.out.println(el + " -- " + el.value);
//        }

        if ( amount <= 0 ) {
            throw new InvalidAmountException("0 or -1 ");
        }
        try{
            for ( int i = d.length - 1 ; i >= 0 ; i-- ) {
                if ( amount > this.getBalance()) {
                    throw  new InsufficientFundsException("amount > this.getBalance() ") ;
                }
                System.out.println("d[i].valuse : " + d[i].value);
                if ( d[i].value <= amount) {
                    Integer division = amount / d[i].value;
                    division = Math.min(division , this.banknotes.get(d[i])) ;
//                    System.out.println(this.banknotes.get(d[i]));
                    if ( division > 0  ) {
                        amount -= division * d[i].value;
                        this.banknotes.put(d[i] , this.banknotes.get(d[i] ) - division ) ;
                        result.put(d[i] , division) ;
                    }
                }
                if (amount == 0 ) {
                    break;
                }
            }
            if (amount >  0 ) {
                banknotes.clear();
                banknotes.putAll(banknotesCopy);
                System.out.println("bad amoutmd : " + amount);
                throw new CannotDispenseException("no can ") ;

            }
        }catch (Exception e) {
            banknotes.clear();
            banknotes.putAll(banknotesCopy);
            throw  e;
        }

        return result;

    }

    public int getBalance() {
        Denomination[] d = Denomination.values();
        Integer result = 0 ;
        for (int i = 0; i < d.length; i++) {
            result += this.banknotes.getOrDefault(d[i] , 0 ) * d[i].value;
        }
        return result;

    }

    public static void main(String[] args) {
        try{
            Atm atm = new Atm();
            atm.deposit(Map.of(Denomination.D100 , 100 , Denomination.D500 , 10));
            System.out.println("balance : " + atm.getBalance());
            atm.printBanknotes();

            Map<Denomination , Integer > a = atm.withdraw(100);

            System.out.println(atm.getBalance());
            atm.printBanknotes();

        } catch (Exception e) {
            System.out.println("error is : ");
            e.printStackTrace();
        }

//        Atm test = new Atm();
//        test.deposit(a);
//        test.printBanknotes();

    }
}






