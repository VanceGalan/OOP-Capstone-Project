package ui.customer;
import com.formdev.flatlaf.FlatClientProperties;

import java.awt.*;
import javax.swing.*;

public class Dashboard {
    private JPanel mainPanel;
    private JPanel bannerPanel;
    private JPanel bottomPanel;
    private JPanel cardsPanel;
    private JButton logoutButton;
    private JLabel welcomeLabel;
    private JLabel subtitleLabel;
    private JButton avatarButton;
    private JButton browseButton;
    private JButton bookingsButton;
    private JButton profileButton;
    private JButton ticketsActiveButton;

    public JPanel getMainPanel() {
        return mainPanel;

    }
    public Dashboard() {
        Color indigo = new Color(0x4F46E5);
        //--------------------------------------------------UI----------------------------------------------------
        //------------------------------- Banner
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        bannerPanel.setBackground(indigo);
        welcomeLabel.setText("Welcome back, Juan!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 24f));
        subtitleLabel.setText("Tonight's picks are ready — what are we watching?");
        subtitleLabel.setForeground(Color.WHITE);
        ticketsActiveButton.setText("Active buttons: ");
        ticketsActiveButton.putClientProperty("FlatLaf.style", "arc: 999; background: #FFFFFF; foreground: #4F46E5");
        avatarButton.setText("USER");
        avatarButton.putClientProperty("FlatLaf.style", "arc: 999; background: #FFFFFF; foreground: #4F46E5");

        //------------------------------- Cards
        styleCard(browseButton, "Browse Movies", "Find your next movie");
        styleCard(bookingsButton, "My Bookings", "2 active tickets");
        styleCard(profileButton, "My Profile", "Details & preferences");

        // ------------------------------ Bottom
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        //--------------------------------------------------UI----------------------------------------------------

        browseButton.addActionListener(e-> {

        });


    }

    private void styleCard(JButton button, String title, String description) {
        button.setText("<html><b style='font-size:14px'>" + title + "</b><br>"
                + description + "<br><br><font color='#4F46E5'><b>Open →</b></font></html>");
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.putClientProperty("FlatLaf.style", "arc: 20; background: #FFFFFF");
    }





    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            JFrame frame = new JFrame("Customer Dashboard");
            frame.setContentPane(new Dashboard().getMainPanel());
            frame.setVisible(true);
            frame.setSize(900,600);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
        });
    }
}
