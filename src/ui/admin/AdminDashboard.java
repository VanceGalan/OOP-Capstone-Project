package ui.admin;

import javax.swing.*;
import java.awt.*;

import static util.Theme.CARD;
import static util.Theme.PRIMARY;

public class AdminDashboard {
    private JButton logoutButton;
    private JPanel cardBanner;
    private JPanel cardApproveScreenings;
    private JPanel cardProcessRefunds;
    private JPanel cardManageUsers;
    private JPanel pnlMain;

    public AdminDashboard() {

        cardBanner.setBackground(PRIMARY);
        cardBanner.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));

        cardApproveScreenings.setBackground(CARD);
        cardApproveScreenings.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));

        cardProcessRefunds.setBackground(CARD);
        cardProcessRefunds.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));

        cardManageUsers.setBackground(CARD);
        cardManageUsers.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));

    }

    public JPanel getMainPanel() {
        return pnlMain;
    }
}
