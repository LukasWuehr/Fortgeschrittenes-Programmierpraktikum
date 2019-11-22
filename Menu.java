//verbindet games, server client
//regelt alles
import games.*;
import games.chomp.*;
import games.connect4.*;
import java.util.Scanner;
/**
 * Menu
 */
public class Menu {
    String playerName;
    public Menu(String playerName){
        this.playerName = playerName;
    }
    
    public void start() {
        Player player1 = new Player(playerName, true);
        System.out.println("Hello "+player1.getPlayerName());
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello \nHow many players?\n(1) 1Player\t(2) 2Player");
        int input = scan.nextInt();
        

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