package model;

import java.time.LocalDate;

public class Customer extends User {
    public Customer(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        super(userId, firstName, lastName, email, passwordHash, status);
    }

    public Customer(String userId, String firstName, String lastName, String email, String passwordHash, LocalDate birthdate, String status) {
        super(userId, firstName, lastName, email, passwordHash, birthdate, status);
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
