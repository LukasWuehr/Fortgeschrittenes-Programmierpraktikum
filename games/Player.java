package games;
/**
 * Player
 */
public class Player {
    private String playerName;
    private boolean isHuman;
    private int wins=0;
    
    public Player(String playerName, boolean isHuman){
        this.playerName=playerName; this.isHuman=isHuman;
    }
    public Player(){}
    public String getPlayerName(){ return this.playerName;}
    public void addWin(){this.wins++;}
    public Boolean getIsHuman(){ return this.isHuman;}
}