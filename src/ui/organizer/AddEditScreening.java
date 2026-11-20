package ui.organizer;

import model.Movie;
import model.Screening;
import service.ScreeningService;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Screen 16 — Add / Edit Screening (600 x 520).
 * Supports creating a screening, editing a Pending screening,
 * fixing & resubmitting a Rejected screening, or viewing an Approved screening read-only.
 */
public class AddEditScreening {
    private static final String[] HALL_OPTIONS = {"Hall 1", "Hall 2", "Hall 3"};
    private static final String[] TIME_OPTIONS = {
            "10:00 AM", "12:30 PM", "2:00 PM", "4:30 PM", "7:00 PM", "9:30 PM", "11:00 PM"
    };
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    private static final DateTimeFormatter PREVIEW_FORMAT = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy • h:mm a", Locale.ENGLISH);

    private JPanel mainPanel;
    private JPanel formCardPanel;
    private JLabel screenTitleLbl;
    private JLabel subtitleLbl;
    private JPanel rejectionBannerPanel;
    private JLabel rejectionReasonLbl;
    private JPanel fieldsGridPanel;
    private JLabel movieLbl;
    private JLabel hallLbl;
    private JComboBox<MovieOption> movieCombo;
    private JComboBox<String> cinemaHallCombo;
    private JLabel dateLbl;
    private JLabel timeLbl;
    private JSpinner showDatePicker;
    private JComboBox<String> timeCombo;
    private JLabel priceLbl;
    private JLabel previewTitleLbl;
    private JTextField priceField;
    private JLabel formattedPreviewLbl;
    private JLabel errorMsgLbl;
    private JPanel actionsPanel;
    private JButton cancelBtn;
    private JButton saveScreeningBtn;

    private final OrganizerContext context;
    private final Screening screeningToEdit;
    private final boolean viewOnly;

    public AddEditScreening() {
        this(OrganizerContext.createDemoContext(), null, false);
    }

    public AddEditScreening(OrganizerContext context, Screening screeningToEdit, boolean viewOnly) {
        this.context = context;
        this.screeningToEdit = screeningToEdit;
        this.viewOnly = viewOnly;
        initComponents();
        populateForm();
        bindEvents();
        updateDateTimePreview();
    }

    private void initComponents() {
        mainPanel.setBackground(Theme.BACKGROUND);
        OrganizerThemeHelper.styleCardPanel(formCardPanel);

        screenTitleLbl.setFont(Theme.TITLE);
        screenTitleLbl.setForeground(Theme.TEXT);
        if (viewOnly) {
            screenTitleLbl.setText("View Approved Screening");
        } else if (screeningToEdit != null && ScreeningService.STATUS_REJECTED.equalsIgnoreCase(screeningToEdit.getStatus())) {
            screenTitleLbl.setText("Fix & Resubmit Screening");
        } else if (screeningToEdit != null) {
            screenTitleLbl.setText("Edit Screening");
        } else {
            screenTitleLbl.setText("Add Screening");
        }

        subtitleLbl.setFont(Theme.BODY);
        subtitleLbl.setForeground(Theme.MUTED);
        if (viewOnly) {
            subtitleLbl.setText("Approved screenings are live for customers and cannot be edited.");
        }

        OrganizerThemeHelper.styleBannerPanel(rejectionBannerPanel, Theme.DANGER);
        rejectionReasonLbl.setFont(Theme.SMALL);
        rejectionReasonLbl.setForeground(Theme.DANGER);
        rejectionBannerPanel.setVisible(false);

        for (JLabel label : new JLabel[]{movieLbl, hallLbl, dateLbl, timeLbl, priceLbl, previewTitleLbl}) {
            label.setFont(Theme.BODY);
            label.setForeground(Theme.TEXT);
        }

        OrganizerThemeHelper.styleInput(movieCombo, null);
        OrganizerThemeHelper.styleInput(cinemaHallCombo, null);
        OrganizerThemeHelper.styleInput(showDatePicker, null);
        OrganizerThemeHelper.styleInput(timeCombo, null);
        OrganizerThemeHelper.styleInput(priceField, "e.g., 320.00");

        formattedPreviewLbl.setFont(Theme.BODY.deriveFont(Font.BOLD));
        formattedPreviewLbl.setForeground(Theme.PRIMARY);

        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        showDatePicker.setModel(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(showDatePicker, "EEE, MMM d, yyyy");
        showDatePicker.setEditor(dateEditor);

        for (String hall : HALL_OPTIONS) {
            cinemaHallCombo.addItem(hall);
        }
        for (String time : TIME_OPTIONS) {
            timeCombo.addItem(time);
        }

        errorMsgLbl.setFont(Theme.SMALL);
        errorMsgLbl.setForeground(Theme.DANGER);
        errorMsgLbl.setText(" ");

        OrganizerThemeHelper.styleSecondaryButton(cancelBtn, false);
        OrganizerThemeHelper.stylePrimaryButton(saveScreeningBtn, false);

        if (viewOnly) {
            cancelBtn.setText("Back");
            saveScreeningBtn.setVisible(false);
        } else if (screeningToEdit != null
                && ScreeningService.STATUS_REJECTED.equalsIgnoreCase(screeningToEdit.getStatus())) {
            saveScreeningBtn.setText("Resubmit → Pending Approval");
        }
    }

    private void populateForm() {
        List<Movie> movies = context.getMovieService().getAllMovies();
        for (Movie movie : movies) {
            movieCombo.addItem(new MovieOption(movie.getMovieId(), movie.getTitle() + " (" + movie.getMtrcbRating() + ")"));
        }

        if (movies.isEmpty()) {
            errorMsgLbl.setText("No movies available. Add a movie first in Manage Movies.");
            saveScreeningBtn.setEnabled(false);
        }

        if (screeningToEdit != null) {
            selectMovieOption(screeningToEdit.getMovieId());
            if (screeningToEdit.getCinemaHall() != null) {
                ensureComboContains(cinemaHallCombo, screeningToEdit.getCinemaHall());
                cinemaHallCombo.setSelectedItem(screeningToEdit.getCinemaHall());
            }
            if (screeningToEdit.getShowDateTime() != null) {
                LocalDate date = screeningToEdit.getShowDateTime().toLocalDate();
                Date spinnerDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                showDatePicker.setValue(spinnerDate);

                String formattedTime = screeningToEdit.getShowDateTime().toLocalTime().format(TIME_FORMAT);
                ensureComboContains(timeCombo, formattedTime);
                timeCombo.setSelectedItem(formattedTime);
            }
            priceField.setText(String.format(Locale.ENGLISH, "%.2f", screeningToEdit.getPrice()));

            if (ScreeningService.STATUS_REJECTED.equalsIgnoreCase(screeningToEdit.getStatus())
                    && screeningToEdit.getRejectionReason() != null
                    && !screeningToEdit.getRejectionReason().trim().isEmpty()) {
                rejectionReasonLbl.setText("<html>Rejection reason: "
                        + OrganizerThemeHelper.escapeHtml(screeningToEdit.getRejectionReason().trim()) + "</html>");
                rejectionBannerPanel.setVisible(true);
            }
        } else {
            Date tomorrow = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            showDatePicker.setValue(tomorrow);
            timeCombo.setSelectedItem("7:00 PM");
            priceField.setText("320.00");
        }

        if (viewOnly) {
            movieCombo.setEnabled(false);
            cinemaHallCombo.setEnabled(false);
            showDatePicker.setEnabled(false);
            timeCombo.setEnabled(false);
            priceField.setEditable(false);
        }
    }

    private void bindEvents() {
        showDatePicker.addChangeListener(e -> updateDateTimePreview());
        timeCombo.addActionListener(e -> updateDateTimePreview());
        cancelBtn.addActionListener(e -> navigateBack());
        saveScreeningBtn.addActionListener(e -> handleSaveScreening());
    }

    private void updateDateTimePreview() {
        LocalDateTime dateTime = buildSelectedDateTime();
        formattedPreviewLbl.setText(dateTime != null ? dateTime.format(PREVIEW_FORMAT) : "Invalid date/time");
    }

    private LocalDateTime buildSelectedDateTime() {
        Object dateVal = showDatePicker.getValue();
        Object timeVal = timeCombo.getSelectedItem();
        if (!(dateVal instanceof Date selectedDate) || !(timeVal instanceof String timeStr)) {
            return null;
        }
        LocalDate localDate = Instant.ofEpochMilli(selectedDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime localTime = LocalTime.parse(timeStr, TIME_FORMAT);
        return LocalDateTime.of(localDate, localTime);
    }

    private void handleSaveScreening() {
        MovieOption selectedMovie = (MovieOption) movieCombo.getSelectedItem();
        if (selectedMovie == null) {
            errorMsgLbl.setText("Please select a movie.");
            return;
        }

        String hall = (String) cinemaHallCombo.getSelectedItem();
        LocalDateTime showDateTime = buildSelectedDateTime();
        double price;
        try {
            price = Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException ex) {
            errorMsgLbl.setText("Price must be a valid number (e.g., 320.00).");
            return;
        }

        try {
            if (screeningToEdit == null) {
                String newId = context.getScreeningService().generateNextScreeningId();
                Screening created = new Screening(
                        newId,
                        selectedMovie.movieId(),
                        showDateTime,
                        hall,
                        price,
                        ScreeningService.STATUS_PENDING,
                        context.getOrganizerId(),
                        null
                );
                context.getScreeningService().addScreening(created);
            } else {
                Screening updated = new Screening(
                        screeningToEdit.getScreeningId(),
                        selectedMovie.movieId(),
                        showDateTime,
                        hall,
                        price,
                        ScreeningService.STATUS_PENDING,
                        screeningToEdit.getOrganizerId() != null
                                ? screeningToEdit.getOrganizerId()
                                : context.getOrganizerId(),
                        null
                );
                context.getScreeningService().updateScreening(updated);
            }
            errorMsgLbl.setText(" ");
            navigateBack();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            errorMsgLbl.setText(ex.getMessage());
        }
    }

    private void selectMovieOption(String movieId) {
        for (int i = 0; i < movieCombo.getItemCount(); i++) {
            MovieOption option = movieCombo.getItemAt(i);
            if (Objects.equals(option.movieId(), movieId)) {
                movieCombo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void ensureComboContains(JComboBox<String> combo, String value) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (Objects.equals(combo.getItemAt(i), value)) {
                return;
            }
        }
        combo.addItem(value);
    }

    private void navigateBack() {
        OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Manage Screenings",
                new ManageScreenings(context).getMainPanel()
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private record MovieOption(String movieId, String label) {
        @Override
        public String toString() {
            return label;
        }
    }
}
