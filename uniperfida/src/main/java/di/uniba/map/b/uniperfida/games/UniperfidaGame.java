/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.games;

import di.uniba.map.b.uniperfida.Engine;
import di.uniba.map.b.uniperfida.GameDescription;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import di.uniba.map.b.uniperfida.type.*;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

import static di.uniba.map.b.uniperfida.print.Printings.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    public void useFileRoomsDescription() {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./resources/roomDescription.txt"));
            String a;
            int i = 0;
            while ((a = inputStream.readLine()) != null) {
                this.getRooms().get(i).setDescription(a);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void useFileRoomsLook() {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./resources/roomLook.txt"));
            String a;
            int i = 0;
            while ((a = inputStream.readLine()) != null) {
                this.getRooms().get(i).setLook(a);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void useFileRoomsName() {
        try {
            BufferedReader inputStream = new BufferedReader(new FileReader("./resources/roomName.txt"));
            String a;
            int i = 0;
            while ((a = inputStream.readLine()) != null) {
                this.getRooms().get(i).setName(a);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() { // questo è il metodo chiamato da Engine, qui dentro dobbiamo inizializzare tutta la struttura del gioco
        // definizione dei comandi compresi anche i sinonimi e poi li aggiungo alla lista presente in GameDescription

        startTime = System.currentTimeMillis();

        Command nord = new Command(CommandType.NORTH, "nord");
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

        Command inventory = new Command(CommandType.INVENTORY, "borsellino");
        inventory.setAlias(new String[]{"portafogli", "portamonete", "b", "B"});
        getCommands().add(inventory);

        Command end = new Command(CommandType.END, "termina");
        end.setAlias(new String[]{"fine", "finire", "terminare", "muori", "morire"});
        getCommands().add(end);

        Command enter = new Command(CommandType.ENTER, "entra");
        enter.setAlias(new String[]{"entrare", "esci", "uscire"});
        getCommands().add(enter);

        Command look = new Command(CommandType.LOOK, "osserva");
        look.setAlias(new String[]{"guarda", "guardare", "vedi", "vedere", "trova", "trovare", "cerca", "cercare", "fissa", "fissare"});
        getCommands().add(look);

        Command pickUp = new Command(CommandType.PICK_UP, "raccogli");
        pickUp.setAlias(new String[]{"prendi", "prendere", "raccogliere"});
        getCommands().add(pickUp);

        Command leave = new Command(CommandType.LEAVE, "lascia");
        leave.setAlias(new String[]{"lasciare", "buttare", "gettare", "butta", "getta"});
        getCommands().add(leave);

        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{"aprire", "schiudi", "schiudere", "spalanca", "spalancare"});
        getCommands().add(open);

        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi", "spingere", "attiva", "attivare", "premere", "schiaccia", "schiacciare"});
        getCommands().add(push);

        Command goUp = new Command(CommandType.GO_UP, "sopra");
        goUp.setAlias(new String[]{"salire", "sali", "sali", "su", "sù"});
        getCommands().add(goUp);

        Command goDown = new Command(CommandType.GO_DOWN, "sotto");
        goDown.setAlias(new String[]{"scendere", "scendi", "scendere", "giu", "giù"});
        getCommands().add(goDown);

        Command help = new Command(CommandType.HELP, "help");
        help.setAlias(new String[]{"aiuto", "h"});
        getCommands().add(help);

        Command use = new Command(CommandType.USE, "usa");
        use.setAlias(new String[]{"usare", "utilizza", "utilizzare"});
        getCommands().add(use);

        Command read = new Command(CommandType.READ, "leggi");
        read.setAlias(new String[]{"leggere", "analizza", "analizzare"});
        getCommands().add(read);

        Command talk = new Command(CommandType.TALK, "parla");
        talk.setAlias(new String[]{"discuti", "parlare", "discutere"});
        getCommands().add(talk);

        Command mappa = new Command(CommandType.MAPPA, "mappa");
        mappa.setAlias(new String[]{"map", "cartina", "m"});
        getCommands().add(mappa);

        // definizione delle stanze
        Room laboratory = new Room(1);
        laboratory.setuniverse("T-237");

        Room firstMachine = new Room(2);
        firstMachine.setuniverse("T-237");

        Room courtyard = new Room(3);
        courtyard.setuniverse("J-371");
        courtyard.setTwoObjects(true);

        Room hall = new Room(4);
        hall.setCount(0);
        hall.setuniverse("J-371");
        hall.setTwoObjects(true);

        Room reception = new Room(5);
        reception.setuniverse("J-371");

        Room firstBathroom = new Room(6);
        firstBathroom.setuniverse("J-371");

        Room corridor = new Room(7);
        corridor.setuniverse("J-371");

        Room infoPoint = new Room(8);
        infoPoint.setuniverse("J-371");

        Room firstElevator = new Room(9);
        firstElevator.setuniverse("J-371");

        Room stairs = new Room(10);
        stairs.setuniverse("J-371");

        Room waitingRoom = new Room(11);
        waitingRoom.setuniverse("J-371");

        Room roomA = new Room(12);
        roomA.setuniverse("J-371");

        Room roomB = new Room(13);
        roomB.setuniverse("J-371");

        Room upStairs = new Room(14);
        upStairs.setuniverse("J-371");

        Room upStairsHall = new Room(15);
        upStairsHall.setuniverse("J-371");

        Room secondElevator = new Room(16);
        secondElevator.setuniverse("J-371");

        Room secondBathroom = new Room(17);
        secondBathroom.setuniverse("J-371");

        Room firstSector = new Room(18);
        firstSector.setuniverse("J-371");

        Room waitingRoomRossetto = new Room(19);
        waitingRoomRossetto.setuniverse("J-371");

        Room waitingRoomImpavido = new Room(20);
        waitingRoomImpavido.setuniverse("J-371");

        Room secondSector = new Room(21);
        secondSector.setuniverse("J-371");

        Room waitingRoomGatto = new Room(22);
        waitingRoomGatto.setuniverse("J-371");

        Room waitingRoomCinquanta = new Room(23);
        waitingRoomCinquanta.setuniverse("J-371");

        Room thirdSector = new Room(24);
        thirdSector.setuniverse("J-371");

        Room thirdBathroom = new Room(25);
        thirdBathroom.setuniverse("J-371");

        Room waitingRoomBasilico = new Room(26);
        waitingRoomBasilico.setuniverse("J-371");
        waitingRoomBasilico.setCount(0);
        waitingRoomBasilico.setVisible(false);

        Room secretary = new Room(27);
        secretary.setuniverse("J-371");
        secretary.setVisible(false);
        secretary.setTwoObjects(true);

        Room secondMachine = new Room(28);
        secondMachine.setuniverse("J-371");
        secondMachine.setPrint(false);

        Room basilicoOffice = new Room(29);
        basilicoOffice.setuniverse("J-371");
        basilicoOffice.setVisible(false);

        // definizione della mappa (collegamenti tra le stanze)
        laboratory.setNorth(firstMachine);
        firstMachine.setSouth(laboratory);
        secondMachine.setNorth(courtyard);
        courtyard.setNorth(hall);
        courtyard.setSouth(secondMachine);
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
        waitingRoomBasilico.setWest(basilicoOffice);
        basilicoOffice.setEast(waitingRoomBasilico);
        // aggiunte stanze alla mappa
        getRooms().add(laboratory);
        getRooms().add(firstMachine);
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
        getRooms().add(secondMachine);
        getRooms().add(basilicoOffice);

        // definizione degli oggetti 
        AdvObject buttonMachine = new AdvObject(1, "bottone", "Un enorme tasto rosso");
        buttonMachine.setAlias(new String[]{"tasto", "pulsante"});
        buttonMachine.setPushable(true);
        firstMachine.getObjects().add(buttonMachine);
        secondMachine.getObjects().add(buttonMachine);

        AdvObject boardsHall = new AdvObject(2, "bacheca", "Pubblicato bando per corso d'hacking.");
        boardsHall.setAlias(new String[]{"diario", "bacheche"});
        boardsHall.setReadable(true);
        hall.getObjects().add(boardsHall);

        AdvObject coin = new AdvObject(4, "moneta", "Una moneta da 50 cents, perfetta per comprare cinque goleador");
        coin.setPickupable(true);
        coin.setDroppable(false);
        coin.setAlias(new String[]{"soldi", "soldo", "monete", "capitale", "denaro"});
        thirdBathroom.getObjects().add(coin);

        AdvObject coinTwo = new AdvObject(16, "moneta", "Una moneta da 50 cents, perfetta per comprare cinque goleador");
        coinTwo.setPickupable(true);
        coinTwo.setDroppable(false);
        coinTwo.setAlias(new String[]{"soldi", "soldo", "monete", "capitale", "denaro"});
        roomB.getObjects().add(coinTwo);

        AdvObject coinFour = new AdvObject(18, "moneta", "Una moneta da 50 cents, perfetta per comprare cinque goleador");
        coinFour.setPickupable(true);
        coinFour.setDroppable(false);
        coinFour.setAlias(new String[]{"soldi", "soldo", "monete", "capitale", "denaro"});
        reception.getObjects().add(coinFour);

        AdvObject boardsInfoPoint = new AdvObject(7, "bacheca", "Anche oggi non ci sono novità.");
        boardsInfoPoint.setAlias(new String[]{"diario", "bacheche"});
        boardsInfoPoint.setReadable(true);
        infoPoint.getObjects().add(boardsInfoPoint);

        AdvObject boardsWaitingRoom = new AdvObject(8, "bacheca", "Indovina indovinello \n"
                + "Come fermo 'sto fardello? \n"
                + "Se la segreteria vuoi trovare \n"
                + "Ed il voto verbalizzare \n"
                + "Non ti resta che pagare \n"
                + "Un bel caffè lungo da assaporare");
        boardsWaitingRoom.setAlias(new String[]{"diario", "bacheche"});
        boardsWaitingRoom.setReadable(true);
        waitingRoom.getObjects().add(boardsWaitingRoom);

        AdvObject buttonElev = new AdvObject(9, "bottone", "Un piccolo tasto");
        buttonElev.setAlias(new String[]{"tasto", "pulsante"});
        firstElevator.getObjects().add(buttonElev);
        secondElevator.getObjects().add(buttonElev);

        AdvObject secretarySheet = new AdvObject(10, "foglio", "Siamo al bar. Ci stiamo riposando."
                + " La mole di lavoro è troppa. Chiamare il numero 3774480028");
        secretarySheet.setAlias(new String[]{"carta", "scartoffie", "messaggio"});
        secretarySheet.setReadable(true);
        secretary.getObjects().add(secretarySheet);

        AdvObjectContainer coffeeDispenser = new AdvObjectContainer(11, "macchinetta", "Una semplice macchinetta del caffè");
        coffeeDispenser.setAlias(new String[]{"dispenser", "macchinetta"});
        coffeeDispenser.setUseable(true);
        hall.getObjects().add(coffeeDispenser);

        AdvObject coffee = new AdvObject(12, "caffè", "Un caffè caldo"); //costruttore da aggiungere
        coffee.setAlias(new String[]{"marocchino"});
        coffeeDispenser.add(coffee);

        AdvObject chocolateCoffee = new AdvObject(13, "caffè con cioccolato", "Un caffè al cioccolato, caldo");
        chocolateCoffee.setAlias(new String[]{"cioccolata"});
        coffeeDispenser.add(chocolateCoffee);

        AdvObject macchiatoCoffee = new AdvObject(14, "caffè macchiato", "Un caffè macchiato caldo");
        macchiatoCoffee.setAlias(new String[]{"macchiato"});
        coffeeDispenser.add(macchiatoCoffee);

        AdvObject longCoffee = new AdvObject(15, "caffè lungo", "Un caffè lungo caldo");
        longCoffee.setAlias(new String[]{"lungo"});
        coffeeDispenser.add(longCoffee);

        AdvObject secretaryPhone = new AdvObject(16, "telefono", "Un semplice telefono da usare");
        secretaryPhone.setAlias(new String[]{"cellulare", "telefonino", "smartphone"});
        secretaryPhone.setUseable(true);
        secretaryPhone.setAvailable(false);
        secretary.getObjects().add(secretaryPhone);

        AdvObject firstTablet = new AdvObject(17, "tablet", "Un semplice tablet da usare");
        firstTablet.setAlias(new String[]{"telefono"});
        firstTablet.setUseable(true);
        firstTablet.setUsed(false);
        waitingRoomRossetto.getObjects().add(firstTablet);

        AdvObject secondTablet = new AdvObject(18, "tablet", "Un semplice tablet da usare");
        secondTablet.setAlias(new String[]{"telefono"});
        secondTablet.setUseable(true);
        secondTablet.setUsed(false);
        waitingRoomImpavido.getObjects().add(secondTablet);

        AdvObject thirdTablet = new AdvObject(19, "tablet", "Un semplice tablet da usare");
        thirdTablet.setAlias(new String[]{"telefono"});
        thirdTablet.setUseable(true);
        thirdTablet.setUsed(false);
        waitingRoomGatto.getObjects().add(thirdTablet);

        AdvObject fourthTablet = new AdvObject(20, "tablet", "Un semplice tablet da usare");
        fourthTablet.setAlias(new String[]{"telefono"});
        fourthTablet.setUseable(true);
        fourthTablet.setUsed(false);
        waitingRoomCinquanta.getObjects().add(fourthTablet);

        AdvObject door = new AdvObject(21, "porta", "Porta di ingresso");
        door.setAlias(new String[]{"portone"});
        door.setOpenable(true);
        door.setOpen(false);
        waitingRoomBasilico.getObjects().add(door);

        AdvObject sign = new AdvObject(22, "cartello", "'Università di Bari Aldo Moro, Facoltà di Informatica'.");
        sign.setAlias(new String[]{"cartellone"});
        sign.setReadable(true);
        courtyard.getObjects().add(sign);

        AdvObject coinThree = new AdvObject(17, "moneta", "Una moneta da 50 cents, perfetta per comprare cinque goleador");
        coinThree.setPickupable(true);
        coinThree.setDroppable(false);
        coinThree.setAlias(new String[]{"soldi", "soldo", "monete", "capitale", "denaro"});
        courtyard.getObjects().add(coinThree);

        Person collaborator = new Person(1, "collaboratore", "Un uomo che lavora in università");
        collaborator.setAlias(new String[]{"bidello", "custode", "assistente", "uomo"});
        collaborator.setTalkable(true);
        reception.getPeople().add(collaborator);

        Person basilico = new Person(2, "Basilico", "Il professor Basilico");
        basilico.setAlias(new String[]{"prof", "professor", "professore", "prof.", "basilico"});
        basilico.setTalkable(true);
        basilicoOffice.getPeople().add(basilico);

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
            if (null != p.getCommand().getType()) {
                switch (p.getCommand().getType()) {
                    case NORTH:
                        if (getCurrentRoom().getNorth() != null && getCurrentRoom() != getRooms().get(27)) {
                            setCurrentRoom(getCurrentRoom().getNorth());
                            move = true;
                        } else if (getCurrentRoom() == getRooms().get(27) && !getRooms().get(27).isPrint()) {
                            setCurrentRoom(getCurrentRoom().getNorth());
                            move = true;
                            printMessage();
                            getRooms().get(27).setPrint(true);
                            out.println();
                        } else if (getCurrentRoom() == getRooms().get(27) && getRooms().get(27).isPrint()) {
                            setCurrentRoom(getCurrentRoom().getNorth());
                            move = true;
                        } else {
                            noroom = true;
                        }
                        break;
                    case SOUTH:
                        if (getCurrentRoom().getSouth() != null) {
                            setCurrentRoom(getCurrentRoom().getSouth());
                            move = true;
                        } else {
                            noroom = true;
                        }
                        break;
                    case EAST:
                        if (getCurrentRoom().getEast() != null) {
                            setCurrentRoom(getCurrentRoom().getEast());
                            move = true;
                        } else {
                            noroom = true;
                        }
                        break;
                    case WEST:
                        if (getCurrentRoom().getWest() != null && getCurrentRoom() != getRooms().get(23) && getCurrentRoom() != getRooms().get(25)) {
                            setCurrentRoom(getCurrentRoom().getWest());
                            move = true;
                        } else if (getCurrentRoom() == getRooms().get(23) && getRooms().get(25).isVisible()) {
                            setCurrentRoom(getCurrentRoom().getWest());
                            move = true;
                        } else if (getCurrentRoom() == getRooms().get(23) && !getRooms().get(25).isVisible()) {
                            out.println("Per entrare dal prof. Basilico devi prima risolvere gli indovinelli.");
                            out.println();
                            move = true;
                        } else if (getCurrentRoom() == getRooms().get(25) && !getRooms().get(28).isVisible()) {
                            out.println("Devi aprire la porta per entrare!");
                            out.println();
                            move = true;
                        } else if (getCurrentRoom() == getRooms().get(25) && getRooms().get(28).isVisible()) {
                            setCurrentRoom(getCurrentRoom().getWest());
                            move = true;
                        } else {
                            noroom = true;
                        }
                        break;
                    case ENTER:
                        out.println("Questo tipo di comando non è supportato, le direzioni disponibili sono:"
                                + "\nNORD"
                                + "\nSUD"
                                + "\nEST"
                                + "\nOVEST"
                                + "\nSALI"
                                + "\nSCENDI");
                        out.println();
                        move = true;

                        break;
                    // case
                    case GO_UP:
                        // controlla se il comando è di tipo GO_UP
                        if (getCurrentRoom().getUp() != null) { // controlla se su si può andare
                            setCurrentRoom(getCurrentRoom().getUp()); // se su si può andare setto la nuova stanza corrente
                            move = true; // booleano che serve a settare il fatto che mi sono mosso
                        } else {
                            noroom = true; // booleano che serve a memorizzare che su non c'è nulla
                        }
                        break;
                    case GO_DOWN:
                        // controlla se il comando è di tipo GO_UP
                        if (getCurrentRoom().getDown() != null && getCurrentRoom() != getRooms().get(11)) { // controlla se su si può andare
                            setCurrentRoom(getCurrentRoom().getDown()); // se su si può andare setto la nuova stanza corrente
                            move = true; // booleano che serve a settare il fatto che mi sono mosso
                        } else if (getCurrentRoom() == getRooms().get(11) && getRooms().get(26).isVisible()) {
                            setCurrentRoom(getCurrentRoom().getDown()); // se su si può andare setto la nuova stanza corrente
                            move = true; // booleano che serve a settare il fatto che mi sono mosso
                        } else {
                            noroom = true; // booleano che serve a memorizzare che su non c'è nulla
                        }
                        break;
                    case INVENTORY:
                        // se il comando è di tipo INVENTORY
                        int i = 0;
                        if (!getInventory().isEmpty()) {
                            for (AdvObject o : getInventory()) { // ciclo sugli elementi dell'inventario
                                i++;
                            }
                            if (i == 1) {
                                out.println("Nel tuo borsellino c'è una moneta");
                                out.println();
                                move = true;
                            } else {
                                out.println("Nel tuo borsellino ci sono " + i + " monete");
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Sei un poveraccio, non hai monete!");
                            out.println();
                            move = true;
                        }
                        break;
                    case LOOK:
                        // se il comando è di tipo LOOK_AT
                        if (p.getObject() == null) { // se la stanza corrente ha l'attributo look
                            if (getCurrentRoom() == getRooms().get(2)) {
                                out.println(getCurrentRoom().getLook()); // stampo cio' che c'e' nell'attributo look
                                getCurrentRoom().setLook("Il cartello recita: 'Università di Bari Aldo Moro, Facoltà di Informatica'. Sei nel posto giusto al momento giusto, aiutiamo Edoardo lo svitato! Di fronte a te c'è una porta aperta, molto probabilmente è rotta.");
                            } else {
                                out.println(getCurrentRoom().getLook()); // stampo cio' che c'e' nell'attributo look
                            }
                            if (!getCurrentRoom().getObjects().isEmpty()) {
                                out.println("Sono presenti inoltre i seguenti oggetti:");
                            }
                            for (AdvObject o : getCurrentRoom().getObjects()) {
                                out.println("- " + o.getName());
                            }
                            out.println();
                            move = true;
                        } else if (p.getObject().getId() == 2 || p.getObject().getId() == 7 || p.getObject().getId() == 8) {
                            out.println("Nella bacheca c'è scritto:\n" + p.getObject().getDescription());
                            out.println();
                            move = true;
                        } else if (p.getObject().getId() == 10) {
                            out.println("Il foglio recita: " + p.getObject().getDescription());
                            out.println();
                            move = true;
                        } else if (p.getObject().getId() == 22) {
                            out.println("Il cartello recita:\n" + p.getObject().getDescription());
                            out.println();
                            move = true;
                        } else if (p.getObject().getId() == 1 || p.getObject().getId() == 3 || p.getObject().getId() == 4 || p.getObject().getId() == 5 || p.getObject().getId() == 9 || p.getObject().getId() == 11 || p.getObject().getId() == 16 || p.getObject().getId() == 17 || p.getObject().getId() == 18 || p.getObject().getId() == 19 || p.getObject().getId() == 20 || p.getObject().getId() == 21) {
                            out.println(p.getObject().getDescription());
                            out.println();
                            move = true;
                        }
                        break;
                    case READ:
                        if (p.getObject() != null) {
                            if (p.getObject().isReadable()) {
                                if (p.getObject().getId() == 2 || p.getObject().getId() == 7 || p.getObject().getId() == 8) {
                                    out.println("Nella bacheca c'è scritto:\n" + p.getObject().getDescription());
                                    out.println();
                                    move = true;
                                } else if (p.getObject().getId() == 22) {
                                    out.println("Il cartello recita:\n" + p.getObject().getDescription());
                                    out.println();
                                    move = true;
                                    getRooms().get(2).getObjects().get(1).setDescription("'Università di Bari Aldo Moro, Facoltà di Informatica'.");
                                } else {
                                    out.println("Il foglio recita: " + p.getObject().getDescription());
                                    out.println();
                                    move = true;
                                }
                            } else {
                                out.println("Non puoi leggere " + p.getObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Leggere cosa? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case USE:
                        boolean flag = true;
                        if (p.getObject() != null) {
                            if (p.getObject().isUseable()) {
                                int j = getRooms().get(25).getCount();
                                int a = getRooms().get(3).getCount();
                                if (p.getObject().getId() == 11) {
                                    if (!getInventory().isEmpty()) {
                                        if (p.getObject() instanceof AdvObjectContainer) {
                                            AdvObjectContainer c = (AdvObjectContainer) p.getObject();
                                            while (flag) {
                                                printCoffeeMenu();
                                                Scanner scanner2 = new Scanner(System.in);
                                                String chooseCoffee = scanner2.nextLine();
                                                out.println();
                                                switch (chooseCoffee) {
                                                    case "1":
                                                        getInventory().remove(getInventory().get(0));
                                                        out.println("Fatto! Hai preso un caffè.");
                                                        out.println("...");
                                                        out.println("Mmh, buono questo caffè");
                                                        out.println("...");
                                                        out.println();
                                                        getRooms().get(3).setCount(++a);
                                                        move = true;
                                                        flag = false;
                                                        break;
                                                    case "2":
                                                        getInventory().remove(getInventory().get(0));
                                                        out.println("Fatto! Hai preso un caffè lungo.");
                                                        out.println("...");
                                                        out.println("Mmh, buono questo caffè lungo");
                                                        out.println("...");
                                                        getRooms().get(26).setVisible(true);
                                                        getRooms().get(11).setLook("Ci sono file di banchi. C'è una nuvola di polvere. Intravedi delle scale.");
                                                        out.println("Ma cos'è stato questo rumore? Proveniva dalla aula A.");
                                                        out.println();
                                                        move = true;
                                                        flag = false;
                                                        break;
                                                    case "3":
                                                        getInventory().remove(getInventory().get(0));
                                                        out.println("Fatto! Hai preso un caffè macchiato.");
                                                        out.println("...");
                                                        out.println("Mmh, buono questo caffè macchiato");
                                                        out.println("...");
                                                        out.println();
                                                        getRooms().get(3).setCount(++a);
                                                        move = true;
                                                        flag = false;
                                                        break;
                                                    case "4":
                                                        getInventory().remove(getInventory().get(0));
                                                        out.println("Fatto! Hai preso un caffè al cioccolato.");
                                                        out.println("...");
                                                        out.println("Mmh, buono questo caffè al cioccolato");
                                                        out.println("...");
                                                        out.println();
                                                        getRooms().get(3).setCount(++a);
                                                        move = true;
                                                        flag = false;
                                                        break;
                                                    case "0":
                                                        out.println("Nessun caffè.");
                                                        out.println();
                                                        move = true;
                                                        flag = false;
                                                        break;
                                                    default:
                                                        out.println("Inserisci un valore valido. \n");
                                                        flag = true;
                                                        break;
                                                }
                                            }
                                        }
                                    } else {
                                        out.println("Non hai monete! Procurati una moneta");
                                        out.println();
                                        move = true;
                                    }

                                } else if (p.getObject().getId() == 16) {
                                    if (p.getObject().isAvailable()) {
                                        while (flag) {
                                            printPhone();
                                            Scanner scanner7 = new Scanner(System.in);
                                            String chooseNumber = scanner7.nextLine().toLowerCase();
                                            out.println();
                                            switch (chooseNumber) {
                                                case "3774480028":
                                                    printPhone2();
                                                    flag = false;
                                                    endTime = System.currentTimeMillis();
                                                    end(out);
                                                    break;
                                                case "0":
                                                    out.println("Fatto! Telefono lasciato.");
                                                    out.println();
                                                    move = true;
                                                    flag = false;
                                                    break;
                                                default:
                                                    out.println("Inserisci un valore valido. \n");
                                                    flag = true;
                                                    break;
                                            }
                                        }
                                    } else {
                                        out.println("Per poter utilizzare il telefono devi accettare il voto! Recati dal prof. Basilico");
                                        out.println();
                                        move = true;
                                    }
                                } else if (p.getObject().getId() == 17) {
                                    if (!p.getObject().isUsed()) {
                                        while (flag) {
                                            printRossetto();
                                            Scanner scanner3 = new Scanner(System.in);
                                            String chooseRossetto = scanner3.nextLine().toLowerCase();
                                            out.println();
                                            switch (chooseRossetto) {
                                                case "rossetto":
                                                    out.println("Soluzione corretta, autenticazione #1 riuscita.");
                                                    p.getObject().setUsed(true);
                                                    out.println();
                                                    move = true;
                                                    getRooms().get(25).setCount(++j);
                                                    flag = false;
                                                    break;
                                                case "0":
                                                    out.println("Autenticazione #1 fallita.");
                                                    out.println();
                                                    move = true;
                                                    flag = false;
                                                    break;
                                                default:
                                                    out.println("Inserisci un valore valido. \n");
                                                    flag = true;
                                                    break;
                                            }
                                        }
                                    } else {
                                        out.println("Qui ti sei già autenticato!");
                                        out.println();
                                        move = true;
                                    }
                                } else if (p.getObject().getId() == 18) {
                                    if (!p.getObject().isUsed()) {
                                        while (flag) {
                                            printImpavido();
                                            Scanner scanner4 = new Scanner(System.in);
                                            String chooseImpavido = scanner4.nextLine().toLowerCase();
                                            out.println();
                                            switch (chooseImpavido) {
                                                case "impavido":
                                                    out.println("Soluzione corretta, autenticazione #2 riuscita.");
                                                    p.getObject().setUsed(true);
                                                    out.println();
                                                    move = true;
                                                    getRooms().get(25).setCount(++j);
                                                    flag = false;
                                                    break;
                                                case "0":
                                                    out.println("Autenticazione #2 fallita.");
                                                    out.println();
                                                    move = true;
                                                    flag = false;
                                                    break;
                                                default:
                                                    out.println("Inserisci un valore valido. \n");
                                                    flag = true;
                                                    break;
                                            }
                                        }
                                    } else {
                                        out.println("Qui ti sei già autenticato!");
                                        out.println();
                                        move = true;
                                    }
                                } else if (p.getObject().getId() == 19) {
                                    if (!p.getObject().isUsed()) {
                                        while (flag) {
                                            printGatto();
                                            Scanner scanner5 = new Scanner(System.in);
                                            String chooseGatto = scanner5.nextLine().toLowerCase();
                                            out.println();
                                            switch (chooseGatto) {
                                                case "gatto":
                                                    out.println("Soluzione corretta, autenticazione #3 riuscita.");
                                                    p.getObject().setUsed(true);
                                                    out.println();
                                                    move = true;
                                                    getRooms().get(25).setCount(++j);
                                                    flag = false;
                                                    break;
                                                case "0":
                                                    out.println("Autenticazione #3 fallita.");
                                                    out.println();
                                                    move = true;
                                                    flag = false;
                                                    break;
                                                default:
                                                    out.println("Inserisci un valore valido. \n");
                                                    flag = true;
                                                    break;
                                            }
                                        }
                                    } else {
                                        out.println("Qui ti sei già autenticato!");
                                        out.println();
                                        move = true;
                                    }
                                } else if (p.getObject().getId() == 20) {
                                    if (!p.getObject().isUsed()) {
                                        while (flag) {
                                            printCinquanta();
                                            Scanner scanner6 = new Scanner(System.in);
                                            String chooseCinquanta = scanner6.nextLine().toLowerCase();
                                            out.println();
                                            switch (chooseCinquanta) {
                                                case "cinquanta":
                                                    out.println("Soluzione corretta, autenticazione #4 riuscita.");
                                                    p.getObject().setUsed(true);
                                                    out.println();
                                                    move = true;
                                                    getRooms().get(25).setCount(++j);
                                                    flag = false;
                                                    break;
                                                case "0":
                                                    out.println("Autenticazione #4 fallita.");
                                                    out.println();
                                                    move = true;
                                                    flag = false;
                                                    break;
                                                default:
                                                    out.println("Inserisci un valore valido. \n");
                                                    flag = true;
                                                    break;
                                            }
                                        }
                                    }
                                } else {
                                    out.println("Qui ti sei già autenticato!");
                                    out.println();
                                    move = true;
                                }
                            } else {
                                out.println("Non puoi utilizzare " + p.getObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Usare cosa? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case PICK_UP:
                        //se il comando è di tipo PICK_UP
                        if (p.getObject() != null) {
                            if (getCurrentRoom().getObjects().contains(p.getObject()) && p.getObject().isPickupable()) {
                                if (getCurrentRoom() != getRooms().get(4)) {
                                    p.getObject().setPickupable(false);
                                    p.getObject().setDroppable(true);
                                    getCurrentRoom().getObjects().remove(p.getObject());
                                    getInventory().add(p.getObject());
                                    out.println("Fatto! Hai preso: " + p.getObject().getName());
                                    out.println();
                                    move = true;
                                } else {
                                    out.println("Non si ruba!");
                                    out.println();
                                    move = true;
                                }
                            } else if (p.getObject().getId() == 17 || p.getObject().getId() == 18 || p.getObject().getId() == 19 || p.getObject().getId() == 20) {
                                out.println("Non puoi prendere il tablet. Limitati ad usarlo.");
                            } else {
                                out.println("Non puoi prendere " + p.getObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Prendere cosa? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case LEAVE:
                        // se il comando è di tipo LEAVE
                        if (p.getInvObject() != null) { // se ci sono oggetti
                            if (getInventory().contains(p.getInvObject()) && p.getInvObject().isDroppable()) { // se l'oggetto è presente nell'inventario
                                p.getInvObject().setDroppable(false);
                                p.getInvObject().setPickupable(true);
                                getInventory().remove(p.getInvObject()); // rimuovo l'oggetto dall'inventario
                                getCurrentRoom().getObjects().add(p.getInvObject()); // e lo aggiungo alla stanza
                                out.println("Fatto! Hai lasciato: " + p.getInvObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Lasciare cosa? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case TALK:
                        boolean accept = true;
                        if (p.getPerson() != null) {
                            if (p.getPerson().isTalkable()) {
                                if (p.getPerson().getId() == 1) {
                                    out.println("“Salve sono Pomarico Edoardo, sto cercando il professor Basilico.”"
                                            + "\n“Ciao Edoardo, il professor Basilico si trova al primo piano. Devi sapere, però, che il professor basilico recentemente è diventato il coordinatore di questo dipartimento."
                                            + "\nEssendo una personalità molto diligente, ha introdotto un nuovo sistema di ricevimento."
                                            + "\nPer evitare inutili perdite di tempo, si è deciso di introdurre degli indovinelli affinché solo gli studenti più motivati possano essere ricevuti."
                                            + "\nGli indovinelli riguardano alcuni tra i cognomi dei professori di questo dipartimento, in questo momento in smart-working."
                                            + "\nUna volta risolti, ti basterà recarti nell’ufficio del coordinatore che si trova in fondo al corridoio sulla est. Buona fortuna.”"
                                            + "\n“Questo mondo è strano”"
                                            + "\n“Cosa?”"
                                            + "\n“Niente, niente, grazie mille!”");
                                    getRooms().get(4).setLook("C'è una scrivania con un computer ed un foglio. Il collaboratore è impegnato e non può parlare. Esci dall'ufficio e non disturbarlo.");
                                    getCurrentRoom().getPeople().get(0).setTalkable(false);
                                    out.println();
                                    move = true;
                                } else if (p.getPerson().getId() == 2) {
                                    out.println("“Salve professore”"
                                            + "\n“Buona sera, chi è lei? Ho sentito dei rumori, come mai ci hai impiegato cosi tanto tempo?”"
                                            + "\n“Sono Edoardo Pomarico. Ho risolto gli indovinelli. Cosi mi ha spiegato il collaboratore”"
                                            + "\n“*risata*"
                                            + "\nVedi Edoardo, il vero problema della nostra società è la mancanza di dialogo. Ho introdotto gli indovinelli per mettere alla prova il nostro sistema interno e non gli studenti."
                                            + "\nNon ne ho mai spiegato il senso eppure nessuno me l’ha mai chiesto perché hanno paura della mia autorità!”"
                                            + "\n“Mh…”"
                                            + "\n“Tornando a noi, perché sei qui?”"
                                            + "\n“Per conoscere l’esito dell’ultimo esame”"
                                            + "\n“Dunque dunque, Pomarico, Pomarico…, matricola n. 697698, hai preso 19!”");
                                    getRooms().get(28).setLook("C'è il professor Basilico che è impegnato e non può parlare. Esci dall'aula e non disturbarlo.");
                                    getCurrentRoom().getPeople().get(0).setTalkable(false);

                                    while (accept) {
                                        printVote();
                                        Scanner scanner8 = new Scanner(System.in);
                                        String chooseAccept = scanner8.nextLine().toLowerCase();
                                        out.println();
                                        switch (chooseAccept) {
                                            case "si":
                                                out.println("Ottima scelta! Ora non le resta che andare in segreteria per verbalizzare il suo voto.");
                                                out.println();
                                                out.println("-----[" + getCurrentRoom().getuniverse() + "]-----");
                                                out.println(getCurrentRoom().getDescription());
                                                getRooms().get(26).getObjects().get(1).setAvailable(true);
                                                accept = false;
                                                break;
                                            case "no":
                                                out.println("Sei pazzo! Come si suol dire trenta 18 fanno una laurea e non diciotto 30\n");
                                                accept = true;
                                                break;
                                            default:
                                                out.println("Inserisci un valore valido. \n");
                                                accept = true;
                                                break;
                                        }
                                    }

                                }
                            } else {
                                out.println("Non disturbare!");
                            }
                        } else {
                            out.println("Parlare con chi? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case OPEN:
                        if (p.getObject() != null) {
                            if (p.getObject().isOpenable()) {
                                p.getObject().setOpenable(false);
                                p.getObject().setOpen(true);
                                out.println("Fatto! Porta aperta.");
                                out.println();
                                move = true;
                                getRooms().get(28).setVisible(true);
                            } else if (!p.getObject().isOpenable()) {
                                out.println("La porta è già aperta.");
                            } else {
                                out.println("Non puoi aprire " + p.getObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Aprire cosa? Sii più preciso.");
                            out.println();
                            move = true;
                        }
                        break;
                    case HELP:
                        printHelp();
                        out.println();
                        move = true;
                        break;
                    case MAPPA:
                        printMap();
                        out.println();
                        move = true;
                        break;
                    case PUSH:
                        // controlla se il comando è di tipo PUSH
                        //ricerca oggetti pushabili
                        if (p.getObject() != null) { // se il parser ha interpretato il comando di tipo oggetto e se l'oggetto è premibile
                            if (p.getObject().isPushable()) {
                                if (p.getObject().getId() == 1) { // istruzione per le volte dispari
                                    if (p.getObject().isPushable()) {
                                        p.getObject().setPushable(false);
                                        setCurrentRoom(getRooms().get(27));
                                        move = true; // booleano che serve a settare il fatto che mi sono mosso
                                        // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                                        out.println("Fatto! Hai premuto: " + p.getObject().getName());
                                        out.println();
                                        out.println("...");
                                        out.println("Sei in viaggio verso il nuovo universo");
                                        out.println("...");
                                        out.println();
                                    } else {
                                        p.getObject().setPushable(true);
                                        setCurrentRoom(getRooms().get(1));
                                        move = true; // booleano che serve a settare il fatto che mi sono mosso
                                        // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                                        out.println("Fatto! Hai premuto: " + p.getObject().getName());
                                        out.println();
                                        out.println("...");
                                        out.println("Sei in viaggio verso il tuo universo");
                                        out.println("...");
                                        out.println();
                                    }
                                } else if (p.getObject().getId() == 9) {
                                    out.println("Fatto! Hai premuto: " + p.getObject().getName());
                                    out.println();
                                    System.out.println("L'ascensore non è disponibile.");
                                    out.println();
                                    move = true;
                                }
                            } else {
                                out.println("Non puoi premere " + p.getObject().getName());
                                out.println();
                                move = true;
                            }
                        } else {
                            out.println("Premere cosa? Sii più preciso."); // se non ci sono oggetti o non si puo premere niente stampa questo
                            out.println();
                            move = true;
                        }
                        break;
                    default:
                        break;
                }
            }

            if (noroom) { // se noroom = true
                out.println("Verso " + p.getCommand().getName().toLowerCase() + " non puoi andare");
            } else if (move) { // se move = true
                if (getRooms().get(3).getCount() == 3) {
                    end1(out);
                }
                // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
                out.println("-----[" + getCurrentRoom().getuniverse() + "]-----");
                out.println(getCurrentRoom().getDescription());
                if (getRooms().get(25).getCount() == 4) {
                    getRooms().get(25).setVisible(true);
                }
            }

        }
    }

    private void end(PrintStream out) {
        out.println();
        printEnd();
        out.println();
        out.println();
        seconds = (endTime - startTime) / 1000;
        System.out.println("Programma eseguito in " + seconds + " secondi");
        System.exit(0);
    }

    private void end1(PrintStream out) {
        out.println("Hai perso!! Hai utilizzato tutte le monete senza riuscire a terminare il gioco.");
        out.println("Hai totalizzato 0 punti.");
        System.exit(0);
    }
}
