package com.techelevator.tenmo.model;

public class Transfer {

    private int transferId;
    private double transferAmount;
    private String from;
    private String to;

    public Transfer() {
    }

    public Transfer(double transferAmount, String from, String to) {
        this.transferAmount = transferAmount;
        this.from = from;
        this.to = to;

    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}


