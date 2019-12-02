import java.io.DataInputStream;
import java.io.DataOutputStream;
import games.*;
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

    /**
     * @param in the in to set
     */
    public static void setIn(DataInputStream in) {
        Message.in = in;
    }

    /**
     * @param out the out to set
     */
    public static void setOut(DataOutputStream out) {
        Message.out = out;
    }

    @Override
    public void run() {
        while (true) {
            switch (in.readByte()) {
            case 10:
                setTurn(in.readUTF());

            case 1:
                System.out.println("ERROR");
                break;

            case 0:
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
            game.setDisc();
        } else if (infos[0].equals("chomp")) {

        }
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public static void sendMessage(Byte b) {
        out.writeByte(b);
    }

    public static void sendMessage(String s) {
        out.writeUTF(s);
    }

    // game spieler 2 ist dran -> wait()
    // message schreibt spielzug -> notify()
}