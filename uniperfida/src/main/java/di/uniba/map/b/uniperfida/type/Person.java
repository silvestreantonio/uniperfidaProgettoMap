package di.uniba.map.b.uniperfida.type;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Person {

    private final int id; // attributo che rende unica quella persona

    private String name; // nome

    private String description; // descrizione

    private boolean talkable = false;

    private Set<String> alias; // set di string che contiene tutti i sinonimi di quell'oggetto

    // costruttore
    public Person(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

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

    public boolean isTalkable() { return talkable;}

    public void setTalkable(boolean talkable){ this.talkable = talkable;}

    public Set<String> getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = new HashSet<>(Arrays.asList(alias));
    }
}

