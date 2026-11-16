package ui.admin;

import javax.swing.*;

public class ApproveScreening {
    private JPanel mainPanel;
    private JScrollPane cardsScrollPane;
    private JPanel cardsPanel;
    private JPanel card1Panel;
    private JLabel avatar1Label;
    private JPanel info1Panel;
    private JLabel title1Label;
    private JLabel subtitle1Label;
    private JLabel hall1Label;
    private JPanel buttons1Panel;
    private JButton approve1Btn;
    private JButton reject1Btn;
    private JPanel card2Panel;
    private JLabel avatar2Label;
    private JLabel title2Label;
    private JLabel subtitle2Label;
    private JLabel hall2Label;
    private JPanel buttons2Panel;
    private JButton approve2Btn;
    private JButton reject2Btn;
    private JPanel info2Panel;

    public ApproveScreening() {
        cardsScrollPane.setViewportView(cardsPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


}
