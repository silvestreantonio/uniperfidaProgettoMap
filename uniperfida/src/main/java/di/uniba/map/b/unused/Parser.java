/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.unused;
//package di.uniba.map.b.uniperfida.parser;

// work in progress
/*
//import di.uniba.map.b.uniperfida.Utils;
import di.uniba.map.b.uniperfida.type.AdvObject;
import di.uniba.map.b.uniperfida.type.Command;
import di.uniba.map.b.uniperfida.type.Person;

import java.util.List;
import java.util.Set;


public class Parser {

    private final Set<String> stopwords;

    public Parser(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    private int checkForCommand(String token, List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    private int checkForObject(String token, List<AdvObject> obejcts) {
        for (int i = 0; i < obejcts.size(); i++) {
            if (obejcts.get(i).getName().equals(token) || obejcts.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    private int checkForPerson(String token, List<Person> person) {
        for (int i = 0; i < person.size(); i++) {
            if (person.get(i).getName().equals(token) || person.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    public ParserOutput parse(String command, List<Command> commands, List<AdvObject> objects, List<AdvObject> inventory, List<Person> person) {
        List<String> tokens = Utils.parseString(command, stopwords);
        if (!tokens.isEmpty()) {
            int ic = checkForCommand(tokens.get(0), commands);
            if (ic > -1) {
                if (tokens.size() > 1) {
                    int io = checkForObject(tokens.get(1), objects);
                    int ioinv = checkForObject(tokens.get(1), inventory);
                    int ip = checkForPerson(tokens.get(1), person);
                    if (io > -1) {
                        return new ParserOutput(commands.get(ic), objects.get(io), null);
                    } else if (ioinv > -1) {
                        return new ParserOutput(commands.get(ic), null, inventory.get(ioinv));
                    } else if (ip > -1) {
                        return new ParserOutput(commands.get(ic), person.get(ip));
                    } else {
                        return new ParserOutput(commands.get(ic), null, null);
                    }
                } else {
                    return new ParserOutput(commands.get(ic), null);
                }
            } else {
                return new ParserOutput(null, null, null);
            }
        } else {
            return null;
        }
    }
}

*/