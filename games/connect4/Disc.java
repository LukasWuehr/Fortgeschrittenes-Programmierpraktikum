package games.connect4;

import GUI.Connect4Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Disc
 */
public class Disc {
    private Connect4Gui gui;
    private  int color;
    private JButton button = new JButton("⚫");

    public Disc(){
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
    }

    public void setGui(Connect4Gui gui) {
        this.gui = gui;
    }

    public JButton getButton(){
        return button;
    }

    public int getColor(){
        return color;
    }
    public void setColor(int color){
        this.color=color;
        switch (color){
            case 1:
                button.setForeground(Color.RED);
                break;
            case 0:
                button.setForeground(Color.YELLOW);
                break;
            default:
                button.setForeground(Color.WHITE);
                break;
        }
    }
}