/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida;

import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.type.AdvObject;
import di.uniba.map.b.uniperfida.type.Command;
import di.uniba.map.b.uniperfida.type.Room;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierpaolo
 */

// per fare in modo che la classe Engine possa essere utilizzata su piu giochi senza vincoli e' stata creata questa classe astratta GameDescription
// questa classe e' una descrizione astratta di un gioco 
public abstract class GameDescription {

    private final List<Room> rooms = new ArrayList<>(); // contiene la lista delle stanze presenti nel gioco

    private final List<Command> commands = new ArrayList<>(); // contiene la lista dei comandi presenti nel gioco

    private final List<AdvObject> inventory = new ArrayList<>(); // contiene l'inventario
 
    private Room currentRoom; // contiene la stanza corrente

    // metodi set e get
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<AdvObject> getInventory() {
        return inventory;
    }

    public abstract void init() throws Exception; // metodo astratto per inizializzare il gioco

    public abstract void nextMove(ParserOutput p, PrintStream out); // metodo astratto per interpretare la mossa

}
