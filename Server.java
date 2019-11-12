import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;
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
    Socket client;
    MulServerThread(Socket client) { this.client = client; }
    public void run(){ // Bearbeitung einer aufgebauten Verbindung
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            String name = in.readUTF();
            Boolean logIn = in.readBoolean();
            if (logIn) {
                int ptr;
                if((ptr = searchName(name)) !=0){
                    out.writeBoolean(true);
                    String pwd = in.readUTF();
                    out.writeBoolean(comparePWD(pwd, ptr));
                } else{ out.writeBoolean(false);}
            } else {
                if (searchName(name)==0) {
                    out.writeBoolean(true);
                    String pwd = in.readUTF();
                    FileWriter pwdOut = new FileWriter("./pwd", true);
                    FileWriter nameOut =  new FileWriter("./usr");
                    BufferedWriter pwds = new BufferedWriter(pwdOut);
                    BufferedWriter names = new BufferedWriter(nameOut);
                    pwds.write(pwd);
                    pwds.close();
                } else {
                    out.writeBoolean(false);
                }
            }

            int factor1 = in.readInt();
            int factor2 = in.readInt();
            int result = factor1 * factor2;
            out.writeInt(result);
        } catch ( IOException e ) {} // Fehler bei Ein- und Ausgabe
        finally { if ( client != null ) 
            try { client.close(); } catch ( IOException e ) { }
        }
    }
    
    private int searchName(String name){
        try{ 
            FileReader usrIn = new FileReader("./usr");
            BufferedReader usrs = new BufferedReader(usrIn);                ArrayList<String> names = new ArrayList<String>();
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
    private boolean comparePWD(String pwd, int ptr){ //TODO: andere abfrage noch schreiben
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

}