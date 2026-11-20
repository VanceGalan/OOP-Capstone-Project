package ui.organizer;

import util.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * Screen 12 — Organizer Dashboard (640 x 460).
 */
public class OrganizerDashboard {
    private JPanel mainPanel;
    private JPanel bannerPanel;
    private JLabel greetingLbl;
    private JLabel subtitleLbl;
    private JLabel pendingPillLbl;
    private JPanel cardsPanel;
    private JButton manageMoviesCardBtn;
    private JButton manageScreeningsCardBtn;
    private JPanel footerPanel;
    private JButton logoutBtn;

    private final OrganizerContext context;

    public OrganizerDashboard() {
        this(OrganizerContext.createDemoContext());
    }

    public OrganizerDashboard(OrganizerContext context) {
        this.context = context;
        initComponents();
        bindEvents();
        refreshSummary();
    }

    private void initComponents() {
        mainPanel.setBackground(Theme.BACKGROUND);
        OrganizerThemeHelper.styleHeroBanner(bannerPanel);

        greetingLbl.setFont(Theme.TITLE);
        greetingLbl.setForeground(Theme.CARD);
        greetingLbl.setText("Hello, " + context.getOrganizerFirstName() + " \uD83C\uDFAC");

        subtitleLbl.setFont(Theme.BODY);
        subtitleLbl.setForeground(OrganizerThemeHelper.tint(Theme.PRIMARY, 0.80));

        OrganizerThemeHelper.styleHeaderPill(pendingPillLbl);

        styleActionCardButton(
                manageMoviesCardBtn,
                "\uD83C\uDFAC",
                "Manage Movies",
                "Add, edit or delete movies · No approval needed"
        );
        styleActionCardButton(
                manageScreeningsCardBtn,
                "\uD83D\uDD52",
                "Manage Screenings",
                "Add, edit or delete screenings · Needs approval"
        );

        OrganizerThemeHelper.styleSecondaryButton(logoutBtn, false);
        logoutBtn.setPreferredSize(new Dimension(130, 34));
    }

    private void styleActionCardButton(JButton btn, String icon, String title, String description) {
        btn.setBackground(Theme.CARD);
        btn.setForeground(Theme.TEXT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        btn.putClientProperty("FlatLaf.style",
                "arc: 14; borderColor: " + OrganizerThemeHelper.toHex(OrganizerThemeHelper.borderColor())
                        + "; hoverBackground: " + OrganizerThemeHelper.toHex(OrganizerThemeHelper.tint(Theme.PRIMARY, 0.94)));

        String primaryHex = OrganizerThemeHelper.toHex(Theme.PRIMARY);
        String mutedHex = OrganizerThemeHelper.toHex(Theme.MUTED);
        String textHex = OrganizerThemeHelper.toHex(Theme.TEXT);
        btn.setText("<html><div style='width:220px;'>"
                + "<div style='font-size:18px;margin-bottom:8px;'>" + icon + "</div>"
                + "<div style='font-size:13px;font-weight:bold;color:" + textHex + ";'>"
                + OrganizerThemeHelper.escapeHtml(title) + "</div>"
                + "<div style='font-size:11px;color:" + mutedHex + ";margin-top:4px;'>"
                + OrganizerThemeHelper.escapeHtml(description) + "</div>"
                + "<div style='font-size:12px;font-weight:bold;color:" + primaryHex + ";margin-top:14px;'>Open \u2192</div>"
                + "</div></html>");
    }

    private void bindEvents() {
        manageMoviesCardBtn.addActionListener(e -> OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Manage Movies",
                new ManageMovies(context).getMainPanel()
        ));

        manageScreeningsCardBtn.addActionListener(e -> OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Manage Screenings",
                new ManageScreenings(context).getMainPanel()
        ));

        logoutBtn.addActionListener(e -> context.logout(mainPanel));
    }

    public void refreshSummary() {
        int pendingCount = context.getScreeningService().getPendingCountByOrganizer(context.getOrganizerId());
        String suffix = pendingCount == 1 ? " pending approval" : " pending approvals";
        pendingPillLbl.setText(pendingCount + suffix);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
