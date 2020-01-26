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
            //Scanner scan = new Scanner(System.in);
            System.out.println("(1)New Player\t(2)Log in \t(0)Exit");
            //int Input = scan.nextInt();
            int Input;
            String []inputs;
            do{
                inputs = frameLogin.getInputs();
                Input = Integer.parseInt(inputs[0]);
                //System.out.println(Input);
                try{Thread.sleep(10);
                }catch (InterruptedException e){System.out.println(Input);}
            } while (Input==0);
            if (Input == 2) {
                out.writeByte(3);
            } else if (Input == 1) {
                out.writeByte(2);
            } else {
                return false;
            }
            //scan.nextLine();
            String pwd, name="Player1";
                while (true) {
                    switch (in.readByte()) {
                        case 2:
                        case 3:
                            System.out.printf("Insert Your Name: ");
                            //name = scan.nextLine();
                            name = inputs[1];
                            out.writeUTF(name);
                            System.out.printf("Insert Your Password: ");
                            //pwd = scan.nextLine();
                            pwd = inputs[2];
                            out.writeUTF(pwd);
                            break;
                        case 4:
                            System.out.println("Use other name");
                            frameLogin.setMessage("Use other name");
                            out.writeByte(2);
                            break;
                        case 5:
                            System.out.println("You logged in as "+name);
                            return true;
                        case 1:
                            System.out.println("Try Again");
                            frameLogin.setMessage("Try Again");
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
