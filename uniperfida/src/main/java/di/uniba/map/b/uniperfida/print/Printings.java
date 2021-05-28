package di.uniba.map.b.uniperfida.print;

public class Printings {
    public static void printIntro(){
        System.out.println("================================");
        System.out.println("* Uniperfida v. 1.1 - 2020-2021 *");
        System.out.println("================================");
        System.out.println("\n\nUniverso T-237, Terra, laboratorio di ricerca “Pablo Escobar”, Martedì, 4 Maggio 2021.");
        System.out.println("\nIl professor Silvestre ha finalmente ultimato il suo progetto: una macchina che permette di viaggiare tra gli universi.");
        System.out.println("\n“Assistente Olivieri, venga qui e mi dica cosa vede.” "
                + "\n“Vedo una macchina interdimensionale?” "
                + "\n“No, è la nostra possibilità per sbagliare, il nostro mondo è talmente perfetto che non ci permette di capire cosa sia giusto o sbagliato”"
                + "\n“Non capisco”"
                + "\n“C’è un universo, conosciuto come J-371, in cui ci sono le stesse persone."
                + "\nPensa: la loro libertà, senza alcun vincolo morale, ha reso quel mondo invivibile. Viviamo due situazioni diametralmente opposte ma desideriamo la stessa cosa: evadere."
                + "\nOra ci serve solo una cavia."
                + "\nSecondo la legge dei Multiversi, se una persona effettua un viaggio interdimensionale si ritroverà nello stesso nostro anno, a vivere una vita che è sua solo in quell’universo, quindi io lì potrei essere un delinquente!”"
                + "\n“Ce l’ho, ce l’ho, conosco un tipo di nome Edoardo abbastanza sconsiderato da affrontare un viaggio da cui potrebbe non fare ritorno” "
                + "\n“Okay, ci incontriamo qui giovedì alle 15”.\n\n");
    }
    public static void printInsertCommand(){
        System.out.println();
        System.out.println("Inserisci il comando qui sotto oppure premi 'help' per visualizzare la lista dei possibili comandi");
    }
    public static void printHelp(){
        System.out.println("========================");
        System.out.println("**********HELP**********");
        System.out.println("========================");
        System.out.println("Digita:");
        System.out.println("'help' per visualizzare i comandi.");
        System.out.println("'N/Nord' per muoverti verso Nord.");
        System.out.println("'S/Sud' per muoverti verso Sud.");
        System.out.println("'E/Est' per muoverti verso Est.");
        System.out.println("'O/Ovest' per muoverti verso Ovest.");
        System.out.println("'Inventario' per visualizzare l'inventario.");
        System.out.println("'Osserva' per osservare l'ambiente circostante.");
        System.out.println("'Esci' per uscire dal gioco.");
        System.out.println("Potrei elencarti tutti i possibili comandi ma, ahime, ciò renderebbe il gioco troppo semplice.");
        System.out.println("Sta a te scoprire tutti gli altri comandi per proseguire nell'avventura. :)");

    }
}
