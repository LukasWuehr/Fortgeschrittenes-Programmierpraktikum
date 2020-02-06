package GUI;

import SC_Kom.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import games.Player;


public class ChompGui {

    private MainScreen screen;
    private int length;
    private int height;
    private Player player1, player2;
    private int turns;
    private boolean yourTurn;
    private String playerOneTurn, playerTwoTurn;
    private int startNumb;
    //-------
    private JPanel chompPanel;
    private JLabel turnLabel;
    private JLabel playerLabel;
    private JButton exitButton;
    private JLabel gameInfo;
    private JPanel gamePanel;
    JButton ButtonArray[][];


    public ChompGui(int length, int height, MainScreen MainScreen, Player player1, Player player2, int starNumb) {
        this.startNumb = starNumb;
        this.player1 = player1;
        this.player2 = player2;
        this.screen = MainScreen;
        this.length = length;
        this.height = height;
        turns = 0;
        if (starNumb == 1) {
            yourTurn = true;
            playerOneTurn = "It's your turn!";
            playerTwoTurn = "It's " + player2.getPlayerName() + "'s turn!";
        } else {
            yourTurn = false;
            playerOneTurn = "It's " + player1.getPlayerName() + "'s turn!";
            playerTwoTurn = "It's your turn!";
        }
        this.chompPanel.setLayout(new GridLayout(length, height));
        ButtonArray = new JButton[length][height];
        exitButton = new JButton();
        playerLabel = new JLabel();
        playerLabel.setText(player1.getPlayerName() + "vs" + player2.getPlayerName());
        turnLabel.setText("Turns = " + turns);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Message.sendMessage(11);
                screen.stopGame("Exit Game");
            }
        });

        for (int i = 0; i < length; i++) {//Initiiere Buttonarray
            for (int j = 0; j < height; j++) {
                ButtonArray[i][j] = new JButton();
                ButtonArray[i][j].setBackground(Color.WHITE);
                chompPanel.add(ButtonArray[i][j]);
                ButtonArray[i][j].addMouseListener(new GridMouseListener(i, j) {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (ButtonArray[i][j].getBackground() == Color.WHITE && yourTurn) {
                            incrementTurns();
                            colorButtons(i, j);
                            if (player1.getIsHuman() && player2.getIsHuman()) {//soll nur bei echten spielern spielzug versenden
                                String turn = "chomp," + player1.getPlayerName() + "," + turns + ",";
                                turn += j + "," + i;
                                Message.sendMessage(10);
                                Message.sendMessage(turn);
                            }
                            if (checkLost()) {
                                gameInfo.setText("You have lost!");
                            }
                            if (player1.getIsHuman() == false && player2.getIsHuman() == false && checkLost() == false) {//Fall spiele gegen Computer
                                incrementTurns();
                                Random rn = new Random();
                                int rnl = rn.nextInt(length);
                                int rnh = rn.nextInt(height);
                                while (ButtonArray[rnl][rnh].getBackground() != Color.WHITE) {//Suche zufällig nach einem Spielzug für PC
                                    rnl = rn.nextInt(length);
                                    rnh = rn.nextInt(height);
                                }
                                colorButtons(rnl, rnh);
                                if (checkWon()) gameInfo.setText("You have won!");
                            }
                        }
                    }

                    /*@Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        if (ButtonArray[i][j].getBackground() != Color.BLACK)
                            colorButtons(i, j, Color.RED);
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        if (ButtonArray[i][j].getBackground() != Color.BLACK)
                            colorButtons(i, j, Color.WHITE);
                    }*/
                });
            }
        }
    }


    public void colorButtons(int i, int j) {
        for (int k = i; k < length; k++) {
            for (int l = j; l < height; l++) {
                ButtonArray[k][l].setBackground(Color.BLACK);
            }
        }
    }

    public JPanel getChompPanel() {
        return gamePanel;
    }

    public void colorButtons(int i, int j, Color x) {//Färbt nur nichtschwarze Felder!
        for (int k = i; k < length; k++) {
            for (int l = j; l < height; l++) {
                if (ButtonArray[k][l].getBackground() != Color.BLACK) ButtonArray[i][j].setBackground(x);
            }
        }
    }

    public boolean checkTaken(int lengthindex, int heightindex) {
        if (ButtonArray[lengthindex][heightindex].getBackground() == Color.BLACK) return true;
        else return false;
    }

    public boolean checkLost() {
        return checkTaken(0, 0);
    }

    public boolean checkWon() {//guckt nachdem der Gegner gespielt hat(nur bei PC)
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.height; j++) {
                if (ButtonArray[i][j].getBackground() != Color.BLACK) return false;
            }
        }
        return true;
    }

    public void incrementTurns() {
        this.turns++;
        this.yourTurn = !this.yourTurn;
        turnLabel.setText("Turns = " + turns);
        if (turns % 2 == 1 && startNumb == 1) gameInfo.setText(playerTwoTurn);
        else gameInfo.setText(playerOneTurn);
    }

    public void setScreen(MainScreen screen) {
        this.screen = screen;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        playerLabel = new JLabel();
        playerLabel.setText("Player1 vs Player2");
        playerLabel.setVerticalAlignment(0);
        gamePanel.add(playerLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        turnLabel = new JLabel();
        turnLabel.setText("Turns");
        gamePanel.add(turnLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chompPanel = new JPanel();
        chompPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(chompPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        gamePanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        gamePanel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gameInfo = new JLabel();
        gameInfo.setText("Label");
        gamePanel.add(gameInfo, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return gamePanel;
    }

}
