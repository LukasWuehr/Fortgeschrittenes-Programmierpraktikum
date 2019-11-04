//verbindet games, server client
//regelt alles
import java.util.Scanner;
import games.chomp.*;
import games.connect4.*;
import games.*;
/**
 * Menu
 */
public class Menu {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello \nHow many players?\n(1) 1Player\t(2) 2Player");
        int input = scan.nextInt();
        Player player1 = new Player("Player 1", true);

        if (input==2) {                      //Erstellen von Spielern
            System.out.println("(1)Connect4\t (2)Chomp"); //Spiele Auswahl
            input = scan.nextInt();
            Player player2 = new Player("Player 2", true);
            if (input==1) {
                Connect4Game game = new Connect4Game(player1,player2);
                game.start();
            } else if(input==2) {
                ChompGame game = new ChompGame(player1,player2);
                game.start();
            }
            
        } else if(input==1) {
            System.out.println("(1)Connect4\t (2)Chomp"); //Spiele Auswahl
            input = scan.nextInt();
            Player player2 = new Player("Computer", false);
            if (input==1) {
                Connect4Game game = new Connect4Game(player1,player2);
                game.start();
            } else if(input==2) {
                ChompGame game = new ChompGame(player1,player2);
                game.start();
            }
        } 
    }
}