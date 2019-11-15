import java.net.*;
import java.io.*;
import java.util.Scanner;
class Client {
    public static void main(String[] args) {
        Socket server = null;
        try {
            server = new Socket("localhost", 3141);
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            Menu menu = new Menu();
            while(!menu.login(in, out)){System.out.println("LogIn");}
            menu.start(in.readUTF());
        } catch ( UnknownHostException e ) {System.out.println("ERROR: "+e);}// Verbindungsfehler
          catch ( IOException e ) {}// Fehler bei Ein-und Ausgabe
        finally {
            if ( server != null )
                try { server.close(); } catch ( IOException e ) { }
        }
    }
}