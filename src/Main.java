import com.formdev.flatlaf.FlatLightLaf;
import ui.auth.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            Login login = new Login();

            JFrame frame = new JFrame("Movie Ticket Booking System - Login");
            frame.setContentPane(login.getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(login.getMainPanel().getPreferredSize());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}