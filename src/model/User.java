package model;

import java.time.Period;
import java.time.LocalDate;

public abstract class User implements Authenticatable {
    String userId;
    String firstName;
    String lastName;
    String email;
    String passwordHash;
    LocalDate birthdate;
    String status;

    //------------------------Constructor

    public User(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        this(userId, firstName, lastName, email, passwordHash, null, status);
    }

    public User(String userId, String firstName, String lastName, String email, String passwordHash, LocalDate birthdate, String status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.birthdate = birthdate;
        this.status = status;
    }
// ----------------------Getters and Setters

    public String getUserId(){
        return userId;
    }

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

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge(){
        if (birthdate == null){
            return 0;
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    //----------------------------Methods

    public boolean login() {
        return true; // not final
    }

    public void logout() {

    }

    public abstract void showRoleMenu();
}
