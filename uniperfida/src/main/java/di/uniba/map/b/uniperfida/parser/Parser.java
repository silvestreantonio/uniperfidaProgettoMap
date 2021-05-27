/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.parser;

import di.uniba.map.b.uniperfida.Utils;
import di.uniba.map.b.uniperfida.type.AdvObject;
import di.uniba.map.b.uniperfida.type.Command;
import java.util.List;
import java.util.Set;
import java.io.*;

/**
 *
 * @author pierpaolo
 */

// la minima intelligenza che ha sta nella gestione delle stopwords
// come prima cosa il parser andra ad eliminare le stopwords e poi verifica cio che è rimasto
public class Parser {

    private final Set<String> stopwords; // sono quelle parole che non hanno semantica (es. articoli, preposizioni, ecc...)

    public Parser(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    // metodo che cicla sui comandi e verifica che il token coincide con un comando o un alias di un comando e restituisce l'indice in cui si trova
    private int checkForCommand(String token, List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    // metodo che cicla sui comandi e verifica che il token coincide con un comando o un alias di un comando e restituisce l'indice in cui si trova
    private int checkForObject(String token, List<AdvObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getName().equals(token) || objects.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    /* ATTENZIONE: il parser è implementato in modo abbastanza independete dalla lingua, ma riconosce solo 
    * frasi semplici del tipo <azione> <oggetto> <oggetto>. Eventuali articoli o preposizioni vengono semplicemente
    * rimossi.
     */
    
    // il parser prende il comando, poi una lista di comandi (che sarebbe la lista dei comandi che il mio gioco può interpretare
    // poi prende la lista di tutti gli oggetti che stanno nell'ambiente, e la lista di tutti gli oggetti nell'inventario
    public ParserOutput parse(String command, List<Command> commands, List<AdvObject> objects, List<AdvObject> inventory) {
        List<String> tokens = Utils.parseString(command, stopwords); // metodo di Utils che elimina le stopwords (vedi classe Utils)
        if (!tokens.isEmpty()) {
            int ic = checkForCommand(tokens.get(0), commands); // controlla se il token 0 è presente nella lista dei comandi
            if (ic > -1) { // se il risultato è maggiore di 0 (quindi se il token è presente)
                if (tokens.size() > 1) { // controlla se ci sono altri token
                    int io = checkForObject(tokens.get(1), objects); // controlla se il token 1 è presente nella lista degli oggetti
                    int ioinv = -1;
                    if (io < 0 && tokens.size() > 2) { // se il token 1 non è un oggetto fa un altro tentativo
                        io = checkForObject(tokens.get(2), objects); // controlla se il token 2 è presente nella lista degli oggetti (andrebbe modificato perchè lo fa solo per due token)
                    }
                    if (io < 0) { // se il token 2 non è un oggetto 
                        ioinv = checkForObject(tokens.get(1), inventory); // controlla se il token 1 è presente nell' inventario
                        if (ioinv < 0 && tokens.size() > 2) { // se il token 1 non è nell'inventario 
                            ioinv = checkForObject(tokens.get(2), inventory); // controlla se il token 2 è presente nell'inventario
                        }
                    }
                    if (io > -1 && ioinv > -1) { // in base ai risultati degli indici restituisce il ParserOutput
                        return new ParserOutput(commands.get(ic), objects.get(io), inventory.get(ioinv));
                    } else if (io > -1) {
                        return new ParserOutput(commands.get(ic), objects.get(io), null);
                    } else if (ioinv > -1) {
                        return new ParserOutput(commands.get(ic), null, inventory.get(ioinv));
                    } else {
                        return new ParserOutput(commands.get(ic), null, null);
                    }
                } else {
                    return new ParserOutput(commands.get(ic), null);
                }
            } else {
                return new ParserOutput(null, null); // se il token 0 non è nessun comando restituisce un ParserOutput null
            }
        } else {
            return null;
        }
    }

}
