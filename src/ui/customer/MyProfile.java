package ui.customer;
import com.formdev.flatlaf.FlatClientProperties;

import java.awt.*;
import javax.swing.*;

import javax.swing.*;

public class MyProfile {
    private JPanel myProfilePanel;
    private JPanel editProfilePanel;
    private JPanel changeEmailPanel;
    private JPanel changePasswordPanel;
    private JPanel profilePanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField textField3;
    private JTextField textField4;
    private JButton saveProfileButton;
    private JLabel editLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel birthdateLabel;
    private JLabel ageLabel;
    private JTextField newEmailTextField;
    private JButton saveEmailButton;
    private JLabel changeEmailLabel;
    private JLabel newEmailLabel;

    public MyProfile() {
        Color indigo = new Color(0x4F46E5);
        profilePanel.setBackground(indigo);

        saveProfileButton.setBackground(indigo);
        saveEmailButton.setBackground(indigo);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("My Profile");
        frame.setContentPane(new MyProfile().myProfilePanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
    }
}
