package com.payment.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //private String paymentMethod;
    private double dueAmount;
    private double amountPaid;
    private String transactionId;
    //    @JsonFormat(pattern = "yyyy-MM-dd")
//    LocalDate checkInDate;
    @Temporal(TemporalType.DATE)
    private Date transactionDate = new Date(System.currentTimeMillis());
    private Date checkinDate;
    //private Date checkOutDate;
    //private int remainder;
    //private double newDuesAmount;
    private boolean onBoard;
    private String paymentPurpose;
    // private double dueDuringOnBoard ;
    // private String occupancyType[] = { "Daily" , "Monthly" , "Regular" };
    private String occupancyType;

    // Fields taking reference from guest-Master Data .
    //    private double securityDeposit = 5000;
    private String guestId; // (f k) from guestId
//    double defaultRent;

    //Getters and setters .

    public int getPaymentId() {
        return id;
    }

    public void setPaymentId(int id) {
        this.id = id;
    }

//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

//    public Date getCheckOutDate() {
//        return checkOutDate;
//    }
//
//    public void setCheckOutDate(Date checkOutDate) {
//        this.checkOutDate = checkOutDate;
//    }
//
//    public int getRemainder() {
//        return remainder;
//    }
//
//    public void setRemainder(int remainder) {
//        this.remainder = remainder;
//    }

//    public double getNewDuesAmount() {
//        return newDuesAmount;
//    }
//
//    public void setNewDuesAmount(double newDuesAmount) {
//        this.newDuesAmount = newDuesAmount;
//    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public String getOccupancyType() {
        return occupancyType;
    }

    public void setOccupancyType(String occupancyType) {
        this.occupancyType = occupancyType;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Payments() {
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", dueAmount=" + dueAmount +
                ", amountPaid=" + amountPaid +
                ", transactionId='" + transactionId + '\'' +
                ", transactionDate=" + transactionDate +
                ", checkinDate=" + checkinDate +
                ", onBoard=" + onBoard +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                ", occupancyType='" + occupancyType + '\'' +
                ", guestId='" + guestId + '\'' +
                '}';
    }

    /*
     * public double getDueAmount() { LocalDate checkInDate = LocalDate.now() ; int
     * remainder = checkInDate.getDayOfMonth();
     *
     * double count=securityDeposit+defaultRent; dueAmount = count;
     *
     *
     * if(remainder <30 && remainder > 1) {
     *
     * dueAmount = (defaultRent/30)*remainder ; } if (onBoard == true ) {
     *
     * newDuesAmount = dueAmount - amountPaid ; }
     *
     */

    /*
     * public double getAmountPaid() { if(occupancyType.toString().equals("Daily"))
     * {
     *
     * amountPaid = (defaultRent/30)* remainder ;
     *
     *
     * }else if (occupancyType.toString().equals("Monthly")) {
     *
     * amountPaid = defaultRent ;
     *
     * }else if (occupancyType.toString().equals("Regular")){
     *
     * amountPaid = defaultRent ; System.out.println("Exception"); } return
     * amountPaid; }
     */

    /*
     * public double getDefaultRent() { if(occupancyType.toString().equals("Daily"))
     * { defaultRent = defaultRent/30;
     *
     *
     * }else if (occupancyType.toString().equals("Monthly")) { defaultRent =
     * amountPaid;
     *
     * }else if (occupancyType.toString().equals("Regular")){ defaultRent =
     * amountPaid; } return defaultRent ; }
     */
}
