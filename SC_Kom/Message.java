package SC_Kom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
        Message.in = in;
        Message.out = out;
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
        try {
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
        }catch (IOException e){
        }
    }

    private void setTurn(String turn) {
        Object []infos = new Object[5]; // game,player,turn,h,l
        infos = turn.split(",");
        // greife auf spiel zu und setze zug
        if (infos[0].equals("connect")) {
            //(Connect4Game) game.setDisc();
        } else if (infos[0].equals("chomp")) {

        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static void sendMessage(Byte b) throws IOException {
       try{ out.writeByte(b);}
        catch(IOException e){}
    }

    public static void sendMessage(String s) {
        try{out.writeUTF(s);}
        catch(IOException e){}
    }

    void logout() {
        // TODO: Logout
    }

    void chat(String s) {

        System.out.println(s);
    }

    void newPlayer(String s){
        System.out.println("New Player: "+ s);
    }
    // turns of vsplayer saved in stack
}