import java.io.PipedOutputStream;
import java.net.Socket;
class ClientNode{
    Socket client;
    String name;
    PipedOutputStream pos;
    String game = "idle"; //TODO: ENUM: IDLE WantC4 WantChomp InC4 InComp

    public ClientNode(Socket client, PipedOutputStream pos){
        this.client=client; this.pos=pos;
    }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public Socket getClient() { return client; }
    public PipedOutputStream getPipe(){ return pos; }
}