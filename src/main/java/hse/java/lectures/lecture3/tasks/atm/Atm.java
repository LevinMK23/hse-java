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
    }

    public void deposit(Map<Denomination, Integer> banknotes) {
        if (banknotes == null){
            throw new InvalidDepositException("null");
        }
        int sum = 0;
        for (var entry : banknotes.entrySet()) {
            if (entry.getValue() < 0) {
                throw new InvalidDepositException("Отрицательное денег");
            }

            sum += entry.getValue();
        }
        if (sum == 0) {
            throw new InvalidDepositException("Ноль денег");
        }
        for (var entry : banknotes.entrySet()) {
            this.banknotes.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("<=0 денег");
        } else if (amount % 50 != 0) {
            throw new CannotDispenseException("Че ты у меня просишь");
        }
        if (amount > getBalance()){
            throw new InsufficientFundsException("нет столько денег");
        }

        Map<Denomination, Integer> result = new EnumMap<>(Denomination.class);
        int remaining = amount;

        Denomination[] denominations = Denomination.values();
        for (int i = denominations.length - 1; i >= 0; i--) {
            Denomination denom = denominations[i];
            int available = banknotes.getOrDefault(denom, 0);
            if (available > 0 && denom.value() <= remaining) {
                int needed = remaining / denom.value();
                int toTake = Math.min(needed, available);

                if (toTake > 0) {
                    result.put(denom, toTake);
                    remaining -= toTake * denom.value();
                }
            }
        }

        if (remaining != 0) {
            throw new CannotDispenseException("Невозможно выдать сумму");
        }

        for (var entry : result.entrySet()) {
            banknotes.merge(entry.getKey(), entry.getValue(),
                    (oldVal, newVal) -> oldVal - newVal);
        }

        return result;
    }

    public int getBalance() {
        int balance = 0;
        for (var entry : banknotes.entrySet()) {
            balance += entry.getKey().value() * entry.getValue();
        }
        return balance;
    }

}
