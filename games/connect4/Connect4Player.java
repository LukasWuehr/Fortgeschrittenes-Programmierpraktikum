package games.connect4;
import games.*;
/**
 * Connect4Player
 */
public class Connect4Player extends Player {
    public Connect4Player(String name,boolean isHuman){ super(name,isHuman);}
    public Connect4Player(){super();}
    //PlayerColor color;
    int color;
    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }
}