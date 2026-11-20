package ui.organizer;

import model.Movie;
import util.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * Reusable Designer-built card template for a Movie in ManageMovies.
 */
public class MovieCard {
    private JPanel mainPanel;
    private JPanel posterPanel;
    private JLabel posterPreviewLbl;
    private JPanel titleRowPanel;
    private JLabel movieTitleLbl;
    private JLabel ratingBadgeLbl;
    private JLabel metaLbl;
    private JLabel descriptionLbl;
    private JPanel actionsPanel;
    private JButton editBtn;
    private JButton deleteBtn;

    public MovieCard(Movie movie, Runnable onEdit, Runnable onDelete) {
        initComponents(movie);
        bindEvents(onEdit, onDelete);
    }

    private void initComponents(Movie movie) {
        OrganizerThemeHelper.styleCardPanel(mainPanel);

        OrganizerThemeHelper.renderPosterPreview(
                posterPanel,
                posterPreviewLbl,
                movie.getTitle(),
                movie.getPoster(),
                210,
                110
        );

        movieTitleLbl.setFont(Theme.BODY.deriveFont(Font.BOLD));
        movieTitleLbl.setForeground(Theme.TEXT);
        movieTitleLbl.setText(movie.getTitle());

        String rating = movie.getMtrcbRating() == null ? "" : movie.getMtrcbRating();
        ratingBadgeLbl.setText(rating);
        Color badgeAccent = "R-18".equalsIgnoreCase(rating) ? Theme.WARNING : Theme.SUCCESS;
        OrganizerThemeHelper.styleStatusPill(ratingBadgeLbl, badgeAccent);

        metaLbl.setFont(Theme.SMALL);
        metaLbl.setForeground(Theme.MUTED);
        metaLbl.setText(movie.getGenre() + " · " + movie.getDurationMins() + " min");

        descriptionLbl.setFont(Theme.SMALL);
        descriptionLbl.setForeground(Theme.TEXT);
        String desc = movie.getDescription() == null ? "" : movie.getDescription().trim();
        if (desc.length() > 95) {
            desc = desc.substring(0, 92) + "...";
        }
        descriptionLbl.setText("<html><div style='width:205px;'>"
                + OrganizerThemeHelper.escapeHtml(desc.isEmpty() ? "No description provided." : desc)
                + "</div></html>");

        OrganizerThemeHelper.stylePrimaryButton(editBtn, true);
        OrganizerThemeHelper.styleOutlineDangerButton(deleteBtn, true);
    }

    private void bindEvents(Runnable onEdit, Runnable onDelete) {
        editBtn.addActionListener(e -> {
            if (onEdit != null) {
                onEdit.run();
            }
        });
        deleteBtn.addActionListener(e -> {
            if (onDelete != null) {
                onDelete.run();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
