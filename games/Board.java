package games;
/**
 * Board
 */
public abstract class Board {
    private int height;
    private int length;
    private String board[][] = new String [length][height];
    public abstract void draw();
    public void setHeight(int height){if(height > 0) this.height = height;}
    public int getHeight(){return this.height;}
    public void setLength(int length){if (length > 0)this.length = length;}
    public int getLength(){return this.length;}
    
}