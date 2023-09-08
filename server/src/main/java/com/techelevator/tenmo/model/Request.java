package com.techelevator.tenmo.model;

public class Request {

    private int requestId;
    private double requestAmount;
    private String requestFrom;
    private String requestTo;


    public Request() {
    }

    public Request(double requestAmount, String requestFrom, String requestTo) {
        this.requestAmount = requestAmount;
        this.requestFrom = requestFrom;
        this.requestTo = requestTo;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }

    }


