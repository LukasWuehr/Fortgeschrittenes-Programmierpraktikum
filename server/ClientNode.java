package server;

import java.io.DataOutputStream;
import java.net.Socket;

class ClientNode {
    private Socket client;
    private String name;
    DataOutputStream out;
    private String game = "login";
    private ClientNode vsPlayer;
    private Gui gui;

    public void setVsPlayer(ClientNode vsPlayer) {
        this.vsPlayer = vsPlayer;
    }

    public ClientNode getVsPlayer() {
        return vsPlayer;
    }

    public ClientNode(Socket client, DataOutputStream out, Gui gui) {
        this.client = client;
        this.out = out;
        this.gui = gui;
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
                System.out.println(name + code + " Message " + Message); //TODO: in log auch
                gui.setLog(name + ":" + code + " Message: " + Message);
                out.writeByte(code);
                out.writeUTF(Message);
            }
        } catch (Exception e) {
        }
    }

    public synchronized void sendMessage(Byte code, Integer Message) {
        try {
            if (!game.equals("login")) {
                gui.setLog(name + ":" + code + " Message: " + Message);
                out.writeByte(code);
                out.writeInt(Message);
            }
        } catch (Exception e) {
        }
    }
}