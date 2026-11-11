package util;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

/**
 * Theme.java — ONE place for colors / fonts / button styles.
 * Location: src/util/Theme.java
 * Owner: Vance (Day 1)
 *
 * RULE: Never hard-code colors in your screens.
 *   DO:   loginBtn.setBackground(Theme.PRIMARY);
 *   DON'T: loginBtn.setBackground(new Color(79, 70, 229));
 *
 * If you follow this, changing ONE line here changes ALL 20 screens.
 * You CAN update this file later — that's the point.
 */
public class Theme {

    // ===== BRAND COLORS =====
    public static final Color PRIMARY = new Color(0x4F, 0x46, 0xE5); // #4F46E5 indigo — main buttons, headers
    public static final Color BACKGROUND = new Color(0xF6, 0xF7, 0xFB); // #F6F7FB — page background
    public static final Color CARD = Color.WHITE; // cards
    public static final Color TEXT = new Color(0x0F, 0x17, 0x2A); // almost black — titles
    public static final Color MUTED = new Color(0x64, 0x74, 0x8B); // gray — subtitles
    public static final Color SUCCESS = new Color(0x16, 0xA3, 0x4A); // green — Approved, Confirmed
    public static final Color DANGER = new Color(0xDC, 0x26, 0x26); // red — Reject, Cancel, Delete
    public static final Color WARNING = new Color(0xF5, 0x9E, 0x0B); // orange — Pending, Pending Refund

    // ===== FONTS =====
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    // ===== SETUP — call this from Main.java =====
    public static void applyTheme() {
        FlatLightLaf.setup();
    }

    // ===== HELPERS — use these in your screens =====
    public static void stylePrimaryButton(JButton btn) {
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFont(BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleDangerButton(JButton btn) {
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        btn.setFont(BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void styleSecondaryButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setForeground(TEXT);
        btn.setFont(BODY);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(CARD);
        p.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));
        return p;
    }
}
