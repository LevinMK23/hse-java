package hse.java.lectures.lecture3.tasks.atm;

import java.util.*;

public class Atm {

    private static final List<Integer> ALLOWED_DENOMINATIONS = List.of(5000, 2000, 1000, 500, 200, 100, 50);

    private final Map<Integer, Integer> banknotes = new HashMap<>();

    public Atm() {
    }

    public void deposit(Map<Integer, Integer> deposit) {
        if (deposit == null) {
            throw new InvalidDepositException("Deposit map cannot be null");
        }

        for (Map.Entry<Integer, Integer> entry : deposit.entrySet()) {
            Integer denomination = entry.getKey();
            Integer count = entry.getValue();

            if (denomination == null || !ALLOWED_DENOMINATIONS.contains(denomination)) {
                throw new InvalidDepositException("Invalid denomination: " + denomination);
            }
            if (count == null || count <= 0) {
                throw new InvalidDepositException("Invalid count for denomination " + denomination + ": " + count);
            }
        }

        for (Map.Entry<Integer, Integer> entry : deposit.entrySet()) {
            int denom = entry.getKey();
            int cnt = entry.getValue();
            banknotes.put(denom, banknotes.getOrDefault(denom, 0) + cnt);
        }
    }

    public Map<Integer, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive: " + amount);
        }
        if (amount > getBalance()) {
            throw new InsufficientFundsException("Insufficient funds: requested " + amount + ", balance " + getBalance());
        }

        Map<Integer, Integer> withdrawal = new HashMap<>();
        int remaining = amount;

        for (int denom : ALLOWED_DENOMINATIONS) {
            int available = banknotes.getOrDefault(denom, 0);
            if (available == 0) continue;

            int maxPossible = remaining / denom;
            int used = Math.min(maxPossible, available);
            if (used > 0) {
                withdrawal.put(denom, used);
                remaining -= used * denom;
            }
        }

        if (remaining != 0) {
            throw new CannotDispenseException("Cannot dispense amount " + amount + " with available banknotes");
        }

        for (Map.Entry<Integer, Integer> entry : withdrawal.entrySet()) {
            int denom = entry.getKey();
            int used = entry.getValue();
            banknotes.put(denom, banknotes.get(denom) - used);
        }

        return withdrawal;
    }

    public int getBalance() {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : banknotes.entrySet()) {
            total += entry.getKey() * entry.getValue();
        }
        return total;
    }
}