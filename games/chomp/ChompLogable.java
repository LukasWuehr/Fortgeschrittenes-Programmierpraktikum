package games.chomp;
import games.*;

class ChompLogable implements Logable{
    @Override public void add(Node n){
        protokoll.push(n);
    }
    @Override public Node delete(){
        return protokoll.pop();
    }
}