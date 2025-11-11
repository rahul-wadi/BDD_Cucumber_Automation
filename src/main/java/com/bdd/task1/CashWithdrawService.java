package com.bdd.task1;

public class CashWithdrawService {
    private final ATM atm;
    private final Account account;

    public CashWithdrawService(Account account, ATM atm) {
        this.account = account;
        this.atm = atm;
    }

    public void withdraw(double amount) {
        account.withdraw(amount);
        atm.dispenseCash(amount);
    }
}
