/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida;

import di.uniba.map.b.uniperfida.games.UniperfidaGame;
import di.uniba.map.b.uniperfida.parser.Parser;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.type.CommandType;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/**
 * ATTENZIONE: l'Engine è molto spartano, in realtà demanda la logica alla
 * classe che implementa GameDescription e si occupa di gestire I/O sul
 * terminale.
 *
 * @author pierpaolo
 */
// classe che rappresenta il motore del gioco
public class Engine {

    private final GameDescription game; // oggetto che contiene la descrizione del gioco

    private Parser parser; // oggetto che deve interpretare i comandi

    // costruttore
    public Engine(GameDescription game) {
        this.game = game; // imposta l'attributo game
        try {
            this.game.init(); // inizializza il gioco
        } catch (Exception ex) {
            System.err.println(ex);
        }
        try {
            Set<String> stopwords = Utils.loadFileListInSet(new File("./resources/stopwords"));  // per creare l'insieme delle stopwords vedi in Utils
            parser = new Parser(stopwords); // inizializza il parser a cui bisogna passare l'insieme delle stopwords
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // questo metodo gestisce l'interazione con l'utente
    public void execute() {
        System.out.println("================================");
        System.out.println("* Uniperfida v. 0.1 - 2020-2021 *");
        System.out.println("================================");
        System.out.println("\n\nUniverso J-371, Terra, laboratorio di ricerca “Pablo Escobar”, Martedì, 4 Maggio 2021.");
        System.out.println("\nIl professor Silvestre ha finalmente ultimato il suo progetto: una macchina che permette di viaggiare tra gli universi.");
        System.out.println("\n“Assistente Olivieri, venga qui e mi dica cosa vede.” "
                + "\n“Vedo una macchina interdimensionale?” "
                + "\n“No, è la nostra possibilità per sbagliare, il nostro mondo è talmente perfetto che non ci permette di capire cosa sia giusto o sbagliato”"
                + "\n“Non capisco”"
                + "\n“C’è un universo, conosciuto come T-237, in cui ci sono le stesse persone."
                + "\nPensa: la loro libertà, senza alcun vincolo morale, ha reso quel mondo invivibile. Viviamo due situazioni diametralmente opposte ma desideriamo la stessa cosa: evadere."
                + "\nOra ci serve solo una cavia."
                + "\nSecondo la legge dei Multiversi, se una persona effettua un viaggio interdimensionale si ritroverà nello stesso nostro anno, a vivere una vita che è sua solo in quell’universo, quindi io lì potrei essere un delinquente!”"
                + "\n“Ce l’ho, ce l’ho, conosco un tipo di nome Edoardo abbastanza sconsiderato da affrontare un viaggio da cui potrebbe non fare ritorno” "
                + "\n“Okay, ci incontriamo qui giovedì alle 15”.\n\n");
        //System.out.println(game.getCurrentRoom().getName());  // stampa il nome della stanza in cui si trova il giocatore
        //System.out.println();
        System.out.println("-----[" + game.getCurrentRoom().getuniverse() + "]-----");
        System.out.println(game.getCurrentRoom().getDescription()); // stampa la descrizione della stanza in cui si trova il giocatore
        System.out.println();
        System.out.println("Inserisci il comando qui sotto:");
        Scanner scanner = new Scanner(System.in); // inizializza lo scanner
        while (scanner.hasNextLine()) {
            System.out.println();
            String command = scanner.nextLine(); // legge i comandi dello scanner
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory()); // ogni volta che legge un comando, lo interpreta con il parser
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.ESCI) { // se il comando è diverso da null ed è di tipo END ti fa uscire
                System.out.println("Addio!");
                break;
            } else {
                game.nextMove(p, System.out); // altrimenti chiama il metodo nextMove che permette di fare una mossa e andare allo step successivo e viene chiamato ogni volta che viene interpretato un comando
                System.out.println();
                System.out.println("Inserisci il comando qui sotto:");

            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("* Uniperfida v. 0.1 - 2020-2021 *");
        System.out.println("================================");
        System.out.println("\n\nUniverso J-371, Terra, laboratorio di ricerca “Pablo Escobar”, Martedì, 4 Maggio 2021.");
        System.out.println("\nIl professor Silvestre ha finalmente ultimato il suo progetto: una macchina che permette di viaggiare tra gli universi.");
        System.out.println("\n“Assistente Olivieri, venga qui e mi dica cosa vede.” "
                + "\n“Vedo una macchina interdimensionale?” "
                + "\n“No, è la nostra possibilità per sbagliare, il nostro mondo è talmente perfetto che non ci permette di capire cosa sia giusto o sbagliato”"
                + "\n“Non capisco”"
                + "\n“C’è un universo, conosciuto come T-237, in cui ci sono le stesse persone."
                + "\nPensa: la loro libertà, senza alcun vincolo morale, ha reso quel mondo invivibile. Viviamo due situazioni diametralmente opposte ma desideriamo la stessa cosa: evadere."
                + "\nOra ci serve solo una cavia."
                + "\nSecondo la legge dei Multiversi, se una persona effettua un viaggio interdimensionale si ritroverà nello stesso nostro anno, a vivere una vita che è sua solo in quell’universo, quindi io lì potrei essere un delinquente!”"
                + "\n“Ce l’ho, ce l’ho, conosco un tipo di nome Edoardo abbastanza sconsiderato da affrontare un viaggio da cui potrebbe non fare ritorno” "
                + "\n“Okay, ci incontriamo qui giovedì alle 15”.\n\n");
        Engine engine = new Engine(new UniperfidaGame()); // crea una istanza di Engine a cui passiamo un'istanza di GameDescription (in questo caso UniperfidaGame che estende GameDescription)
        engine.execute();
    }

}
