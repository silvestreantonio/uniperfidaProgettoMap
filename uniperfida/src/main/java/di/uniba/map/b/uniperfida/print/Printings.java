package di.uniba.map.b.uniperfida.print;

public class Printings {

    public static long startTime;
    public static long endTime;
    public static long seconds;
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
    public static void printIntro2(){
        System.out.println("**********DUE GIORNI DOPO**********");
        System.out.println("\n\n“Buongiorno professor Silvestre, io sono Edoardo.”" +
                "\n“Ciao Edoardo. Sono molto felice di conoscerti, prima di partire lascia che ti spieghi un paio di cose. Iniziamo subito, sei pronto?”" +
                "\n“Sissignore!”" +
                "\n“Come vedi questa è una macchina interdimensionale e serve per viaggiare attraverso i Multiversi. Il viaggio durerà qualche secondo e non ti accorgerai di nulla. Una volta dentro la macchina, premi il tasto rosso per partire. " +
                "\nRicorda: hai a disposizione solamente sessanta minuti. Passata l’ora ritorna esattamente nel punto in cui hai approdato”" +
                "\n“Ho capito, ci sono rischi?”" +
                "\n“No”." +
                "\n…" +
                "\n…" +
                "\n“Ah dimenticavo, molto probabilmente ti ritroverai in un mondo diviso per nazioni e lingue. Ancora non conoscono bene il concetto di integrazione, in bocca al lupo!”.");
        System.out.println();
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
        System.out.println("'h/help' per visualizzare i comandi.");
        System.out.println("'n/nord' per muoverti verso Nord.");
        System.out.println("'s/sud' per muoverti verso Sud.");
        System.out.println("'e/est' per muoverti verso Est.");
        System.out.println("'o/ovest' per muoverti verso Ovest.");
        System.out.println("'m/mappa' per visualizzare la mappa.");
        System.out.println("'borsellino' per sapere quante monete hai.");
        System.out.println("'osserva' per osservare l'ambiente circostante.");
        System.out.println("'termina' per uscire dal gioco.");
        System.out.println("Qualunque cosa tu scriva non inerente al contesto del gioco non verrà presa in considerazione.");
        System.out.println("Potrei elencarti tutti i possibili comandi ma, ahime, ciò renderebbe il gioco troppo semplice." +
                "\nL'unico consiglio che posso darti è: non esagerare con i caffè.");
        System.out.println("Sta a te scoprire tutti gli altri comandi per proseguire nell'avventura. :)");

    }
    public static void printCoffeeMenu() {
        System.out.println("Premere: ");
        System.out.println("1) Caffè");
        System.out.println("2) Caffè lungo");
        System.out.println("3) Caffè macchiato");
        System.out.println("4) Caffè al cioccolato");
        System.out.println("0) Esci");
        System.out.println("Inserisci il numero qui sotto:");
    }
    public static void printEnd(){
        System.out.println("A quanto pare verbalizzare i voti in questo universo è una cosa molto complessa.");
        System.out.println("...");
        System.out.println("Oh no, è gia tempo di andare. I sessanta minuti scadono tra pochissimo!");
        System.out.println("...");
        System.out.println("\nCorri verso la navicella. Ti volti e noti un ragazzo molto simile all'assistente Olivieri che preso dall'ansia esclama “Uff, non riuscirò mai a verbalizzare il mio voto!“." +
                "\nSorridi. In cuor tuo sai che ha ragione."+
                "\n..." +
                "\n..." +
                "\nIntanto rientri nella tua dimensione." +
                "\n“Com'è stato? Com'erano le persone? C'erano guerre? Che lingua si parlava? La gente è davvero senza morale? E che mi dici delle...“"+
                "\n“Non ricordo nulla. So solo che non mi pento di non aver fatto l'università!“");
    }
    public static void printRossetto(){
        System.out.println("#1 Benvenuto nel menu di accettazione." +
                "\nRisolvi l’indovinello per autenticarti:" +
                "\n - a volte in delle situazioni sono fuori luogo;" +
                "\n - non lascio le tue labbra;" +
                "\n - resisto all’acqua;" +
                "\n - non sono rosso per forza." +
                "\nInserisci la soluzione qui sotto oppure premi 0 per uscire.");
    }
    public static void printImpavido(){
        System.out.println("#2 Benvenuto nel menu di accettazione." +
                "\nRisolvi l’indovinello per autenticarti:" +
                "\n - sono un aggettivo;" +
                "\n - sono coraggioso;" +
                "\n - sono razionale di fronte ad una minaccia;" +
                "\n - letteralmente qualcosa in più di avido." +
                "\nInserisci la soluzione qui sotto oppure premi 0 per uscire.");
    }
    public static void printGatto(){
        System.out.println("#3 Benvenuto nel menu di accettazione." +
                "\nRisolvi l’indovinello per autenticarti:" +
                "\n - notoriamente domestico;" +
                "\n - i miei video sono divertenti;" +
                "\n - duro a morire;" +
                "\n - spesso in compagnia di una volpe." +
                "\nInserisci la soluzione qui sotto oppure premi 0 per uscire.");
    }
    public static void printCinquanta(){
        System.out.println("#4 Benvenuto nel menu di accettazione." +
                "\nRisolvi l’indovinello per autenticarti:" +
                "\n - a questa età si va in crisi;" +
                "\n - a napoli sono il pane;" +
                "\n - arancione per le banche;" +
                "\n - sono un numero." +
                "\nInserisci la soluzione qui sotto oppure premi 0 per uscire.");
    }
    public static void printPhone(){
        System.out.println("***TELEFONO***");
        System.out.println("\nDigita il numero che vuoi chiamare." +
                "\nInserisci il numero qui sotto oppure premi 0 per uscire.");
    }
    public static void printPhone2(){
        System.out.println("“Benvenuto nella segreteria studenti dell'Università di Bari.“" +
                "\n..." +
                "\n..." +
                "\n“Spiacente, i nostri operatori sono tutti occupati. È inutile riprovare.“");
    }
    public static void printVote() {
        System.out.println("Accetti?" +
                "\nsi" +
                "\nno" +
                "\nInserisci la tua scelta qui sotto:");
    }
    public static void printMessage(){
        System.out.println("All'improvviso, a causa del vento, ti capita tra le mani un fogliettino che recita:" +
                "\nMessaggio per l'Edoardo Pomarico del futuro: aiutami a verbalizzare il voto, non riesco a prenotare il ricevimento con il prof. Basilico perchè l'app MyUni fa schifo!" +
                "\nChe coincidenza! Nel tuo primo passo sul nuovo universo hai scoperto due cose: il tuo obiettivo e il fatto che il tuo alter ego sia uno svitato!");
    }

    public static void printMap(){
        System.out.println("Qui ci sarà la mappa.");
    }
}
