package model;

import java.time.LocalDate;

public class EventOrganizer extends User {
    public EventOrganizer(String userId, String firstName, String lastName, String email, String passwordHash, String status) {
        super(userId, firstName, lastName, email, passwordHash, status);
    }

    public EventOrganizer(String userId, String firstName, String lastName, String email, String passwordHash, LocalDate birthdate, String status) {
        super(userId, firstName, lastName, email, passwordHash, birthdate, status);
    }


    @Override
    public void showRoleMenu() {

    }

    public void createMovie() {

    }

    public void updateMovie() {

    }

    public void deleteMovie() {

    }

    public void createScreening() {

    }

    public void updateScreening() {

    }

    public void deleteScreening() {

    }
}
