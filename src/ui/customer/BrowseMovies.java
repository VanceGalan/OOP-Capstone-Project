package ui.customer;

import javax.swing.*;
import java.awt.*;

public class BrowseMovies {
    private JButton allMoviesButton;
    private JButton actionButton;
    private JButton comedyButton;
    private JButton romanceButton;
    private JTextField textField1;
    private JPanel mainPanel;
    private JLabel movieTitle1Label;
    private JPanel moviePanel;
    private JPanel title_genrePanel;
    private JLabel movieTitle2Label;
    private JLabel genreLabel;
    private JLabel mtrcbLabel;
    private JLabel genre_timeDurationLabel;
    private JButton viewShowButton;
    private JLabel showTime_DayLabel;
    private JLabel availableShowTimeLabel;

    public BrowseMovies() {
        Color white = new Color(255, 255, 255);
        Color blue = new Color(137,207,240);
        moviePanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        moviePanel.setBackground(white);

        title_genrePanel.setBackground(blue);

        viewShowButton.setBackground(blue);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Browse Movies");
        frame.setContentPane(new BrowseMovies().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(820,560);
        frame.setLocationRelativeTo(null);
    }
}
