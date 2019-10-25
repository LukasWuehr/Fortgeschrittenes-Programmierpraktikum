package games;
import java.util.Scanner;

import games.chomp.ChompGame;
import games.connect4.Connect4Game;
/**
 *  Game
 */
public abstract class Game {

    private Player player1 = new Player();
    private Player player2 = new Player();
    private Board board;
    private int turns;
    /**
     * @return the player1
     */
    public Player getPlayer1() {
        return this.player1;
    }
    /**
     * @return the player2
     */
    public Player getPlayer2() {
        return this.player2;
    }
    public abstract void start();
}
