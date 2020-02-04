package SC_Kom;

import GUI.Login;
import GUI.MainScreen;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
    JFrame frame;
    public Client(){
        this.frame = new JFrame();
    }

    public static void main(String[] args) {
        Socket server = null;
        try {
            server = new Socket("localhost", 3141);
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            Message.setIn(in); Message.setOut(out);
            Client client = new Client();
            if (!client.login(in, out)) {
                System.out.println("Exit");
                out.writeByte(0);
                System.exit(0);
                return;
            }
            String name = in.readUTF();
            Message message = new Message(in,out, client);
            MainScreen screen = new MainScreen(client.frame,name);
            message.setScreen(screen);
            Message.sendMessage(5);
            message.start();
            while (true){
            }
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
            System.exit(0);
        }
    }

    public boolean login(DataInputStream in, DataOutputStream out) {
        Login frameLogin = new Login(frame);
        try {
            System.out.println("(1)New Player\t(2)Log in \t(0)Exit");
            String []inputs;
            String pwd, name="Player1";
                while (true) {
                    switch (in.readByte()) {
                        case 2:
                        case 3:
                            inputs = frameLogin.getInputs();
                            System.out.printf("Insert Your Name: ");
                            name = inputs[1];
                            out.writeUTF(name);
                            System.out.printf("Insert Your Password: ");
                            pwd = inputs[2];
                            out.writeUTF(pwd);
                            break;
                        case 4:
                            System.out.println("Use other name");
                            frameLogin.setMessage("Use other name");
                            break;
                        case 5:
                            System.out.println("You logged in as "+name);
                            return true;
                        case 1:
                            System.out.println("Try Again");
                            frameLogin.setMessage("Try Again");
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
