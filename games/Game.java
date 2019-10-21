package games;
import java.util.Scanner;

import games.connect4.Connect4Game;
/**
 *  Game
 */
public abstract class Game {

    private Player player1, player2;
    private Board board;
    private int turns;
    
    /**
     * @return the player1
     */
    public Player getPlayer1() {
        return player1;
    }
    /**
     * @return the player2
     */
    public Player getPlayer2() {
        return player2;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hallo \nWas möchtest du Spielen?\n(1) 1SPIELER\t(2) 2SPIELER");
        int input = scan.nextInt();
        if (input==2) {                      //Erstellen von Spielern
            Player player2 = new Player("Spieler 2", true);
        } else if(input==1) {
            Player player2 = new Player("Computer", false);
        }
        Player player1 = new Player("Spieler 1", true);
        
        System.out.println("(1)Vier Gewinnt\t (2)Fressen"); //Spiele Auswahl
        input = scan.nextInt();
        if (input==1) {
            Connect4Game game = new Connect4Game();
            game.start();
        } else if(input==2) {
            //Fressen
        }
    }
}
