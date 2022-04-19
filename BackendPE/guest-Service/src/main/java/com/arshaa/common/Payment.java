package com.arshaa.common;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Payment {

    private double amountPaid;
    private String guestId;
    private String transactionId;
    private String occupancyType;
    private Date transactionDate;
    private double dueAmount;
    private Date checkinDate;
    private String paymentPurpose;

    public Payment() {
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Payment(double amountPaid, String guestId, String transactionId, String occupancyType, Date transactionDate, double dueAmount, Date checkinDate, String paymentPurpose) {
        this.amountPaid = amountPaid;
        this.guestId = guestId;
        this.transactionId = transactionId;
        this.occupancyType = occupancyType;
        this.transactionDate = transactionDate;
        this.dueAmount = dueAmount;
        this.checkinDate = checkinDate;
        this.paymentPurpose = paymentPurpose;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amountPaid=" + amountPaid +
                ", guestId='" + guestId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", occupancyType='" + occupancyType + '\'' +
                ", transactionDate=" + transactionDate +
                ", dueAmount=" + dueAmount +
                ", checkinDate=" + checkinDate +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                '}';
    }
}
