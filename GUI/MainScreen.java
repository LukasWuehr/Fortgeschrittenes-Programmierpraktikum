package GUI;

import SC_Kom.Message;
import games.Player;

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
    private JScrollPane scrollText;
    //-------------------------------------------------------------------
    private ArrayList<String> players = new ArrayList<String>();
    private String playerName;
    private MainMenu menu;
    public Connect4Gui connectGui;
    public ChompGui chompGui;
    private CardLayout card;
    private Boolean HaveChompCard = false;

    public MainScreen(JFrame frame, String playerName) {
        this.card = (CardLayout) gamePanel.getLayout();
        this.playerName = playerName;
        sendButton.setForeground(Color.DARK_GRAY);
        sendButton.setBackground(Color.LIGHT_GRAY);
        atPlayer.setBackground(Color.LIGHT_GRAY);
        atPlayer.setForeground(Color.DARK_GRAY);
        menu = new MainMenu(this);
        frame.setContentPane(mainPanel);
        frame.setTitle("GAME");
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        atPlayer.addItem("@ALL");
        sendButton.addActionListener(new ActionListener() { //send chat button
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = chatInput.getText();
                chatInput.setText("");
                processChatMessage(message);
            }
        });
        //set menu panel
        JPanel menuPanel = menu.getCurrentPanel();
        gamePanel.add("Card1", menuPanel);
        card.first(gamePanel);

    }

    public void startGame(String vsPlayerName, String game, int length, int height, int startNumb) {
        atPlayer.setSelectedItem(vsPlayerName);
        Player player1, player2;
        if (startNumb == 1) {
            if (vsPlayerName.equals("@Computer")) {     //local game
                player1 = new Player(playerName, false);
                player2 = new Player(vsPlayerName, false);
            } else {
                player1 = new Player(playerName, true);
                player2 = new Player(vsPlayerName, true);
            }
        } else {
            player1 = new Player(vsPlayerName, true);
            player2 = new Player(playerName, true);
        }
        if (game.equals("Chomp")) { //decide game
            chompGui = new ChompGui(length, height, this, player1, player2, startNumb);
            gamePanel.add("ChompCard", chompGui.getChompPanel()); //set panel/ screen
            chompGui.setScreen(this);
            card.last(gamePanel);
            HaveChompCard = true;
        } else {
            connectGui = new Connect4Gui(player1, player2, length, height, startNumb, this);
            gamePanel.add("ConnectFourCard", connectGui.getPanel()); //set panel/ screen
            connectGui.setScreen(this);
            card.last(gamePanel);
            connectGui.start();
        }
    }

    public void stopGame(String stop) {
        JOptionPane.showMessageDialog(new JFrame(), stop);//info window
        if (HaveChompCard) card.removeLayoutComponent(chompGui.getChompPanel());
        else {
            card.removeLayoutComponent(connectGui.getPanel());
        }  //only menuPanel
        card.first(gamePanel);//show menu
    }

    public void setInvite(String invite) {
        menu.setInvites(invite);
    }

    public String getPLayer() {
        return playerName;
    }

    private void processChatMessage(String message) {
        String name = (String) atPlayer.getSelectedItem();
        addChatMessage("You: " + message); // write to your self
        synchronized (this) {
            if (name.equals("@ALL")) {
                Message.sendMessage(7); // send to all
            } else {
                Message.sendMessage(6); // send to player
                Message.sendMessage(name);
            }
            Message.sendMessage(message);
        }

    }

    public void addChatMessage(String message) {
        chat.setText(chat.getText() + "\n" + message);
    }

    public void addPlayer(String player) {
        players.add(player);
        if (!playerName.equals(player)) {
            atPlayer.addItem(player);
            menu.setPlayerComboBox(player);
        }
        updatePlayerOnline();
    }

    public void removePlayer(String player) {
        players.remove(player);
        atPlayer.removeItem(player);
        menu.removePlayerComboBox(player);
        updatePlayerOnline();
    }

    private void updatePlayerOnline() {
        StringBuilder text = new StringBuilder();
        for (String player : players) {
            text.append(player).append("\n");
        }
        playerOnline.setText(text.toString());
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
        chatInput = new JTextField();
        chatInput.setColumns(15);
        Font chatInputFont = this.$$$getFont$$$("JetBrains Mono", -1, 14, chatInput.getFont());
        if (chatInputFont != null) chatInput.setFont(chatInputFont);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(chatInput, gbc);
        sendButton = new JButton();
        Font sendButtonFont = this.$$$getFont$$$("JetBrains Mono", -1, -1, sendButton.getFont());
        if (sendButtonFont != null) sendButton.setFont(sendButtonFont);
        sendButton.setText("Send");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(sendButton, gbc);
        atPlayer = new JComboBox();
        Font atPlayerFont = this.$$$getFont$$$("JetBrains Mono", -1, -1, atPlayer.getFont());
        if (atPlayerFont != null) atPlayer.setFont(atPlayerFont);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(atPlayer, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatPanel.add(spacer1, gbc);
        scrollText = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(scrollText, gbc);
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setRows(20);
        scrollText.setViewportView(chat);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(scrollPane1, gbc);
        playerOnline = new JTextArea();
        playerOnline.setEditable(false);
        playerOnline.setRows(8);
        scrollPane1.setViewportView(playerOnline);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("JetBrains Mono", -1, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("PlayerOnline");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        chatPanel.add(label1, gbc);
        gamePanel = new JPanel();
        gamePanel.setLayout(new CardLayout(0, 0));
        mainPanel.add(gamePanel, BorderLayout.CENTER);
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
        return mainPanel;
    }

}
