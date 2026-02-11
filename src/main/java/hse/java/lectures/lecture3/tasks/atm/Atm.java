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
        for (Denomination d : Denomination.values()) {
            banknotes.put(d, 0);
        }
    }

    public void deposit(Map<Denomination, Integer> banknotes){
        if (banknotes == null) {
            throw new InvalidDepositException("передано отрицательное или нулевое количество купюр");
        }

        for (Map.Entry<Denomination, Integer> entry : banknotes.entrySet()){
            if (entry.getValue() <= 0) {
                throw new InvalidDepositException("передано отрицательное или нулевое количество купюр");
            }
        }

        for (Map.Entry<Denomination, Integer> entry : banknotes.entrySet()){
            this.banknotes.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("amount <= 0");
        }
        if (amount > getBalance()) {
            throw new InsufficientFundsException("amount > getBalance()");
        }

        Map<Denomination, Integer> withdraw = new HashMap<>();

        Denomination[] values = Denomination.values();
        for (int i = values.length - 1; i >= 0; i--) {
            Denomination denom = values[i];
            int available = banknotes.get(denom);
            int value = denom.value();

            int need = amount / value;
            int used = Math.min(need, available);

            if (used > 0) {
                withdraw.put(denom, used);
                amount -= used * value;
            }
        }

        if (amount != 0) {
            throw new CannotDispenseException("сумма не набирается доступными купюрами");
        }

        for (Map.Entry<Denomination, Integer> e : withdraw.entrySet()) {
            Denomination denom = e.getKey();
            banknotes.put(denom, banknotes.get(denom) - e.getValue());
        }

        return withdraw;
    }


    public int getBalance() {
        int sum = 0;
        for (Map.Entry<Denomination, Integer> entry : banknotes.entrySet()){
            sum += entry.getKey().value() * entry.getValue();
        }
        return sum;
    }
}
