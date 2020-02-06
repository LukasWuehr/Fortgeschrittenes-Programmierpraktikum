package games.connect4;

import javax.swing.*;
import java.awt.*;


public class Disc {
    private Connect4Gui gui;
    private  int color;
    private JButton button;
    private ImageIcon icon;

    public Disc(){
        icon = new ImageIcon("games/connect4/dotWhite.png");
        this.button = new JButton(icon);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        this.color = 2;
    }

    public void setFont(int size){
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance( size, size,  Image.SCALE_FAST ) ;
        button.setIcon(new ImageIcon( newimg ));
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
        int size = Math.min(button.getWidth(), button.getHeight()) - Math.max(button.getInsets().left, button.getInsets().top);
        switch (color){
            case 1:
                icon = new ImageIcon("games/connect4/dotRed.png");
                setFont(size);
                break;
            case 0:
                icon = new ImageIcon("games/connect4/dotYellow.png");
                setFont(size);
                break;
            default:
                icon = new ImageIcon("games/connect4/dotWhite.png");
                setFont(size);
                break;
        }
    }
}