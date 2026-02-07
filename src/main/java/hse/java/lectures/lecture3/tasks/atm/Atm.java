package hse.java.lectures.lecture3.tasks.atm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

public class Atm {
    private enum Denomination {
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
    }

    private final Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    private int balance;

    public Atm() {
        for (Denomination denom : Denomination.values()) {
            banknotes.put(denom, 0);
        }
        balance = 0;
    }

    public void deposit(Map<Integer, Integer> banknotes){
        for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value > 0 && banknotes.containsKey(key)) {
                banknotes.put(key, banknotes.getOrDefault(key, 0) + value);
                balance += key * value;
            }
            else {
                throw new InvalidAmountException("Передано отрицательное количество купюр или они недопустимого номинала!");
            }
        }
    }

    public Map<Integer, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Нельзя выдать неположительное количество денег!");
        } else if (amount > balance) {
            throw new InsufficientFundsException("Нельзя запросить больше денег чем есть!");
        } else {
            Denomination[] denoms = {
                    Denomination.D5000,
                    Denomination.D1000,
                    Denomination.D500,
                    Denomination.D100,
                    Denomination.D50
            };
            Map<Denomination, Integer> banknotesCopy = new EnumMap<>(banknotes);
            int balanceCopy = balance;

            Map<Integer, Integer> dispenceMap = new TreeMap<>();
            dispenceMap.put(5000, 0);
            dispenceMap.put(1000, 0);
            dispenceMap.put(500, 0);
            dispenceMap.put(100, 0);
            dispenceMap.put(50, 0);

            for (Denomination denom : denoms) {
                int nominal = denom.value();

                if (nominal <= amount) {
                    int available = banknotes.get(denom);

                    while (available > 0 && nominal <= amount) {
                        dispenceMap.put(nominal, dispenceMap.getOrDefault(nominal, 0) + 1);
                        amount -= nominal;
                        available--;

                        banknotes.put(denom, available);
                    }
                }
            }

            if (amount == 0) {
                balance -= amount;
                return dispenceMap;
            }
            else {
                banknotes.clear();
                banknotes.putAll(banknotesCopy);
                balance = balanceCopy;

                throw new CannotDispenseException("Невозможно набрать сумму возможными купюрами!");
            }
        }
    }

    public int getBalance() {
        return balance;
    }
}
