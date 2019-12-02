import java.io.*;
import java.net.*;
import java.util.ArrayList;

/*
    #Alle spielzuege speichern auf server?


    byte codierung fuer messagelisener
    0000 0001 - letztes bit als boolean
    7 bit sagen welche aktion 

    0- logout
    1- error message
    3/2- login /new Player
    4/5- password
    6/7- message for player
    8/9- Lobby/Ready to play
    10/11- game message    

*/

public class Server {
    private static File pwd = new File("./pwd");
    private static File usr = new File("./usr");

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3141);
        if (!pwd.exists()) {
            pwd.createNewFile();
        }
        if (!usr.exists()) {
            usr.createNewFile();
        }
        while (true) { // einzelner Thread bearbeitet eine aufgebaute Verbindung
            MulServerThread mulThread = new MulServerThread(server.accept());
            mulThread.start();
        }
    }
}

class MulServerThread extends Thread {
    static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();
    Socket client;
    ClientNode node;
    ClientNode vsPlayer;

    MulServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() { // Bearbeitung einer aufgebauten Verbindung
        try {
            // PipedInputStream pis = node.getPipe();
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            this.node = new ClientNode(client, out);
            clients.add(node);
            while (!login(in, out)) {
            }
            node.setGame("idle");
            while (true) {
                /*
                 * try{ int pipeIn = pis.read(); out.writeByte(6); //liest alles von anderen
                 * thread out.writeInt(pipeIn); } catch(IOException e){}
                 */
                switch (in.readByte()) {
                case 10:
                    Byte code = 10;
                    vsPlayer.sendMessage(code, in.readInt()); // game message
                case 8:
                    getPlayers(in, out);
                    break;
                case 9:
                    String playerName = in.readUTF();
                    this.vsPlayer = searchPlayer(playerName);
                    if (vsPlayer != null) {
                        out.writeByte(9);
                    } else {
                        out.writeByte(8);
                    }
                    break;

                default:
                    return;
                }
            }
        } catch (

        IOException e) {
        } // Fehler bei Ein- und Ausgabe
        finally {
            if (client != null)
                try {
                    client.close();
                } catch (IOException e) {
                }
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
                i++;
                if (name.equals(usrName)) {
                    return i;
                }
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
            if (pwd.equals(passwords.get(ptr))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("No users found");
        }
        return false;
    }

    boolean login(DataInputStream in, DataOutputStream out) throws IOException {
        boolean loggedIn = false;
        try {
            String name;
            switch (in.readByte()) {
            case 2: // new Player
                out.writeByte(2);
                name = in.readUTF();
                if (searchName(name) == 0) {// name noch nicht registriert
                    out.writeByte(3);
                    synchronized (this) { // write pwd, name into files
                        String pwd = in.readUTF();
                        FileWriter pwdOut = new FileWriter("./pwd", true);
                        FileWriter nameOut = new FileWriter("./usr", true);
                        BufferedWriter pwds = new BufferedWriter(pwdOut);
                        BufferedWriter names = new BufferedWriter(nameOut);
                        pwds.write(pwd);
                        names.write(name);
                        pwds.close();
                        names.close();
                    }
                    out.writeByte(5); // success
                    out.writeUTF(name);
                    loggedIn = true;
                } else {
                    out.writeByte(1); // name allready used
                }
                break;
            case 3: // log in
                out.writeByte(2);
                name = in.readUTF();
                int ptr;
                if ((ptr = searchName(name)) != 0) {// name suchen und stelle im file zurueckgeben
                    out.writeByte(3); // pwd anfordern
                    String pwd = in.readUTF();
                    if (comparePWD(pwd, ptr)) {// teste auf pwd
                        out.writeByte(5); // success
                        loggedIn = true;
                    } else { // DONE: benoetigt noch loop
                        out.writeByte(1);
                        out.writeByte(2);
                    }
                } else {
                    out.writeByte(1);
                    out.writeByte(2);
                }
                break;
            case 1: // error message
                System.out.println(this.getName() + "Error: " + in.readUTF());
                loggedIn = false;
            case 0: // log out
            default:
                logout();
                loggedIn = false;
            }
            loggedIn = false;
            // return loggedIn; lieber hier login
        } catch (IOException e) {
        } finally {
            return loggedIn;
        }
    }

    synchronized void getPlayers(DataInputStream in, DataOutputStream out) throws IOException {
        try {
            String name;
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
        System.out.println("Client logged out");
        clients.remove(node);
        if (client != null)
            try {
                client.close();
            } catch (IOException e) {
            }

        // sende nachricht an mitspieler
    }

    synchronized void sendToAllMessage(Int code, String message) throws IOException {
        try {
            for (ClientNode player : clients) {
                if (!player.getGame().equals("login")) {
                    player.sendMessage(code, message);
                }
            }
            /*
             * PipedOutputStream pos = new PipedOutputStream();
             * pos.connect(vsPlayer.getPipe()); pos.write(coord); pos.close();
             */
        } catch (IOException e) {
        }
    }
}