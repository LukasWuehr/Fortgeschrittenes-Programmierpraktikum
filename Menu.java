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
        System.out.println("Hallo \nWas möchtest du Spielen?\n(1) 1SPIELER\t(2) 2SPIELER");
        int input = scan.nextInt();
        Player player1 = new Player("Spieler 1", true);

        if (input==2) {                      //Erstellen von Spielern
            System.out.println("(1)Vier Gewinnt\t (2)Fressen"); //Spiele Auswahl
            input = scan.nextInt();
            Player player2 = new Player("Spieler 2", true);
            if (input==1) {
                Connect4Game game = new Connect4Game(player1,player2);
                game.start();
            } else if(input==2) {
                ChompGame game = new ChompGame(player1,player2);
                game.start();
            }
            
        } else if(input==1) {
            System.out.println("(1)Vier Gewinnt\t (2)Fressen"); //Spiele Auswahl
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