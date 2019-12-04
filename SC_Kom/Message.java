package SC_Kom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import games.*;
import games.connect4.*;
import games.chomp.*;

/**
 * Message
 */
public class Message extends Thread {
    static DataOutputStream out;
    static DataInputStream in;
    Game game;
    Player player;

    public Message(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public Message() {
    }

    public static void setIn(DataInputStream in) {
        Message.in = in;
    }

    public static void setOut(DataOutputStream out) {
        Message.out = out;
    }

    @Override
    public void run() {
        while (true) {
            switch (in.readByte()) {
            case 10: // turn of VSplayer
                setTurn(in.readUTF());
                break;
            case 6: // chat message
                chat(in.readUTF());
                break;
            case 2: // new Player logged in
                newPlayer(in.readUTF());
                break;
            case 1: // error Message
                System.out.println("ERROR");
                break;

            case 0: // log out
                logout();
                out.writeByte(0);
                break;
            }
        }
    }

    private void setTurn(String turn) {
        Object infos[] = new Object[5]; // game,player,turn,h,l
        infos = turn.split(",");
        // greife auf spiel zu und setze zug
        if (infos[0].equals("connect")) {
            (Connect4Game) game.setDisc();
        } else if (infos[0].equals("chomp")) {

        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static void sendMessage(Byte b) {
        out.writeByte(b);
    }

    public static void sendMessage(String s) {
        out.writeUTF(s);
    }

    void logout() {
        // TODO: Logout
    }

    void chat(String s) {
        System.out.println(s);
    }

    // turns of vsplayer saved in stack
}