package com.techelevator.tenmo.model;

public class Status {

    private int statusId;
    private int transferId;
    private String statusDescription;


    public Status() {
    }

    public Status(int transferId, String statusDescription) {
        this.transferId = transferId;
        this.statusDescription = statusDescription;
    }

    public int isStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
