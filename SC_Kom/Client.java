package SC_Kom;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
    boolean loggedIn;

    public Client(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static void main(String[] args) {
        Socket server = null;
        try {
            server = new Socket("localhost", 3141);
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            Client client = new Client(false);
            while (!client.login(in, out)) {
                System.out.println("LogIn");
            }
            Menu menu = new Menu(in.readUTF());
            menu.start();
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
        boolean loggedIn = false;
        try {
            Scanner scanInt = new Scanner(System.in);
            Scanner scanString = new Scanner(System.in);
            System.out.println("(1)New Player\t(2)Log in");
            int intInput = scanInt.nextInt();
            if (intInput == 2) {
                out.writeByte(3);
                while (!loggedIn) {
                    switch (in.readByte()) {
                    case 2:
                        System.out.println("Insert Your Name");
                        String name = scanString.nextLine();
                        out.writeUTF(name);
                        break;
                    case 3:
                        System.out.println("Insert Your Password"); // TODO: hashing
                        String pwd = scanString.nextLine();
                        out.writeUTF(pwd);
                        break;
                    /*
                     * case 4: break;
                     */
                    case 5:
                        loggedIn = true;
                        break;
                    case 1:
                        System.out.println("Try Again");
                        out.writeByte(3);
                        // case 0:
                    default:
                        loggedIn = false;
                        break;
                    }
                }
            } else if (intInput == 1) {
                out.writeByte(2);
                while (!loggedIn) {
                    switch (in.readByte()) {
                    case 5:
                        loggedIn = true;
                        break;
                    case 3:
                        System.out.println("Insert Your Password:");
                        String pwd = scanString.nextLine();
                        out.writeUTF(pwd);
                        break;
                    case 2:
                        System.out.println("Insert Your Name:");
                        String name = scanString.nextLine();
                        out.writeUTF(name);
                        break;
                    case 1:
                        System.out.println("Name already used!");
                        out.writeByte(2);
                        break;
                    // case 0:
                    default:
                        loggedIn = false;
                        break;
                    }
                }
            } else if (intInput == 0) {
                out.writeByte(0);
                loggedIn = false;
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            return loggedIn;
        }
    }
}
