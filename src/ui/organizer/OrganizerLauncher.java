package ui.organizer;

import util.Theme;

import javax.swing.*;

/**
 * Standalone preview launcher for the Organizer screens.
 * Keeps shared src/Main.java untouched until team integration.
 */
public class OrganizerLauncher {
    public static void main(String[] args) {
        Theme.applyTheme();

        SwingUtilities.invokeLater(() -> {
            OrganizerContext context = OrganizerContext.createDemoContext();
            OrganizerDashboard dashboard = new OrganizerDashboard(context);

            JFrame frame = new JFrame("Movie Ticket Booking System - Organizer Dashboard");
            frame.setContentPane(dashboard.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
