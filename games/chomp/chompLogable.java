package games.chomp;
import games.*;

class chompLogable implements logable{
    @Override public void push(Node n){
        Node oldTop = top;
        this.top = n;
        n.setNextNode(oldTop);
    
    }
}