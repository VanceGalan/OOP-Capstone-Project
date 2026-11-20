package ui.organizer;

import model.Movie;
import model.Screening;
import util.Theme;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;

/**
 * Screen 14 — Add / Edit Movie (640 x 560).
 */
public class AddEditMovie {
    private static final String[] MTRCB_RATINGS = {"G", "PG", "PG-13", "R-13", "R-16", "R-18"};

    private JPanel mainPanel;
    private JPanel formCardPanel;
    private JLabel screenTitleLbl;
    private JLabel subtitleLbl;
    private JPanel fieldsGridPanel;
    private JLabel titleLbl;
    private JLabel genreLbl;
    private JTextField titleField;
    private JTextField genreField;
    private JLabel durationLbl;
    private JLabel mtrcbLbl;
    private JTextField durationField;
    private JComboBox<String> mtrcbRatingCombo;
    private JLabel descriptionLbl;
    private JScrollPane descriptionScroll;
    private JTextArea descriptionArea;
    private JLabel posterLbl;
    private JLabel posterHintLbl;
    private JPanel posterRowPanel;
    private JPanel posterPreviewPanel;
    private JLabel posterPreviewLbl;
    private JLabel posterPathLbl;
    private JButton choosePosterBtn;
    private JLabel errorMsgLbl;
    private JPanel actionsPanel;
    private JButton cancelBtn;
    private JButton deleteBtn;
    private JButton saveMovieBtn;
    private JPanel noticeBannerPanel;
    private JLabel noticeLbl;

    private final OrganizerContext context;
    private final Movie movieToEdit;
    private String selectedPosterPath = "";

    public AddEditMovie() {
        this(OrganizerContext.createDemoContext(), null);
    }

    public AddEditMovie(OrganizerContext context, Movie movieToEdit) {
        this.context = context;
        this.movieToEdit = movieToEdit;
        initComponents();
        populateForm();
        bindEvents();
    }

    private void initComponents() {
        mainPanel.setBackground(Theme.BACKGROUND);
        OrganizerThemeHelper.styleCardPanel(formCardPanel);

        screenTitleLbl.setFont(Theme.TITLE);
        screenTitleLbl.setForeground(Theme.TEXT);
        screenTitleLbl.setText(movieToEdit == null ? "Add Movie" : "Edit Movie");

        subtitleLbl.setFont(Theme.BODY);
        subtitleLbl.setForeground(Theme.MUTED);

        for (JLabel label : new JLabel[]{titleLbl, genreLbl, durationLbl, mtrcbLbl, descriptionLbl, posterLbl}) {
            label.setFont(Theme.BODY);
            label.setForeground(Theme.TEXT);
        }

        posterHintLbl.setFont(Theme.SMALL);
        posterHintLbl.setForeground(Theme.MUTED);
        posterPathLbl.setFont(Theme.SMALL);
        posterPathLbl.setForeground(Theme.MUTED);

        OrganizerThemeHelper.styleInput(titleField, "e.g., Dune: Part Two");
        OrganizerThemeHelper.styleInput(genreField, "e.g., Sci-Fi");
        OrganizerThemeHelper.styleInput(durationField, "e.g., 166");
        OrganizerThemeHelper.styleInput(mtrcbRatingCombo, null);
        OrganizerThemeHelper.styleInput(descriptionArea, null);

        for (String rating : MTRCB_RATINGS) {
            mtrcbRatingCombo.addItem(rating);
        }

        descriptionScroll.setViewportView(descriptionArea);
        descriptionScroll.putClientProperty("FlatLaf.style", "arc: 10");

        errorMsgLbl.setFont(Theme.SMALL);
        errorMsgLbl.setForeground(Theme.DANGER);
        errorMsgLbl.setText(" ");

        OrganizerThemeHelper.styleSecondaryButton(choosePosterBtn, true);
        OrganizerThemeHelper.styleSecondaryButton(cancelBtn, false);
        OrganizerThemeHelper.styleOutlineDangerButton(deleteBtn, false);
        OrganizerThemeHelper.stylePrimaryButton(saveMovieBtn, false);

        deleteBtn.setVisible(movieToEdit != null);

        OrganizerThemeHelper.styleBannerPanel(noticeBannerPanel, Theme.WARNING);
        noticeLbl.setFont(Theme.SMALL);
        noticeLbl.setForeground(Theme.WARNING.darker());
    }

    private void populateForm() {
        if (movieToEdit != null) {
            titleField.setText(movieToEdit.getTitle());
            genreField.setText(movieToEdit.getGenre());
            durationField.setText(String.valueOf(movieToEdit.getDurationMins()));
            mtrcbRatingCombo.setSelectedItem(movieToEdit.getMtrcbRating());
            descriptionArea.setText(movieToEdit.getDescription() == null ? "" : movieToEdit.getDescription());
            selectedPosterPath = movieToEdit.getPoster() == null ? "" : movieToEdit.getPoster();
        } else {
            mtrcbRatingCombo.setSelectedItem("PG");
        }
        updatePosterPreview();
    }

    private void bindEvents() {
        titleField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePosterPreview();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePosterPreview();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePosterPreview();
            }
        });

        choosePosterBtn.addActionListener(e -> handleChoosePoster());
        cancelBtn.addActionListener(e -> navigateBack());
        saveMovieBtn.addActionListener(e -> handleSaveMovie());
        deleteBtn.addActionListener(e -> handleDeleteCurrentMovie());
    }

    private void handleChoosePoster() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Movie Poster Image");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Image files (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif"
        ));
        if (!selectedPosterPath.isEmpty()) {
            File current = new File(selectedPosterPath);
            if (current.getParentFile() != null && current.getParentFile().isDirectory()) {
                chooser.setCurrentDirectory(current.getParentFile());
            }
        }
        int result = chooser.showOpenDialog(mainPanel);
        if (result == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null) {
            selectedPosterPath = chooser.getSelectedFile().getAbsolutePath();
            updatePosterPreview();
        }
    }

    private void updatePosterPreview() {
        String previewTitle = titleField.getText().trim();
        OrganizerThemeHelper.renderPosterPreview(
                posterPreviewPanel,
                posterPreviewLbl,
                previewTitle.isEmpty() ? "POSTER" : previewTitle,
                selectedPosterPath,
                72,
                90
        );
        if (selectedPosterPath.isEmpty()) {
            posterPathLbl.setText("No poster file selected (title card preview used)");
        } else {
            posterPathLbl.setText(new File(selectedPosterPath).getName());
        }
    }

    private void handleSaveMovie() {
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        String durationText = durationField.getText().trim();
        String rating = (String) mtrcbRatingCombo.getSelectedItem();
        String description = descriptionArea.getText().trim();

        int durationMins;
        try {
            durationMins = Integer.parseInt(durationText);
        } catch (NumberFormatException ex) {
            errorMsgLbl.setText("Duration must be a whole number of minutes.");
            return;
        }

        try {
            if (movieToEdit == null) {
                String newId = context.getMovieService().generateNextMovieId();
                Movie created = new Movie(newId, title, genre, durationMins, rating, description, selectedPosterPath);
                context.getMovieService().addMovie(created);
            } else {
                Movie updated = new Movie(
                        movieToEdit.getMovieId(),
                        title,
                        genre,
                        durationMins,
                        rating,
                        description,
                        selectedPosterPath
                );
                context.getMovieService().updateMovie(updated);
            }
            errorMsgLbl.setText(" ");
            navigateBack();
        } catch (IllegalArgumentException ex) {
            errorMsgLbl.setText(ex.getMessage());
        }
    }

    private void handleDeleteCurrentMovie() {
        if (movieToEdit == null) {
            return;
        }
        List<Screening> upcoming = context.getScreeningService().getUpcomingScreeningsByMovie(movieToEdit.getMovieId());
        String prompt = upcoming.isEmpty()
                ? "Delete movie \"" + movieToEdit.getTitle() + "\"?"
                : "\"" + movieToEdit.getTitle() + "\" has " + upcoming.size()
                  + " upcoming screening(s) (next: " + upcoming.get(0).getFormattedDateTime()
                  + ").\nDelete the movie AND all its screenings?";
        int choice = JOptionPane.showConfirmDialog(
                mainPanel,
                prompt,
                "Confirm Delete Movie",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            context.getScreeningService().deleteScreeningsByMovie(movieToEdit.getMovieId());
            context.getMovieService().deleteMovie(movieToEdit.getMovieId());
            navigateBack();
        }
    }

    private void navigateBack() {
        OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Manage Movies",
                new ManageMovies(context).getMainPanel()
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
