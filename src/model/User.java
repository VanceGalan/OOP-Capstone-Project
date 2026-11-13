package model;

public abstract class User implements Authenticatable {
    String userId;
    String firstName;
    String lastName;
    String email;
    String passwordHash;
    //LocalDate birthdate;
    String status;

    public User(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean login() {
        return true; // not final
    }

    public void logout() {

    }

    public abstract void showRoleMenu();
}
