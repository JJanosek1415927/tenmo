package com.techelevator.tenmo.model;

public class AccountDTO {

    private String username;
    private double balance;

    public AccountDTO(String username) {
        this.username = username;
    }

    public AccountDTO(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public AccountDTO(){
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username=" + username +
                ", balance=" + balance +
                '}';
    }
}
