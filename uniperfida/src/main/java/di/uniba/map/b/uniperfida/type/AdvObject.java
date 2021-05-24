/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author pierpaolo
 */
public class AdvObject {

    private final int id; // attributo che rende unico quell'oggetto

    private String name; // nome 

    private String description; // descrizione
    
    private Set<String> alias; // set di string che contiene tutti i sinonimi di quell'oggetto

    private boolean openable = false; // l'oggetto puo essere aperto?

    private boolean pickupable = true; // l'oggetto puo essere raccolto?

    private boolean pushable = false; // l'oggetto puo essere premuto?

    private boolean open = false; // l'oggetto è aperto?

    private boolean push = false; // l'oggetto è chiuso?


    // costruttore
    public AdvObject(int id) {
        this.id = id;
    }

    // costruttore
    public AdvObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // costruttore
    public AdvObject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // costruttore
    public AdvObject(int id, String name, String description, Set<String> alias) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.alias = alias;
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

    public boolean isOpenable() {
        return openable;
    }

    public void setOpenable(boolean openable) {
        this.openable = openable;
    }

    public boolean isPickupable() {
        return pickupable;
    }

    public void setPickupable(boolean pickupable) {
        this.pickupable = pickupable;
    }

    public boolean isPushable() {
        return pushable;
    }

    public void setPushable(boolean pushable) {
        this.pushable = pushable;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(Set<String> alias) {
        this.alias = alias;
    }
    
    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

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
        final AdvObject other = (AdvObject) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
