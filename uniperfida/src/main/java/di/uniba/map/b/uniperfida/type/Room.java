/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.type;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierpaolo
 */

// classe che astrae il concetto di stanza
public class Room {

    private final int id; // attributo che serve ad individuare in maniera univica questa stanza

    private String name; // nome

    private String description; // descrizione stanza (es. Sei all'interno di una grotta ...)

    private String look; // stringa che viene visualizzata quando il giocatore preme "osserva" o "descrivi"

    private String universe;

    private boolean visible = true; // attributo che serve per nascondere la stanza (es. la segreteria)

    private Room south = null; // attributo che memorizza la stanza in cui si puo andare se si preme "sud"

    private Room north = null; // attributo che memorizza la stanza in cui si puo andare se si preme "nord"

    private Room east = null; // attributo che memorizza la stanza in cui si puo andare se si preme "est"

    private Room west = null; // attributo che memorizza la stanza in cui si puo andare se si preme "ovest"

    private Room up = null;

    private Room down = null;
    // nord sud est e ovest è detto concetto di aggregazione 
    
    private final List<AdvObject> objects=new ArrayList<>();// la stanza puo contenere degli oggetti, questi oggetti sono una lista di AdvObjects
    private final List<AdvObjectContainer> objectsContainers=new ArrayList<>();
    // costruttore
    public Room(int id, String name, String description, boolean visible, String universe) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visible = visible;
        this.universe = universe;
    }

    public Room(int id, String name, String description, String universe) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.universe = universe;
    }

    // metodi set e get
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Room getUp() {
        return up;
    }

    public void setUp(Room up) {
        this.up = up;
    }

    public Room getDown() {
        return down;
    }

    public void setDown(Room down) {
        this.down = down;
    }

    public String getuniverse() {return universe; }

    public void setuniverse(String universe) {this.universe = universe; }

    public List<AdvObject> getObjects() {
        return objects;
    }

    public List<AdvObjectContainer> getObjectsContainers() {
        return objectsContainers;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id;
        return hash;
    }

    // metodo equals sull'id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

}
