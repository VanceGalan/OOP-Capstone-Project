package ui.organizer;

import model.Movie;
import model.Screening;
import service.ScreeningService;
import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Screen 15 — Manage Screenings (820 x 560).
 */
public class ManageScreenings {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JButton backBtn;
    private JLabel titleLbl;
    private JButton addScreeningBtn;
    private JPanel infoBannerPanel;
    private JLabel infoLbl;
    private JPanel emptyStatePanel;
    private JLabel emptyStateLbl;
    private JScrollPane screeningsScroll;
    private JPanel screeningsWrapperPanel;
    private JPanel screeningsListPanel;
    private JPanel noticeBannerPanel;
    private JLabel noticeLbl;

    private final OrganizerContext context;

    public ManageScreenings() {
        this(OrganizerContext.createDemoContext());
    }

    public ManageScreenings(OrganizerContext context) {
        this.context = context;
        initComponents();
        bindEvents();
        loadScreeningsList();
    }

    private void initComponents() {
        mainPanel.setBackground(Theme.BACKGROUND);

        titleLbl.setFont(Theme.TITLE);
        titleLbl.setForeground(Theme.TEXT);

        OrganizerThemeHelper.styleSecondaryButton(backBtn, false);
        OrganizerThemeHelper.stylePrimaryButton(addScreeningBtn, false);

        OrganizerThemeHelper.styleBannerPanel(infoBannerPanel, Theme.PRIMARY);
        infoLbl.setFont(Theme.SMALL);
        infoLbl.setForeground(Theme.PRIMARY);

        OrganizerThemeHelper.styleCardPanel(emptyStatePanel);
        emptyStateLbl.setFont(Theme.BODY);
        emptyStateLbl.setForeground(Theme.MUTED);

        screeningsScroll.setViewportView(screeningsWrapperPanel);
        screeningsScroll.setBorder(BorderFactory.createEmptyBorder());
        screeningsScroll.getViewport().setBackground(Theme.BACKGROUND);
        screeningsScroll.getVerticalScrollBar().setUnitIncrement(16);

        screeningsWrapperPanel.setBackground(Theme.BACKGROUND);
        screeningsListPanel.setBackground(Theme.BACKGROUND);
        screeningsListPanel.setLayout(new BoxLayout(screeningsListPanel, BoxLayout.Y_AXIS));

        OrganizerThemeHelper.styleBannerPanel(noticeBannerPanel, Theme.WARNING);
        noticeLbl.setFont(Theme.SMALL);
        noticeLbl.setForeground(Theme.WARNING.darker());
    }

    private void bindEvents() {
        backBtn.addActionListener(e -> OrganizerContext.navigateTo(
                mainPanel,
                "Movie Ticket Booking System - Organizer Dashboard",
                new OrganizerDashboard(context).getMainPanel()
        ));

        addScreeningBtn.addActionListener(e -> {
            if (context.getMovieService().getAllMovies().isEmpty()) {
                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Please add at least one movie in Manage Movies first.",
                        "No Movies Available",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            OrganizerContext.navigateTo(
                    mainPanel,
                    "Movie Ticket Booking System - Add Screening",
                    new AddEditScreening(context, null, false).getMainPanel()
            );
        });
    }

    public void loadScreeningsList() {
        screeningsListPanel.removeAll();

        List<Screening> myScreenings = context.getScreeningService().getScreeningsByOrganizer(context.getOrganizerId());
        boolean hasScreenings = !myScreenings.isEmpty();
        emptyStatePanel.setVisible(!hasScreenings);
        screeningsScroll.setVisible(hasScreenings);

        Map<String, List<Screening>> grouped = new LinkedHashMap<>();
        for (Screening screening : myScreenings) {
            grouped.computeIfAbsent(screening.getMovieId(), k -> new ArrayList<>()).add(screening);
        }

        for (Map.Entry<String, List<Screening>> entry : grouped.entrySet()) {
            Movie movie = context.getMovieService().getMovieById(entry.getKey());
            String movieTitle = movie != null ? movie.getTitle() : "Unknown Movie (" + entry.getKey() + ")";
            List<Screening> groupItems = entry.getValue();

            ScreeningMovieGroup group = new ScreeningMovieGroup(movieTitle, groupItems.size());
            for (Screening screening : groupItems) {
                ScreeningCard card = new ScreeningCard(
                        screening,
                        movieTitle,
                        () -> handlePrimaryAction(screening),
                        () -> handleDeleteScreening(screening, movieTitle)
                );
                group.addScreeningCard(card);
            }
            screeningsListPanel.add(group.getMainPanel());
        }

        screeningsListPanel.revalidate();
        screeningsListPanel.repaint();
    }

    private void handlePrimaryAction(Screening screening) {
        boolean viewOnly = ScreeningService.STATUS_APPROVED.equalsIgnoreCase(screening.getStatus());
        String windowTitle = viewOnly
                ? "Movie Ticket Booking System - View Screening"
                : "Movie Ticket Booking System - Edit Screening";
        OrganizerContext.navigateTo(
                mainPanel,
                windowTitle,
                new AddEditScreening(context, screening, viewOnly).getMainPanel()
        );
    }

    private void handleDeleteScreening(Screening screening, String movieTitle) {
        int choice = JOptionPane.showConfirmDialog(
                mainPanel,
                "Delete screening for \"" + movieTitle + "\" on " + screening.getFormattedDateTime() + "?",
                "Confirm Delete Screening",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            context.getScreeningService().deleteScreening(screening.getScreeningId());
            loadScreeningsList();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
