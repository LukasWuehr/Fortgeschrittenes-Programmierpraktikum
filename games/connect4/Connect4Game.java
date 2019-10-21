package games.connect4;
import games.*;
import java.util.Scanner;
/**
 * Connect4Game
 */
public class Connect4Game extends Game {
    private Connect4Player player1, player2;
    public Connect4Game(){
        this.player1 = (Connect4Player)getPlayer1(); this.player2=(Connect4Player)getPlayer2();
        this.player1.setColor(PlayerColor.RED);
        this.player2.setColor(PlayerColor.YELLOW);
    }
    public void start(){
        System.out.println("Spielfeldgröße Höhe, Breite:");
        Scanner scan = new Scanner(System.in);
        int height = scan.nextInt();
        int with = scan.nextInt();

    }
}