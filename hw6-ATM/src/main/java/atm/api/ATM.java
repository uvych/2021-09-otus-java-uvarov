package atm.api;

import atm.model.UserAccount;
import atm.service.ATMServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ATM {

    private UserAccount userAccount;
    private static final ATMServiceImpl atmService = ATMServiceImpl.getInstance();

    public int getMoney(int amountOfMoney) {
        return 0;
    }
}
