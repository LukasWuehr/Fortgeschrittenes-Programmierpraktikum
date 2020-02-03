package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;

/*
    #Alle spielzuege speichern auf server?


    byte codierung fuer messagelisener
    0000 0001 - letztes bit als boolean
    7 bit sagen welche aktion 

    0- logout
    1- error message
    3/2- login /new Player
    4/5- password / logout Player
    6/7- message for player
    8/9- Invite/Ready to play
    10/11- game message   / stopgame

*/

public class Server {
    private static File pwd = new File("./pwd");
    private static File usr = new File("./usr");
    public Gui gui;

    public Server(Gui gui) throws IOException {
        this.gui=gui;
        ServerSocket server = new ServerSocket(3141);
        gui.setLog(server.toString());
        if (!pwd.exists()) {
            pwd.createNewFile();
            FileWriter pwdOut = new FileWriter("./pwd", false);
            BufferedWriter pwds = new BufferedWriter(pwdOut);
            pwds.write("Passwords:");
            pwds.newLine();
            pwds.close();
            gui.setLog("New pwd file created");
        }
        if (!usr.exists()) {
            usr.createNewFile();
            FileWriter nameOut = new FileWriter("./usr", true);
            BufferedWriter names = new BufferedWriter(nameOut);
            names.write("Usernames:");
            names.newLine();
            names.close();
            gui.setLog("New pwd file created");
        }
        MulServerThread.gui = this.gui;
        while (true) { // einzelner Thread bearbeitet eine aufgebaute Verbindung
            MulServerThread mulThread = new MulServerThread(server.accept());
            mulThread.start();
        }
    }
}

class MulServerThread extends Thread {
    static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();
    static Gui gui;
    Socket client;
    ClientNode node;
    ClientNode vsPlayer;
    Date date = new Date();

    MulServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() { // Bearbeitung einer aufgebauten Verbindung
        try {
            gui.setLog("new client:"+client.toString()+date);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            this.node = new ClientNode(client, out);
            clients.add(node);
            if (!login(in, out)) {
                System.out.println("Client logged out");
                gui.setLog("logout client: "+client.toString()+date);
                return;
            }
            node.setGame("idle");
            gui.setLog("player Log in: "+node.getName()+ " "+date);
            gui.addPlayer(node.getName());
            sendToAllMessage((byte) 2, node.getName());
            while (true) {
                int x = in.readByte();
                switch (x) {
                case 11:
                    //player left game
                    node.setGame("idle");
                    node.getVsPlayer().setGame("idle");
                    node.getVsPlayer().sendMessage((byte)11,"Player left");
                    break;
                case 10:
                    node.getVsPlayer().sendMessage((byte) 10, in.readUTF()); // game message  // game,player,turn,h,l
                    break;
                case 5:
                    getPlayers(in, out);
                    break;
                case 9: //game start
                    startGame(in.readUTF());
                    break;
                case 8: //invite Game
                    sendMessage(in.readUTF(),(byte)8,in.readUTF());
                    break;
                case 7:
                    sendToAllMessage((byte)6,node.getName()+": "+in.readUTF());
                    break;
                case 6:
                    String playerName = in.readUTF();
                    sendMessage(playerName,(byte)6,node.getName()+": "+in.readUTF());
                    break;
                case 0:
                    logout();
                default:
                    System.out.println(x);
                    return;
                }
            }
        } catch (

        IOException e) {
        } // Fehler bei Ein- und Ausgabe
        finally {
            logout();
        }
    }

    private ClientNode searchPlayer(String name) {
        for (ClientNode clientNode : clients) {
            if (clientNode.getName().equals(name)) {
                return clientNode;
            }
        }
        return null;
    }

    private synchronized void startGame(String input) {

        String[] invite = input.split("#");
        this.vsPlayer = searchPlayer(invite[0]);
        if (vsPlayer != null && vsPlayer.getGame().equals("idle")) {
            vsPlayer.sendMessage((byte)9,node.getName()+"#"+invite[1]+"#"+invite[2]+"#2");
            node.sendMessage((byte)9,input+"#1");
            vsPlayer.setGame(invite[1]);
            node.setGame(invite[1]);
            node.setVsPlayer(vsPlayer);
            vsPlayer.setVsPlayer(node);
        } else {
            node.sendMessage((byte)12,"To slow :/");// 12 invite error
        }
    }

    private int searchName(String name) {
        try {
            FileReader usrIn = new FileReader("./usr");
            BufferedReader usrs = new BufferedReader(usrIn);
            ArrayList<String> names = new ArrayList<String>();
            String user;
            while ((user = usrs.readLine()) != null) {
                names.add(user);
            }
            usrs.close();
            int i = 0;
            for (String usrName : names) {
                if (name.equals(usrName)) {
                    return i;
                }
                i++;
            }
            return 0;
        } catch (Exception e) {
            System.out.println("No users found");
        }
        return 0;
    }

    private boolean comparePWD(String pwd, int ptr) {
        try {
            FileReader pwdIn = new FileReader("./pwd");
            BufferedReader pwds = new BufferedReader(pwdIn);
            ArrayList<String> passwords = new ArrayList<String>();
            String password;
            while ((password = pwds.readLine()) != null) {
                passwords.add(password);
            }
            pwds.close();
            return pwd.equals(passwords.get(ptr));
        } catch (Exception e) {
            System.out.println("No users found");
        }
        return false;
    }

    boolean login(DataInputStream in, DataOutputStream out) {
        try {
            String name;
            String pwd;
            while(true) {
                switch (in.readByte()) {
                    case 2: // new Player
                        out.writeByte(2);
                        name = in.readUTF();
                        pwd = in.readUTF();
                        if (searchName(name) == 0) {// name noch nicht registriert
                            synchronized (this) { // write pwd, name into files
                                FileWriter pwdOut = new FileWriter("./pwd", true);
                                FileWriter nameOut = new FileWriter("./usr", true);
                                BufferedWriter pwds = new BufferedWriter(pwdOut);
                                BufferedWriter names = new BufferedWriter(nameOut);
                                pwds.write(pwd);
                                pwds.newLine();
                                names.write(name);
                                names.newLine();
                                pwds.close();
                                names.close();
                            }
                            out.writeByte(5); // success
                            out.writeUTF(name);
                            node.setName(name);
                            System.out.println("Player logged in: "+name+" "+ date);
                            return true;
                        } else {
                            out.writeByte(4);// name allready used
                        }
                        break;
                    case 3: // log in
                        out.writeByte(3);
                        name = in.readUTF();
                        pwd = in.readUTF();
                        int ptr;
                        if ((ptr = searchName(name)) != 0) {// name suchen und stelle im file zurueckgeben
                            if (comparePWD(pwd, ptr)) {// teste auf pwd
                                out.writeByte(5); // success
                                out.writeUTF(name);
                                node.setName(name);
                                System.out.println("Player logged in: "+name+" "+ date);
                                return true;
                            } else {
                                out.writeByte(1);//false pwd or name
                            }
                        } else {
                            out.writeByte(1);//false pwd or name
                        }
                        break;
                    case 0: // log out
                    default:
                        return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    synchronized void getPlayers(DataInputStream in, DataOutputStream out) {
        try {
            String name;
            out.writeByte(3);
            out.writeInt(clients.size());
            for (ClientNode node : clients) {
                name = node.getName();
                if (name != null) {
                    out.writeUTF(name);
                }
            }
        } catch (IOException e) {
        }
    }

    void logout() { // remove this server-client from list, close connection -> error in client ->
                    // terminates
        String name = node.getName();
        gui.setLog("Client logged out: "+name+" at "+date);
        gui.removePlayer(name);
        sendToAllMessage((byte) 4,name);
        clients.remove(node);
        //vsPlayer.sendMessage((byte)0,"logout");
        if (client != null)
            try {
                client.close();
            } catch (IOException e) {
            }

        // sende nachricht an mitspieler
    }

    synchronized void sendToAllMessage(Byte code, String message){// throws IOException {
            for (ClientNode player : clients) {
                if (!player.getGame().equals("login")&&!player.getName().equals(node.getName())) {
                    player.sendMessage(code, message);
                }
            }
    }

    synchronized void sendMessage(String name,Byte code, String message) {
        ClientNode client = searchPlayer(name);
            client.sendMessage(code, message);//TODO: NullPointer
    }
    synchronized void sendMessage(String name,Byte code, int message) {
        ClientNode client = searchPlayer(name);
        client.sendMessage(code, message);
    }
    }