package games.chomp;
import games.*;

public class ChompBoard extends Board{
    private boolean chompBoard[][] = new boolean [this.getLength()][this.getHeight()];// true = belegt
    @Override public void draw(){
        for(int i = 0; i < this.getHeight(); i++){
            for(int j = 0; j < this.getLength(); j++){
                System.out.print("|");
                if (this.chompBoard[i][j] == false)System.out.print("0|");
                else System.out.print("1|");
            }
            System.out.println();
            for(int k = 0; k < 2*this.getLength()+1;k++)System.out.print("-");
            System.out.println();
        }
    }
    public void setBoardField(int lengthindex, int heightindex){
        if(lengthindex < this.getLength() && heightindex < this.getHeight())
            for(int i = lengthindex; i < this.getLength(); i++){
                for (int j = heightindex; j < this.getHeight();j++){
                    this.chompBoard[i][j] = true;
                }
            }
    }
    public void initBoard(){
        this.setBoardField(0,0);
    }
}
