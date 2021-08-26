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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author antoniosilvestre
 */
public class EngineFrame extends javax.swing.JFrame {

    private UniperfidaGame game;
    boolean noroom = false;
    boolean move = false;
    private AudioInputStream ais;
    private Clip clip;
    private boolean music = true;
    private boolean map = false;
    private boolean help = false;

    /**
     * Creates new form EngineFrame
     */
    public EngineFrame() {
        initComponents();
        init();
        playMusic();
        addWallpaper();
    }

    private void addWallpaper() {
        Wallpaper wallpaper = new Wallpaper();
        getContentPane().add(wallpaper);
        wallpaper.setSize(559, 499);
        wallpaper.setVisible(true);
    }

    private void playMusic() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("resources/Payday 2 Official Soundtrack - AndNowWeWait.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            startMusic();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException exc) {
            JOptionPane.showMessageDialog(this, "Errore nella riproduzione della musica", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startMusic() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void control() {
        if (noroom) { // se noroom = true
            GameTextArea.append("\nVerso questa direzione non puoi andare\n");
        } else if (move) { // se move = true
            // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
            UniverseLabel1.setVisible(true);
            UniverseLabel2.setVisible(true);
            UniverseLabel2.setText(game.getCurrentRoom().getuniverse());
            NameRoom.setText(game.getCurrentRoom().getDescription());
            controlMap();
        }
        controlObjects();
        controlInventory();
        controlButton();
        if (game.getRooms().get(3).getCount() == 3) {
            exit();
            NameRoom.setText("Hai perso!");
            GameTextArea.setText("");
            GameTextArea.append("Le monete sono terminate e non puoi andare in segreteria.\nHai totalizzato 0 punti quindi il tuo punteggio non verrà salvato nel database.\nPremi il tasto esci per uscire.");
        } else if (game.getRooms().get(3).getCount() == 4) {
            exit();
            Exit.setVisible(false);
            Avanti.setVisible(true);
        }
    }

    private void controlButton() {
        if (game.getCurrentRoom() == game.getRooms().get(1) || game.getCurrentRoom() == game.getRooms().get(27) || game.getCurrentRoom() == game.getRooms().get(8) || game.getCurrentRoom() == game.getRooms().get(15)) {
            Push.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Push.setEnabled(false);

        }
        if (game.getCurrentRoom() == game.getRooms().get(2) || game.getCurrentRoom() == game.getRooms().get(3) || game.getCurrentRoom() == game.getRooms().get(7) || game.getCurrentRoom() == game.getRooms().get(10) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Read.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Read.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(13) || game.getCurrentRoom() == game.getRooms().get(11)) {
            Down.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
            if (!game.getRooms().get(26).isVisible() && game.getCurrentRoom() == game.getRooms().get(11)) {
                Down.setEnabled(false);
            }
        } else {
            Down.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(9) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Up.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Up.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(24) || game.getCurrentRoom() == game.getRooms().get(12) || game.getCurrentRoom() == game.getRooms().get(2) || game.getCurrentRoom() == game.getRooms().get(4)) {
            if (game.getCurrentRoom().isCoin()) {
                PickUp.setEnabled(true);
                North.setEnabled(true);
                South.setEnabled(true);
                East.setEnabled(true);
                West.setEnabled(true);
            } else {
                PickUp.setEnabled(false);
            }
        } else {
            PickUp.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(18) || game.getCurrentRoom() == game.getRooms().get(19) || game.getCurrentRoom() == game.getRooms().get(21) || game.getCurrentRoom() == game.getRooms().get(22) || game.getCurrentRoom() == game.getRooms().get(3) || game.getCurrentRoom() == game.getRooms().get(26)) {
            Use.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Use.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(25) && !game.getRooms().get(28).isVisible()) {
            Open.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Open.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(4) || game.getCurrentRoom() == game.getRooms().get(28)) {
            Talk.setEnabled(true);
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        } else {
            Talk.setEnabled(false);
        }
        if (ProfessorsName.isVisible()) {
            PickUp.setEnabled(false);
            North.setEnabled(false);
            South.setEnabled(false);
            East.setEnabled(false);
            West.setEnabled(false);
            Push.setEnabled(false);
            Open.setEnabled(false);
            Talk.setEnabled(false);
            Up.setEnabled(false);
            Use.setEnabled(false);
            Down.setEnabled(false);
            Read.setEnabled(false);
        }
        if (game.getCurrentRoom() == game.getRooms().get(0)) {
            North.setEnabled(true);
            South.setEnabled(true);
            East.setEnabled(true);
            West.setEnabled(true);
        }
    }

    public void controlObjects() {
        if (!game.getCurrentRoom().getObjects().isEmpty()) {
            ObjectsLabel1.setText("");
            ObjectsLabel1.setText("Sono presenti inoltre i seguenti oggetti:");
            ObjectsLabel4.setText("\n- " + game.getCurrentRoom().getObjects().get(0).getName());
            ObjectsLabel2.setText("");
            ObjectsLabel3.setText("");
            if (game.getCurrentRoom().isTwoObjects()) {
                ObjectsLabel2.setText("\n-" + game.getCurrentRoom().getObjects().get(0).getName());
                ObjectsLabel3.setText("\n-" + game.getCurrentRoom().getObjects().get(1).getName());
                ObjectsLabel4.setText("|");
            }
        } else {
            ObjectsLabel1.setText("");
            ObjectsLabel1.setText("In questa stanza non ci sono oggetti.");
            ObjectsLabel2.setText("");
            ObjectsLabel3.setText("");
            ObjectsLabel4.setText("");
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
            switch (i) {
                case 1:
                    InventoryLabel.setVisible(true);
                    InventoryLabel2.setVisible(true);
                    InventoryLabel2.setIcon(image);
                    InventoryLabel1.setVisible(true);
                    InventoryLabel3.setVisible(true);
                    InventoryLabel1.setIcon(null);
                    InventoryLabel3.setIcon(null);
                    break;
                case 2:
                    InventoryLabel.setVisible(true);
                    InventoryLabel1.setVisible(true);
                    InventoryLabel3.setVisible(true);
                    InventoryLabel1.setIcon(image);
                    InventoryLabel3.setIcon(image);
                    InventoryLabel2.setVisible(true);
                    InventoryLabel2.setIcon(null);
                    break;
                case 3:
                    InventoryLabel.setVisible(true);
                    InventoryLabel1.setVisible(true);
                    InventoryLabel2.setVisible(true);
                    InventoryLabel3.setVisible(true);
                    InventoryLabel1.setIcon(image);
                    InventoryLabel2.setIcon(image);
                    InventoryLabel3.setIcon(image);
                    break;
                default:
                    break;
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

    public void controlMap() {
        NowLabel.setText(game.getCurrentRoom().getName());
        if (game.getCurrentRoom().getNorth() == null) {
            NordLabel.setText("A nord non c'è nulla");
        } else {
            NordLabel.setText(game.getCurrentRoom().getNorth().getName());
        }
        if (game.getCurrentRoom().getSouth() == null) {
            SudLabel.setText("A sud non c'è nulla");
        } else {
            SudLabel.setText(game.getCurrentRoom().getSouth().getName());
        }
        if (game.getCurrentRoom().getWest() == null) {
            OvestLabel.setText("A ovest non c'è nulla");
        } else {
            OvestLabel.setText(game.getCurrentRoom().getWest().getName());
        }
        if (game.getCurrentRoom().getEast() == null) {
            EstLabel.setText("A est non c'è nulla");
        } else {
            EstLabel.setText(game.getCurrentRoom().getEast().getName());
        }
    }

    public void enterToPlay() {
        GameTextArea.append("Perfetto! Premi invio per iniziare.\n");
        Insert.setVisible(true);
        Insert.setText("Invio");
    }

    public void exit() {
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
        NewGame.setVisible(false);
        Exit.setVisible(true);
        ObjectsLabel1.setVisible(false);
        ObjectsLabel2.setVisible(false);
        ObjectsLabel3.setVisible(false);
        ObjectsLabel4.setVisible(false);
        InventoryLabel.setVisible(false);
        InventoryLabel1.setVisible(false);
        InventoryLabel2.setVisible(false);
        InventoryLabel3.setVisible(false);
        UniverseLabel1.setVisible(false);
        UniverseLabel2.setVisible(false);
        Map.setVisible(false);
        NordLabel.setVisible(false);
        NowLabel.setVisible(false);
        SudLabel.setVisible(false);
        EstLabel.setVisible(false);
        OvestLabel.setVisible(false);
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
                + "\n“Ah dimenticavo, molto probabilmente ti ritroverai in un mondo diviso per nazioni e lingue. Ancora non conoscono bene"
                + "\nil concetto di integrazione, in bocca al lupo!”\n");
        UniverseLabel1.setVisible(true);
        UniverseLabel2.setVisible(true);
        UniverseLabel2.setText(game.getCurrentRoom().getuniverse());
        NameRoom.setVisible(true);
        NameRoom.setText(game.getCurrentRoom().getDescription());
        GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
        controlMap();
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
        NewGame.setVisible(true);
        NewGame.setText("UNIPERFIDA");
        NewGame.setEnabled(false);
        ObjectsLabel1.setVisible(true);
        ObjectsLabel2.setVisible(true);
        ObjectsLabel3.setVisible(true);
        ObjectsLabel4.setVisible(true);
        InventoryLabel.setVisible(true);
        InventoryLabel1.setVisible(true);
        InventoryLabel2.setVisible(true);
        InventoryLabel3.setVisible(true);
        Map.setEnabled(true);
        Help.setEnabled(true);
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
        NordLabel = new javax.swing.JLabel();
        NowLabel = new javax.swing.JLabel();
        SudLabel = new javax.swing.JLabel();
        EstLabel = new javax.swing.JLabel();
        OvestLabel = new javax.swing.JLabel();
        Exit = new javax.swing.JButton();
        ObjectsLabel4 = new javax.swing.JLabel();
        NameRoom = new javax.swing.JTextField();
        Avanti = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        GameMenu = new javax.swing.JMenu();
        Music = new javax.swing.JCheckBoxMenuItem();
        Map = new javax.swing.JCheckBoxMenuItem();
        AboutMenu = new javax.swing.JMenu();
        Help = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UNIPERFIDA");
        setBackground(new java.awt.Color(0, 0, 0));
        setFocusTraversalPolicyProvider(true);
        setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        setForeground(java.awt.Color.yellow);
        setResizable(false);

        NewGame.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        NewGame.setText("Nuova Partita");
        NewGame.setAutoscrolls(true);
        NewGame.setDefaultCapable(false);
        NewGame.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        NewGame.setMaximumSize(new java.awt.Dimension(777, 29));
        NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        GameTextArea.setEditable(false);
        GameTextArea.setBackground(new java.awt.Color(0, 51, 102));
        GameTextArea.setColumns(20);
        GameTextArea.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        GameTextArea.setForeground(new java.awt.Color(255, 255, 255));
        GameTextArea.setRows(5);
        GameTextArea.setAlignmentX(2.0F);
        GameTextArea.setAlignmentY(2.0F);
        GameTextArea.setMaximumSize(new java.awt.Dimension(179, 548));
        GameTextArea.setMinimumSize(new java.awt.Dimension(179, 548));
        GameTextArea.setOpaque(false);
        jScrollPane2.setViewportView(GameTextArea);

        North.setBackground(new java.awt.Color(255, 255, 255));
        North.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        North.setText("N");
        North.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NorthActionPerformed(evt);
            }
        });

        South.setBackground(new java.awt.Color(255, 255, 255));
        South.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        South.setText("S");
        South.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SouthActionPerformed(evt);
            }
        });

        East.setBackground(new java.awt.Color(255, 255, 255));
        East.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        East.setText("E");
        East.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EastActionPerformed(evt);
            }
        });

        West.setBackground(new java.awt.Color(255, 255, 255));
        West.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        West.setText("O");
        West.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WestActionPerformed(evt);
            }
        });

        Push.setBackground(new java.awt.Color(255, 255, 255));
        Push.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Push.setText("Premi");
        Push.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PushActionPerformed(evt);
            }
        });

        PickUp.setBackground(new java.awt.Color(255, 255, 255));
        PickUp.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        PickUp.setText("Raccogli");
        PickUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickUpActionPerformed(evt);
            }
        });

        Open.setBackground(new java.awt.Color(255, 255, 255));
        Open.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Open.setText("Apri");
        Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        Talk.setBackground(new java.awt.Color(255, 255, 255));
        Talk.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Talk.setText("Parla");
        Talk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TalkActionPerformed(evt);
            }
        });

        Use.setBackground(new java.awt.Color(255, 255, 255));
        Use.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Use.setText("Usa");
        Use.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UseActionPerformed(evt);
            }
        });

        Up.setBackground(new java.awt.Color(255, 255, 255));
        Up.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Up.setText("Sali");
        Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpActionPerformed(evt);
            }
        });

        Down.setBackground(new java.awt.Color(255, 255, 255));
        Down.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Down.setText("Scendi");
        Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownActionPerformed(evt);
            }
        });

        Read.setBackground(new java.awt.Color(255, 255, 255));
        Read.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Read.setText("Leggi");
        Read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReadActionPerformed(evt);
            }
        });

        ProfessorsName.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        ProfessorsName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ProfessorsName.setFocusTraversalPolicyProvider(true);
        ProfessorsName.setHighlighter(null);

        Insert.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        Insert.setText("Inserisci");
        Insert.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertActionPerformed(evt);
            }
        });

        InventoryLabel1.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N

        InventoryLabel2.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N

        InventoryLabel3.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N

        ObjectsLabel1.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        ObjectsLabel1.setForeground(new java.awt.Color(255, 255, 255));
        ObjectsLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel1.setText("1");
        ObjectsLabel1.setToolTipText("");

        ObjectsLabel2.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        ObjectsLabel2.setForeground(new java.awt.Color(255, 255, 255));
        ObjectsLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel2.setText("2");

        ObjectsLabel3.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        ObjectsLabel3.setForeground(new java.awt.Color(255, 255, 255));
        ObjectsLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel3.setText("3");

        InventoryLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        InventoryLabel.setForeground(new java.awt.Color(255, 255, 255));
        InventoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        InventoryLabel.setText("Borsellino");

        UniverseLabel1.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        UniverseLabel1.setForeground(new java.awt.Color(255, 255, 255));
        UniverseLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UniverseLabel1.setText("Universo");

        UniverseLabel2.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        UniverseLabel2.setForeground(new java.awt.Color(255, 255, 255));
        UniverseLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        UniverseLabel2.setText("1");

        NordLabel.setBackground(new java.awt.Color(255, 255, 255));
        NordLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        NordLabel.setForeground(new java.awt.Color(255, 255, 255));
        NordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NordLabel.setText("Nord");

        NowLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        NowLabel.setForeground(new java.awt.Color(255, 255, 255));
        NowLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NowLabel.setText("Now");

        SudLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        SudLabel.setForeground(new java.awt.Color(255, 255, 255));
        SudLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SudLabel.setText("Sud");

        EstLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        EstLabel.setForeground(new java.awt.Color(255, 255, 255));
        EstLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        EstLabel.setText("Est");

        OvestLabel.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        OvestLabel.setForeground(new java.awt.Color(255, 255, 255));
        OvestLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OvestLabel.setText("Ovest");

        Exit.setText("Esci");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        ObjectsLabel4.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N
        ObjectsLabel4.setForeground(new java.awt.Color(255, 255, 255));
        ObjectsLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ObjectsLabel4.setText("4");

        NameRoom.setEditable(false);
        NameRoom.setBackground(new java.awt.Color(0, 51, 102));
        NameRoom.setFont(new java.awt.Font(".AppleSystemUIFont", 1, 14)); // NOI18N
        NameRoom.setForeground(new java.awt.Color(255, 255, 255));
        NameRoom.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Avanti.setText("Avanti");
        Avanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AvantiActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font(".AppleSystemUIFont", 0, 14))); // NOI18N
        jMenuBar1.setToolTipText("");
        jMenuBar1.setFont(new java.awt.Font(".AppleSystemUIFont", 0, 14)); // NOI18N

        GameMenu.setText("Gioco");

        Music.setSelected(true);
        Music.setText("Musica");
        Music.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MusicActionPerformed(evt);
            }
        });
        GameMenu.add(Music);

        Map.setText("Mappa");
        Map.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MapActionPerformed(evt);
            }
        });
        GameMenu.add(Map);

        jMenuBar1.add(GameMenu);

        AboutMenu.setBackground(new java.awt.Color(153, 153, 153));
        AboutMenu.setText("?");

        Help.setText("Aiuto");
        Help.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpActionPerformed(evt);
            }
        });
        AboutMenu.add(Help);

        jMenuBar1.add(AboutMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NameRoom)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfessorsName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Avanti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(NordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SudLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(OvestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(NowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(EstLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(UniverseLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(UniverseLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ObjectsLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(ObjectsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ObjectsLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ObjectsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(Talk, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(Push, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(Read, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(Use, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(West, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(North, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(South, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(East, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(Up, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Down, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(InventoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(InventoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(InventoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(PickUp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Open, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(InventoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 5, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NameRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InventoryLabel)
                    .addComponent(ObjectsLabel1)
                    .addComponent(UniverseLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InventoryLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InventoryLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ObjectsLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(UniverseLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ObjectsLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ObjectsLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(East)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Up)
                            .addComponent(PickUp))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Down)
                            .addComponent(Open))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NordLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NowLabel)
                    .addComponent(OvestLabel)
                    .addComponent(EstLabel))
                .addGap(18, 18, 18)
                .addComponent(SudLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Insert, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfessorsName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Avanti, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EastActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getEast() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && !game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
            move = true;
            noroom = false;
            GameTextArea.append("\nAll'improvviso, a causa del vento, ti capita tra le mani un fogliettino che recita:"
                    + "\nMessaggio per l'Edoardo Pomarico del futuro: aiutami a verbalizzare il voto, non riesco a prenotare il ricevimento con il prof. Basilico perchè l'app MyUni fa schifo!"
                    + "\nChe coincidenza! Nel tuo primo passo sul nuovo universo hai scoperto due cose: il tuo obiettivo e il fatto che il tuo alter ego sia uno svitato!"
                    + "\nSei nel posto giusto al momento giusto, aiutiamo Edoardo lo svitato!"
                    + "\nDi fronte a te c'è una porta aperta, molto probabilmente è rotta."
                    + "\nNel frattempo butti a terra il foglietto, che con il vento vola via, perchè è risaputo che a Bari ci si comporta così.\n");
            game.getRooms().get(27).setPrint(true);
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(23) && game.getRooms().get(25).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
            GameTextArea.append("...\n");
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
        } else if (game.getCurrentRoom() == game.getRooms().get(27)) {
            game.setCurrentRoom(game.getRooms().get(1));
            GameTextArea.append("\n" + "Vuoi già tornare?" + "\n");
            move = true;
            noroom = false;
            // out.println("*** " + getCurrentRoom().getName() + " ***"); // ti dice il nome della stanza
            GameTextArea.append("\nFatto! Hai premuto il bottone\n");
            GameTextArea.append("\n");
            GameTextArea.append("...");
            GameTextArea.append("\nSei in viaggio verso il tuo universo\n");
            GameTextArea.append("...\n");
            GameTextArea.append("\n" + "Il professore ti guarda basito. ”Perchè sei già qui?”" + "\n");
        } else if (game.getCurrentRoom() == game.getRooms().get(8) || game.getCurrentRoom() == game.getRooms().get(15)) {
            GameTextArea.append("\nL'ascensore non è disponibile.");
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_PushActionPerformed

    private void UpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getUp() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getUp());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
        if (game.getCurrentRoom().getDown() != null && game.getCurrentRoom() != game.getRooms().get(11)) {
            game.setCurrentRoom(game.getCurrentRoom().getDown());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(11) && game.getRooms().get(26).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getDown());
            GameTextArea.append("\n" + game.getCurrentRoom().getLook() + "\n");
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
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #1 fallita.\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:\n");
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
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #2 fallita.\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:\n");
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
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #3 fallita.\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:\n");
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
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    game.getRooms().get(25).setCount(++j);
                    break;
                case "0":
                    GameTextArea.append("Autenticazione #4 fallita.\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                default:
                    GameTextArea.append("Il nome non è corretto. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            String number = ProfessorsName.getText();
            switch (number) {
                case "1":
                    exit();
                    GameTextArea.setText("");
                    GameTextArea.append("\n...");
                    GameTextArea.append("\n...");
                    GameTextArea.append("“Benvenuto nella segreteria studenti dell'Università di Bari.“"
                            + "\n..."
                            + "\n..."
                            + "\n“Spiacente, i nostri operatori sono tutti occupati. È inutile riprovare.“");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    endTime = System.currentTimeMillis();
                    seconds = (endTime - startTime) / 1000;
                    GameTextArea.append("\n...");
                    GameTextArea.append("\n...");
                    GameTextArea.append("\nA quanto pare verbalizzare i voti in questo universo è una cosa molto complessa.");
                    GameTextArea.append("\n...");
                    GameTextArea.append("\nOh no, è gia tempo di andare. I sessanta minuti scadono tra pochissimo!");
                    GameTextArea.append("\n...");
                    GameTextArea.append("\nCorri verso la navicella. Ti volti e noti un ragazzo molto simile all'assistente Olivieri che preso dall'ansia esclama “Uff, non riuscirò mai a verbalizzare il mio voto!“."
                            + "\nSorridi. In cuor tuo sai che ha ragione."
                            + "\n..."
                            + "\n..."
                            + "\nIntanto rientri nella tua dimensione."
                            + "\n“Com'è stato? Com'erano le persone? C'erano guerre? Che lingua si parlava? La gente è davvero senza morale? E che mi dici delle...“"
                            + "\n“Non ricordo nulla. So solo che non mi pento di non aver fatto l'università!“");
                    game.getRooms().get(3).setCount(4);
                    break;
                case "0":
                    GameTextArea.append("\nFatto! Telefono lasciato.\n");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
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
                    NewGame.setVisible(true);
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
                    NewGame.setVisible(true);
                    game.getRooms().get(26).setVisible(true);
                    if (game.getRooms().get(3).getCount() != 5) {
                        game.getRooms().get(11).setLook("Ci sono file di banchi. C'è una nuvola di polvere. Intravedi delle scale.");
                        GameTextArea.append("Ma cos'è stato questo rumore? Proveniva dalla aula A.\n");
                    }
                    game.getRooms().get(3).setCount(5);
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
                    NewGame.setVisible(true);
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
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                case "0":
                    GameTextArea.append("\nNessun caffè.");
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
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
                    GameTextArea.append("\nOttima scelta! Ora non ti resta che andare in segreteria per verbalizzare il suo voto.\n");
                    game.getRooms().get(26).getObjects().get(1).setAvailable(true);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
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
                case "a":
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    enterToPlay();
                    break;
                default:
                    GameTextArea.append("\nIl nome utente è stato già utilizzato. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if ("Invio".equals(Insert.getText())) {
            Insert.setText("Inserisci");
            Insert.setVisible(false);
            ProfessorsName.setVisible(false);
            NewGameFunction();
        }
        ProfessorsName.setText("");
        control();
    }//GEN-LAST:event_InsertActionPerformed

    private void UseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UseActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(18)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #1");
                GameTextArea.append("\n#1 Benvenuto nel menu di accettazione.\n"
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- a volte in delle situazioni sono fuori luogo;"
                        + "\n- non lascio le tue labbra;"
                        + "\n- resisto all’acqua;"
                        + "\n- non sono rosso per forza."
                        + "\n\nInserisci la soluzione oppure premi 0 per uscire.\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
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
                NameRoom.setText("Tablet #2");
                GameTextArea.append("\n#2 Benvenuto nel menu di accettazione.\n"
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- sono un aggettivo;"
                        + "\n- sono coraggioso;"
                        + "\n- sono razionale di fronte ad una minaccia;"
                        + "\n- letteralmente qualcosa in più di avido."
                        + "\n\nInserisci la soluzione oppure premi 0 per uscire.\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei già autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(21)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #3");
                GameTextArea.append("\n#3 Benvenuto nel menu di accettazione.\n"
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- notoriamente domestico;"
                        + "\n- i miei video sono divertenti;"
                        + "\n- duro a morire;"
                        + "\n- spesso in compagnia di una volpe."
                        + "\n\nInserisci la soluzione oppure premi 0 per uscire.\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei già autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(22)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #4");
                GameTextArea.append("\n#4 Benvenuto nel menu di accettazione.\n"
                        + "\nRisolvi l’indovinello per autenticarti:"
                        + "\n- a questa età si va in crisi;"
                        + "\n- a napoli sono il pane;"
                        + "\n- arancione per le banche;"
                        + "\n- sono un numero."
                        + "\n\nInserisci la soluzione oppure premi 0 per uscire.\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei già autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            if (game.getCurrentRoom().getObjects().get(1).isAvailable()) {
                NameRoom.setText("Telefono");
                GameTextArea.append("\nDigita il numero che vuoi chiamare."
                        + "\nDigita il numero oppure premi 0 per uscire.\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Per poter utilizzare il telefono devi accettare il voto! Recati dal prof. Basilico");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) {
            if (!game.getInventory().isEmpty()) {
                NameRoom.setText("Macchinetta del caffè");
                GameTextArea.append("\nPremere: ");
                GameTextArea.append("\n1) Caffè");
                GameTextArea.append("\n2) Caffè lungo");
                GameTextArea.append("\n3) Caffè macchiato");
                GameTextArea.append("\n4) Caffè al cioccolato");
                GameTextArea.append("\n0) Esci");
                GameTextArea.append("\nInserisci il numero desiderato:\n");
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nNon hai monete! Procurati una moneta\n");
                move = true;
                noroom = false;
            }
        }
        control();
    }//GEN-LAST:event_UseActionPerformed

    private void PickUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickUpActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(24)) {
            GameTextArea.append("\nFatto, hai preso la moneta!\n");
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
            GameTextArea.append("\nFatto, hai preso la moneta!\n");
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
            GameTextArea.append("\nFatto, hai preso la moneta!\n");
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
            GameTextArea.append("\nNon si ruba!\n");
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
            Open.setEnabled(false);
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
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            }
        } else {
            GameTextArea.append("\nNon disturbare!");
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_TalkActionPerformed

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
        // TODO add your handling code here:
        GameTextArea.setText("");
        GameTextArea.append("Inserisci il tuo nome.\n");
        Insert.setVisible(true);
        ProfessorsName.setVisible(true);
        NewGame.setVisible(false);
    }//GEN-LAST:event_NewGameActionPerformed

    private void MusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MusicActionPerformed
        // TODO add your handling code here:
        music = !music;
        if (!music) {
            clip.stop();
        } else {
            startMusic();
        }
    }//GEN-LAST:event_MusicActionPerformed

    private void MapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapActionPerformed
        // TODO add your handling code here:
        map = !map;
        if (!map) {
            NordLabel.setVisible(false);
            NowLabel.setVisible(false);
            SudLabel.setVisible(false);
            EstLabel.setVisible(false);
            OvestLabel.setVisible(false);
        } else {
            NordLabel.setVisible(true);
            NowLabel.setVisible(true);
            SudLabel.setVisible(true);
            EstLabel.setVisible(true);
            OvestLabel.setVisible(true);
        }
    }//GEN-LAST:event_MapActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    private void HelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpActionPerformed
        // TODO add your handling code here:
        help = !help;
        if (help) {
            Help.setText("Nessun aiuto");
            Help.setEnabled(false);
        }
    }//GEN-LAST:event_HelpActionPerformed

    private void AvantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AvantiActionPerformed
        // TODO add your handling code here:
        NameRoom.setText("Complimenti!");
        GameTextArea.setText("");
        GameTextArea.append("Programma eseguito in " + seconds + " secondi\n");
        Avanti.setVisible(false);
        Exit.setVisible(true);
    }//GEN-LAST:event_AvantiActionPerformed

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
            ObjectsLabel4.setVisible(false);
            InventoryLabel.setVisible(false);
            InventoryLabel1.setVisible(false);
            InventoryLabel2.setVisible(false);
            InventoryLabel3.setVisible(false);
            UniverseLabel1.setVisible(false);
            UniverseLabel2.setVisible(false);
            NordLabel.setVisible(false);
            NowLabel.setVisible(false);
            SudLabel.setVisible(false);
            EstLabel.setVisible(false);
            OvestLabel.setVisible(false);
            GameMenu.setEnabled(true);
            Map.setEnabled(false);
            AboutMenu.setEnabled(true);
            Help.setEnabled(false);
            Exit.setVisible(false);
            Avanti.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        NameRoom.setText("");
        NameRoom.setText("Benvenuto!");
        GameTextArea.setText("");
        GameTextArea.append("\n"
                + "\n==========================================================================="
                + "\n---------------------------------------* Uniperfida v. 1.1 - 2020-2021 *---------------------------------------"
                + "\n===========================================================================");

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
            java.util.logging.Logger.getLogger(EngineFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EngineFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JMenu AboutMenu;
    private javax.swing.JButton Avanti;
    private javax.swing.JButton Down;
    private javax.swing.JButton East;
    private javax.swing.JLabel EstLabel;
    private javax.swing.JButton Exit;
    private javax.swing.JMenu GameMenu;
    private javax.swing.JTextArea GameTextArea;
    private javax.swing.JMenuItem Help;
    private javax.swing.JButton Insert;
    private javax.swing.JLabel InventoryLabel;
    private javax.swing.JLabel InventoryLabel1;
    private javax.swing.JLabel InventoryLabel2;
    private javax.swing.JLabel InventoryLabel3;
    private javax.swing.JCheckBoxMenuItem Map;
    private javax.swing.JCheckBoxMenuItem Music;
    private javax.swing.JTextField NameRoom;
    private javax.swing.JButton NewGame;
    private javax.swing.JLabel NordLabel;
    private javax.swing.JButton North;
    private javax.swing.JLabel NowLabel;
    private javax.swing.JLabel ObjectsLabel1;
    private javax.swing.JLabel ObjectsLabel2;
    private javax.swing.JLabel ObjectsLabel3;
    private javax.swing.JLabel ObjectsLabel4;
    private javax.swing.JButton Open;
    private javax.swing.JLabel OvestLabel;
    private javax.swing.JButton PickUp;
    private javax.swing.JTextField ProfessorsName;
    private javax.swing.JButton Push;
    private javax.swing.JButton Read;
    private javax.swing.JButton South;
    private javax.swing.JLabel SudLabel;
    private javax.swing.JButton Talk;
    private javax.swing.JLabel UniverseLabel1;
    private javax.swing.JLabel UniverseLabel2;
    private javax.swing.JButton Up;
    private javax.swing.JButton Use;
    private javax.swing.JButton West;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
