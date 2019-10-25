package games.connect4;
import games.*;
import java.util.Scanner;
import java.util.Random;
/**
 * Connect4Game
 */
public class Connect4Game extends Game {
    private int turns=0;
    private Player player1 , player2;
    private Connect4Board board;
    public Connect4Game(Player player1, Player player2){
        this.player1=player1; this.player2=player2;
    }
     public void start(){
        boolean turnStatus;
        System.out.println("Board Height, Length:"); //boardsize
        Scanner scan = new Scanner(System.in);
        int height = scan.nextInt();
        int lenght = scan.nextInt();
        board = new Connect4Board(height, lenght);
        board.draw();
        while (turns<lenght*height) {   //beginning of playing
            if (turns%2==0) {
                turnStatus = nextTurn(this.player1);
            } else {
                turnStatus = nextTurn(this.player2);
            }
            if (turnStatus) {
                win();
                return;
            }
            turns++;
        }
        System.out.println("DRAW");
    }

    private boolean nextTurn(Player player){ //next turn of player
        Scanner scan = new Scanner(System.in);
        int discIsSet=0;
        while(0==discIsSet){
            if (player.getIsHuman()){
            System.out.printf("%s\n Choose Field(0-%d): ", player.getPlayerName(),board.getLength()-1);
            discIsSet = board.setDisc(scan.nextInt(), player,this.turns);
            } else {
                System.out.printf("Computer:\n");
                Random rn = new Random();
                discIsSet = board.setDisc(rn.nextInt(board.getLength()), player, this.turns);
            }
            
        }
        board.draw();
        if (discIsSet==2) { 
            return true;    //return win
        } else {
            return false;   //return next turn
        }
    }

    private void win(){
        Player winner = new Player();
        if (turns%2==0) { //gewinner finden
            winner= player1;
        } else {
            winner = player2;
        }
        winner.addWin();
        System.out.printf("%s Wins\n", winner.getPlayerName());
    }
}