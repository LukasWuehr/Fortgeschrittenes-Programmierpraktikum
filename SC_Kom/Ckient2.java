package SC_Kom;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client2 {
    public Client2(){}

    public static void main(String[] args) {
        Socket server = null;
        try {
            server = new Socket("localhost", 3141);
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            Client client = new Client();
            if (!client.login(in, out)) {
                System.out.println("Exit");
                out.writeByte(0);
                return;
            }
            Menu menu = new Menu(in.readUTF());
            menu.start();
            Message message = new Message(in,out, client);
            message.start();
            Message.sendMessage(8);
        } catch (UnknownHostException e) {
            System.out.println("ERROR: " + e);
        } // Verbindungsfehler
        catch (IOException e) {
        } // Fehler bei Ein-und Ausgabe
        finally {
            if (server != null)
                try {
                    server.close();
                } catch (IOException e) {
                }
        }
    }

    public boolean login(DataInputStream in, DataOutputStream out) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("(1)New Player\t(2)Log in \t(0)Exit");
            int Input = scan.nextInt();
            if (Input == 2) {
                out.writeByte(3);
            } else if (Input == 1) {
                out.writeByte(2);
            } else {
                return false;
            }
            scan.nextLine();
            while (true) {
                String pwd, name="Player1";
                switch (in.readByte()) {
                    case 2:
                    case 3:
                        System.out.printf("Insert Your Name: ");
                        name = scan.nextLine();
                        out.writeUTF(name);
                        System.out.printf("Insert Your Password: ");
                        pwd = scan.nextLine();
                        out.writeUTF(pwd);
                        break;
                    case 4:
                        System.out.println("Use other name");
                        out.writeByte(2);
                        break;
                    case 5:
                        System.out.println("You logged in as "+name);
                        return true;
                    case 1:
                        System.out.println("Try Again");
                        out.writeByte(3);
                        break;
                    case 0:
                    default:
                        return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
