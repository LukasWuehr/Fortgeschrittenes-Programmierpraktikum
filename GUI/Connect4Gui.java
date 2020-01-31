package GUI;

import SC_Kom.Message;
import games.Game;
import games.Player;
import games.connect4.Connect4Board;
import games.connect4.Disc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class Connect4Gui extends Game {
    private JLabel playersLabel;
    private JLabel turnLabel;
    private JButton exitButton;
    private JPanel connect4Panel;
    private JPanel connect4BoardPanel;
    //-------------------------------------
    private Disc[][] discs;
    private Player player1, player2;
    private int startPlayer;
    private Connect4Board board;
    private int turns = 0;
    private MainScreen screen;


    public Connect4Gui(Player player1, Player player2, int length, int height, int startPlayer, MainScreen mainScreen) {
        setPlayers(player1, player2);
        this.startPlayer = startPlayer;
        this.discs = new Disc[length][height];
        board = new Connect4Board(discs, this);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message.sendMessage(11);
                screen.stopGame("Exit Game");
            }
        });
    }

    public void setScreen(MainScreen screen) {
        this.screen = screen;
    }

    private void setButtons(Player player) {
        connect4BoardPanel.setLayout(new GridLayout(discs[0].length + 1, discs.length));
        for (int i = 0; i < discs.length; i++) {
            JButton topButton = new JButton("▼");
            connect4BoardPanel.add(topButton);
            topButton.setForeground(Color.WHITE);
            topButton.setBackground(Color.BLUE);
            topButton.addActionListener(new GridActionListener(i, 0) {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    board.setDisc(i, player, turns);
                }
            });
            topButton.addMouseListener(new GridMouseListener(i, 0) {
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    for (Disc disc : discs[i]) {
                        disc.getButton().setBackground(Color.CYAN);
                    }
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    for (Disc disc : discs[i]) {
                        disc.getButton().setBackground(Color.BLUE);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    board.setDisc(i, player, turns);
                }
            });
        }

        for (int i = 0; discs[0].length > i; i++) { // links oben 0,0
            for (int j = 0; discs.length > j; j++) {
                Disc disc = discs[j][i];
                connect4BoardPanel.add(disc.getButton());
                //disc.getButton().setText(j + " " + i);
                disc.getButton().addActionListener(new GridActionListener(i, j) {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        board.setDisc(j, player, turns);
                    }
                });
                disc.getButton().addMouseListener(new GridMouseListener(i, j) {
                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {
                        for (Disc disc : discs[j]) {
                            disc.getButton().setBackground(Color.CYAN);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {
                        for (Disc disc : discs[j]) {
                            disc.getButton().setBackground(Color.BLUE);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        board.setDisc(j, player, turns);
                    }
                });
            }
        }
    }

    public void setFontSize() {
        for (Disc[] discss : discs) {
            for (Disc disc : discss) {
                disc.setFont();
            }
        }
    }

    public Connect4Board getBoard() {
        return board;
    }

    @Override
    public void start() {
        for (int i = 0; discs[0].length > i; i++) { // links oben 0,0
            for (int j = 0; discs.length > j; j++) {
                discs[j][i] = new Disc();
            }
        }
        if (startPlayer == 2) {
            setButtons(player2);
            turns = -1;
            changeClickable();
            setFontSize();
        } else {
            setButtons(player1);
            setTurnLabel(turns);
            setFontSize();
        }
    }


    public void changeClickable() {
        connect4BoardPanel.setEnabled(!connect4BoardPanel.isEnabled());
        turns++;
        setTurnLabel(turns);
        if (turns > discs.length * discs[0].length) draw();
    }

    private void draw() {
        //TODO: exit + message
        screen.stopGame("DRAW");
    }

    public void win() {
        Player winner;
        if (turns % 2 == 0) { //gewinner finden
            winner = player1;
        } else {
            winner = player2;
        }
        winner.addWin();
        System.out.printf("%s Wins\n", winner.getPlayerName());
        //TODO: exit+ message
        screen.stopGame("WINNER: " + winner.getPlayerName());

    }

    public JPanel getPanel() {
        return connect4Panel;
    }

    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        playersLabel.setText(player1.getPlayerName() + " vs " + player2.getPlayerName());
    }

    public void setTurnLabel(int turn) {
        if (turn % 2 == 0) {
            turnLabel.setText("Turn of " + player1.getPlayerName());
        } else {
            turnLabel.setText("Turn of " + player2.getPlayerName());
        }
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
        connect4Panel = new JPanel();
        connect4Panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        playersLabel = new JLabel();
        Font playersLabelFont = this.$$$getFont$$$("JetBrains Mono", -1, -1, playersLabel.getFont());
        if (playersLabelFont != null) playersLabel.setFont(playersLabelFont);
        playersLabel.setText("Player1 vs PLayer2");
        connect4Panel.add(playersLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        turnLabel = new JLabel();
        Font turnLabelFont = this.$$$getFont$$$("JetBrains Mono", -1, -1, turnLabel.getFont());
        if (turnLabelFont != null) turnLabel.setFont(turnLabelFont);
        turnLabel.setText("Turn");
        connect4Panel.add(turnLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        Font exitButtonFont = this.$$$getFont$$$("JetBrains Mono", -1, -1, exitButton.getFont());
        if (exitButtonFont != null) exitButton.setFont(exitButtonFont);
        exitButton.setText("Exit");
        connect4Panel.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        connect4Panel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        connect4BoardPanel = new JPanel();
        connect4BoardPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        connect4BoardPanel.setEnabled(true);
        connect4Panel.add(connect4BoardPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return connect4Panel;
    }

}
