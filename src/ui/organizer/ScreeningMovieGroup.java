package ui.organizer;

import util.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * Reusable Designer-built section container grouping screening cards under one movie heading.
 */
public class ScreeningMovieGroup {
    private JPanel mainPanel;
    private JLabel groupHeaderLbl;
    private JPanel cardsContainerPanel;

    public ScreeningMovieGroup(String movieTitle, int screeningCount) {
        initComponents(movieTitle, screeningCount);
    }

    private void initComponents(String movieTitle, int screeningCount) {
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupHeaderLbl.setFont(Theme.BODY.deriveFont(Font.BOLD));
        groupHeaderLbl.setForeground(Theme.TEXT);
        String suffix = screeningCount == 1 ? " screening" : " screenings";
        groupHeaderLbl.setText(movieTitle + " (" + screeningCount + suffix + ")");

        cardsContainerPanel.setLayout(new BoxLayout(cardsContainerPanel, BoxLayout.Y_AXIS));
    }

    public void addScreeningCard(ScreeningCard card) {
        if (cardsContainerPanel.getComponentCount() > 0) {
            cardsContainerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        cardsContainerPanel.add(card.getMainPanel());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
