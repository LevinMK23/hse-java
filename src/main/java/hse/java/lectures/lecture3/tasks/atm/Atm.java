package hse.java.lectures.lecture3.tasks.atm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

        static Denomination fromValue(int value) {
            for (Denomination d : values()) {
                if (d.value == value) {
                    return d;
                }
            }
            throw new IllegalArgumentException("No denomination with value " + value);
        }
    }

    private final Map<Denomination, Integer> banknotes = new EnumMap<>(Denomination.class);

    public Atm() {
    }

    public void deposit(Map<Integer, Integer> deposit) {
        if (deposit == null) {
            throw new InvalidDepositException("bebebe");
        }
        for (Map.Entry<Integer, Integer> entry : deposit.entrySet()) {
            Integer denomination_ = entry.getKey();
            if (denomination_ != 50 && denomination_ != 100 && denomination_ != 500 && denomination_ != 1000 && denomination_ != 5000) {
                throw new InvalidDepositException("invalid denomination");
            }
            Integer amount_ = entry.getValue();
            if (amount_ <= 0) {
                throw new InvalidDepositException("invalid amount of banknotes");
            }
        }
        Map<Denomination, Integer> banknotes_ = new EnumMap<>(Denomination.class);
        for (Map.Entry<Integer, Integer> entry : deposit.entrySet()) {
            Integer denomination_1 = entry.getKey();
            Integer amount_ = entry.getValue();
            Denomination denom = Denomination.fromValue(denomination_1);
            banknotes_.put(denom, this.banknotes.getOrDefault(denom, 0) + amount_);
        }
        this.banknotes.putAll(banknotes_);
    }

    public Map<Integer, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "invalid amount"
            );
        }
        if (amount > getBalance()) {
            throw new InsufficientFundsException(
                    "insufficient funds"
            );
        }
        int am_c = amount;
        Map<Integer, Integer> banknotes_to_withdraw = new HashMap<>();
        Map<Denomination, Integer> banknotes_ = new EnumMap<>(Denomination.class);
        int cnt5000 = Integer.min(amount / 5000, banknotes.getOrDefault(Denomination.D5000, 0));
        amount -= 5000 * cnt5000;
        int cnt1000 = Integer.min(amount / 1000, banknotes.getOrDefault(Denomination.D1000, 0));
        amount -= 1000 * cnt1000;
        int cnt500 = Integer.min(amount / 500, banknotes.getOrDefault(Denomination.D500, 0));
        amount -= 500 * cnt500;
        int cnt100 = Integer.min(amount / 100, banknotes.getOrDefault(Denomination.D100, 0));
        amount -= 100 * cnt100;
        int cnt50 = Integer.min(amount / 50, banknotes.getOrDefault(Denomination.D50, 0));
        amount -= 50 * cnt50;
        if (amount > 0) {
            throw new CannotDispenseException("cannot dispense");
        }
        amount = am_c;

        if (cnt5000 > 0)
            banknotes_to_withdraw.put(5000, cnt5000);
        if (cnt1000 > 0)
            banknotes_to_withdraw.put(1000, cnt1000);
        if (cnt500 > 0)
            banknotes_to_withdraw.put(500, cnt500);
        if (cnt100 > 0)
            banknotes_to_withdraw.put(100, cnt100);
        if (cnt50 > 0)
            banknotes_to_withdraw.put(50, cnt50);
        banknotes_.put(Denomination.D5000, this.banknotes.getOrDefault(Denomination.D5000, 0) - cnt5000);

        banknotes_.put(Denomination.D1000, this.banknotes.getOrDefault(Denomination.D1000, 0) - cnt1000);

        banknotes_.put(Denomination.D500, this.banknotes.getOrDefault(Denomination.D500, 0) - cnt500);

        banknotes_.put(Denomination.D100, this.banknotes.getOrDefault(Denomination.D100, 0) - cnt100);

        banknotes_.put(Denomination.D50, this.banknotes.getOrDefault(Denomination.D50, 0) - cnt50);

        this.banknotes.putAll(banknotes_);
        return banknotes_to_withdraw;
    }

    public int getBalance() {
        int ans = 0;
        ans += this.banknotes.getOrDefault(Denomination.D5000, 0) * 5000;
        ans += this.banknotes.getOrDefault(Denomination.D1000, 0) * 1000;
        ans += this.banknotes.getOrDefault(Denomination.D500, 0) * 500;
        ans += this.banknotes.getOrDefault(Denomination.D100, 0) * 100;
        ans += this.banknotes.getOrDefault(Denomination.D50, 0) * 50;
        return ans;
    }

}
