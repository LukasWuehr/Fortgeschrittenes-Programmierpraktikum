import java.io.PipedInputStream;
import java.net.Socket;
class ClientNode{
    Socket client;
    String name;
    PipedInputStream pis;
    String game = "idle"; //TODO: ENUM: IDLE WantC4 WantChomp InC4 InComp

    public ClientNode(Socket client, PipedInputStream pis){
        this.client=client; this.pis=pis;
    }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public Socket getClient() { return client; }
    public PipedInputStream getPipe(){ return pis; }
}