import ui.admin.EditUserDialog;
import ui.admin.Refund;
import util.Theme;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Theme.applyTheme();

        SwingUtilities.invokeLater(() -> {
            EditUserDialog login = new EditUserDialog();

            JFrame frame = new JFrame("Movie Ticket Booking System - Login");
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            //frame.setSize(720, 520);
            //frame.setSize(640, 440);
            //frame.setSize(login.getMainPanel().getPreferredSize());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}