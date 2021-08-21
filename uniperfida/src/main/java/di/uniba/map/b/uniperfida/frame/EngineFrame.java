/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.frame;

import di.uniba.map.b.uniperfida.Engine;
import di.uniba.map.b.uniperfida.games.UniperfidaGame;
import di.uniba.map.b.uniperfida.parser.Parser;
import di.uniba.map.b.uniperfida.parser.ParserOutput;
import static di.uniba.map.b.uniperfida.print.Printings.*;
import di.uniba.map.b.uniperfida.type.AdvObject;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author antoniosilvestre
 */
public class EngineFrame extends javax.swing.JFrame {

    private UniperfidaGame game;
    private Parser parser;
    boolean noroom = false;
    boolean move = false;

    /**
     * Creates new form EngineFrame
     */
    public EngineFrame() {
        initComponents();
        init();
    }

    private void control() {
        if (noroom) { // se noroom = true
            GameTextArea.append("\nVerso questa direzione non puoi andare\n");
        } else if (move) { // se move = true
            // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
            UniverseLabel1.setVisible(true);
            UniverseLabel2.setVisible(true);
            UniverseLabel2.setText(game.getCurrentRoom().getuniverse());
            GameTextArea.append("\n\n-----[" + game.getCurrentRoom().getDescription() + "]-----\n");
        }
        controlObjects();
        controlInventory();
        controlButton();
        if (game.getRooms().get(3).getCount() == 3) {
            //end1(out);
            PickUp.setVisible(false);
            North.setVisible(false);
            South.setVisible(false);
            East.setVisible(false);
            West.setVisible(false);
            Push.setVisible(false);
            Open.setVisible(false);
            Talk.setVisible(false);
            Up.setVisible(false);
            Use.setVisible(false);
            Down.setVisible(false);
            Read.setVisible(false);
            Insert.setVisible(false);
            ProfessorsName.setVisible(false);
            ObjectsLabel1.setVisible(false);
            ObjectsLabel2.setVisible(false);
            ObjectsLabel3.setVisible(false);
            InventoryLabel.setVisible(false);
            InventoryLabel1.setVisible(false);
            InventoryLabel2.setVisible(false);
            InventoryLabel3.setVisible(false);
            UniverseLabel1.setVisible(false);
            UniverseLabel2.setVisible(false);
            GameTextArea.setText("");
            GameTextArea.append("\nHai perso, 0 punti");
        }
    }

    private void controlButton() {
        if (game.getCurrentRoom() == game.getRooms().get(1) || game.getCurrentRoom() == game.getRooms().get(27) || game.getCurrentRoom() == game.getRooms().get(8) || game.getCurrentRoom() == game.getRooms().get(15)) {
            Push.setEnabled(true);
        } else {
            Push.setEnabled(false);

        }
        if (game.getCurrentRoom() == game.getRooms().get(2) || game.getCurrentRoom() == game.getRooms().get(3) || game.getCurrentRoom() == game.getRooms().get(7) || game.getCurrentRoom() == game.getRooms().get(10) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Read.setEnabled(true);
        } else {
            Read.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(9) || game.getCurrentRoom() == game.getRooms().get(13) || game.getCurrentRoom() == game.getRooms().get(11) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Up.setEnabled(true);
            Down.setEnabled(true);
        } else {
            Up.setEnabled(false);
            Down.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(24) || game.getCurrentRoom() == game.getRooms().get(12) || game.getCurrentRoom() == game.getRooms().get(2) || game.getCurrentRoom() == game.getRooms().get(4)) {
            if (game.getCurrentRoom().isCoin()) {
                PickUp.setEnabled(true);
            } else {
                PickUp.setEnabled(false);
            }
        } else {
            PickUp.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(18) || game.getCurrentRoom() == game.getRooms().get(19) || game.getCurrentRoom() == game.getRooms().get(21) || game.getCurrentRoom() == game.getRooms().get(22) || game.getCurrentRoom() == game.getRooms().get(3) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Use.setEnabled(true);
        } else {
            Use.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(25) && !game.getRooms().get(28).isVisible()) {
            Open.setEnabled(true);
        } else {
            Open.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(4) || game.getCurrentRoom() == game.getRooms().get(28)) {
            Talk.setEnabled(true);
        } else {
            Talk.setEnabled(false);
        }
    }

    public void controlObjects() {
        if (!game.getCurrentRoom().getObjects().isEmpty()) {
            ObjectsLabel1.setText("");
            ObjectsLabel1.setText("Sono presenti inoltre i seguenti oggetti:");
            ObjectsLabel2.setText("\n- " + game.getCurrentRoom().getObjects().get(0).getName());
            ObjectsLabel3.setText("");
            if (game.getCurrentRoom().isTwoObjects()) {
                ObjectsLabel3.setText("\n- " + game.getCurrentRoom().getObjects().get(1).getName());
            }
        } else {
            ObjectsLabel1.setText("");
            ObjectsLabel1.setText("In questa stanza non ci sono oggetti.");
            ObjectsLabel2.setText("");
            ObjectsLabel3.setText("");
        }
        if (game.getRooms().get(25).getCount() == 4) {
            game.getRooms().get(25).setVisible(true);
        }
    }

    public void controlInventory() {
        int i = 0;
        ImageIcon image = new ImageIcon("resources/coin.png");
        ImageIcon image2 = new ImageIcon("resources/no.png");
        if (!game.getInventory().isEmpty()) {
            for (AdvObject o : game.getInventory()) { // ciclo sugli elementi dell'inventario
                i++;
            }
            if (i == 1) {
                InventoryLabel.setVisible(true);
                InventoryLabel2.setVisible(true);
                InventoryLabel2.setIcon(image);
                InventoryLabel1.setVisible(true);
                InventoryLabel3.setVisible(true);
                InventoryLabel1.setIcon(null);
                InventoryLabel3.setIcon(null);
            } else if (i == 2) {
                InventoryLabel.setVisible(true);
                InventoryLabel1.setVisible(true);
                InventoryLabel3.setVisible(true);
                InventoryLabel1.setIcon(image);
                InventoryLabel3.setIcon(image);
                InventoryLabel2.setVisible(true);
                InventoryLabel2.setIcon(null);
            } else if (i == 3) {
                InventoryLabel.setVisible(true);
                InventoryLabel1.setVisible(true);
                InventoryLabel2.setVisible(true);
                InventoryLabel3.setVisible(true);
                InventoryLabel1.setIcon(image);
                InventoryLabel2.setIcon(image);
                InventoryLabel3.setIcon(image);
            }
        } else {
            if (!"Nuova Partita".equals(NewGame.getText())) {
                InventoryLabel1.setVisible(true);
                InventoryLabel2.setVisible(true);
                InventoryLabel3.setVisible(true);
                InventoryLabel1.setIcon(null);
                InventoryLabel2.setIcon(image2);
                InventoryLabel3.setIcon(null);
            } else {
                InventoryLabel1.setVisible(true);
                InventoryLabel2.setVisible(true);
                InventoryLabel3.setVisible(true);
                InventoryLabel1.setIcon(null);
                InventoryLabel2.setIcon(null);
                InventoryLabel3.setIcon(null);
            }
        }
    }

    public void enterToPlay() {
        GameTextArea.append("\n\nPerfetto! Premi invio per iniziare.\n");
        Insert.setVisible(true);
        Insert.setText("Invio");
    }

    public void NewGameFunction() {
        GameTextArea.setText("");
        GameTextArea.append("Universo T-237, Terra, laboratorio di ricerca “Pablo Escobar”, Martedì, 4 Maggio 2021."
                + "\nIl professor Silvestre ha finalmente ultimato il suo progetto: una macchina che permette di viaggiare tra gli universi."
                + "\n“Assistente Olivieri, venga qui e mi dica cosa vede.” "
                + "\n“Vedo una macchina interdimensionale?” "
                + "\n“No, è la nostra possibilità per sbagliare, il nostro mondo è talmente perfetto che non ci permette di capire cosa sia giusto o sbagliato”"
                + "\n“Non capisco”"
                + "\n“C’è un universo, conosciuto come J-371, in cui ci sono le stesse persone."
                + "\nPensa: la loro libertà, senza alcun vincolo morale, ha reso quel mondo invivibile. Viviamo due situazioni diametralmente opposte ma desideriamo la stessa cosa: evadere."
                + "\nOra ci serve solo una cavia."
                + "\nSecondo la legge dei Multiversi, se una persona effettua un viaggio interdimensionale si ritroverà nello stesso nostro anno, a vivere una vita che è sua solo in quell’universo,"
                + "\nquindi io lì potrei essere un delinquente!”"
                + "\n“Ce l’ho, ce l’ho, conosco un tipo di nome Edoardo abbastanza sconsiderato da affrontare un viaggio da cui potrebbe non fare ritorno” "
                + "\n“Okay, ci incontriamo qui giovedì alle 15”.");
        GameTextArea.append("\n");
        GameTextArea.append("\n...\n");
        GameTextArea.append("...\n");
        GameTextArea.append("\n**********DUE GIORNI DOPO**********");
        GameTextArea.append("\n\n“Buongiorno professor Silvestre, io sono Edoardo.”"
                + "\n“Ciao Edoardo. Sono molto felice di conoscerti, prima di partire lascia che ti spieghi un paio di cose. Iniziamo subito, sei pronto?”"
                + "\n“Sissignore!”"
                + "\n“Come vedi questa è una macchina interdimensionale e serve per viaggiare attraverso i Multiversi."
                + "\nIl viaggio durerà qualche secondo e non ti accorgerai di nulla. Una volta dentro la macchina, premi il tasto rosso per partire. "
                + "\nRicorda: hai a disposizione solamente sessanta minuti. Passata l’ora ritorna esattamente nel punto in cui hai approdato”"
                + "\n“Ho capito, ci sono rischi?”"
                + "\n“No”."
                + "\n..."
                + "\n..."
                + "\n“Ah dimenticavo, molto probabilmente ti ritroverai in un mondo diviso per nazioni e lingue. Ancora non conoscono bene il concetto di integrazione, in bocca al lupo!”\n");
        UniverseLabel1.setVisible(true);
        UniverseLabel2.setVisible(true);
        UniverseLabel2.setText(game.getCurrentRoom().getuniverse());
        GameTextArea.append("\n\n-----[" + game.getCurrentRoom().getDescription() + "]-----\n");
        PickUp.setVisible(true);
        North.setVisible(true);
        South.setVisible(true);
        East.setVisible(true);
        West.setVisible(true);
        Push.setVisible(true);
        Open.setVisible(true);
        Talk.setVisible(true);
        Up.setVisible(true);
        Use.setVisible(true);
        Down.setVisible(true);
        Read.setVisible(true);
        Insert.setVisible(false);
        ProfessorsName.setVisible(false);
        NewGame.setText("UNIPERFIDA");
        NewGame.setEnabled(false);
        ObjectsLabel1.setVisible(true);
        ObjectsLabel2.setVisible(true);
        ObjectsLabel3.setVisible(true);
        InventoryLabel.setVisible(true);
        InventoryLabel1.setVisible(true);
        InventoryLabel2.setVisible(true);
        InventoryLabel3.setVisible(true);
        control();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NewGame = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        GameTextArea = new javax.swing.JTextArea();
        North = new javax.swing.JButton();
        South = new javax.swing.JButton();
        East = new javax.swing.JButton();
        West = new javax.swing.JButton();
        Push = new javax.swing.JButton();
        PickUp = new javax.swing.JButton();
        Open = new javax.swing.JButton();
        Talk = new javax.swing.JButton();
        Use = new javax.swing.JButton();
        Up = new javax.swing.JButton();
        Down = new javax.swing.JButton();
        Read = new javax.swing.JButton();
        ProfessorsName = new javax.swing.JTextField();
        Insert = new javax.swing.JButton();
        InventoryLabel1 = new javax.swing.JLabel();
        InventoryLabel2 = new javax.swing.JLabel();
        InventoryLabel3 = new javax.swing.JLabel();
        ObjectsLabel1 = new javax.swing.JLabel();
        ObjectsLabel2 = new javax.swing.JLabel();
        ObjectsLabel3 = new javax.swing.JLabel();
        InventoryLabel = new javax.swing.JLabel();
        UniverseLabel1 = new javax.swing.JLabel();
        UniverseLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UNIPERFIDA");
        setResizable(false);

        NewGame.setText("Nuova Partita");
        NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameActionPerformed(evt);
            }
        });
        NewGame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NewGameKeyPressed(evt);
            }
        });

        GameTextArea.setEditable(false);
        GameTextArea.setColumns(20);
        GameTextArea.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        GameTextArea.setRows(5);
        jScrollPane2.setViewportView(GameTextArea);

        North.setText("N");
        North.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NorthActionPerformed(evt);
            }
        });

        South.setText("S");
        South.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SouthActionPerformed(evt);
            }
        });

        East.setText("E");
        East.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EastActionPerformed(evt);
            }
        });

        West.setText("O");
        West.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WestActionPerformed(evt);
            }
        });

        Push.setText("Premi");
        Push.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PushActionPerformed(evt);
            }
        });

        PickUp.setText("Raccogli");
        PickUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickUpActionPerformed(evt);
            }
        });

        Open.setText("Apri");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        Talk.setText("Parla");
        Talk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TalkActionPerformed(evt);
            }
        });

        Use.setText("Usa");
        Use.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UseActionPerformed(evt);
            }
        });

        Up.setText("Sali");
        Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpActionPerformed(evt);
            }
        });

        Down.setText("Scendi");
        Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownActionPerformed(evt);
            }
        });

        Read.setText("Leggi");
        Read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadActionPerformed(evt);
            }
        });

        ProfessorsName.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Insert.setText("Inserisci");
        Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertActionPerformed(evt);
            }
        });

        ObjectsLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel1.setText("1");
        ObjectsLabel1.setToolTipText("");

        ObjectsLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel2.setText("2");

        ObjectsLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel3.setText("3");

        InventoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        InventoryLabel.setText("Borsellino");

        UniverseLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UniverseLabel1.setText("Universo");

        UniverseLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UniverseLabel2.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                                        .addComponent(ObjectsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(UniverseLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ObjectsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(89, 89, 89)
                                .addComponent(InventoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InventoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InventoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(UniverseLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ObjectsLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(InventoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProfessorsName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Insert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Talk, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Use, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Read, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Push, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(118, 118, 118)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(South, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(West, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(North, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(East, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Up, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Down, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PickUp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InventoryLabel)
                    .addComponent(ObjectsLabel1)
                    .addComponent(UniverseLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InventoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ObjectsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UniverseLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ObjectsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Up)
                            .addComponent(PickUp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Down)
                            .addComponent(Open)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Talk)
                            .addComponent(Push, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Use)
                            .addComponent(Read)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(North)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(South))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(West)
                            .addComponent(East))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(ProfessorsName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Insert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EastActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getEast() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_EastActionPerformed

    private void NorthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NorthActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getNorth() != null && game.getCurrentRoom() != game.getRooms().get(27)) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && !game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            move = true;
            noroom = false;
            GameTextArea.append("\nAll'improvviso, a causa del vento, ti capita tra le mani un fogliettino che recita:"
                    + "\nMessaggio per l'Edoardo Pomarico del futuro: aiutami a verbalizzare il voto, non riesco a prenotare il ricevimento con il prof. Basilico perchè l'app MyUni fa schifo!"
                    + "\nChe coincidenza! Nel tuo primo passo sul nuovo universo hai scoperto due cose: il tuo obiettivo e il fatto che il tuo alter ego sia uno svitato!");
            game.getRooms().get(27).setPrint(true);
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_NorthActionPerformed

    private void SouthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SouthActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getSouth() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getSouth());
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_SouthActionPerformed

    private void WestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WestActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getWest() != null && game.getCurrentRoom() != game.getRooms().get(23) && game.getCurrentRoom() != game.getRooms().get(25)) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(23) && game.getRooms().get(25).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(23) && !game.getRooms().get(25).isVisible()) {
            GameTextArea.append("\nPer entrare dal prof. Basilico devi prima risolvere gli indovinelli.");
            GameTextArea.append("\n");
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(25) && !game.getRooms().get(28).isVisible()) {
            GameTextArea.append("\nDevi aprire la porta per entrare!");
            GameTextArea.append("\n");
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(25) && game.getRooms().get(28).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_WestActionPerformed

    private void PushActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PushActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(1)) {
            game.setCurrentRoom(game.getRooms().get(27));
            move = true;
            noroom = false;
            // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
            GameTextArea.append("\nFatto! Hai premuto il bottone\n");
            GameTextArea.append("\n");
            GameTextArea.append("...");
            GameTextArea.append("\nSei in viaggio verso il nuovo universo\n");
            GameTextArea.append("...");
        } else if (game.getCurrentRoom() == game.getRooms().get(27)) {

            game.setCurrentRoom(game.getRooms().get(1));
            move = true;
            noroom = false;
            // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
            GameTextArea.append("\nFatto! Hai premuto il bottone\n");
            GameTextArea.append("\n");
            GameTextArea.append("...");
            GameTextArea.append("\nSei in viaggio verso il tuo universo\n");
            GameTextArea.append("...");
        } else if (game.getCurrentRoom() == game.getRooms().get(8) || game.getCurrentRoom() == game.getRooms().get(15)) {
            GameTextArea.append("\nL'ascensore non è disponibile.");
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_PushActionPerformed

    private void UpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getUp() != null) { // controlla se su si può andare
            game.setCurrentRoom(game.getCurrentRoom().getUp()); // se su si può andare setto la nuova stanza corrente
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_UpActionPerformed

    private void DownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getDown() != null && game.getCurrentRoom() != game.getRooms().get(11)) { // controlla se su si può andare
            game.setCurrentRoom(game.getCurrentRoom().getDown()); // se su si può andare setto la nuova stanza corrente
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(11) && game.getRooms().get(26).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getDown()); // se su si può andare setto la nuova stanza corrente
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = true;
        }
        control();
    }//GEN-LAST:event_DownActionPerformed

    private void ReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(2)) { //cartello in cortile
            GameTextArea.append("\nIl cartello recita:\n" + game.getCurrentRoom().getObjects().get(0).getDescription());
            move = true;
            noroom = false;
            game.getRooms().get(2).getObjects().get(0).setDescription("'Università di Bari Aldo Moro, Facoltà di Informatica'.");
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) { //bacheca in hall
            GameTextArea.append("\nNella bacheca c'è scritto:\n" + game.getCurrentRoom().getObjects().get(0).getDescription());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(7)) {
            GameTextArea.append("\nNella bacheca c'è scritto:\n" + game.getCurrentRoom().getObjects().get(0).getDescription());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(10)) {
            GameTextArea.append("\nNella bacheca c'è scritto:\n" + game.getCurrentRoom().getObjects().get(0).getDescription());
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            GameTextArea.append("\nNel foglio c'è scritto:\n" + game.getCurrentRoom().getObjects().get(0).getDescription());
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_ReadActionPerformed

    private void InsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertActionPerformed
        // TODO add your handling code here:
        int j = game.getRooms().get(25).getCount();
        int a = game.getRooms().get(3).getCount();
        if (game.getCurrentRoom() == game.getRooms().get(18)) {
            String rossetto = ProfessorsName.getText();
            rossetto = rossetto.toLowerCase();
            switch (rossetto) {
                case "rossetto":
                    GameTextArea.append("\nSoluzione corretta, autenticazione #1 riuscita.\n");
                    game.getCurrentRoom().getObjects().get(0).setUsed(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #1 fallita.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(19)) {
            String impavido = ProfessorsName.getText();
            impavido = impavido.toLowerCase();
            switch (impavido) {
                case "impavido":
                    GameTextArea.append("\nSoluzione corretta, autenticazione #2 riuscita.\n");
                    game.getCurrentRoom().getObjects().get(0).setUsed(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #2 fallita.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(21)) {
            String gatto = ProfessorsName.getText();
            gatto = gatto.toLowerCase();
            switch (gatto) {
                case "gatto":
                    GameTextArea.append("\nSoluzione corretta, autenticazione #3 riuscita.\n");
                    game.getCurrentRoom().getObjects().get(0).setUsed(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #3 fallita.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(22)) {
            String cinquanta = ProfessorsName.getText();
            cinquanta = cinquanta.toLowerCase();
            switch (cinquanta) {
                case "cinquanta":
                    GameTextArea.append("\nSoluzione corretta, autenticazione #4 riuscita.\n");
                    game.getCurrentRoom().getObjects().get(0).setUsed(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #4 fallita.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            String number = ProfessorsName.getText();
            switch (number) {
                case "1":
                    GameTextArea.append("\nprintPhone2\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    endTime = System.currentTimeMillis();
                    seconds = (endTime - startTime) / 1000;
                    GameTextArea.setText("");
                    GameTextArea.append("\nprintEnd\n");
                    GameTextArea.append("Programma eseguito in " + seconds + " secondi\n");
                    GameTextArea.append("\nVedi come far finire il gioco");
                    break;
                case "0":
                    GameTextArea.append("Fatto! Telefono lasciato.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Inserisci un valore valido. \n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) {
            String chooseCoffee = ProfessorsName.getText();
            switch (chooseCoffee) {
                case "1":
                    game.getInventory().remove(game.getInventory().get(0));
                    GameTextArea.append("\nFatto! Hai preso un caffè.\n");
                    GameTextArea.append("...\n");
                    GameTextArea.append("Mmh, buono questo caffè\n");
                    GameTextArea.append("...");
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                case "2":
                    game.getInventory().remove(game.getInventory().get(0));
                    GameTextArea.append("\nFatto! Hai preso un caffè lungo.\n");
                    GameTextArea.append("...\n");
                    GameTextArea.append("Mmh, buono questo caffè lungo\n");
                    GameTextArea.append("...");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    game.getRooms().get(26).setVisible(true);
                    game.getRooms().get(11).setLook("Ci sono file di banchi. C'è una nuvola di polvere. Intravedi delle scale.");
                    GameTextArea.append("Ma cos'è stato questo rumore? Proveniva dalla aula A.\n");
                    move = true;
                    noroom = false;
                    break;
                case "3":
                    game.getInventory().remove(game.getInventory().get(0));
                    GameTextArea.append("\nFatto! Hai preso un caffè macchiato.\n");
                    GameTextArea.append("...\n");
                    GameTextArea.append("Mmh, buono questo caffè macchiato\n");
                    GameTextArea.append("...");
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                case "4":
                    game.getInventory().remove(game.getInventory().get(0));
                    GameTextArea.append("\nFatto! Hai preso un caffè al cioccolato.\n");
                    GameTextArea.append("...\n");
                    GameTextArea.append("Mmh, buono questo caffè al cioccolato\n");
                    GameTextArea.append("...");
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                case "0":
                    GameTextArea.append("\nNessun caffè.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Inserisci un valore valido.\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(28)) {
            String chooseVote = ProfessorsName.getText();
            chooseVote = chooseVote.toLowerCase();
            switch (chooseVote) {
                case "si":
                    GameTextArea.append("\nOttima scelta! Ora non le resta che andare in segreteria per verbalizzare il suo voto.");
                    game.getRooms().get(26).getObjects().get(1).setAvailable(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("\nDevi accettare per forza! Non ti laureerai mai altrimenti.\n");
                    break;
            }
        } else if ("Nuova Partita".equals(NewGame.getText()) && !"Invio".equals(Insert.getText())) {
            String antonio = ProfessorsName.getText();
            antonio = antonio.toLowerCase();
            switch (antonio) {
                case "antonio":
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    enterToPlay();
                    break;

                default:
                    GameTextArea.append("\nIl nome utente è stato già utilizzato. \nInserisci di nuovo il nome:");
                    break;
            }
        } else if("Invio".equals(Insert.getText())) {
            Insert.setText("Inserisci");
            Insert.setVisible(false);
            NewGameFunction();
        }
        ProfessorsName.setText("");
        control();
    }//GEN-LAST:event_InsertActionPerformed

    private void UseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UseActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(18)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                GameTextArea.append("\n#1 Benvenuto nel menu di accettazione."
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- a volte in delle situazioni sono fuori luogo;"
                        + "\n- non lascio le tue labbra;"
                        + "\n- resisto all’acqua;"
                        + "\n- non sono rosso per forza."
                        + "\nInserisci la soluzione oppure premi 0 per uscire.");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Qui ti sei già autenticato!");
                GameTextArea.append("\n");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(19)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                GameTextArea.append("\n#2 Benvenuto nel menu di accettazione."
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- sono un aggettivo;"
                        + "\n- sono coraggioso;"
                        + "\n- sono razionale di fronte ad una minaccia;"
                        + "\n- letteralmente qualcosa in più di avido."
                        + "\nInserisci la soluzione oppure premi 0 per uscire.");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Qui ti sei già autenticato!");
                GameTextArea.append("\n");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(21)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                GameTextArea.append("\n#3 Benvenuto nel menu di accettazione."
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- notoriamente domestico;"
                        + "\n- i miei video sono divertenti;"
                        + "\n- duro a morire;"
                        + "\n- spesso in compagnia di una volpe."
                        + "\nInserisci la soluzione oppure premi 0 per uscire.");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Qui ti sei già autenticato!");
                GameTextArea.append("\n");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(22)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                GameTextArea.append("\n#4 Benvenuto nel menu di accettazione."
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- a questa età si va in crisi;"
                        + "\n- a napoli sono il pane;"
                        + "\n- arancione per le banche;"
                        + "\n- sono un numero."
                        + "\nInserisci la soluzione oppure premi 0 per uscire.");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Qui ti sei già autenticato!");
                GameTextArea.append("\n");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            if (game.getCurrentRoom().getObjects().get(1).isAvailable()) {
                GameTextArea.append("\nprintPhone\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Per poter utilizzare il telefono devi accettare il voto! Recati dal prof. Basilico");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) {
            if (!game.getInventory().isEmpty()) {
                GameTextArea.append("\nprintCoffeeMenu\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Non hai monete! Procurati una moneta");
                move = true;
                noroom = false;
            }
        }
        control();
    }//GEN-LAST:event_UseActionPerformed

    private void PickUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickUpActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(24)) {
            GameTextArea.append("\nFatto, hai preso la moneta!");
            game.getCurrentRoom().getObjects().get(0).setPickupable(false);
            game.getCurrentRoom().getObjects().get(0).setDroppable(true);
            game.getCurrentRoom().setCoin(false);
            game.getCurrentRoom().setTwoObjects(false);
            game.getInventory().add(game.getCurrentRoom().getObjects().get(0));
            game.getCurrentRoom().getObjects().remove(0);
            move = true;
            noroom = false;
            controlInventory();
            controlObjects();
        } else if (game.getCurrentRoom() == game.getRooms().get(12)) {
            GameTextArea.append("\nFatto, hai preso la moneta!");
            game.getCurrentRoom().getObjects().get(0).setPickupable(false);
            game.getCurrentRoom().getObjects().get(0).setDroppable(true);
            game.getCurrentRoom().setCoin(false);
            game.getCurrentRoom().setTwoObjects(false);
            game.getInventory().add(game.getCurrentRoom().getObjects().get(0));
            game.getCurrentRoom().getObjects().remove(0);
            move = true;
            noroom = false;
            controlInventory();
            controlObjects();
        } else if (game.getCurrentRoom() == game.getRooms().get(2)) {
            GameTextArea.append("\nFatto, hai preso la moneta!");
            game.getCurrentRoom().getObjects().get(1).setPickupable(false);
            game.getCurrentRoom().getObjects().get(1).setDroppable(true);
            game.getCurrentRoom().setCoin(false);
            game.getCurrentRoom().setTwoObjects(false);
            game.getInventory().add(game.getCurrentRoom().getObjects().get(1));
            game.getCurrentRoom().getObjects().remove(1);
            move = true;
            noroom = false;
            controlInventory();
            controlObjects();
        } else if (game.getCurrentRoom() == game.getRooms().get(4)) {
            GameTextArea.append("\nNon si ruba!");
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_PickUpActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(25)) {
            GameTextArea.append("\nFatto! Porta aperta.\n");
            move = true;
            noroom = false;
            game.getCurrentRoom().setDescription("Ti trovi nella sala d'attesa del prof. Basilico. La porta è aperta.");
            Open.setVisible(false);
            game.getCurrentRoom().getObjects().remove(0);
            controlObjects();
            game.getRooms().get(28).setVisible(true);
        }
        control();
    }//GEN-LAST:event_OpenActionPerformed

    private void TalkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TalkActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getPeople().get(0).isTalkable()) {
            if (game.getCurrentRoom() == game.getRooms().get(4)) {
                GameTextArea.append("\n“Salve sono Pomarico Edoardo, sto cercando il professor Basilico.”"
                        + "\n“Ciao Edoardo, il professor Basilico si trova al primo piano. Devi sapere, però, che il professor basilico recentemente è diventato il coordinatore di questo dipartimento."
                        + "\nEssendo una personalità molto diligente, ha introdotto un nuovo sistema di ricevimento."
                        + "\nPer evitare inutili perdite di tempo, si è deciso di introdurre degli indovinelli affinché solo gli studenti più motivati possano essere ricevuti."
                        + "\nGli indovinelli riguardano alcuni tra i cognomi dei professori di questo dipartimento, in questo momento in smart-working."
                        + "\nUna volta risolti, ti basterà recarti nell’ufficio del coordinatore che si trova in fondo al corridoio sulla est. Buona fortuna.”"
                        + "\n“Questo mondo è strano”"
                        + "\n“Cosa?”"
                        + "\n“Niente, niente, grazie mille!”\n");
                game.getCurrentRoom().getPeople().get(0).setTalkable(false);
                move = true;
                noroom = false;
            } else if (game.getCurrentRoom() == game.getRooms().get(28)) {
                GameTextArea.append("“Salve professore”"
                        + "\n“Buona sera, chi è lei? Ho sentito dei rumori, come mai ci hai impiegato cosi tanto tempo?”"
                        + "\n“Sono Edoardo Pomarico. Ho risolto gli indovinelli. Cosi mi ha spiegato il collaboratore”"
                        + "\n“*risata*"
                        + "\n“Vedi Edoardo, il vero problema della nostra società è la mancanza di dialogo. Ho introdotto gli indovinelli per mettere alla prova il nostro sistema interno e non gli studenti.“"
                        + "\n“Non ne ho mai spiegato il senso eppure nessuno me l’ha mai chiesto perché hanno paura della mia autorità!”"
                        + "\n“Mh…”"
                        + "\n“Tornando a noi, perché sei qui?”"
                        + "\n“Per conoscere l’esito dell’ultimo esame”"
                        + "\n“Dunque dunque, Pomarico, Pomarico…, matricola n. 697698, hai preso 19!”");
                GameTextArea.append("\nAccetti?\n");
                game.getCurrentRoom().getPeople().get(0).setTalkable(false);
                Talk.setVisible(false);
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                move = false;
                noroom = false;
            }
        } else {
            GameTextArea.append("\nNon disturbare!\n");
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_TalkActionPerformed

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
        // TODO add your handling code here:
        Insert.setVisible(true);
        ProfessorsName.setVisible(true);
        NewGame.setEnabled(false);
    }//GEN-LAST:event_NewGameActionPerformed

    private void NewGameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NewGameKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_NewGameKeyPressed

    /**
     * @param args the command line arguments
     */
    private void init() {
        game = new UniperfidaGame();
        try {
            this.game.init();
            this.game.useFileRoomsDescription();
            this.game.useFileRoomsLook();
            this.game.useFileRoomsName();
            PickUp.setVisible(false);
            North.setVisible(false);
            South.setVisible(false);
            East.setVisible(false);
            West.setVisible(false);
            Push.setVisible(false);
            Open.setVisible(false);
            Talk.setVisible(false);
            Up.setVisible(false);
            Use.setVisible(false);
            Down.setVisible(false);
            Read.setVisible(false);
            Insert.setVisible(false);
            ProfessorsName.setVisible(false);
            ObjectsLabel1.setVisible(false);
            ObjectsLabel2.setVisible(false);
            ObjectsLabel3.setVisible(false);
            InventoryLabel.setVisible(false);
            InventoryLabel1.setVisible(false);
            InventoryLabel2.setVisible(false);
            InventoryLabel3.setVisible(false);
            UniverseLabel1.setVisible(false);
            UniverseLabel2.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        GameTextArea.setText("");
        GameTextArea.append("\n"
                + "\n==================================="
                + "\n * Uniperfida v. 1.1 - 2020-2021 *"
                + "\n==================================="
                + "\nBENVENUTO");
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EngineFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Down;
    private javax.swing.JButton East;
    private javax.swing.JTextArea GameTextArea;
    private javax.swing.JButton Insert;
    private javax.swing.JLabel InventoryLabel;
    private javax.swing.JLabel InventoryLabel1;
    private javax.swing.JLabel InventoryLabel2;
    private javax.swing.JLabel InventoryLabel3;
    private javax.swing.JButton NewGame;
    private javax.swing.JButton North;
    private javax.swing.JLabel ObjectsLabel1;
    private javax.swing.JLabel ObjectsLabel2;
    private javax.swing.JLabel ObjectsLabel3;
    private javax.swing.JButton Open;
    private javax.swing.JButton PickUp;
    private javax.swing.JTextField ProfessorsName;
    private javax.swing.JButton Push;
    private javax.swing.JButton Read;
    private javax.swing.JButton South;
    private javax.swing.JButton Talk;
    private javax.swing.JLabel UniverseLabel1;
    private javax.swing.JLabel UniverseLabel2;
    private javax.swing.JButton Up;
    private javax.swing.JButton Use;
    private javax.swing.JButton West;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
