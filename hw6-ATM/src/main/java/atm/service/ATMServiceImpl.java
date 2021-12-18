package atm.service;

import atm.exception.BalanceException;
import atm.model.*;
import atm.repo.ATMRepository;
import lombok.extern.java.Log;

import java.util.*;


@Log
public class ATMServiceImpl implements ATMService {

    private static final ATMRepository atmAccount = ATMRepository.getInstance();

    private static ATMServiceImpl instance;

    private ATMServiceImpl(){}

    public static ATMServiceImpl getInstance(){
        if(instance == null) {
            instance = new ATMServiceImpl();
        }
        return instance;
    }

    public int getMoney(UserAccount userAccount, int amountOfMoney) {
        var userBalance = userAccount.getBalance();
        var atmBalance = atmAccount.getBalance();

        try {
            checkUserBalance(userBalance, amountOfMoney);
            checkATMBalance(atmBalance, amountOfMoney);
           /* atmAccount.setATMBalance(atmBalance - amountOfMoney);*/
            userAccount.setBalance(userBalance - amountOfMoney);
            return amountOfMoney;
        } catch (BalanceException ex) {
            log.warning(ex.getMessage());
        }

        return 0;
    }

  /*  public int depositMoney(Map<Denomination, Integer> money) {
        atmAccount.depositATM(money);

    }*/

    private void checkUserBalance(int userBalance, int amountOfMoney) {
        if (userBalance < amountOfMoney) throw new BalanceException("Insufficient funds");
    }

    private void checkATMBalance(int atmBalance, int amountOfMoney) {
        if (atmBalance < amountOfMoney) throw new BalanceException("Insufficient funds in ATM");
    }
}
