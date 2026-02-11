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

    public void deposit(Map<Denomination, Integer> banknotes){
        if (banknotes == null) throw new InvalidDepositException("banknotes map is empty");
        if (banknotes.isEmpty()) return;

        Map<Denomination, Integer> deposit_plan = new EnumMap<>(Denomination.class);

        for (Map.Entry<Denomination, Integer> entry : banknotes.entrySet()) {
            Denomination d = entry.getKey();
            Integer count_object = entry.getValue();

            if (d == null) throw new InvalidDepositException("denomination is null");
            if (count_object == null) throw new InvalidDepositException("missing count for " + d);

            int count = count_object;
            if (count <= 0) throw new InvalidDepositException("count must be positive for " + d + ": " + count);

            deposit_plan.put(d, deposit_plan.getOrDefault(d, 0) + count);
        }

        for (Map.Entry<Denomination, Integer> entry : deposit_plan.entrySet()) {
            Denomination d = entry.getKey();
            int add_count = entry.getValue();
            this.banknotes.put(d, this.banknotes.getOrDefault(d, 0) + add_count);
        }
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        if (amount <= 0) throw new InvalidAmountException("amount must be positive: " + amount);

        int balance = getBalance();
        if (amount > balance) throw new InsufficientFundsException("insufficient funds: " + amount + " > " + balance);

        int remaining = amount;
        Map<Denomination, Integer> withdraw_plan = new EnumMap<>(Denomination.class);
        Denomination[] denominations = Denomination.values();

        for (int i = denominations.length - 1; i >= 0 && remaining > 0; i--) {
            Denomination d = denominations[i];
            int available = this.banknotes.getOrDefault(d, 0);

            if (available == 0) continue;

            int take = remaining / d.value();
            if (take > available) take = available;
            if (take == 0) continue;

            withdraw_plan.put(d, take);
            remaining -= take * d.value();
        }

        if (remaining != 0) throw new CannotDispenseException("can't dispense amount: " + amount);

        for (Map.Entry<Denomination, Integer> entry : withdraw_plan.entrySet()) {
            Denomination d = entry.getKey();
            int left = this.banknotes.getOrDefault(d, 0) - entry.getValue();

            if (left == 0) this.banknotes.remove(d);
            else this.banknotes.put(d, left);
        }

        return withdraw_plan;
    }

    public int getBalance() {
        int sum = 0;
        for (Map.Entry<Denomination, Integer> entry : banknotes.entrySet()) {
            sum += entry.getKey().value() * entry.getValue();
        }
        return sum;
    }

}
