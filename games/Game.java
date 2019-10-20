import java.util.Scanner;
/**
 *  Game
 */
public abstract class Game {

    private Player player1, player2;
    private Board board;
    private int turns;
    
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
            
        } else if(input==2) {
            //Fressen
        }
    }
}
