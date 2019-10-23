package games.chomp;
import games.*;

class chompLogable implements logable{
    @Override public void add(Node n){
        protokoll.push(n);
    }
    @Override public Node delete(){
        return protokoll.pop();
    }
}