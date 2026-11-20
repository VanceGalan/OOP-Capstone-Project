package ui.organizer;

import util.Theme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Styling and poster-rendering helpers for Organizer screens, built strictly
 * on top of {@link util.Theme} colors and fonts.
 */
public final class OrganizerThemeHelper {
    private OrganizerThemeHelper() {
    }

    public static Color tint(Color accent, double cardWeight) {
        double accentWeight = 1.0 - cardWeight;
        int r = (int) Math.round(Theme.CARD.getRed() * cardWeight + accent.getRed() * accentWeight);
        int g = (int) Math.round(Theme.CARD.getGreen() * cardWeight + accent.getGreen() * accentWeight);
        int b = (int) Math.round(Theme.CARD.getBlue() * cardWeight + accent.getBlue() * accentWeight);
        return new Color(clamp(r), clamp(g), clamp(b));
    }

    public static String toHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color borderColor() {
        return tint(Theme.MUTED, 0.78);
    }

    public static void styleCardPanel(JPanel panel) {
        panel.setBackground(Theme.CARD);
        panel.putClientProperty("FlatLaf.style",
                "arc: 14; border: 1,1,1,1," + toHex(borderColor()) + ",1,14");
    }

    public static void styleBannerPanel(JPanel panel, Color accent) {
        panel.setBackground(tint(accent, 0.88));
        panel.putClientProperty("FlatLaf.style",
                "arc: 12; border: 1,1,1,1," + toHex(tint(accent, 0.55)) + ",1,12");
    }

    public static void styleHeroBanner(JPanel panel) {
        panel.setBackground(Theme.PRIMARY);
        panel.putClientProperty("FlatLaf.style", "arc: 14");
    }

    public static void styleInput(JComponent component, String placeholder) {
        component.setFont(Theme.BODY);
        component.setForeground(Theme.TEXT);
        component.setBackground(Theme.CARD);
        if (!(component instanceof JTextArea)) {
            component.setPreferredSize(new Dimension(-1, 34));
        }
        component.putClientProperty("FlatLaf.style", "arc: 10");
        if (placeholder != null && component instanceof JTextField) {
            component.putClientProperty("JTextField.placeholderText", placeholder);
        }
    }

    public static void stylePrimaryButton(JButton btn, boolean small) {
        Theme.stylePrimaryButton(btn);
        applyButtonSizing(btn, small, "arc: 10");
    }

    public static void styleSecondaryButton(JButton btn, boolean small) {
        Theme.styleSecondaryButton(btn);
        btn.setBorderPainted(true);
        applyButtonSizing(btn, small,
                "arc: 10; borderColor: " + toHex(borderColor()) + "; hoverBackground: " + toHex(Theme.BACKGROUND));
    }

    public static void styleDangerButton(JButton btn, boolean small) {
        Theme.styleDangerButton(btn);
        applyButtonSizing(btn, small, "arc: 10");
    }

    public static void styleOutlineDangerButton(JButton btn, boolean small) {
        btn.setBackground(Theme.CARD);
        btn.setForeground(Theme.DANGER);
        btn.setFont(Theme.BODY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyButtonSizing(btn, small,
                "arc: 10; borderColor: " + toHex(tint(Theme.DANGER, 0.45))
                        + "; hoverBackground: " + toHex(tint(Theme.DANGER, 0.90)));
    }

    public static void styleStatusPill(JLabel badge, Color accent) {
        badge.setOpaque(true);
        badge.setBackground(tint(accent, 0.84));
        badge.setForeground(darken(accent, 0.72));
        badge.setFont(Theme.SMALL.deriveFont(Font.BOLD));
        badge.putClientProperty("FlatLaf.style", "arc: 999");
        badge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
    }

    public static void styleHeaderPill(JLabel pill) {
        pill.setOpaque(true);
        pill.setBackground(Theme.CARD);
        pill.setForeground(Theme.PRIMARY);
        pill.setFont(Theme.SMALL.deriveFont(Font.BOLD));
        pill.putClientProperty("FlatLaf.style", "arc: 999");
        pill.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
    }

    public static void renderPosterPreview(JPanel container,
                                          JLabel previewLbl,
                                          String movieTitle,
                                          String posterPath,
                                          int width,
                                          int height) {
        Color posterTint = selectPosterColor(movieTitle);
        container.setBackground(posterTint);
        container.putClientProperty("FlatLaf.style", "arc: 10");
        previewLbl.setHorizontalAlignment(SwingConstants.CENTER);
        previewLbl.setVerticalAlignment(SwingConstants.CENTER);

        if (posterPath != null && !posterPath.trim().isEmpty()) {
            File file = new File(posterPath.trim());
            if (file.isFile()) {
                try {
                    BufferedImage raw = ImageIO.read(file);
                    if (raw != null) {
                        Image scaled = raw.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        previewLbl.setIcon(new ImageIcon(scaled));
                        previewLbl.setText("");
                        return;
                    }
                } catch (IOException ignored) {
                    // Fall back to styled title poster below if file cannot be decoded.
                }
            }
        }

        previewLbl.setIcon(null);
        previewLbl.setForeground(Theme.CARD);
        previewLbl.setFont(Theme.HEADING);
        String displayTitle = (movieTitle == null || movieTitle.trim().isEmpty()) ? "POSTER" : movieTitle.trim();
        previewLbl.setText("<html><div style='text-align:center;width:" + Math.max(50, width - 20)
                + "px;'>" + escapeHtml(displayTitle) + "</div></html>");
    }

    public static Color selectPosterColor(String seedText) {
        Color[] palette = {
                Theme.PRIMARY,
                darken(Theme.SUCCESS, 0.72),
                darken(Theme.WARNING, 0.75),
                darken(Theme.TEXT, 0.90)
        };
        if (seedText == null || seedText.isEmpty()) {
            return palette[0];
        }
        int idx = Math.abs(seedText.hashCode()) % palette.length;
        return palette[idx];
    }

    public static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private static void applyButtonSizing(JButton btn, boolean small, String flatStyle) {
        int height = small ? 28 : 34;
        btn.setPreferredSize(new Dimension(-1, height));
        btn.putClientProperty("FlatLaf.style", flatStyle);
    }

    private static Color darken(Color color, double factor) {
        int r = (int) Math.round(color.getRed() * factor);
        int g = (int) Math.round(color.getGreen() * factor);
        int b = (int) Math.round(color.getBlue() * factor);
        return new Color(clamp(r), clamp(g), clamp(b));
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
