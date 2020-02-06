package SC_Kom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import GUI.MainScreen;
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
    MainScreen screen;

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
        System.out.println("Ready for msg");
        try {
            while (true) {
                switch (in.readByte()) {
                    case 12:
                        //screen.infoWindow(in.readUTF());
                        in.readUTF();
                        break;
                    case 11: //stop game
                        screen.stopGame(in.readUTF());
                        break;
                    case 10: // turn of VSplayer
                        setTurn(in.readUTF());
                        break;
                    case 8://game invitations
                        screen.setInvite(in.readUTF());
                        break;
                    case 9://spiel starten force
                        String[] game = in.readUTF().split("#");
                        String[] dim = game[2].split("x");
                        screen.startGame(game[0], game[1], Integer.parseInt(dim[0]), Integer.parseInt(dim[1]), Integer.parseInt(game[3])); //vsPlayer, game, l,h, playerNumb
                        in.skip(in.available());
                        break;
                    case 6: // chat message
                        chat(in.readUTF());
                        break;
                    case 4: //if logout
                        screen.removePlayer(in.readUTF());
                        break;
                    case 3://list of all players
                        playerList(in.readInt());
                        break;
                    case 2: // new Player logged in
                        System.out.println("newPlayer");
                        newPlayer(in.readUTF());
                        break;
                    case 1: // error Message
                        System.out.println("ERROR");
                        break;

                    case 0: // log out
                        logout();
                        out.writeByte(0);
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        }catch (IOException e){
        }
    }

    private void setTurn(String turn) {
        String []infos = turn.split(","); // game,player,turn,h,l
        // greife auf spiel zu und setze zug
        if (infos[0].equals("connect")) {
            Player player = new Player(infos[1],false);
            screen.connectGui.getBoard().setDisc(Integer.parseInt(infos[4]),player,Integer.parseInt(infos[2]));
            //(Connect4Game) game.setDisc();
        } else if (infos[0].equals("chomp")) {
            screen.chompGui.colorButtons(Integer.parseInt(infos[4]),Integer.parseInt(infos[3]));
            //screen.chompGui.incrementTurns();
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static void sendMessage(Integer b)  {
       try{ out.writeByte(b);}
        catch(IOException e){
            System.out.println("connection Error");
            System.exit(0);
        }
    }

    public static void sendMessage(String s) {
        try{out.writeUTF(s);}
        catch(IOException e){
            System.out.println("connection Error");
            System.exit(0);
        }
    }

    private void logout() {
        // DONE: Logout
        System.exit(0);
    }

    private void chat(String s) {
        screen.addChatMessage(s);
        System.out.println(s);
    }

    private void newPlayer(String s){
        System.out.println("New Player: "+ s);
        screen.addPlayer(s);
    }

    private void playerList(int size){ //all players who are logged in
         try {
             String player;
             System.out.println("Players Online:"+size);
             for (int i = 0; i < size; i++) {
                 System.out.println(player=in.readUTF());//check
                 screen.addPlayer(player);
             }
         }catch (IOException e){
             System.out.println("cant write players");
         }
    }

    public void setScreen(MainScreen screen) {
        this.screen = screen;
    }

    // turns of vsplayer saved in stack
}