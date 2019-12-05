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
    Client client;

    public Message(DataInputStream in, DataOutputStream out, Client client) {
        Message.in = in;
        Message.out = out;
        this.client = client;
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
                    case 9:
                        //spiel starten
                        break;
                    case 6: // chat message
                        chat(in.readUTF());
                        break;
                    case 3:
                        playerList(in.readInt());
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
                    default:
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

    public static void sendMessage(Integer b)  {
       try{ out.writeByte(b);}
        catch(IOException e){
            System.out.println("connection Error");
        }
    }

    public static void sendMessage(String s) {
        try{out.writeUTF(s);}
        catch(IOException e){
            System.out.println("connection Error");
        }
    }

    private void logout() {
        // TODO: Logout
    }

    private void chat(String s) {

        System.out.println(s);
    }

    private void newPlayer(String s){
        System.out.println("New Player: "+ s);
    }

    private void playerList(int size){
         try {
             System.out.println("Players Online:");
             for (int i = 0; i < size; i++) {
                 System.out.println(in.readUTF());
             }
         }catch (IOException e){
             System.out.println("cant write players");
         }
    }
    // turns of vsplayer saved in stack
}