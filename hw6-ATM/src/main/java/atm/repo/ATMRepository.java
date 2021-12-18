package atm.repo;

import atm.model.Denomination;

import java.util.HashMap;
import java.util.Map;

public class ATMRepository {
    private int balance;
    private static Map<Denomination, Integer> banknoteStorage;

    private static ATMRepository instance;

    private ATMRepository(){}

    public static ATMRepository getInstance(){
        if(instance == null) {
            banknoteStorage = new HashMap<>() {{
                put(Denomination.FIFTY, 0);
                put(Denomination.FIVE_HUNDRED, 0);
                put(Denomination.FIVE_THOUSAND, 0);
                put(Denomination.ONE_HUNDRED, 0);
                put(Denomination.ONE_THOUSAND, 0);
            }};
            instance = new ATMRepository();
        }
        return instance;
    }

    public int getBalance() {
        return this.balance;
    }

    public int depositATM(Map<Denomination, Integer> banknotes) {
        banknoteStorage.forEach((k, v) -> banknotes.merge(k, v, Integer::sum));
        countTheBalance();
        return balance;
    }

    public int getMoney(int amountOfMoney) {
        int tempAmount = amountOfMoney / Denomination.FIVE_THOUSAND.ordinal();
        int countDenomination = banknoteStorage.get(Denomination.FIVE_THOUSAND);
        if (tempAmount > 0
            && countDenomination > 0) {

        }
        return 0;
    }

    private void countTheBalance() {
        this.balance = banknoteStorage.keySet().stream()
            .mapToInt(denomination -> banknoteStorage.get(denomination) * denomination.ordinal())
            .sum();
    }
}
