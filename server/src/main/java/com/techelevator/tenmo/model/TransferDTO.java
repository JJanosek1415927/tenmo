package com.techelevator.tenmo.model;

public class TransferDTO {


    private double transferAmount;
    private String to;


    public TransferDTO(double transferAmount, String to) {
        this.transferAmount = transferAmount;
        this.to = to;
    }

    public TransferDTO() {
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
