package games.chomp;
import games.*;

public class ChompBoard extends Board{
    private int height;
    private int length;
    private boolean chompBoard[][] = new boolean [this.getLength()][this.getHeight()];// true = belegt
    public ChompBoard(int length, int height){
        this.chompBoard = new boolean[length][height];
    }
    @Override public void draw(){
        for(int k = 0; k < 2*this.getLength()+1;k++)System.out.print("-");
        System.out.println();
        for(int i = 0; i < this.getHeight(); i++){
            System.out.print("|");
            for(int j = 0; j < this.getLength(); j++){
                if (this.chompBoard[j][i] == false)System.out.print("0|");
                else System.out.print("1|");
            }
            System.out.println();
            for(int k = 0; k < 2*this.getLength()+1;k++)System.out.print("-");
            System.out.println();
        }
    }
    public void setBoardField(int lengthindex, int heightindex, boolean taken){
        if(lengthindex < this.getLength() && heightindex < this.getHeight())
            for(int i = lengthindex; i < this.getLength(); i++){
                for (int j = heightindex; j < this.getHeight();j++){
                    this.chompBoard[i][j] = taken;
                }
            }
    }
    public void setBoardField(int lengthindex, int heightindex){
        setBoardField(lengthindex,heightindex,true);
    }
    public void initBoard(){
        this.setBoardField(0,0,false);
    }

    public boolean checkTaken(int length, int height){
        return this.chompBoard[length][height];
    }
}
