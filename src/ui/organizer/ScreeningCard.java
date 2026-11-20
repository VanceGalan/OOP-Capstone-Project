package ui.organizer;

import model.Screening;
import service.ScreeningService;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Reusable Designer-built card template for a Screening row in ManageScreenings.
 */
public class ScreeningCard {
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JLabel movieTitleLbl;
    private JLabel detailsLbl;
    private JLabel reasonLbl;
    private JLabel statusBadgeLbl;
    private JButton actionBtn;
    private JButton deleteBtn;

    public ScreeningCard(Screening screening,
                         String movieTitle,
                         Runnable onPrimaryAction,
                         Runnable onDelete) {
        initComponents(screening, movieTitle);
        bindEvents(onPrimaryAction, onDelete);
    }

    private void initComponents(Screening screening, String movieTitle) {
        OrganizerThemeHelper.styleCardPanel(mainPanel);
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        movieTitleLbl.setFont(Theme.BODY.deriveFont(Font.BOLD));
        movieTitleLbl.setForeground(Theme.TEXT);
        movieTitleLbl.setText(movieTitle);

        detailsLbl.setFont(Theme.SMALL);
        detailsLbl.setForeground(Theme.MUTED);
        String hall = screening.getCinemaHall() == null ? "Unassigned Hall" : screening.getCinemaHall();
        String priceText = String.format(Locale.ENGLISH, "₱%.2f", screening.getPrice());
        detailsLbl.setText(screening.getFormattedDateTime() + " · " + hall + " · " + priceText);

        String status = screening.getStatus() == null ? ScreeningService.STATUS_PENDING : screening.getStatus();
        statusBadgeLbl.setText(status);

        if (ScreeningService.STATUS_APPROVED.equalsIgnoreCase(status)) {
            OrganizerThemeHelper.styleStatusPill(statusBadgeLbl, Theme.SUCCESS);
            actionBtn.setText("View");
            OrganizerThemeHelper.styleSecondaryButton(actionBtn, true);
            reasonLbl.setVisible(false);
        } else if (ScreeningService.STATUS_REJECTED.equalsIgnoreCase(status)) {
            OrganizerThemeHelper.styleStatusPill(statusBadgeLbl, Theme.DANGER);
            actionBtn.setText("Fix & Resubmit");
            OrganizerThemeHelper.styleSecondaryButton(actionBtn, true);
            String reason = screening.getRejectionReason();
            if (reason != null && !reason.trim().isEmpty()) {
                reasonLbl.setFont(Theme.SMALL);
                reasonLbl.setForeground(Theme.DANGER);
                reasonLbl.setText("<html><div style='width:420px;'>Reason: "
                        + OrganizerThemeHelper.escapeHtml(reason.trim()) + "</div></html>");
                reasonLbl.setVisible(true);
            } else {
                reasonLbl.setVisible(false);
            }
        } else {
            OrganizerThemeHelper.styleStatusPill(statusBadgeLbl, Theme.WARNING);
            actionBtn.setText("Edit");
            OrganizerThemeHelper.stylePrimaryButton(actionBtn, true);
            reasonLbl.setVisible(false);
        }

        OrganizerThemeHelper.styleOutlineDangerButton(deleteBtn, true);
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, mainPanel.getPreferredSize().height + 8));
    }

    private void bindEvents(Runnable onPrimaryAction, Runnable onDelete) {
        actionBtn.addActionListener(e -> {
            if (onPrimaryAction != null) {
                onPrimaryAction.run();
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
