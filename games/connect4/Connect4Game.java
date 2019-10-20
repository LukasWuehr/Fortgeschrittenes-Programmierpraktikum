import java.util.Scanner;
/**
 * Connect4Game
 */
public class Connect4Game extends Game {
    private Connect4Player player1, player2;
    public Connect4Game(Player player1, Player player2){
        this.player1 = player1; this.player2=player2;
        this.player1.color=PlayerColor.RED;
        this.player2.color=PlayerColor.YELLOW;

        System.out.println("Spielfeldgröße Höhe, Breite:");
        Scanner scan = new Scanner(System.in);
        int height = scan.nextInt();
        int with = scan.nextInt();

    }
}