package ui.organizer;

import model.Movie;
import model.Screening;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Screen 13 — Manage Movies (820 x 560).
 */
public class ManageMovies {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JButton backBtn;
    private JLabel titleLbl;
    private JButton addMovieBtn;
    private JPanel infoBannerPanel;
    private JLabel infoLbl;
    private JPanel emptyStatePanel;
    private JLabel emptyStateLbl;
    private JScrollPane moviesScroll;
    private JPanel cardsWrapperPanel;
    private JPanel cardsGridPanel;
    private JPanel warningPanel;
    private JLabel warningMsgLbl;
    private JButton deleteAnywayBtn;
    private JButton keepMovieBtn;

    private final OrganizerContext context;
    private Movie pendingDeleteMovie;

    public ManageMovies() {
        this(OrganizerContext.createDemoContext());
    }

    public ManageMovies(OrganizerContext context) {
        this.context = context;
        initComponents();
        bindEvents();
        refreshMovieGrid();
    }

    private void initComponents() {
        mainPanel.setBackground(Theme.BACKGROUND);

        titleLbl.setFont(Theme.TITLE);
        titleLbl.setForeground(Theme.TEXT);

        OrganizerThemeHelper.styleSecondaryButton(backBtn, false);
        OrganizerThemeHelper.stylePrimaryButton(addMovieBtn, false);

        OrganizerThemeHelper.styleBannerPanel(infoBannerPanel, Theme.PRIMARY);
        infoLbl.setFont(Theme.SMALL);
        infoLbl.setForeground(Theme.PRIMARY);

        OrganizerThemeHelper.styleCardPanel(emptyStatePanel);
        emptyStateLbl.setFont(Theme.BODY);
        emptyStateLbl.setForeground(Theme.MUTED);

        moviesScroll.setViewportView(cardsWrapperPanel);
        moviesScroll.setBorder(BorderFactory.createEmptyBorder());
        moviesScroll.getViewport().setBackground(Theme.BACKGROUND);
        moviesScroll.getVerticalScrollBar().setUnitIncrement(16);

        cardsWrapperPanel.setBackground(Theme.BACKGROUND);
        cardsGridPanel.setBackground(Theme.BACKGROUND);
        cardsGridPanel.setLayout(new GridLayout(0, 3, 12, 12));

        OrganizerThemeHelper.styleBannerPanel(warningPanel, Theme.DANGER);
        warningMsgLbl.setFont(Theme.SMALL);
        warningMsgLbl.setForeground(Theme.DANGER);
        OrganizerThemeHelper.styleDangerButton(deleteAnywayBtn, true);
        OrganizerThemeHelper.styleSecondaryButton(keepMovieBtn, true);
        warningPanel.setVisible(false);
    }

    private void bindEvents() {
        backBtn.addActionListener(e -> OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Organizer Dashboard",
                new OrganizerDashboard(context).getMainPanel()
        ));

        addMovieBtn.addActionListener(e -> OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Add Movie",
                new AddEditMovie(context, null).getMainPanel()
        ));

        deleteAnywayBtn.addActionListener(e -> confirmCascadeDelete());
        keepMovieBtn.addActionListener(e -> clearPendingDelete());
    }

    public void refreshMovieGrid() {
        clearPendingDelete();
        cardsGridPanel.removeAll();

        List<Movie> movies = context.getMovieService().getAllMovies();
        boolean hasMovies = !movies.isEmpty();
        emptyStatePanel.setVisible(!hasMovies);
        moviesScroll.setVisible(hasMovies);

        for (Movie movie : movies) {
            MovieCard card = new MovieCard(
                    movie,
                    () -> handleEditMovie(movie),
                    () -> handleDeleteMovie(movie)
            );
            cardsGridPanel.add(card.getMainPanel());
        }

        cardsGridPanel.revalidate();
        cardsGridPanel.repaint();
    }

    private void handleEditMovie(Movie movie) {
        OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Edit Movie",
                new AddEditMovie(context, movie).getMainPanel()
        );
    }

    private void handleDeleteMovie(Movie movie) {
        List<Screening> upcoming = context.getScreeningService().getUpcomingScreeningsByMovie(movie.getMovieId());
        if (!upcoming.isEmpty()) {
            pendingDeleteMovie = movie;
            String nextDate = upcoming.get(0).getFormattedDateTime();
            warningMsgLbl.setText("<html>Delete warning: <b>\"" + OrganizerThemeHelper.escapeHtml(movie.getTitle())
                    + "\"</b> has " + upcoming.size() + " upcoming screening(s) (next: "
                    + OrganizerThemeHelper.escapeHtml(nextDate)
                    + "). Delete the screenings first, or confirm to delete both.</html>");
            warningPanel.setVisible(true);
            mainPanel.revalidate();
            mainPanel.repaint();
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                mainPanel,
                "Delete movie \"" + movie.getTitle() + "\"?",
                "Confirm Delete Movie",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            context.getScreeningService().deleteScreeningsByMovie(movie.getMovieId());
            context.getMovieService().deleteMovie(movie.getMovieId());
            refreshMovieGrid();
        }
    }

    private void confirmCascadeDelete() {
        if (pendingDeleteMovie == null) {
            return;
        }
        String movieId = pendingDeleteMovie.getMovieId();
        context.getScreeningService().deleteScreeningsByMovie(movieId);
        context.getMovieService().deleteMovie(movieId);
        refreshMovieGrid();
    }

    private void clearPendingDelete() {
        pendingDeleteMovie = null;
        warningPanel.setVisible(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
