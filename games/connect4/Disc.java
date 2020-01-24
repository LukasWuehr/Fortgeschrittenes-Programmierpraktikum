package games.connect4;

import javax.swing.*;
import java.awt.*;

/**
 * Disc
 */
public class Disc {

    private  int color;
    private JButton button = new JButton("o");

    public Disc(int color){
        setColor(color);
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
                button.setBackground(Color.RED);
                break;
            case 0:
                button.setBackground(Color.YELLOW);
                break;
            default:
                button.setBackground(Color.BLUE);
                break;
        }
    }
}