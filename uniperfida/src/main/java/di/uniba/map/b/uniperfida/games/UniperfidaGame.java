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
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command inventory = new Command(CommandType.INVENTORY, "inventario");
        inventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(inventory);
        Command end = new Command(CommandType.END, "end");
        end.setAlias(new String[]{"end", "fine", "finire", "esci", "uscire", "muori", "morire", "ammazzati", "ucciditi", "suicidati", "exit"});
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK_AT, "osserva");
        look.setAlias(new String[]{"guarda", "guardare", "vedi", "vedere", "trova", "trovare", "cerca", "cercare", "fissa", "fissare"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PICK_UP, "raccogli");
        pickup.setAlias(new String[]{"prendi", "prendere", "raccogliere"});
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{"aprire", "schiudi", "schiudere", "spalanca", "spalancare"});
        getCommands().add(open);
        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi", "spingere", "attiva", "attivare", "premere"});
        getCommands().add(push);
        Command goUp = new Command(CommandType.GO_UP, "sali");
        goUp.setAlias(new String[]{"salire", "sali scale", "salire scale"});
        getCommands().add(goUp);
        Command goDown = new Command(CommandType.GO_DOWN, "scendi");
        goDown.setAlias(new String[]{"scendere", "scendi scale", "scendere scale"});
        getCommands().add(goDown);
        // definizione delle stanze
        Room laboratory = new Room(1, "Laboratorio del professor Silvestre", "Ti trovi nel laboratorio del professor Silvestre. Il professore ed il suo assistente stanno attendendo una tua mossa.");
        laboratory.setLook("Hai davanti la macchina, la porta è aperta;");
        Room machine = new Room(2, "Navicella Space-Voyager", "Ti trovi nella navicella Space-Voyager. Il professore ed il suo assistente fremono per la tua imminente partenza.");
        machine.setLook("C'è una luce soffusa e un enorme tasto rosso");
        Room courtyard = new Room(3, "Cortile", "Ti trovi nel cortile. C'è uno strano edificio pieno zeppo di finestre. C'è un cartello con incisa una scritta.");
        courtyard.setLook("Il cartello recita: 'Università di Bari Aldo Moro, Facoltà di Informatica'. Fortunatamente qui si parla italiano. Di fronte a te c'è una porta."); //Senti qualcosa nelle tasche vibrare
        Room hall = new Room(4, "Atrio principale", "Ti trovi nell'atrio principale. C'è molto eco.");
        hall.setLook("C'è una macchinetta del caffè e una bacheca.");
        Room reception = new Room(5, "Ufficio del collaboratore", "Ti trovi nell'ufficio del collaboratore. Sembra essere vuoto.");
        reception.setLook("C'è una scrivania con un computer ed un foglio.");
        Room firstBathroom = new Room(6, "Bagno del piano terra", "Ti trovi nei bagni del piano terra. Non noti nulla di strano.");
        firstBathroom.setLook("Un semplice bagno universitario, senza carta igienica e sapone..." + "ovviamente.");
        Room corridor = new Room(7, "Corridoio del piano terra", "Sei nel corridoio. C'è un cartellone in onore di 'Accendino', un ragazzo fantastico.");
        corridor.setLook("Non c'è niente di particolare.");
        Room infoPoint = new Room(8, "Zona delle bacheche", "Ti trovi nella zona delle bacheche.");
        infoPoint.setLook("Nelle bacheche non c'è nulla infisso. Come sempre d'altronde.");
        Room firstElevator = new Room(9, "Ascensore", "Ti trovi nell'ascensore. E' molto grande.");
        firstElevator.setLook("C'è un bottone.");
        Room stairs = new Room(10, "Scale piano terra", "Ti trovi nel pianerottolo delle scale del piano terra.");
        stairs.setLook("C'è un'enorme scalinata che sale.");
        Room waitingRoom = new Room(11, "Sala d'attesa aule", "Ti trovi nella sala d'attesa delle aule.");
        waitingRoom.setLook("Ci sono un paio di sedie. Sopra le sedie c'è una bacheca, l'ennesima bacheca.");
        Room roomA = new Room(12, "Aula A", "Ti trovi nell'aula A. Non vedi nulla di interessante.");
        roomA.setLook("Ci sono file di banchi. L'aula è vuota.");
        Room roomB = new Room(13, "Aula B", "Ti trovi nell'aula B. Non vedi nulla di interessante.");
        roomB.setLook("Ci sono file di banchi. L'aula è vuota.");
        Room upStairs = new Room(14, "Scale primo piano", "Ti trovi nel pianerottolo delle scale del primo piano.");
        upStairs.setLook("C'è un'enorme scalinata che scende.");
        Room upStairsHall = new Room(15, "Atrio del primo piano", "Ti trovi nell'atrio del primo piano. C'è molto eco.");
        upStairsHall.setLook("C'è un enorme corridoio ed una mezza dozzina di uffici.");
        Room secondElevator = new Room(16, "Ascensore", "Ti trovi nell'ascensore. E' molto grande.");
        secondElevator.setLook("C'è un bottone.");
        Room secondBathroom = new Room(17, "Bagno del primo piano", "Ti trovi nel bagno del primo piano. Non noti nulla di strano.");
        secondBathroom.setLook("Un semplice bagno universitario, senza carta igienica e sapone..." + "ovviamente.");
        Room firstSector = new Room(18, "Primo settore del corridoio", "Ti trovi nel primo settore del corridoio. Sembra non finisca mai.");
        firstSector.setLook("Ai lati ci sono due uffici.");
        Room waitingRoomRossetto = new Room(19, "Sala d'attesa della prof.ssa Rossetto", "Ti trovi nella sala d'attesa della prof.ssa *****");
        waitingRoomRossetto.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room waitingRoomImpavido = new Room(20, "Sala d'attesa del prof. Impavido", "Ti trovi nella sala d'attesa del prof. *****");
        waitingRoomImpavido.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room secondSector = new Room(21, "Secondo settore del corridoio", "Ti trovi nel secondo settore del corridoio. Intravedi la fine.");
        secondSector.setLook("Ai lati ci sono due uffici.");
        Room waitingRoomGatto = new Room(22, "Sala d'attesa del prof. Gatto", "Ti trovi nella sala d'attesa del prof. *****");
        waitingRoomGatto.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room waitingRoomCinquanta = new Room(23, "Sala d'attesa del prof. Cinquanta", "Ti trovi nella sala d'attesa del prof. *****");
        waitingRoomCinquanta.setLook("Ci sono delle sedie e una porta con uno schermo.");
        Room thirdSector = new Room(24, "Terzo settore del corridoio", "Ti trovi nel terzo settore del corridoio. Di fronte a te c'è un muro.");
        thirdSector.setLook("Ai lati c'è l'ennesimo ufficio e l'ennesimo bagno. Avranno problemi d'incontinenza.");
        Room thirdBathroom = new Room(25, "Bagno dei professori", "Ti trovi nel bagno dei professori.");
        thirdBathroom.setLook("Questo bagno è molto meglio degli altri..." + "Sapone, carta igienica, persino un asciugamani ad aria!");
        Room waitingRoomBasilico = new Room(26, "Sala d'attesa del prof. Basilico", "Ti trovi nella sala d'attesa del prof. Basilico.");
        waitingRoomBasilico.setLook("C'è una porta, niente indovinelli questa volta.");
        Room secretary = new Room(27, "Segreteria", "Ti trovi nella segreteria.", false);
        secretary.setLook("C'è uno sportello con un foglio.");
        // definizione della mappa (collegamenti tra le stanze)
        courtyard.setNorth(hall);
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
        AdvObject battery = new AdvObject(1, "batteria", "Un pacco di batterie, chissà se sono cariche.");
        battery.setAlias(new String[]{"batterie", "pile", "pila"});
        bathroom.getObjects().add(battery);
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
        // definizione della stanza corrente
        setCurrentRoom(laboratory);
    }

    // questo metodo in base alla stanza in cui mi trovo deve interpretare un comando 
    @Override
    public void nextMove(ParserOutput p, PrintStream out) { // riceve un input dal parser e un PrintStream dove scrivere
        if (p.getCommand() == null) { // controlla se il parser ha interpetato il comando
            out.println("Non ho capito cosa devo fare! Prova con un altro comando."); // se non l'ha fatto stampa che non ha capito
        } else {
            // comandi di movimento
            boolean noroom = false;
            boolean move = false;
            if (p.getCommand().getType() == CommandType.NORD) { // controlla se il comando è di tipo NORD
                if (getCurrentRoom().getNorth() != null) { // controlla se a nord si può andare
                    setCurrentRoom(getCurrentRoom().getNorth()); // se a nord si può andare setto la nuova stanza corrente 
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che a nord non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.SOUTH) { // controlla se il comando è di tipo SOUTH
                if (getCurrentRoom().getSouth() != null) { // controlla se a sud si può andare
                    setCurrentRoom(getCurrentRoom().getSouth()); // se a sud si può andare setto la nuova stanza corrente 
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che a sud non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.EAST) { // controlla se il comando è di tipo EAST
                if (getCurrentRoom().getEast() != null) { // controlla se a est si può andare
                    setCurrentRoom(getCurrentRoom().getEast()); // se a est si può andare setto la nuova stanza corrente
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che a est non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.WEST) { // controlla se il comando è di tipo WEST
                if (getCurrentRoom().getWest() != null) { // controlla se a ovest si può andare
                    setCurrentRoom(getCurrentRoom().getWest()); // se a ovest si può andare setto la nuova stanza corrente 
                    move = true; // booleano che serve a settare il fatto che mi sono mosso
                } else {
                    noroom = true; // booleano che serve a memorizzare che a ovest non c'è nulla
                }
            } else if (p.getCommand().getType() == CommandType.INVENTORY) { // se il comando è di tipo INVENTORY
                out.println("Nel tuo inventario ci sono:");
                for (AdvObject o : getInventory()) { // ciclo sugli elementi dell'inventario
                    out.println(o.getName() + ": " + o.getDescription()); // e stampo nome + descrizione
                }
            } else if (p.getCommand().getType() == CommandType.LOOK_AT) { // se il comando è di tipo LOOK_AT
                if (getCurrentRoom().getLook() != null) { // se la stanza corrente ha l'attributo look
                    out.println(getCurrentRoom().getLook()); // stampo cio' che c'e' nell'attributo look
                } else {
                    out.println("Non c'è niente di interessante qui.");
                }
            } else if (p.getCommand().getType() == CommandType.PICK_UP) { // se il comando è di tipo PICK_UP
                if (p.getObject() != null) { // se ci sono oggetti
                    if (p.getObject().isPickupable()) { // se l'oggetto si puo raccogliere
                        getInventory().add(p.getObject()); // aggiungo l'oggetto all'inventario
                        getCurrentRoom().getObjects().remove(p.getObject()); // e lo rimuovo dalla stanza
                        out.println("Hai raccolto: " + p.getObject().getDescription());
                    } else {
                        out.println("Non puoi raccogliere questo oggetto.");
                    }
                } else {
                    out.println("Non c'è niente da raccogliere qui.");
                }
            } else if (p.getCommand().getType() == CommandType.OPEN) { // se il comando è di tipo apri 
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
                                out.println("Hai aperto: " + p.getObject().getName());
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
            } else if (p.getCommand().getType() == CommandType.PUSH) { // controlla se il comando è di tipo PUSH
                //ricerca oggetti pushabili
                if (p.getObject() != null && p.getObject().isPushable()) { // se il parser ha interpretato il comando di tipo oggetto e se l'oggetto è premibile
                    out.println("Hai premuto: " + p.getObject().getName());
                    if (p.getObject().getId() == 3) { // se premi il giocattolo numero 3 hai finito il gioco
                        end(out);
                    }
                } else if (p.getInvObject() != null && p.getInvObject().isPushable()) { // stessa cosa ma con oggetto nell'inventario
                    out.println("Hai premuto: " + p.getInvObject().getName());
                    if (p.getInvObject().getId() == 3) {
                        end(out);
                    }
                } else {
                    out.println("Non ci sono oggetti che puoi premere qui."); // se non ci sono oggetti o non si puo premere niente stampa questo
                }
            }
            if (noroom) { // se noroom = true
                out.println("Da quella parte non si può andare c'è un muro! Non hai ancora acquisito i poteri per oltrepassare i muri...");
            } else if (move) { // se move = true
                out.println(getCurrentRoom().getName());
                out.println("================================================");
                out.println(getCurrentRoom().getDescription());
            }
        }
    }

    private void end(PrintStream out) {
        out.println("Premi il pulsante del giocattolo e in seguito ad una forte esplosione la tua casa prende fuoco... tu e tuoi famigliari cercate invano di salvarvi e venite avvolti dalle fiamme... è stata una morte CALOROSA... addio!");
        System.exit(0); 
    }
}
