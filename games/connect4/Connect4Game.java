package games.connect4;
import games.*;
import java.util.Scanner;
/**
 * Connect4Game
 */
public class Connect4Game extends Game {
    private Connect4Player player1 =new Connect4Player(), player2 = new Connect4Player();
    private Connect4Board board;
    public Connect4Game(){
        this.player1 = (Connect4Player)getPlayer1(); this.player2=(Connect4Player)getPlayer2();

    }
    public void start(){
        player1.setColor(2);//PlayerColor.RED);
        player2.setColor(1);//PlayerColor.YELLOW);
        System.out.println("Spielfeldgröße Höhe, Breite:");
        Scanner scan = new Scanner(System.in);
        int height = scan.nextInt();
        int lenght = scan.nextInt();
        board = new Connect4Board(height, lenght);
        
    }
    int nextTurn(){

    }
}