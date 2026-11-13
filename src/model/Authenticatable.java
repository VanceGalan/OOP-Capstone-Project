package model;

public interface Authenticatable {

    default boolean login() {
        return true; // temporary
    }

    default void logout() {

    }
}
