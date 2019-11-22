import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Message
 */
public class Message extends Thread {
    DataOutputStream out;
    DataInputStream in;

    public Message(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    // game spieler 2 ist dran -> wait()
    // message schreibt spielzug -> notify()
}