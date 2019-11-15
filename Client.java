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
            while(!login(in, out)){System.out.println("LogIn");}
            Menu menu = new Menu();
            menu.start(in.readUTF());
        } catch ( UnknownHostException e ) {System.out.println("ERROR: "+e);}// Verbindungsfehler
          catch ( IOException e ) {}// Fehler bei Ein-und Ausgabe
        finally {
            if ( server != null )
                try { server.close(); } catch ( IOException e ) { }
        }
    }

    public boolean login(DataInputStream in, DataOutputStream out){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1)New Player\t(2)Log in");
            int input = scan.nextInt();
            if (input==2) {
                out.writeByte(3);
                while(true){
                    switch (in.readByte()) {
                        case 2:
                            System.out.println("Insert Your Name");
                            String name = scan.nextLine();
                            out.writeUTF(name);
                            break;
                        case 3:
                            System.out.println("Insert Your Password"); //TODO: hashing
                            String pwd = scan.nextLine();
                            out.writeUTF(pwd);
                            break;
                        case 4:
                            break;
                        case 5:
                            return true;
                        case 1:
                            System.out.println("Try Again");
                            out.writeByte(3);
                        case 0:
                        default:
                            return false;
                    }
                }
            } else if(input==1){
                out.writeByte(2);
                while(true){
                    switch (in.readByte()) {
                        case 5:
                            return true;
                        case 3:
                            System.out.println("Insert Your Password:")
                            pwd = scan.nextLine();
                            out.writeUTF(pwd);
                            break;
                        case 2:
                            System.out.println("Insert Your Name:");
                            name = scan.nextLine();
                            out.writeUTF(name);
                            break;
                        case 1:
                            System.out.println("Name already used!");
                            out.writeByte(2);
                            break;
                        case 0:
                        default:
                            return false;
                    }
                    String name = scan.nextLine();
                out.writeUTF(name);
                out.writeBoolean(true);
            } else if(input == 0){out.writeByte(0);}
                
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}