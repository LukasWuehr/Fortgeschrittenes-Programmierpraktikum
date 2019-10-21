package games;
/**
 * Board
 */
public abstract class Board {
    private int height;
    private int length;
    private String board[][] = new String [length][height];
    public abstract void draw();
}