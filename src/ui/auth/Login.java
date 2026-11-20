package ui.auth;

import util.Theme;
import javax.swing.*;
import java.awt.*;

public class Login {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton loginBtn;
    private JButton signupBtn;

    public Login() {
        mainPanel.setBackground(Theme.BACKGROUND);
        cardPanel.setBackground(Theme.CARD);
        cardPanel.putClientProperty("FlatLaf.style", "arc: 16; border: 1,1,1,1,#E2E8F0,1,16");

        titleLabel.setFont(Theme.TITLE);
        titleLabel.setForeground(Theme.TEXT);
        subtitleLabel.setFont(Theme.BODY);
        subtitleLabel.setForeground(Theme.MUTED);
        emailLabel.setFont(Theme.BODY);
        emailLabel.setForeground(Theme.TEXT);
        passwordLabel.setFont(Theme.BODY);
        passwordLabel.setForeground(Theme.TEXT);
        errorLabel.setFont(Theme.SMALL);
        errorLabel.setForeground(Theme.DANGER);
        errorLabel.setText(" ");

        emailField.setFont(Theme.BODY);
        emailField.setPreferredSize(new Dimension(-1, 34));
        emailField.putClientProperty("FlatLaf.style", "arc: 10");
        passwordField.setFont(Theme.BODY);
        passwordField.setPreferredSize(new Dimension(-1, 34));
        passwordField.putClientProperty("FlatLaf.style", "arc: 10");

        Theme.stylePrimaryButton(loginBtn);
        loginBtn.setPreferredSize(new Dimension(-1, 34));
        loginBtn.putClientProperty("FlatLaf.style", "arc: 10");

        signupBtn.setFont(Theme.SMALL);
        signupBtn.setForeground(Theme.PRIMARY);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setBorderPainted(false);
        signupBtn.setFocusPainted(false);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()){
                errorLabel.setText("Please enter both email and password");
            } else {
                errorLabel.setText(" ");
                JOptionPane.showMessageDialog(mainPanel, "You are now logged in.");
            }
        });

        signupBtn.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
            if (frame == null) {
                return;
            }
            frame.setTitle("Movie Ticket Booking System - Register");
            frame.setContentPane(new Register().getMainPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
