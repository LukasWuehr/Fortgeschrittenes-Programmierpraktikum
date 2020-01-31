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
    private JButton button;

    public Disc(){
        this.button = new JButton("⚫");
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        this.color = 2;
    }

    public void setFont(){
        Font buttonFont = button.getFont();
        String buttonText = button.getText();
        int stringWidth = button.getFontMetrics(buttonFont).stringWidth(buttonText);
        int componentWidth = Math.min(button.getWidth(), button.getHeight());
// Find out how much the font can grow in width.
        double widthRatio = (double)componentWidth / (double)stringWidth;
        int newFontSize = (int)(10 * widthRatio);
        int componentHeight = Math.min(button.getWidth(), button.getHeight());
// Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
// Set the label's font size to the newly determined size.
        button.setFont(new Font(buttonFont.getName(), Font.PLAIN, fontSizeToUse));
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