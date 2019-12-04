package SC_Kom;

import java.io.DataOutputStream;
import java.net.Socket;

class ClientNode {
    private Socket client;
    private String name;
    DataOutputStream out;
    private String game = "login"; // TODO: ENUM: IDLE WantC4 WantChomp InC4 InComp

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

    public String getGame() {
        return (game);
    }

    public void setGame(String game) {
        this.game = game;
    }

    public synchronized void sendMessage(Byte code, String Message) {
        try {
            if (!game.equals("login")) {
                out.writeByte(code);
                out.writeUTF(Message);
            }
        } catch (Exception e) {
        }
    }

    public synchronized void sendMessage(Byte code, Integer Message) {
        try {
            if (!game.equals("login")) {
                out.writeByte(code);
                out.writeInt(Message);
            }
        } catch (Exception e) {
        }
    }
}