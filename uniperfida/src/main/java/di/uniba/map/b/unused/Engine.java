/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.unused;
//package di.uniba.map.b.uniperfida;

// work in progress
/*
import di.uniba.map.b.uniperfida.games.UniperfidaGame;
import di.uniba.map.b.uniperfida.parser.Parser;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.type.CommandType;

import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static di.uniba.map.b.uniperfida.print.Printings.*;

// classe che rappresenta il motore del gioco
public class Engine {

    private GameDescription game; // oggetto che contiene la descrizione del gioco

    private Parser parser; // oggetto che deve interpretare i comandi

    // costruttore
    public Engine(GameDescription game) {
        this.game = game; // imposta l'attributo game
        try {
            this.game.init(); // inizializza il gioco
            this.game.useFileRoomsDescription();
            this.game.useFileRoomsLook();
            this.game.useFileRoomsName();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        try {
            Set<String> stopwords = Utils.loadFileListInSet(new File("./resources/files/stopwords"));  // per creare l'insieme delle stopwords vedi in Utils
            parser = new Parser(stopwords); // inizializza il parser a cui bisogna passare l'insieme delle stopwords
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    // questo metodo carica da file tutte le descrizioni
    // questo metodo gestisce l'interazione con l'utente
    public void execute() {
        printIntro();
        //System.out.println(game.getCurrentRoom().getName());  // stampa il nome della stanza in cui si trova il giocatore
        //System.out.println();
        // qui mettiamo un controllo, se si preme il tasto invio si continua
        printIntro2();
        System.out.println("-----[" + game.getCurrentRoom().getuniverse() + "]-----");
        System.out.println(game.getCurrentRoom().getDescription()); // stampa la descrizione della stanza in cui si trova il giocatore
        printInsertCommand();
        Scanner scanner = new Scanner(System.in); // inizializza lo scanner
        while (scanner.hasNextLine()) {
            System.out.println();
            String command = scanner.nextLine(); // legge i comandi dello scanner
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getObjects(), game.getInventory(), game.getCurrentRoom().getPeople()); // ogni volta che legge un comando, lo interpreta con il parser
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) { // se il comando è diverso da null ed è di tipo END ti fa uscire
                System.out.println("Ti sei arreso!");
                System.out.println("Hai totalizzato 0 punti.");
                break;
            } else {
                game.nextMove(p, System.out); // altrimenti chiama il metodo nextMove che permette di fare una mossa e andare allo step successivo e viene chiamato ogni volta che viene interpretato un comando
                printInsertCommand();
            }
        }
    }

    public static void main(String[] args) {
        Engine engine = new Engine(new UniperfidaGame());// crea una istanza di Engine a cui passiamo un'istanza di GameDescription (in questo caso UniperfidaGame che estende GameDescription)
        engine.execute();
    }

}
*/