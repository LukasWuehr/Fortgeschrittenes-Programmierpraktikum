package GUI;

import SC_Kom.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainScreen {
    private JPanel mainPanel;
    private JTextArea chat;
    private JTextField chatInput;
    private JButton sendButton;
    private JPanel chatPanel;
    private JPanel gamePanel;
    private JComboBox atPlayer;
    private JTextArea playerOnline;
    private ArrayList<String> players = new ArrayList<String>();

    public MainScreen(JFrame frame) {
        frame.setContentPane(mainPanel);
        frame.setTitle("GAME");
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        atPlayer.addItem("@ALL");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = chatInput.getText();
                chatInput.setText("");
                processChatMessage(message);
            }
        });
    }

    private void processChatMessage(String message) {

    }

    private void sendChatMessage(String message) {
        synchronized (this) {
            Message.sendMessage(6);
            Message.sendMessage(message);
        }
    }

    public void addPlayer(String player) {
        players.add(player);
        atPlayer.addItem(player);
        updatePlayerOnline();
    }

    public void removePlayer(String player) {
        int ptr = players.indexOf(player);
        players.remove(ptr);
        atPlayer.removeItem(player);
        updatePlayerOnline();
    }

    private void updatePlayerOnline() {
        String text = "";
        for (String player : players) {
            text += player + "\n";
        }
        playerOnline.setText(text);
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        mainPanel.add(chatPanel, BorderLayout.EAST);
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setRows(20);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(chat, gbc);
        chatInput = new JTextField();
        chatInput.setColumns(15);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(chatInput, gbc);
        sendButton = new JButton();
        sendButton.setText("Send");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(sendButton, gbc);
        atPlayer = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(atPlayer, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(spacer1, gbc);
        playerOnline = new JTextArea();
        playerOnline.setRows(8);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(playerOnline, gbc);
        gamePanel = new JPanel();
        gamePanel.setLayout(new CardLayout(0, 0));
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(panel1, "Card1");
        final JLabel label1 = new JLabel();
        label1.setText("MAINMENU");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(panel2, "Card2");
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(panel3, "Card3");
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
