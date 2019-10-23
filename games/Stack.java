package games;
import games.*;

public class Stack{
    private Node top;
    public void push(Node n){
        n.setNextNode(top);
        top = n;
    }
    public Node pop(){
        Node toPop = top;
        top = top.getNextNode();
        return toPop;
    }
}