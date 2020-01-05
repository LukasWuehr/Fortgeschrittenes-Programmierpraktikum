package server;

import java.io.IOException;
import java.util.Scanner;

public class ServerInput extends Thread{
    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        while(true){
            if(scan.nextLine().equals("/exit")){
                for (ClientNode node: MulServerThread.clients ) {
                    try{node.getClient().close();}
                    catch (IOException e){System.out.println("Cant close Server");}
                }
                return;
            }
        }
    }
}
