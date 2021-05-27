/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida;

import di.uniba.map.b.uniperfida.games.UniperfidaGame;
import di.uniba.map.b.uniperfida.parser.Parser;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.print.Printings;
import di.uniba.map.b.uniperfida.type.CommandType;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import static di.uniba.map.b.uniperfida.print.Printings.printInsertCommand;
import static di.uniba.map.b.uniperfida.print.Printings.printIntro;
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
        printIntro();
        //System.out.println(game.getCurrentRoom().getName());  // stampa il nome della stanza in cui si trova il giocatore
        //System.out.println();
        System.out.println("-----[" + game.getCurrentRoom().getuniverse() + "]-----");
        System.out.println(game.getCurrentRoom().getDescription()); // stampa la descrizione della stanza in cui si trova il giocatore
        printInsertCommand();
        Scanner scanner = new Scanner(System.in); // inizializza lo scanner
        while (scanner.hasNextLine()) {
            System.out.println();
            String command = scanner.nextLine(); // legge i comandi dello scanner
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory()); // ogni volta che legge un comando, lo interpreta con il parser
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) { // se il comando è diverso da null ed è di tipo END ti fa uscire
                System.out.println("Addio!");
                break;
            } else {
                game.nextMove(p, System.out); // altrimenti chiama il metodo nextMove che permette di fare una mossa e andare allo step successivo e viene chiamato ogni volta che viene interpretato un comando
                printInsertCommand();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Engine engine = new Engine(new UniperfidaGame()); // crea una istanza di Engine a cui passiamo un'istanza di GameDescription (in questo caso UniperfidaGame che estende GameDescription)
        engine.execute();
    }

}
