package games.chomp;
import games.*;
import java.util.*;

public class chomp extends Game{
    Player player1 = super.getPlayer1();
    Player player2 = super.getPlayer2();
    boolean lost = false;
    boolean currentPlayer; //true = player1; false = player2
    ChompBoard board = new ChompBoard();
    public void gameMove(int lengthplace, int heightplace){
        board.setBoardField(lengthplace, heightplace);
    }
    public void switchPlayers(){
        if(this.currentPlayer==true)this.currentPlayer = false;
        else this.currentPlayer = true;
    }
    public boolean checkLost(int lengthcoordinate, int heightcoordinate){
        if(lengthcoordinate == 0 && heightcoordinate == 0) return true;
        else return false;
    }
    
    public void start(){
        Scanner scan = new Scanner(System.in);
        int chosenLengthCoordinate;
        int chosenHeightCoordinate;
        System.out.println("Please insert the length of the Board.");//InitBoard
        this.board.setLength(scan.nextInt());
        System.out.println("Please insert the height of the Board.");
        this.board.setHeight(scan.nextInt());
        this.board.initBoard();
        System.out.println("Player 1: You are first to make a move.");
        boolean currentPlayer = true;//Player1s turn
        while(lost != true){ 
            System.out.println("This is the current state of the board:");
            board.draw();
            System.out.println("Please insert the coordinates of the field you want to choose.");
            System.out.println("Lengthcoordinate: ");
            chosenLengthCoordinate = scan.nextInt();
            System.out.println("Heightcoordinate: ");
            chosenHeightCoordinate = scan.nextInt();
            gameMove(chosenLengthCoordinate,chosenHeightCoordinate);
            lost = checkLost(chosenLengthCoordinate,chosenHeightCoordinate);
        }
        if (currentPlayer) System.out.println("Player1 has lost.");
        else System.out.println("Player2 has lost.");
    }
    

}