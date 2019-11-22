import java.io.DataOutputStream;
import java.net.Socket;

class ClientNode {
    Socket client;
    String name;
    DataOutputStream out;
    String game = "login"; // TODO: ENUM: IDLE WantC4 WantChomp InC4 InComp

    public ClientNode(Socket client, DataOutputStream out) {
        this.client = client;
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getClient() {
        return client;
    }

    public synchronized void sendMessage(Byte code, String Message) {
        if (!game.equals("login")) {
            out.writeByte(code);
            out.writeUTF(Message);
        }
    }
}