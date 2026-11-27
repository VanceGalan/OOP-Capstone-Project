package ui.auth;

import util.Theme;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import service.AuthService;

public class Register {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel nameLabel;
    private JPanel nameRowPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JLabel birthdayLabel;
    private JPanel birthdayRowPanel;
    private JComboBox<String> monthCombo;
    private JComboBox<String> dayCombo;
    private JComboBox<String> yearCombo;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JCheckBox termsCheckBox;
    private JLabel errorLabel;
    private JButton createAccountBtn;
    private JButton alreadyAccountBtn;

    public Register() {
        applyStyles();
        populateBirthdayPickers();
        wireActions();
    }

    private void applyStyles() {
        mainPanel.setBackground(Theme.BACKGROUND);
        cardPanel.setBackground(Theme.CARD);
        cardPanel.putClientProperty("FlatLaf.style", "arc: 16; border: 1,1,1,1,#E2E8F0,1,16");

        titleLabel.setFont(Theme.TITLE);
        titleLabel.setForeground(Theme.TEXT);

        subtitleLabel.setFont(Theme.BODY);
        subtitleLabel.setForeground(Theme.MUTED);

        for (JLabel label : new JLabel[]{nameLabel, birthdayLabel, emailLabel, passwordLabel}) {
            label.setFont(Theme.BODY);
            label.setForeground(Theme.TEXT);
        }

        styleInput(firstNameField, "First name");
        styleInput(lastNameField, "Last name");
        styleInput(emailField, "you@example.com");
        styleInput(passwordField, "At least 8 characters");

        for (JComboBox<?> combo : new JComboBox<?>[]{monthCombo, dayCombo, yearCombo}) {
            combo.setFont(Theme.BODY);
            combo.setPreferredSize(new Dimension(-1, 34));
            combo.putClientProperty("FlatLaf.style", "arc: 10");
        }

        termsCheckBox.setFont(Theme.SMALL);
        termsCheckBox.setForeground(Theme.MUTED);
        termsCheckBox.setOpaque(false);
        termsCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        errorLabel.setFont(Theme.SMALL);
        errorLabel.setForeground(Theme.DANGER);
        errorLabel.setText(" ");

        Theme.stylePrimaryButton(createAccountBtn);
        createAccountBtn.setPreferredSize(new Dimension(-1, 34));
        createAccountBtn.putClientProperty("FlatLaf.style", "arc: 10");

        Theme.styleSecondaryButton(alreadyAccountBtn);
        alreadyAccountBtn.setPreferredSize(new Dimension(-1, 34));
        alreadyAccountBtn.putClientProperty("FlatLaf.style", "arc: 10");
    }

    private void styleInput(JTextField field, String placeholder) {
        field.setFont(Theme.BODY);
        field.setPreferredSize(new Dimension(-1, 34));
        field.putClientProperty("FlatLaf.style", "arc: 10");
        field.putClientProperty("JTextField.placeholderText", placeholder);
    }

    private void populateBirthdayPickers() {
        monthCombo.addItem("Month");
        for (Month month : Month.values()) {
            monthCombo.addItem(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
        }

        yearCombo.addItem("Year");
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear; year >= 1920; year--) {
            yearCombo.addItem(String.valueOf(year));
        }

        refreshDayOptions();
        monthCombo.addActionListener(e -> refreshDayOptions());
        yearCombo.addActionListener(e -> refreshDayOptions());
    }

    /** Keeps day choices valid for the chosen month/year (e.g., Feb 28/29). */
    private void refreshDayOptions() {
        Object previousSelection = dayCombo.getSelectedItem();
        int maxDays = 31;

        int monthIndex = monthCombo.getSelectedIndex();
        if (monthIndex > 0) {
            int year = yearCombo.getSelectedIndex() > 0
                    ? Integer.parseInt((String) yearCombo.getSelectedItem())
                    : 2000; // Leap year fallback so Feb 29 stays selectable before picking a year
            maxDays = YearMonth.of(year, monthIndex).lengthOfMonth();
        }

        dayCombo.removeAllItems();
        dayCombo.addItem("Day");
        for (int d = 1; d <= maxDays; d++) {
            dayCombo.addItem(String.valueOf(d));
        }

        if (previousSelection instanceof String prev && !prev.equals("Day")) {
            int day = Integer.parseInt(prev);
            if (day <= maxDays) {
                dayCombo.setSelectedItem(prev);
            }
        }
    }

    private void wireActions() {
        createAccountBtn.addActionListener(e -> handleRegister());
        alreadyAccountBtn.addActionListener(e -> navigateToLogin());
    }

    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (firstName.isEmpty() || lastName.isEmpty()) {
            errorLabel.setText("Please enter your first and last name.");
            return;
        }
        if (monthCombo.getSelectedIndex() == 0 || dayCombo.getSelectedIndex() == 0 || yearCombo.getSelectedIndex() == 0) {
            errorLabel.setText("Please select your complete birthday.");
            return;
        }

        int month = monthCombo.getSelectedIndex();
        int day = Integer.parseInt((String) dayCombo.getSelectedItem());
        int year = Integer.parseInt((String) yearCombo.getSelectedItem());
        LocalDate birthdate = LocalDate.of(year, month, day);

        if (birthdate.isAfter(LocalDate.now())) {
            errorLabel.setText("Birthday cannot be in the future.");
            return;
        }
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            errorLabel.setText("Please enter a valid email address.");
            return;
        }
        if (password.length() < 8) {
            errorLabel.setText("Password must be at least 8 characters.");
            return;
        }
        if (!termsCheckBox.isSelected()) {
            errorLabel.setText("Please agree to the Terms & Conditions.");
            return;
        }

        try {
            AuthService.register(firstName, lastName, email, password, birthdate);
            errorLabel.setText(" ");
            JOptionPane.showMessageDialog(mainPanel,"Account created for " + firstName + " " + lastName + "!\nYou can now log in.");
            navigateToLogin();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }

    }

    private void navigateToLogin() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame == null) {
            return;
        }
        frame.setTitle("Movie Ticket Booking System - Login");
        frame.setContentPane(new Login().getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
