package hse.java.lectures.lecture3.tasks.atm;

import java.util.*;

public class Atm {
    public enum Denomination {
        D5000(5000),
        D1000(1000),
        D500(500),
        D100(100),
        D50(50);

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

    private final Map<Denomination, Integer> atm_banknotes = new EnumMap<>(Denomination.class);

    public Atm() {
        for (Denomination d : Denomination.values()) {
            atm_banknotes.put(d, 0);
        }
    }

    public void deposit(Map<Denomination, Integer> banknotes) {
        if (banknotes == null) {
            throw new InvalidDepositException("Banknotes can't be null");
        }

        int bills_number = 0;
        for (Denomination key : banknotes.keySet()) {
            if (atm_banknotes.containsKey(key)) {
                int tmp = banknotes.get(key);
                if (tmp > 0) {
                    bills_number += tmp;
                } else {
                    throw new InvalidDepositException("Negative number of bills");
                }
            } else {
                throw new InvalidDepositException("Denomination not exists");
            }
        }
        if (bills_number == 0) {
            throw new InvalidDepositException("MALO BABLA");
        }
        for (Denomination key : banknotes.keySet()) {
            atm_banknotes.put(key, atm_banknotes.get(key) + banknotes.get(key));
        }

    }

    public Map<Denomination, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Negative withdrawal amount");
        }
        if (amount > this.getBalance()) {
            throw new InsufficientFundsException("The amount is too large");
        }

        Map<Denomination, Integer> cash = new EnumMap<>(Denomination.class);

        for (Denomination bill_value : atm_banknotes.keySet()) {
            while (amount - bill_value.value() >= 0) {
                if (atm_banknotes.get(bill_value) - cash.getOrDefault(bill_value, 0) > 0) {
                    amount -= bill_value.value();
                    cash.merge(bill_value, 1, Integer::sum);
                } else {
                    break;
                }
            }
        }

        if (amount > 0) {
            throw new CannotDispenseException("Impossible to collect amount");
        }
        for (Map.Entry<Denomination, Integer> entry : cash.entrySet()) {
            atm_banknotes.merge(entry.getKey(), -entry.getValue(), Integer::sum);
        }

        return cash;
    }

    public int getBalance() {
        int Balance = 0;
        for (Map.Entry<Denomination, Integer> entry : atm_banknotes.entrySet()) {
            Balance += entry.getKey().value() * entry.getValue();
        }
        return Balance;
    }
}