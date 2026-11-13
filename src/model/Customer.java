package model;

public class Customer extends User {
    public Customer(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        super(userId, firstName, lastName, email, passwordHash, status);
    }


    @Override
    public void showRoleMenu() {

    }

    public void selectSeats() {

    }

    public void purchaseTicket() {

    }

    public void cancelRefund() {

    }


}
