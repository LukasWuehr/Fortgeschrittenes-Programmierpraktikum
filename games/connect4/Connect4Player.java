package games.connect4;
import games.*;
/**
 * Connect4Player
 */
public class Connect4Player extends Player {
    Connect4Player(String name,boolean isHuman){ super(name,isHuman);}
    PlayerColor color;
    /**
     * @param color the color to set
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }
}