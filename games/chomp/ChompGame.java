package games.chomp;
import games.*;
import java.util.*;
import java.util.Random;

public class ChompGame extends Game implements Logable{
    Player player1;
    Player player2;
    boolean lost = false;
    boolean currentPlayer; //true = player1; false = player2
    ChompBoard board;

    public ChompGame(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }
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
    @Override public void add(Node n){
        protokoll.push(n);
    }
    @Override public Node delete(){
        return protokoll.pop();
    }
    public void start(){
        boolean currentPlayerIsPlayer1 = true;
        boolean player2IsHuman = player2.getIsHuman();
         
        Random randomLength = new Random();
        Random randomHeight = new Random();

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

        while(lost != true){ 

            if(currentPlayerIsPlayer1)System.out.println("Player1, it's your turn.");
            else System.out.println("Player2, it's your turn.");

            if(currentPlayerIsPlayer1 || player2IsHuman){
                System.out.println("This is the current state of the board:");
                this.board.draw();
                System.out.println("Please insert the coordinates of the field you want to choose.");
                System.out.println("Lengthcoordinate: ");
                chosenLengthCoordinate = scan.nextInt();
                System.out.println("Heightcoordinate: ");
                chosenHeightCoordinate = scan.nextInt();
            }
            else{
               chosenLengthCoordinate = randomLength.nextInt(board.getLength());
               chosenHeightCoordinate = randomHeight.nextInt(board.getHeight());
            }
            boolean outOfBound = chosenLengthCoordinate >= this.board.getLength() || chosenHeightCoordinate >= this.board.getHeight() || chosenLengthCoordinate < 0 || chosenHeightCoordinate < 0;
            if(!outOfBound){
                boolean taken = this.board.checkTaken(chosenLengthCoordinate, chosenHeightCoordinate);
                if(taken && (currentPlayerIsPlayer1 || player2IsHuman)){
                    System.out.println("This coordinate is already taken. Please try again");
                    continue;
                }
                else if(!player2IsHuman && !currentPlayerIsPlayer1 && taken)continue;//Fall Computer wählt besetztes Feld
            
                gameMove(chosenLengthCoordinate,chosenHeightCoordinate);
            }
            else{
                System.out.println("This coordinates doesn't exist. Please try again.");
                continue;
            }

            if(currentPlayerIsPlayer1) add(new Node(chosenLengthCoordinate,chosenHeightCoordinate,player1));
            else add(new Node(chosenLengthCoordinate,chosenHeightCoordinate,player2));

            lost = checkLost(chosenLengthCoordinate,chosenHeightCoordinate);
            currentPlayerIsPlayer1 = switchPlayers(currentPlayerIsPlayer1);
        }
        currentPlayerIsPlayer1 = switchPlayers(currentPlayerIsPlayer1);//zurückswitchen im Fall lost
        if (currentPlayerIsPlayer1) System.out.println("Player1 has lost.");
        else System.out.println("Player2 has lost.");
    }
    

}