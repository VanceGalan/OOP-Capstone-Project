package service;

import model.Admin;
import model.Customer;
import model.EventOrganizer;
import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static final Path USERS_FILE = Paths.get("data", "users.txt");
    private static User currentUser = null;

    /**
     * Registers a new Customer account and saves it to data/users.txt.
     * Throws IllegalArgumentException if the email is already taken.
     */
    public static Customer register(String firstName, String lastName, String email, String plainPassword, LocalDate birthdate) {
        List<User> users = loadUsers();
        String cleanEmail = email.trim();

        for (User u : users) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(cleanEmail)) {
                throw new IllegalArgumentException("An account with this email already exists.");
            }
        }

        String userId = generateNextUserId(users);
        String passwordHash = hashPassword(plainPassword);
        Customer newCustomer = new Customer(
                userId,
                firstName.trim(),
                lastName.trim(),
                cleanEmail,
                passwordHash,
                birthdate,
                "Active"
        );

        users.add(newCustomer);
        saveUsers(users);
        return newCustomer;
    }

    /**
     * Validates email + password against data/users.txt.
     * Only registered, Active accounts can log in.
     * Throws IllegalArgumentException with a user-friendly message if login fails.
     */
    public static User login(String email, String plainPassword) {
        List<User> users = loadUsers();
        String cleanEmail = email.trim();
        String enteredHash = hashPassword(plainPassword);

        for (User u : users) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(cleanEmail)) {
                if (!enteredHash.equals(u.getPasswordHash())) {
                    throw new IllegalArgumentException("Invalid email or password.");
                }
                if (!"Active".equalsIgnoreCase(u.getStatus())) {
                    throw new IllegalArgumentException("This account is deactivated. Contact an admin.");
                }
                currentUser = u;
                return u;
            }
        }

        throw new IllegalArgumentException("Invalid email or password.");
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }

    public static List<User> loadUsers() {
        ensureSeededFile();
        List<User> users = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(USERS_FILE, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|", -1);
                if (parts.length < 8) {
                    continue;
                }
                String userId = parts[0];
                String role = parts[1];
                String firstName = parts[2];
                String lastName = parts[3];
                String email = parts[4];
                String passwordHash = parts[5];
                LocalDate birthdate = parts[6].isBlank() || "null".equals(parts[6])
                        ? null
                        : LocalDate.parse(parts[6]);
                String status = parts[7];

                User user = switch (role) {
                    case "Admin" -> new Admin(userId, firstName, lastName, email, passwordHash, birthdate, status);
                    case "EventOrganizer" -> new EventOrganizer(userId, firstName, lastName, email, passwordHash, birthdate, status);
                    default -> new Customer(userId, firstName, lastName, email, passwordHash, birthdate, status);
                };
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read users file: " + USERS_FILE, e);
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        try {
            if (USERS_FILE.getParent() != null) {
                Files.createDirectories(USERS_FILE.getParent());
            }
            List<String> lines = new ArrayList<>();
            for (User u : users) {
                String role = u.getClass().getSimpleName(); // Customer, EventOrganizer, or Admin
                String birthStr = u.getBirthdate() == null ? "null" : u.getBirthdate().toString();
                lines.add(String.join("|",
                        safe(u.getUserId()),
                        role,
                        safe(u.getFirstName()),
                        safe(u.getLastName()),
                        safe(u.getEmail()),
                        safe(u.getPasswordHash()),
                        birthStr,
                        safe(u.getStatus())
                ));
            }
            Files.write(USERS_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users file: " + USERS_FILE, e);
        }
    }

    /** Creates data/users.txt with 1 default Admin and 1 default Organizer if missing/empty. */
    private static void ensureSeededFile() {
        try {
            if (Files.exists(USERS_FILE) && Files.size(USERS_FILE) > 0) {
                return;
            }
            List<User> seedUsers = new ArrayList<>();
            seedUsers.add(new Admin(
                    "U-001",
                    "Jup",
                    "Admin",
                    "admin@cinema.com",
                    hashPassword("admin1234"),
                    LocalDate.of(1999, 1, 18),
                    "Active"
            ));
            seedUsers.add(new EventOrganizer(
                    "U-002",
                    "Lance",
                    "Organizer",
                    "organizer@cinema.com",
                    hashPassword("organizer1234"),
                    LocalDate.of(1998, 6, 22),
                    "Active"
            ));
            saveUsers(seedUsers);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize users file.", e);
        }
    }

    private static String generateNextUserId(List<User> users) {
        int max = 0;
        for (User u : users) {
            String id = u.getUserId();
            if (id != null && id.startsWith("U-")) {
                try {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("U-%03d", max + 1);
    }

    public static String hashPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }

    /** Strips '|' so user input can never break the pipe-separated file format. */
    private static String safe(String value) {
        return value == null ? "" : value.replace("|", "");
    }
}
