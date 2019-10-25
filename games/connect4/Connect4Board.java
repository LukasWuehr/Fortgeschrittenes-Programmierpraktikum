package games.connect4;
import games.*;
/**
 * Connect4Board
 */
public class Connect4Board extends Board implements Logable {
    private Stack protokoll;
    private Disc discs[][];
    public Connect4Board(int height, int length){
        discs=new Disc[length][height];
        this.protokoll = new Stack();

    }
   @Override
   public void draw() {
    for (int i=0;discs[0].length>i;i++) {        //links oben 0,0
        for (int j=0;discs.length>j;j++) {
            Disc disc = discs[j][i];
            if (disc != null) {
                System.out.printf("|%d", disc.getColor());
            } else{
                System.out.printf("| ");
            }
        }
        System.out.printf("|\n");
    }
   }

   public int setDisc(int coordinate, Player player, int turn){ //0 Colum is full //1 successfull turn //2 win
    if (coordinate<0 || coordinate >= discs.length){ return 0;}  
    int row= discs[coordinate].length-1;
        while (row>-1 && discs[coordinate][row] != null) {
            row--;
        }
        if (row<= -1) { 
            return 0;
        } else {
            discs[coordinate][row]=new Disc(turn%2);
            add(new Node(coordinate, row, player));
            if (win(coordinate,row)) { //win status 2
                return 2;
            } else {
                return 1;
            }
        }    
   }

   private boolean win(int length, int height){ //gewinnbedingungen pruefen
       int counterH=0,counterV=0,counterD1=0,counterD2=0;
       for (int i = -3; i < 4; i++) {   //!muss noch testen ob i im array ist!
            if(discs.length > length+i && length+i >=0 && discs[length+i][height]!=null 
               && discs[length][height].getColor()==discs[length+i][height].getColor()){  // teste horizontale
                if (++counterH>=4) {
                    return true;
                }
            }
            if(discs[length].length > height+i && height+i >=0 && discs[length][height+i]!=null 
               && discs[length][height].getColor()==discs[length][height+i].getColor()){  // teste Vertikale
                if (++counterV>=4) {
                    return true;
                }
            }
            if(discs.length > length+i && length+i >=0 && discs[length].length > height+i && height+i >=0 && discs[length+i][height+i]!=null
               && discs[length][height].getColor()==discs[length+i][height+i].getColor()){  // teste Diagonale links oben nach rechts unten
                if (++counterD1>=4) {
                    return true;
                }
            }
            if(discs.length > length-i && length-i >=0 && discs[length].length > height-i && height-i >=0 && discs[length-i][height-i]!=null 
               && discs[length][height].getColor()==discs[length-i][height-i].getColor()){  // teste horizontale links unten nach rechts oben
                if (++counterD2>=4) {
                    return true;
                }
            }
        }
        return false;    
   }
   @Override
   public int getLength() {
       return discs.length;
   }
   @Override
   public Node delete() {
       Node nodeDelete = protokoll.pop();
       discs[nodeDelete.getLengthCoordinate()][nodeDelete.getHeightCoordinate()]= null;
       draw();
       return nodeDelete;
   }
   @Override
   public void add(Node n) {
        protokoll.push(n);
   }
}
