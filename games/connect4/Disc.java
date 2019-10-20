package games.connect4;

/**
 * Disc
 */
public class Disc {

    PlayerColor color;
    int coordinates[] = new int[2];

    public Disc(PlayerColor color, int coordinate){
        this.color=color;
        this.coordinates[0]=coordinate;
    }
}