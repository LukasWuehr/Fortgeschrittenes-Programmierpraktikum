package games.chomp;
import games.*;
import java.util.*;

public class ChompGame extends Game{
    Player player1 = super.getPlayer1();
    Player player2 = super.getPlayer2();
    boolean lost = false;
    boolean currentPlayer; //true = player1; false = player2
    ChompBoard board;
    public void gameMove(int lengthplace, int heightplace){
        board.setBoardField(lengthplace, heightplace);
    }
    public boolean switchPlayers(boolean p){
        if(p) p = false;
        else p = true;
        return p;

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
        chosenLengthCoordinate = scan.nextInt();
        System.out.println("Please insert the height of the Board.");
        chosenHeightCoordinate = scan.nextInt();
        this.board = new ChompBoard(chosenLengthCoordinate,chosenHeightCoordinate);
        this.board.setLength(chosenLengthCoordinate);
        this.board.setHeight(chosenHeightCoordinate);
        this.board.initBoard();
        boolean currentPlayer = true;//Player1s turn
        while(lost != true){ 
            if(currentPlayer)System.out.println("Player1, it's your turn.");
            else System.out.println("Player2, it's your turn.");
            System.out.println("This is the current state of the board:");
            this.board.draw();
            System.out.println("Please insert the coordinates of the field you want to choose.");
            System.out.println("Lengthcoordinate: ");
            chosenLengthCoordinate = scan.nextInt();
            System.out.println("Heightcoordinate: ");
            chosenHeightCoordinate = scan.nextInt();
            boolean taken = this.board.checkTaken(chosenLengthCoordinate, chosenHeightCoordinate);
            if(taken){
                System.out.println("This coordinate is already taken. Please try again");
                continue;
            }
            gameMove(chosenLengthCoordinate,chosenHeightCoordinate);
            lost = checkLost(chosenLengthCoordinate,chosenHeightCoordinate);
            currentPlayer = switchPlayers(currentPlayer);
        }
        currentPlayer = switchPlayers(currentPlayer);//zurückswitchen im Fall lost
        if (currentPlayer) System.out.println("Player1 has lost.");
        else System.out.println("Player2 has lost.");
    }
    

}