//verbindet games, server client
//regelt alles
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
import games.chomp.*;
import games.connect4.*;
import games.*;
/**
 * Menu
 */
public class Menu {

    public boolean login(DataInputStream in, DataOutputStream out){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1)New Player\t(2)Log in");
            int input = scan.nextInt();
            if (input==1) {
                out.writeByte(3);
                while(true){
                    switch (in.readByte()) {
                        case 2:
                            System.out.println("Insert Your Name");
                            String name = scan.nextLine();
                            out.writeUTF(name);
                            break;
                        case 3:
                            System.out.println("Insert Your Password"); //TODO: hashing
                            String pwd = scan.nextLine();
                            out.writeUTF(pwd);
                            break;
                        case 4:
                            break;
                        case 5:
                            return true;
                        case 1:
                            System.out.println("Try Again");
                        case 0:
                        default:
                            return false;
                    }
                }
            } else if(input==2){
                out.writeByte(2);
                while(true){
                    switch (in.readByte()) {
                        case 3:
                        case 1:
                            System.out.println("Name already used!");
                            out.writeByte(2);
                            break;
                        case 0:
                        default:
                            return false;
                    }
                    String name = scan.nextLine();
                out.writeUTF(name);
                out.writeBoolean(true);
            }
                
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void start(String playerName) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hello \nHow many players?\n(1) 1Player\t(2) 2Player");
        int input = scan.nextInt();
        Player player1 = new Player(playerName, true);

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