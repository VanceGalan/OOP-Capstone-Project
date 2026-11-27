package model;

import java.time.LocalDate;

public class Admin extends User {
    public Admin(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        super(userId, firstName, lastName, email, passwordHash, status);
    }

    public Admin(String userId, String firstName, String lastName, String email, String passwordHash, LocalDate birthdate, String status) {
        super(userId, firstName, lastName, email, passwordHash, birthdate, status);
    }

    @Override
    public void showRoleMenu() {

    }

    public boolean approveScreening() {
        return true; // not final
    }

    public boolean rejectScreening() {
        return true; // not final
    }

    public boolean processRefund() {
        return true; // not final
    }

    public void manageUser() {

    }

    public void resetPassword() {

    }


}
