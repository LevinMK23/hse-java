package hse.java.lectures.lecture3.tasks.atm;

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

        static Denomination fromInt(int v) throws InvalidDepositException{
            for (Denomination d : values()) {
                if (d.value == v) {
                    return d;
                }
            }
            throw new InvalidDepositException("Invalid nominal value");
        }

        int value() {
            return value;
        }

        public static Denomination fromInt(int value) {
            return Arrays.stream(values()).filter(v -> v.value == value)
                    .findFirst()
                    .orElse(null);
        }
    }

    private final Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    public Atm() {
        for(Denomination val : banknotes.keySet()) {
            banknotes.put(val, 0);
        }
    }

    public void deposit(Map<Integer, Integer> banknotes_) {
        try {
            if (banknotes_.isEmpty()) {
                throw new InvalidDepositException("There is no money");
            }
            for (Map.Entry<Integer, Integer> entry : banknotes_.entrySet()) {
                Denomination val = Denomination.fromInt(entry.getKey());
                int oldValue = banknotes.get(val);
                int amountOfNominal = entry.getValue();
                if (amountOfNominal < 0) {
                    throw new InvalidDepositException("Negative amount of " + entry.getKey().toString());
                }
                int newValue = oldValue + amountOfNominal;
                banknotes.replace(val, oldValue, newValue);
            }
        }
        catch (InvalidDepositException e) {
            System.err.println(e.getMessage());
        }
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        Map<Denomination, Integer> mapka = new EnumMap<>(Denomination.class);
        try {
            if (amount <= 0) {
                throw new InvalidAmountException("Amount is non positive");
            }
            if (amount > getBalance()) {
                throw new InsufficientFundsException("Amount > atm balance");
            }
            for(int val : new int[]{5000, 1000, 500, 100, 50}) {
                while (amount >= val) {
                    int oldValue = mapka.get(Denomination.fromInt(val));
                    mapka.replace(Denomination.fromInt(val), oldValue, oldValue + 1);
                    amount -= val;
                }
            }
            if (amount != 0) {
                throw new CannotDispenseException("Can`t dicpense");
            }
            return mapka;
        } catch (InvalidAmountException e) {
            System.err.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        }
        return mapka;
    }

    public int getBalance() {
        int sum = 0;
        for(Map.Entry<Denomination, Integer> entry : banknotes.entrySet()) {
            sum += entry.getKey().value() * entry.getValue();
        }
        return sum;
    }

}
