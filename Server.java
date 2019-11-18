import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

/*
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
        if(!pwd.exists()){pwd.createNewFile();}
        if(!usr.exists()){usr.createNewFile();}
        while ( true ) { // einzelner Thread bearbeitet eine aufgebaute Verbindung
            MulServerThread mulThread = new MulServerThread(server.accept());
            mulThread.start(); 
        }
    }
}

class MulServerThread extends Thread {
    static ArrayList<ClientNode> clients = new ArrayList<ClientNode>();
    Socket client;
    ClientNode node;
    MulServerThread(Socket client) { 
        this.client = client; 
        this.node=new ClientNode(client, new PipedOutputStream()); 
        clients.add(node);
    }
    public void run(){ // Bearbeitung einer aufgebauten Verbindung
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while(!login(in,out)){}     
        } catch ( IOException e ) {} // Fehler bei Ein- und Ausgabe
        finally { if ( client != null ) 
            try { client.close(); } catch ( IOException e ) { }
        }
    }


    
    private int searchName(String name){
        try{ 
            FileReader usrIn = new FileReader("./usr");
            BufferedReader usrs = new BufferedReader(usrIn);                
            ArrayList<String> names = new ArrayList<String>();
            String user;
            while ((user = usrs.readLine()) !=null) {
                names.add(user);
            }
            usrs.close();               
            int i=0;
            for (String usrName : names) {
                i++;
                if(name.equals(usrName)){ return i;}
            }
            return 0;
        }catch(Exception e){
            System.out.println("No users found");
        }
        return 0;
    }
    private boolean comparePWD(String pwd, int ptr){ 
        try{
            FileReader pwdIn = new FileReader("./pwd");
            BufferedReader pwds = new BufferedReader(pwdIn);
            ArrayList<String> passwords = new ArrayList<String>();
            String password;
            while ((password = pwds.readLine()) !=null) {
                passwords.add(password);
            }
            pwds.close();
            if(pwd.equals(passwords.get(ptr))){return true;}
            return false;
        }catch(Exception e) {
            System.out.println("No users found");
        }
        return false;
    }
    boolean login(DataInputStream in, DataOutputStream out){
        String name;
        switch (in.readByte()) {
            case 2:                         //new Player
                out.writeByte(2);
                name = in.readUTF();
                if (searchName(name)==0) {
                    out.writeByte(3);
                    String pwd = in.readUTF();
                    FileWriter pwdOut = new FileWriter("./pwd", true);
                    FileWriter nameOut =  new FileWriter("./usr", true);
                    BufferedWriter pwds = new BufferedWriter(pwdOut);
                    BufferedWriter names = new BufferedWriter(nameOut);
                    pwds.write(pwd);
                    names.write(name);
                    pwds.close();
                    names.close();
                    out.writeByte(5);
                    out.writeUTF(name);
                    return true;
                } else { 
                    out.writeByte(1);}
                break;
            case 3:                        //log in
                out.writeByte(2);
                name = in.readUTF();
                int ptr;
                if((ptr = searchName(name)) !=0){
                    out.writeByte(3);
                    String pwd = in.readUTF();
                    if (comparePWD(pwd, ptr)) {
                        out.writeByte(5);
                        return true;
                    } else {            //TODO: benoetigt noch loop
                        out.writeByte(1);
                        out.writeByte(2);
                    }
                } else{ 
                    out.writeByte(1);
                    out.writeByte(2);}
                break;
            case 1:                         //error message
                System.out.println(this.getName()+"Error: "+in.readUTF());
                return false;
            case 0:                         //log out
            default:
                logout();
                return false;
            }
            return false;
        }
    

    void getPlayers(DataInputStream in, DataOutputStream out){
        String name;
        out.writeInt(clients.lenght());
        for (ClientNode node : clients) {
            name = node.getName();
            if(name != null){
                out.writeUTF(name);
            }
        }
    }

    void logout(){
        System.out.println("Client logged out");
        //sende nachricht an mitspieler
    }

    void sendMessage(String message, int mode){//maybe not
        //pipe to other thread
        PipedOutputStream pipeOut = new PipedOutputStream(snk);
        PipedInputStream pipeIn = new PipedInputStream(src);
        switch (mode) {
            case 2: //message plain text

                break;
            case 1: //error
                
                break;
            case 0:
            default: //player loged out

                break;
        }
    }

}