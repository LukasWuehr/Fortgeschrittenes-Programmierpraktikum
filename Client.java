import java.net.*;
import java.io.*;
import games.*;
class Client {
    public static void main(String[] args) {
        Socket server = null;
        try {
            server = new Socket("localhost", 3141);
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            Player player=login();
            out.writeInt(4);// sende 1. Operanden
            out.writeInt(10000);// sende 2. Operanden
            int result = in.readInt();// lese das Ergebnis
            System.out.println( result );
        } catch ( UnknownHostException e ) {}// Verbindungsfehler
          catch ( IOException e ) {}// Fehler bei Ein-und Ausgabe
        finally {
            if ( server != null )
                try { server.close(); } catch ( IOException e ) { }
        }
    }
    private Player login(){
        return new Player();
    }
}