package games;

public class Node{
    private Player player;
    private int lengthcoordinate;
    private int heightcoordinate;
    private Node next;
    public Node(){}
    public Node(int lengthcoordinate, int heightcoordinate, Player player){
        this.heightcoordinate=heightcoordinate;
        this.lengthcoordinate=lengthcoordinate;
        this.player=player;
    }
    public Player getPlayer(){return this.player;}
    public void setPlayer(Player p){this.player = p;}
    public int getLengthCoordinate(){return this.lengthcoordinate;}
    public void setLengthCoordinate(int l){this.lengthcoordinate = l;}
    public int getHeightCoordinate(){return this.heightcoordinate;}
    public void setHeightCoordinate(int h){this.heightcoordinate = h;}
    public Node getNextNode(){return this.next;}
    public void setNextNode(Node n){this.next = n;}
}