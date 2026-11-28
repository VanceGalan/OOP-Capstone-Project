package ui;

import model.Admin;
import model.Customer;
import model.EventOrganizer;
import model.User;
import service.AuthService;
import service.MovieService;
import service.ScreeningService;
import ui.admin.AdminDashboard;
import ui.auth.Login;
import ui.organizer.OrganizerContext;
import ui.organizer.OrganizerDashboard;
import util.Theme;
import javax.swing.*;
import java.awt.*;

public class DashboardFactory {
    private static final MovieService MOVIE_SERVICE;
    private static final ScreeningService SCREENING_SERVICE;

    static {
        OrganizerContext oc = OrganizerContext.createDemoContext();
        MOVIE_SERVICE = oc.getMovieService();
        SCREENING_SERVICE = oc.getScreeningService();
    }

    public static JPanel createDashboard(User user){
        if (user instanceof Admin){
            return new AdminDashboard().getMainPanel();
        } else if (user instanceof EventOrganizer organizer){
            OrganizerContext c = new OrganizerContext(MOVIE_SERVICE, SCREENING_SERVICE,
                    organizer.getUserId(), organizer, DashboardFactory::logoutToLogin);
            return new OrganizerDashboard(c).getMainPanel();
        } else if (user instanceof Customer customer){
            //replace it with new CustomerDashboard later
            return CustomerPlaceholder(customer);
        }
        throw new IllegalArgumentException("User role not found.");
    }

    public static String getDashboardTitle(User user){
        if (user instanceof Admin) {
            return "Movie Ticket Booking System - Admin Dashboard";
        } else if (user instanceof EventOrganizer) {
            return "Movie Ticket Booking System - Organizer Dashboard";
        } else if (user instanceof Customer) {
            return "Movie Ticket Booking System - Customer Dashboard";
        }
        return "Movie Ticket Booking System";
    }

    private static JPanel CustomerPlaceholder(Customer customer) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.BACKGROUND);
        panel.setPreferredSize(new Dimension(520, 320));
        JPanel card = Theme.cardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        JLabel title = new JLabel("Welcome, " + customer.getFullName() + "!");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.TEXT);
        JLabel subtitle = new JLabel("Implement later.");
        subtitle.setFont(Theme.BODY);
        subtitle.setForeground(Theme.MUTED);
        JButton logoutBtn = new JButton("Logout");
        Theme.styleSecondaryButton(logoutBtn);
        logoutBtn.addActionListener(e -> logoutToLogin());
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(18));
        card.add(logoutBtn);
        panel.add(card);
        return panel;
    }

    private static void logoutToLogin(){
        AuthService.logout();
        for (Frame frame : Frame.getFrames()){
            if (frame instanceof JFrame jf && frame.isDisplayable()){
                jf.setTitle("Movie Ticket Booking System - Login");
                jf.setContentPane(new Login().getMainPanel());
                jf.pack();
                jf.setLocationRelativeTo(null);
                jf.revalidate();
                jf.repaint();
                break;
            }
        }
    }
}
