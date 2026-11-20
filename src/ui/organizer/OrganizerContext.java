package ui.organizer;

import model.EventOrganizer;
import model.Movie;
import model.Screening;
import service.MovieService;
import service.ScreeningService;
import ui.auth.Login;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Shared dependency and navigation context passed across Organizer screens
 * so UI forms stay decoupled from global singletons and login/main entrypoints.
 */
public class OrganizerContext {
    private final MovieService movieService;
    private final ScreeningService screeningService;
    private final String organizerId;
    private final EventOrganizer organizer;
    private final Runnable onLogout;

    public OrganizerContext(MovieService movieService,
                            ScreeningService screeningService,
                            String organizerId,
                            EventOrganizer organizer,
                            Runnable onLogout) {
        if (movieService == null || screeningService == null) {
            throw new IllegalArgumentException("MovieService and ScreeningService are required.");
        }
        this.movieService = movieService;
        this.screeningService = screeningService;
        this.organizerId = (organizerId != null && !organizerId.trim().isEmpty()) ? organizerId.trim() : "ORG-001";
        this.organizer = organizer;
        this.onLogout = onLogout;
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public ScreeningService getScreeningService() {
        return screeningService;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public String getOrganizerFirstName() {
        if (organizer != null && organizer.getFirstName() != null && !organizer.getFirstName().trim().isEmpty()) {
            return organizer.getFirstName().trim();
        }
        return "Organizer";
    }

    public void logout(JPanel currentPanel) {
        if (onLogout != null) {
            onLogout.run();
            return;
        }
        navigateTo(currentPanel, "Movie Ticket Booking System - Login", new Login().getMainPanel());
    }

    public static void navigateTo(JPanel currentPanel, String frameTitle, JPanel nextPanel) {
        if (currentPanel == null || nextPanel == null) {
            return;
        }
        Window ancestor = SwingUtilities.getWindowAncestor(currentPanel);
        if (ancestor instanceof JFrame frame) {
            frame.setTitle(frameTitle);
            frame.setContentPane(nextPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        }
    }

    /**
     * Creates a ready-to-preview context with realistic in-memory sample data
     * for standalone testing without touching shared Main.java or auth screens.
     */
    public static OrganizerContext createDemoContext() {
        MovieService movieService = new MovieService();
        ScreeningService screeningService = new ScreeningService();
        String demoOrganizerId = "ORG-001";
        EventOrganizer demoOrganizer = new EventOrganizer(
                demoOrganizerId, "Lance", "Organizer", "lance@example.com", "demo", "Active"
        );

        Movie dune = new Movie(
                "MOV-001",
                "Dune: Part Two",
                "Sci-Fi",
                166,
                "PG-13",
                "Paul Atreides unites with the Fremen across Arrakis to face House Harkonnen.",
                ""
        );
        Movie river = new Movie(
                "MOV-002",
                "River Festival",
                "Drama",
                110,
                "PG",
                "A small riverside town prepares its annual lantern celebration amid quiet secrets.",
                ""
        );
        Movie manila = new Movie(
                "MOV-003",
                "Old Manila",
                "Drama",
                124,
                "R-13",
                "An archivist uncovers letters hidden inside a restored Intramuros theater.",
                ""
        );
        movieService.addMovie(dune);
        movieService.addMovie(river);
        movieService.addMovie(manila);

        LocalDate baseDate = LocalDate.now().plusDays(3);
        screeningService.seedScreening(new Screening(
                "SCR-001",
                dune.getMovieId(),
                LocalDateTime.of(baseDate, LocalTime.of(19, 0)),
                "Hall 1",
                320.00,
                ScreeningService.STATUS_APPROVED,
                demoOrganizerId,
                null
        ));
        screeningService.seedScreening(new Screening(
                "SCR-002",
                dune.getMovieId(),
                LocalDateTime.of(baseDate.plusDays(1), LocalTime.of(21, 30)),
                "Hall 2",
                350.00,
                ScreeningService.STATUS_PENDING,
                demoOrganizerId,
                null
        ));
        screeningService.seedScreening(new Screening(
                "SCR-003",
                river.getMovieId(),
                LocalDateTime.of(baseDate.plusDays(2), LocalTime.of(16, 30)),
                "Hall 2",
                280.00,
                ScreeningService.STATUS_PENDING,
                demoOrganizerId,
                null
        ));
        screeningService.seedScreening(new Screening(
                "SCR-004",
                manila.getMovieId(),
                LocalDateTime.of(baseDate.plusDays(2), LocalTime.of(19, 0)),
                "Hall 3",
                260.00,
                ScreeningService.STATUS_REJECTED,
                demoOrganizerId,
                "Hall 3 has scheduled maintenance at 7:00 PM. Please move to Hall 1 or another time."
        ));

        return new OrganizerContext(movieService, screeningService, demoOrganizerId, demoOrganizer, null);
    }
}
