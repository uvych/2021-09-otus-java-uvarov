package atm.service;

import atm.model.UserAccount;

public interface ATMService {
    int getMoney(UserAccount userAccount, int amountOfMoney);
}
