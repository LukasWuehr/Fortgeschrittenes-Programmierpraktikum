/*package GUI;

import SC_Kom.Message;
        import games.Game;
        import games.Player;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.MouseAdapter;
        import java.awt.event.MouseEvent;

public class ChompGUI extends Game {
    private JPanel ChompPanel;
    JButton ButtonArray[][];
    int length;
    int height;
    Message chompMessage;
    Player player;
    int turns = 0;


    public ChompGUI(int length, int height, Message message) {
        this.chompMessage = message;
        ChompPanel = new JPanel(new GridLayout(length, height));
        ButtonArray = new JButton[length][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                ButtonArray[j][i] = new JButton();
                ButtonArray[j][i].setBackground(Color.WHITE);
                ChompPanel.add(ButtonArray[j][i]);
                ButtonArray[j][i].addActionListener(new GridActionListener(j, i));
                ButtonArray[j][i].addMouseListener(new GridMouseAdapter(j,i,ButtonArray));
            }
        }
    }

    public void colorButtons(int lengthindex, int heightindex) {
        for (int i = heightindex; i < height; i++) {
            for (int j = lengthindex; j < length; j++) {
                ButtonArray[j][i].setBackground(Color.BLACK);
            }
        }
    }

    public void colorButtons(int lengthindex, int heightindex, Color x){//Färbt nur weiße Felder!
        for(int i = heightindex; i < height; i++){
            for(int j = lengthindex; j < length; j++){
                if(ButtonArray[j][i].getBackground()==Color.WHITE) ButtonArray[j][i].setBackground(x);
            }
        }
    }

    public class GridActionListener implements ActionListener {
        private int lengthindex;
        private int heightindex;

        public GridActionListener(int length, int height) {
            this.lengthindex = length;
            this.heightindex = height;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (ButtonArray[lengthindex][heightindex].getBackground() == Color.WHITE) {
                turns++;
                colorButtons(lengthindex, heightindex);
                String turn = "chomp," + player.getPlayerName() + "," + Integer.toString(turns) + ",";
                turn += Integer.toString(heightindex) + "," + Integer.toString(lengthindex);
                chompMessage.sendMessage(10);
                chompMessage.sendMessage(turns);
                if(checkLost()){}//Dann muss ausgegeben werden, dass man verloren hat
            }
        }
    }

    public class GridMouseAdapter extends MouseAdapter{
        private int lengthindex;
        private int heightindex;
        private JButton[][] Buttons;

        public GridMouseAdapter(int l, int h, JButton Buttons[][]){
            this.lengthindex = l;
            this.heightindex = h;
            this.Buttons = Buttons;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            if(Buttons[lengthindex][heightindex].getBackground() == Color.WHITE)
                colorButtons(lengthindex,heightindex,Color.RED);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            returnButtons(Buttons);
        }
    }

    public void returnButtons(JButton[][] Buttons){this.ButtonArray = Buttons;}
    public boolean checkTaken(int lengthindex, int heightindex) {
        if (ButtonArray[lengthindex][heightindex].getBackground() == Color.WHITE) return false;
        else return true;
    }

    public boolean checkLost(){return checkTaken(0,0);}

    @Override public void start(){}

} */