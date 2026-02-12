package hse.java.lectures.lecture3.tasks.atm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Arrays;

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

        public static Denomination fromInt(int value) {
            return Arrays.stream(values()).filter(v -> v.value == value)
                    .findFirst()
                    .orElse(null);
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

    public void deposit(Map<Denomination, Integer> depositBanknotes){
        if (depositBanknotes == null) {
            throw new InvalidDepositException("Карта банкнот не может быть null");
        }

        for (Map.Entry<Denomination, Integer> entry : depositBanknotes.entrySet()) {
            Denomination denom = entry.getKey();
            Integer count = entry.getValue();

            if (count > 0 && banknotes.containsKey(denom)) {
                banknotes.put(denom, banknotes.getOrDefault(denom, 0) + count);
                balance += denom.value() * count;
            }
            else {
                throw new InvalidDepositException("Передано отрицательное количество купюр или они недопустимого номинала!");
            }
        }
    }

    public Map<Denomination, Integer> withdraw(int amount) {
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

            Map<Denomination, Integer> dispenceMap = new TreeMap<>();

            for (Denomination denom : denoms) {
                int nominal = denom.value();

                if (nominal <= amount) {
                    int available = banknotes.get(denom);

                    while (available > 0 && nominal <= amount) {
                        dispenceMap.put(denom, dispenceMap.getOrDefault(denom, 0) + 1);
                        amount -= nominal;
                        balance -= nominal;
                        available--;

                        banknotes.put(denom, available);
                    }
                }
            }

            if (amount == 0) {
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
