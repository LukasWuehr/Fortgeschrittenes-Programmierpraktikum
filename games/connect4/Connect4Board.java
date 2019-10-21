package games.connect4;
import games.*;
/**
 * Connect4Board
 */
public class Connect4Board extends Board {

    private Disc discs[][];
    public Connect4Board(int height, int lenght){
        discs=new Disc[lenght][height];
    }
   @Override
   public void draw() {
    for (Disc[] discColum : discs) {        //links oben 0,0
        for (Disc disc : discColum) {
            if (disc != null) {
                System.out.printf("|%d", disc.getColor());
            } else{
                System.out.printf("|\t");
            }
        }
        System.out.printf("|\n");
    }
   }

   public int setDisc(int coordinate, Player player){
       int row= discs[coordinate].length-1;
        while (discs[coordinate][row] != null && row>=-1) {
            row--;
        }
        if (row== -1) { 
            return 0;
        } else {
            discs[coordinate][row]=new Disc(player.getNumber());
            if (win(coordinate,row)) {
                return 2;
            } else {
                return 1;
            }
        }    
   }

   private boolean win(int lenght, int height){
       int counterH=0,counterV=0,counterD1=0,counterD2=0;
       for (int i = -3; i < 4; i++) {
            if(discs[lenght][height].getColor()==discs[lenght+i][height].getColor()){  // teste horizontale
                if (++counterH>=4) {
                    return true;
                }
            }
            if(discs[lenght][height].getColor()==discs[lenght][height+i].getColor()){  // teste Vertikale
                if (++counterV>=4) {
                    return true;
                }
            }
            if(discs[lenght][height].getColor()==discs[lenght+i][height+i].getColor()){  // teste Diagonale links oben nach rechts unten
                if (++counterD1>=4) {
                    return true;
                }
            }
            if(discs[lenght][height].getColor()==discs[lenght-i][height-i].getColor()){  // teste horizontale links unten nach rechts oben
                if (++counterD2>=4) {
                    return true;
                }
            }
        }
        return false;    
   }
}

enum PlayerColor{
    RED,
    YELLOW;
}