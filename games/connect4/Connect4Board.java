package games.connect4;

import GUI.Connect4Gui;
import SC_Kom.Message;
import games.*;

import javax.swing.*;

/**
 * Connect4Board
 */
public class Connect4Board extends Board {
    private Stack protokoll;
    private Disc[][] discs;
    private Connect4Gui gui;
    public Connect4Board(Disc[][] discs, Connect4Gui gui) {
        this.discs = discs;
        this.protokoll = new Stack();
        this.gui=gui;
    }

    public Connect4Gui getGui(){ return gui;}

    @Override
    public void draw() {
        for (int i = 0; i < discs.length; i++) {
            System.out.printf(" %d", i);
        }
        System.out.printf("\n");
        for (int i = 0; discs[0].length > i; i++) { // links oben 0,0
            for (int j = 0; discs.length > j; j++) {
                Disc disc = discs[j][i];
                if (disc != null) {
                    System.out.printf("|%d", disc.getColor());
                } else {
                    System.out.printf("| ");
                }
            }
            System.out.printf("|\n");
        }
    }

    public void setDisc(int coordinate, Player player, int turn) { // 0 Colum is full //1 successfull turn //2 win
        if (coordinate < 0 || coordinate >= discs.length) {
            return ;
        }
        int row = discs[coordinate].length - 1;
        while (row > -1 && discs[coordinate][row].getColor() != 2) {
            row--;
        }
        if (row <= -1) { // colum full
            return;
        } else {
            discs[coordinate][row].setColor(turn % 2);
            add(new Node(coordinate, row, player),turn);
            if (win(coordinate, row)) { // win status 2
                gui.win();
            } else {
                gui.changeClickable();
            }
        }
    }

    private boolean win(int length, int height) { // gewinnbedingungen pruefen
        int counterH = 0, counterV = 0, counterD1 = 0, counterD2 = 0;
        for (int i = -3; i < 4; i++) {
            if (discs.length > length + i && length + i >= 0 && discs[length + i][height] != null
                    && discs[length][height].getColor() == discs[length + i][height].getColor()) { // teste horizontale
                if (++counterH >= 4) {
                    return true;
                }
            } else {
                counterH = 0;
            }
            if (discs[length].length > height + i && height + i >= 0 && discs[length][height + i] != null
                    && discs[length][height].getColor() == discs[length][height + i].getColor()) { // teste Vertikale
                if (++counterV >= 4) {
                    return true;
                }
            } else {
                counterV = 0;
            }
            if (discs.length > length + i && length + i >= 0 && discs[length].length > height + i && height + i >= 0
                    && discs[length + i][height + i] != null
                    && discs[length][height].getColor() == discs[length + i][height + i].getColor()) { // teste
                                                                                                       // Diagonale
                                                                                                       // links oben
                                                                                                       // nach rechts
                                                                                                       // unten
                if (++counterD1 >= 4) {
                    return true;
                }
            } else {
                counterD1 = 0;
            }
            if (discs.length > length - i && length - i >= 0 && discs[length].length > height + i && height + i >= 0
                    && discs[length - i][height + i] != null
                    && discs[length][height].getColor() == discs[length - i][height + i].getColor()) { // teste
                                                                                                       // horizontale
                                                                                                       // links unten
                                                                                                       // nach rechts
                                                                                                       // oben
                if (++counterD2 >= 4) {
                    return true;
                }
            } else {
                counterD2 = 0;
            }
        }
        return false;
    }

    @Override
    public int getLength() {
        return discs.length;
    }


    public Node delete() {
        Node nodeDelete = protokoll.pop();
        discs[nodeDelete.getLengthCoordinate()][nodeDelete.getHeightCoordinate()] = null;
        draw();
        return nodeDelete;
    }

    public void add(Node n,int turn) {
        protokoll.push(n);
        if(n.getPlayer().getIsHuman())
            send(n,turn);
    }

    synchronized private void send(Node n, int turn) {
        String msg = "connect,";
        msg += n.getPlayer().getPlayerName() + ","+turn+",0,";
        msg += n.getLengthCoordinate();
        Message.sendMessage(10);
        Message.sendMessage(msg);
    }
}
