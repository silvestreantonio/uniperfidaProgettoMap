/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.games;

import di.uniba.map.b.uniperfida.GameDescription;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.type.AdvObject;
import di.uniba.map.b.uniperfida.type.AdvObjectContainer;
import di.uniba.map.b.uniperfida.type.Command;
import di.uniba.map.b.uniperfida.type.CommandType;
import di.uniba.map.b.uniperfida.type.Room;
import java.io.PrintStream;
import java.util.Iterator;

/**
 * ATTENZIONE: La descrizione del gioco è fatta in modo che qualsiasi gioco
 * debba estendere la classe GameDescription. L'Engine è fatto in modo che posso
 * eseguire qualsiasi gioco che estende GameDescription, in questo modo si
 * possono creare più gioci utilizzando lo stesso Engine.
 *
 * Diverse migliorie possono essere applicate: - la descrizione del gioco
 * potrebbe essere caricate da file o da DBMS in modo da non modificare il
 * codice sorgente - l'utilizzo di file e DBMS non è semplice poiché all'interno
 * del file o del DBMS dovrebbe anche essere codificata la logica del gioco
 * (nextMove) oltre alla descrizione di stanze, oggetti, ecc...
 *
 * @author pierpaolo
 */
// all'interno di questa classe deve andare tutta la logica del gioco possiamo mettere la logica del gioco ovvero tutta questa classe all'interno di un file o un DB e creare una classe che richiami il file o il DB
public class UniperfidaGame extends GameDescription {

    @Override
    public void init() throws Exception { // questo è il metodo chiamato da Engine, qui dentro dobbiamo inizializzare tutta la struttura del gioco
        // definizione dei comandi compresi anche i sinonimi e poi li aggiungo alla lista presente in GameDescription
        Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command sud = new Command(CommandType.SUD, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.OVEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command inventory = new Command(CommandType.INVENTARIO, "inventario");
        inventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(inventory);
        Command end = new Command(CommandType.ESCI, "end");
        end.setAlias(new String[]{"end", "fine", "finire", "esci", "uscire", "muori", "morire", "ammazzati", "ucciditi", "suicidati", "exit"});
        getCommands().add(end);
        Command look = new Command(CommandType.OSSERVA, "osserva");
        look.setAlias(new String[]{"guarda", "guardare", "vedi", "vedere", "trova", "trovare", "cerca", "cercare", "fissa", "fissare"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PRENDI, "raccogli");
        pickup.setAlias(new String[]{"prendi", "prendere", "raccogliere"});
        getCommands().add(pickup);
        Command lascia = new Command(CommandType.LASCIA, "lascia");
        lascia.setAlias(new String[]{"lasciare", "buttare", "gettare", "butta", "getta"});
        getCommands().add(lascia);
        Command open = new Command(CommandType.APRI, "apri");
        open.setAlias(new String[]{"aprire", "schiudi", "schiudere", "spalanca", "spalancare"});
        getCommands().add(open);
        Command push = new Command(CommandType.PREMI, "premi");
        push.setAlias(new String[]{"spingi", "spingere", "attiva", "attivare", "premere", "schiaccia", "schiacciare"});
        getCommands().add(push);
        Command goUp = new Command(CommandType.SALI, "sopra");
        goUp.setAlias(new String[]{"salire", "sali scale", "salire scale", "sali", "su", "sù"});
        getCommands().add(goUp);
        Command goDown = new Command(CommandType.SCENDI, "sotto");
        goDown.setAlias(new String[]{"scendere", "scendi scale", "scendere scale", "scendi", "giu", "giù"});
        getCommands().add(goDown);
        // definizione delle stanze
        Room laboratory = new Room(1, "Laboratorio del professor Silvestre", "Ti trovi nel laboratorio del professor Silvestre. Il professore ed il suo assistente stanno attendendo una tua mossa.", "Universo J-371");
        laboratory.setLook("Hai davanti la macchina, la porta è aperta");
        Room machine = new Room(2, "Navicella Space-Voyager", "Ti trovi nella navicella Space-Voyager. Il professore ed il suo assistente fremono per la tua imminente partenza.", "Universo J-371");
        machine.setLook("C'è una luce soffusa e un enorme tasto rosso");
        Room courtyard = new Room(3, "Cortile", "Ti trovi nel cortile. C'è uno strano edificio pieno zeppo di finestre. C'è un cartello con incisa una scritta.", "Universo J-371");
        courtyard.setLook("Il cartello recita: 'Università di Bari Aldo Moro, Facoltà di Informatica'. Fortunatamente qui si parla italiano. Di fronte a te c'è una porta."); //Senti qualcosa nelle tasche vibrare
        Room hall = new Room(4, "Atrio principale", "Ti trovi nell'atrio principale. C'è molto eco.", "Universo J-371");
        hall.setLook("C'è una macchinetta del caffè e una bacheca.");
        Room reception = new Room(5, "Ufficio del collaboratore", "Ti trovi nell'ufficio del collaboratore. Sembra essere vuoto.", "Universo J-371");
        reception.setLook("C'è una scrivania con un computer ed un foglio.");
        Room firstBathroom = new Room(6, "Bagno del piano terra", "Ti trovi nei bagni del piano terra. Non noti nulla di strano.", "Universo J-371");
        firstBathroom.setLook("Un semplice bagno universitario, senza carta igienica e sapone..." + "ovviamente.");
        Room corridor = new Room(7, "Corridoio del piano terra", "Sei nel corridoio. C'è un cartellone in onore di 'Accendino', un ragazzo fantastico.", "Universo J-371");
        corridor.setLook("Non c'è niente di particolare.");
        Room infoPoint = new Room(8, "Zona delle bacheche", "Ti trovi nella zona delle bacheche.", "Universo J-371");
        infoPoint.setLook("Nelle bacheche non c'è nulla infisso. Come sempre d'altronde.");
        Room firstElevator = new Room(9, "Ascensore", "Ti trovi nell'ascensore. E' molto grande.", "Universo J-371");
        firstElevator.setLook("C'è un bottone.");
        Room stairs = new Room(10, "Scale piano terra", "Ti trovi nel pianerottolo delle scale del piano terra.", "Universo J-371");
        stairs.setLook("C'è un'enorme scalinata che sale.");
        Room waitingRoom = new Room(11, "Sala d'attesa aule", "Ti trovi nella sala d'attesa delle aule.", "Universo J-371");
        waitingRoom.setLook("Ci sono un paio di sedie. Sopra le sedie c'è una bacheca, l'ennesima bacheca.");
        Room roomA = new Room(12, "Aula A", "Ti trovi nell'aula A. Non vedi nulla di interessante.", "Universo J-371");
        roomA.setLook("Ci sono file di banchi. L'aula è vuota.");
        Room roomB = new Room(13, "Aula B", "Ti trovi nell'aula B. Non vedi nulla di interessante.", "Universo J-371");
        roomB.setLook("Ci sono file di banchi. L'aula è vuota.");
        Room upStairs = new Room(14, "Scale primo piano", "Ti trovi nel pianerottolo delle scale del primo piano.", "Universo J-371");
        upStairs.setLook("C'è un'enorme scalinata che scende.");
        Room upStairsHall = new Room(15, "Atrio del primo piano", "Ti trovi nell'atrio del primo piano. C'è molto eco.", "Universo J-371");
        upStairsHall.setLook("C'è un enorme corridoio ed una mezza dozzina di uffici.");
        Room secondElevator = new Room(16, "Ascensore", "Ti trovi nell'ascensore. E' molto grande.", "Universo J-371");
        secondElevator.setLook("C'è un bottone.");
        Room secondBathroom = new Room(17, "Bagno del primo piano", "Ti trovi nel bagno del primo piano. Non noti nulla di strano.", "Universo J-371");
        secondBathroom.setLook("Un semplice bagno universitario, senza carta igienica e sapone..." + "ovviamente.");
        Room firstSector = new Room(18, "Primo settore del corridoio", "Ti trovi nel primo settore del corridoio. Sembra non finisca mai.", "Universo J-371");
        firstSector.setLook("Ai lati ci sono due uffici.");
        Room waitingRoomRossetto = new Room(19, "Sala d'attesa della prof.ssa Rossetto", "Ti trovi nella sala d'attesa della prof.ssa *****", "Universo J-371");
        waitingRoomRossetto.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room waitingRoomImpavido = new Room(20, "Sala d'attesa del prof. Impavido", "Ti trovi nella sala d'attesa del prof. *****", "Universo J-371");
        waitingRoomImpavido.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room secondSector = new Room(21, "Secondo settore del corridoio", "Ti trovi nel secondo settore del corridoio. Intravedi la fine.", "Universo J-371");
        secondSector.setLook("Ai lati ci sono due uffici.");
        Room waitingRoomGatto = new Room(22, "Sala d'attesa del prof. Gatto", "Ti trovi nella sala d'attesa del prof. *****", "Universo J-371");
        waitingRoomGatto.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room waitingRoomCinquanta = new Room(23, "Sala d'attesa del prof. Cinquanta", "Ti trovi nella sala d'attesa del prof. *****", "Universo J-371");
        waitingRoomCinquanta.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room thirdSector = new Room(24, "Terzo settore del corridoio", "Ti trovi nel terzo settore del corridoio. Di fronte a te c'è un muro.", "Universo J-371");
        thirdSector.setLook("Ai lati c'è l'ennesimo ufficio e l'ennesimo bagno. Avranno problemi d'incontinenza.");
        Room thirdBathroom = new Room(25, "Bagno dei professori", "Ti trovi nel bagno dei professori.", "Universo J-371");
        thirdBathroom.setLook("Questo bagno è molto meglio degli altri..." + "Sapone, carta igienica, persino un asciugamani ad aria!");
        Room waitingRoomBasilico = new Room(26, "Sala d'attesa del prof. Basilico", "Ti trovi nella sala d'attesa del prof. Basilico.", "Universo J-371");
        waitingRoomBasilico.setLook("C'è una porta, niente indovinelli questa volta.");
        Room secretary = new Room(27, "Segreteria", "Ti trovi nella segreteria.", false, "Universo J-371");
        secretary.setLook("C'è uno sportello con un foglio.");
        // definizione della mappa (collegamenti tra le stanze)
        laboratory.setNorth(machine);
        machine.setSouth(laboratory);
        courtyard.setNorth(hall);
        courtyard.setSouth(machine);
        hall.setNorth(corridor);
        hall.setSouth(courtyard);
        hall.setEast(firstBathroom);
        hall.setWest(reception);
        reception.setEast(hall);
        firstBathroom.setWest(hall);
        corridor.setNorth(infoPoint);
        corridor.setSouth(hall);
        infoPoint.setNorth(firstElevator);
        infoPoint.setSouth(corridor);
        infoPoint.setEast(stairs);
        infoPoint.setWest(waitingRoom);
        firstElevator.setSouth(infoPoint);
        stairs.setWest(infoPoint);
        stairs.setUp(upStairs);
        waitingRoom.setNorth(roomA);
        waitingRoom.setSouth(roomB);
        waitingRoom.setEast(infoPoint);
        roomA.setSouth(waitingRoom);
        roomA.setDown(secretary);
        roomB.setNorth(waitingRoom);
        upStairs.setWest(upStairsHall);
        upStairs.setDown(stairs);
        upStairsHall.setNorth(secondElevator);
        upStairsHall.setSouth(firstSector);
        upStairsHall.setEast(upStairs);
        upStairsHall.setWest(secondBathroom);
        secondElevator.setSouth(upStairsHall);
        secondBathroom.setEast(upStairsHall);
        firstSector.setNorth(upStairsHall);
        firstSector.setSouth(secondSector);
        firstSector.setEast(waitingRoomRossetto);
        firstSector.setWest(waitingRoomImpavido);
        waitingRoomRossetto.setWest(firstSector);
        waitingRoomImpavido.setEast(firstSector);
        secondSector.setNorth(firstSector);
        secondSector.setSouth(thirdSector);
        secondSector.setEast(waitingRoomGatto);
        secondSector.setWest(waitingRoomCinquanta);
        waitingRoomGatto.setWest(secondSector);
        waitingRoomCinquanta.setEast(secondSector);
        thirdSector.setNorth(secondSector);
        thirdSector.setEast(thirdBathroom);
        thirdSector.setWest(waitingRoomBasilico);
        thirdBathroom.setWest(thirdSector);
        waitingRoomBasilico.setEast(thirdSector);
        secretary.setUp(roomA);
        // aggiunte stanze alla mappa
        getRooms().add(laboratory);
        getRooms().add(machine);
        getRooms().add(courtyard);
        getRooms().add(hall);
        getRooms().add(reception);
        getRooms().add(firstBathroom);
        getRooms().add(corridor);
        getRooms().add(infoPoint);
        getRooms().add(firstElevator);
        getRooms().add(stairs);
        getRooms().add(waitingRoom);
        getRooms().add(roomA);
        getRooms().add(roomB);
        getRooms().add(upStairs);
        getRooms().add(upStairsHall);
        getRooms().add(secondElevator);
        getRooms().add(secondBathroom);
        getRooms().add(firstSector);
        getRooms().add(waitingRoomRossetto);
        getRooms().add(waitingRoomImpavido);
        getRooms().add(secondSector);
        getRooms().add(waitingRoomGatto);
        getRooms().add(waitingRoomCinquanta);
        getRooms().add(thirdSector);
        getRooms().add(thirdBathroom);
        getRooms().add(waitingRoomBasilico);
        getRooms().add(secretary);
        // definizione degli oggetti 
        AdvObject buttonMachine = new AdvObject(1, "bottone", "Un enorme tasto rosso", true, false);
        buttonMachine.setAlias(new String[]{"tasto"});
        machine.getObjects().add(buttonMachine);
        AdvObject boardsHall = new AdvObject(2, "bacheca", "Una semplice bacheca", true);
        boardsHall.setAlias(new String[]{"diario"});
        hall.getObjects().add(boardsHall);
        AdvObject prova = new AdvObject(3, "prova", "Una semplice bacheca", true);
        prova.setAlias(new String[]{"prova"});
        AdvObject coin = new AdvObject(4,"moneta",true,"Una moneta da 50 cents, perfetta per comprare cinque goleador");
        coin.setAlias(new String[]{"soldi", "soldo", "monete", "capitale", "denaro"});
        AdvObject snack = new AdvObject(5,"snack",true,"Uno snack al cioccolato");
        snack.setAlias(new String[]{"merendina", "cibo", "merenda", "dolce", "dolcetto"});
        AdvObject receptionSheet = new AdvObject(6, "foglio", "'Sono al bar, torno tra cinque minuti'", true);
        receptionSheet.setAlias(new String[]{"carta", "scartoffie", "messaggio"});
        reception.getObjects().add(receptionSheet);
        AdvObject boardsInfoPoint = new AdvObject(7, "bacheca", "Nulla di interessante", true);
        boardsInfoPoint.setAlias(new String[]{"diario"});
        infoPoint.getObjects().add(boardsInfoPoint);
        AdvObject boardsWaitingRoom = new AdvObject(8, "bacheca","Indovina indovinello \n" +
                "Come fermo 'sto fardello? \n" +
                "Se la segreteria vuoi trovare \n" +
                "Ed il voto verbalizzare \n" +
                "Non ti resta che pagare \n" +
                "Un bel caffè da assaporare");
        boardsWaitingRoom.setAlias(new String[]{"diario"});
        waitingRoom.getObjects().add(boardsWaitingRoom);
        AdvObject buttonFirstElev = new AdvObject(9, "bottone", "Un piccolo tasto", true, false);
        buttonFirstElev.setAlias(new String[]{"tasto"});
        firstElevator.getObjects().add(buttonFirstElev);
        AdvObject buttonSecondElev = new AdvObject(10, "bottone", "Un piccolo tasto", true, false);
        buttonSecondElev.setAlias(new String[]{"tasto"});
        secondElevator.getObjects().add(buttonSecondElev);
        AdvObject secretarySheet = new AdvObject(11, "foglio", "Siamo al bar. Ci stiamo riposando." +
                "La mola di lavoro è troppa. Chiamare il numero 3774480028");
        secretarySheet.setAlias(new String[]{"carta", "scartoffie", "messaggio"});
        secretary.getObjects().add(secretarySheet);


        // inventario
        getInventory().add(prova);
        getInventory().add(coin);
        getInventory().add(snack);
        /*
        AdvObjectContainer wardrobe = new AdvObjectContainer(2, "armadio", "Un semplice armadio.");
        wardrobe.setAlias(new String[]{"guardaroba", "vestiario"});
        wardrobe.setOpenable(true);
        wardrobe.setPickupable(false);
        wardrobe.setOpen(false);
        yourRoom.getObjects().add(wardrobe);
        AdvObject toy = new AdvObject(3, "giocattolo", "Il gioco che ti ha regalato zia Lina.");
        toy.setAlias(new String[]{"gioco", "robot"});
        toy.setPushable(true);
        toy.setPush(false);
        wardrobe.add(toy);
         */
        // definizione della stanza corrente
        setCurrentRoom(laboratory);

    }

    // questo metodo in base alla stanza in cui mi trovo deve interpretare un comando 
    @Override
    public void nextMove(ParserOutput p, PrintStream out) {
        if (p.getCommand() == null) {
            out.println("Non ho capito cosa devo fare! Prova con un altro comando.");
        } else {
            //move
            boolean noroom = false;
            boolean move = false;
            if (p.getCommand().getType() == CommandType.NORD) {
                if (getCurrentRoom().getNorth() != null) {
                    setCurrentRoom(getCurrentRoom().getNorth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.SUD) {
                if (getCurrentRoom().getSouth() != null) {
                    setCurrentRoom(getCurrentRoom().getSouth());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.EST) {
                if (getCurrentRoom().getEast() != null) {
                    setCurrentRoom(getCurrentRoom().getEast());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.OVEST) {
                if (getCurrentRoom().getWest() != null) {
                    setCurrentRoom(getCurrentRoom().getWest());
                    move = true;
                } else {
                    noroom = true;
                }
            } else if (p.getCommand().getType() == CommandType.SALI) { // controlla se il comando è di tipo GO_UP
                if (getCurrentRoom().getUp() != null) { // controlla se su si può andare
                    setCurrentRoom(getCurrentRoom().getUp()); // se su si può andare setto la nuova stanza corrente 
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che su non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.SCENDI) { // controlla se il comando è di tipo GO_UP
                if (getCurrentRoom().getDown() != null) { // controlla se su si può andare
                    setCurrentRoom(getCurrentRoom().getDown()); // se su si può andare setto la nuova stanza corrente 
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che su non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.INVENTARIO) { // se il comando è di tipo INVENTORY
                out.println("Nel tuo inventario ci sono:");
                for (AdvObject o : getInventory()) { // ciclo sugli elementi dell'inventario
                    out.println(o.getName() + ": " + o.getDescription()); // e stampo nome + descrizione
                }
            } else if (p.getCommand().getType() == CommandType.OSSERVA) { // se il comando è di tipo LOOK_AT
                if (getCurrentRoom().getLook() != null) { // se la stanza corrente ha l'attributo look
                    out.println(getCurrentRoom().getLook()); // stampo cio' che c'e' nell'attributo look
                    if (!getCurrentRoom().getObjects().isEmpty())
                        out.println("Sono presenti inoltre i seguenti oggetti:");
                    for (AdvObject o : getCurrentRoom().getObjects()) {
                        out.println("- " + o.getName());
                    }
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }
            
            } else if (p.getCommand().getType() == CommandType.LASCIA) { // se il comando è di tipo PICK_UP
                if (p.getInvObject() != null) { // se ci sono oggetti
                    if (getInventory().contains(p.getInvObject())) { // se l'oggetto è presente nell'inventario
                        getInventory().remove(p.getInvObject()); // rimuovo l'oggetto dall'inventario
                        getCurrentRoom().getObjects().add(p.getInvObject()); // e lo aggiungo alla stanza
                        out.println("Fatto! Hai lasciato: " + p.getInvObject().getName());
                    } else {
                        out.println("Non puoi lasciare questo oggetto.");
                    }
                } else {
                    out.println("Non c'è niente da lasciare.");
                }
            } else if (p.getCommand().getType() == CommandType.APRI) { // se il comando è di tipo apri
                /*ATTENZIONE: quando un oggetto contenitore viene aperto, tutti gli oggetti contenuti
                * vengongo inseriti nella stanza o nell'inventario a seconda di dove si trova l'oggetto contenitore.
                * Questa soluzione NON va bene poiché quando un oggetto contenitore viene richiuso è complicato
                * non rendere più disponibili gli oggetti contenuti rimuovendoli dalla stanza o dall'invetario.
                * Trovare altra soluzione.
                 */
                if (p.getObject() == null && p.getInvObject() == null) { // l'oggetto puo stare o nella stanza oppure nell'inventario e se non c'è da nessuna parte
                    out.println("Non c'è niente da aprire qui."); // lo stampo
                } else {
                    if (p.getObject() != null) { // se il parser ha interpretato un oggetto nel comando
                        if (p.getObject().isOpenable() && p.getObject().isOpen() == false) { // se l'oggetto si puo aprire e non è stato aperto
                            if (p.getObject() instanceof AdvObjectContainer) { // se l'oggetto è un'istanza di AdvObjectContainer 
                                out.println("Fatto! Hai aperto: " + p.getObject().getName());
                                AdvObjectContainer c = (AdvObjectContainer) p.getObject(); // converto un object in un AdvObjectContainer facendo il casting
                                if (!c.getList().isEmpty()) { // se la lista oggetti non è vuota
                                    out.print(c.getName() + " contiene:"); // stampo tutti gli oggetti
                                    Iterator<AdvObject> it = c.getList().iterator(); // all'interno del contenitore
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        getCurrentRoom().getObjects().add(next); // li aggiungo alla stanza
                                        out.print(" " + next.getName());
                                        it.remove(); // li rimuovo dal contentitore
                                    }
                                    out.println();
                                }
                            } else {
                                out.println("Hai aperto: " + p.getObject().getName()); // nel caso in cui non è un contenitore 
                                p.getObject().setOpen(true); // setto lo stato dell'oggetto in aperto
                            }
                        } else {
                            out.println("Non puoi aprire questo oggetto."); // se non è un oggetto apribile
                        }
                    }
                    if (p.getInvObject() != null) { // fa la stessa con un oggetto nell'inventario
                        if (p.getInvObject().isOpenable() && p.getInvObject().isOpen() == false) {
                            if (p.getInvObject() instanceof AdvObjectContainer) {
                                AdvObjectContainer c = (AdvObjectContainer) p.getInvObject();
                                if (!c.getList().isEmpty()) {
                                    out.print(c.getName() + " contiene:");
                                    Iterator<AdvObject> it = c.getList().iterator();
                                    while (it.hasNext()) {
                                        AdvObject next = it.next();
                                        getInventory().add(next);
                                        out.print(" " + next.getName());
                                        it.remove();
                                    }
                                    out.println();
                                }
                            } else {
                                p.getInvObject().setOpen(true);
                            }
                            out.println("Hai aperto nel tuo inventario: " + p.getInvObject().getName());
                        } else {
                            out.println("Non puoi aprire questo oggetto.");
                        }
                    }
                }
            } else if (p.getCommand().getType() == CommandType.PREMI) { // controlla se il comando è di tipo PUSH
                //ricerca oggetti pushabili
                if (p.getObject() != null && p.getObject().isPushable()) { // se il parser ha interpretato il comando di tipo oggetto e se l'oggetto è premibile
                    out.println("Fatto! Hai premuto: " + p.getObject().getName());
                    out.println();
                    if (p.getObject().getId() == 1 && p.getObject().isPushable()) { // istruzione per le volte dispari
                        p.getObject().setPushable(false);
                        p.getObject().setPush(true);
                        setCurrentRoom(getRooms().get(2));
                        move = true; // booleano che serve a settare il fatto che mi sono mosso
                        // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                        out.println("...");
                        out.println("...");
                        out.println();
                        out.println(getCurrentRoom().getDescription());
                    }
                } else if (p.getObject() != null && p.getObject().isPush()) {
                    out.println("Fatto! Hai premuto: " + p.getObject().getName());
                    out.println();
                    if (p.getObject().getId() == 1 && p.getObject().isPush()) { // istruzione per le volte pari
                        p.getObject().setPush(false);
                        p.getObject().setPushable(true);
                        setCurrentRoom(getRooms().get(0));
                        move = true; // booleano che serve a settare il fatto che mi sono mosso
                        // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                        out.println("...");
                        out.println("...");
                        out.println();
                        out.println(getCurrentRoom().getDescription());
                    }
                } else if (p.getObject() == null) {
                    out.println("Premere cosa? Sii più preciso."); // se non ci sono oggetti o non si puo premere niente stampa questo
                } else if (p.getInvObject() != null && p.getInvObject().isPushable()) { // se il parser ha interpretato il comando di tipo oggetto INVENTARIO e se l'oggetto è premibile
                    out.println("Fatto! Hai premuto: " + p.getInvObject().getName());
                    out.println();
                    if (p.getInvObject().getId() == 1 && p.getInvObject().isPushable()) { // istruzione per le volte dispari
                        p.getInvObject().setPushable(false);
                        p.getInvObject().setPush(true);
                        out.println("prova dispari");
                    }
                } else if (p.getInvObject() != null && p.getInvObject().isPush()) {
                    out.println("Fatto! Hai premuto: " + p.getInvObject().getName());
                    out.println();
                    if (p.getInvObject().getId() == 1 && p.getInvObject().isPush()) { // istruzione per le volte pari
                        p.getInvObject().setPush(false);
                        p.getInvObject().setPushable(true);
                        out.println("prova pari");
                    }
                } else if (p.getInvObject() == null) {
                    out.println("Premere cosa? Sii più preciso."); // se non ci sono oggetti o non si puo premere niente stampa questo
                }
            }

            if (noroom) { // se noroom = true
                out.println("Verso " + p.getCommand().getName().toString().toLowerCase() + " non puoi andare");
            } else if (move) { // se move = true
                // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                out.println("-----[" + getCurrentRoom().getuniverse() + "]-----");
                out.println(getCurrentRoom().getDescription());
            }

        }
    }

    private void end(PrintStream out) {
        out.println("Premi il pulsante del giocattolo e in seguito ad una forte esplosione la tua casa prende fuoco... tu e tuoi famigliari cercate invano di salvarvi e venite avvolti dalle fiamme... è stata una morte CALOROSA... addio!");
        System.exit(0);
    }
}
