package com.bdd.task1;

public class ATM {
    public double cashDispensed;

    public void dispenseCash(double amount) {
        cashDispensed = amount;
        System.out.println("Dispensing $" + amount);
    }

    public double getCashDispensed() {
        return cashDispensed;
    }
}
