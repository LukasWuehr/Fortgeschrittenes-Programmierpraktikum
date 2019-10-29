import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3141);
        while ( true ) { // einzelner Thread bearbeitet eine aufgebaute Verbindung
            MulServerThread mulThread = new MulServerThread(server.accept());
            mulThread.start();
        }
    }
}

class MulServerThread extends Thread {
    Socket client;
    MulServerThread(Socket client) { this.client = client; }
    public void run(){ // Bearbeitung einer aufgebauten Verbindung
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            int factor1 = in.readInt();
            int factor2 = in.readInt();
            int result = factor1 * factor2;
            out.writeInt(result);
        } catch ( IOException e ) {} // Fehler bei Ein- und Ausgabe
        finally { if ( client != null ) 
            try { client.close(); } catch ( IOException e ) { }
        }
    }   
}