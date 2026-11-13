package model;

public abstract class User implements Authenticatable {
    String userId;
    String firstName;
    String lastName;
    String email;
    String passwordHash;
    //LocalDate birthdate;
    String status;

    //------------------------Constructor

    public User(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
    }
// ----------------------Getters and Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    //----------------------------Methods

    public boolean login() {
        return true; // not final
    }

    public void logout() {

    }

    public abstract void showRoleMenu();
}
