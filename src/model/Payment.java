package model;

public class Payment implements Payable {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String method;
    private String status;

    public Payment(String paymentId, String bookingId, double amount, String method, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    @Override
    public boolean processPay() {
        return false;
    }

    @Override
    public boolean refundPay() {
        return false;
    }
}
