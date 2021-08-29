/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.frame;

import di.uniba.map.b.uniperfida.Engine;
import static di.uniba.map.b.uniperfida.frame.Strings.*;
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
import javax.swing.Timer;

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
    private boolean fast = false;
    private StringBuilder s = new StringBuilder();
    private int counter = 0;
    private Timer tm;

    /**
     * Creates new form EngineFrame
     */
    public EngineFrame() {
        initComponents();
        playMusic();
        addWallpaper();
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                enableElements(false);
                counter++;
                if (counter >= s.length()) {
                    counter = 0;
                    tm.stop();
                    enableElements(true);
                    control();
                } else {
                    StringBuilder supp = new StringBuilder();
                    supp.append(s.toString().charAt(counter));
                    GameTextArea.append(supp.toString());
                    supp = new StringBuilder();
                }
            }
        };
        int delay = 50;
        tm = new Timer(delay, taskPerformer);
        tm.start();
        init();

    }

    private void addWallpaper() {
        Wallpaper wallpaper = new Wallpaper();
        getContentPane().add(wallpaper);
        wallpaper.setSize(559, 499);
        wallpaper.setVisible(true);
    }

    private void playMusic() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("resources/music/Payday 2 Official Soundtrack - AndNowWeWait.wav"));
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
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(noWay.toString());
            } else {
                s.append(noWay);
            }
            noroom = false;
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
            GameTextArea.append("Le monete sono terminate e non puoi andare in segreteria.\nHai totalizzato 0 punti.\nIl tuo punteggio non verra' salvato nel database.");
        } else if (game.getRooms().get(3).getCount() == 4) {
            exit();
            game.getRooms().get(3).setCount(10);
        } else if (game.getRooms().get(3).getCount() == 10 || game.getRooms().get(3).getCount() == 12) {
            InventoryLabel2.setIcon(null);
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
        ImageIcon image = new ImageIcon("resources/images/coin.png");
        ImageIcon image2 = new ImageIcon("resources/images/no.png");
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
            NordLabel.setText("A nord non c'e' nulla");
        } else {
            NordLabel.setText(game.getCurrentRoom().getNorth().getName());
        }
        if (game.getCurrentRoom().getSouth() == null) {
            SudLabel.setText("A sud non c'e' nulla");
        } else {
            SudLabel.setText(game.getCurrentRoom().getSouth().getName());
        }
        if (game.getCurrentRoom().getWest() == null) {
            OvestLabel.setText("A ovest non c'e' nulla");
        } else {
            OvestLabel.setText(game.getCurrentRoom().getWest().getName());
        }
        if (game.getCurrentRoom().getEast() == null) {
            EstLabel.setText("A est non c'e' nulla");
        } else {
            EstLabel.setText(game.getCurrentRoom().getEast().getName());
        }
    }

    public void enterToPlay() {
        GameTextArea.append("\nPerfetto!\nPremi invio per iniziare.\n");
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
        Map.setEnabled(false);
        Speed1.setEnabled(false);
        Speed10.setEnabled(false);
        NordLabel.setVisible(false);
        NowLabel.setVisible(false);
        SudLabel.setVisible(false);
        EstLabel.setVisible(false);
        OvestLabel.setVisible(false);
        Avanti.setVisible(true);
    }

    private void enableElements(boolean enable) {
        PickUp.setEnabled(enable);
        North.setEnabled(enable);
        South.setEnabled(enable);
        East.setEnabled(enable);
        West.setEnabled(enable);
        Push.setEnabled(enable);
        Open.setEnabled(enable);
        Talk.setEnabled(enable);
        Up.setEnabled(enable);
        Use.setEnabled(enable);
        Down.setEnabled(enable);
        Read.setEnabled(enable);
        Insert.setEnabled(enable);
        ProfessorsName.setEnabled(enable);
        Speed1.setEnabled(enable);
        Speed10.setEnabled(enable);
        Exit.setEnabled(enable);
        Avanti.setEnabled(enable);
    }

    public void NewGameFunction() {
        StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
        tm.start();
        GameTextArea.setText("");
        s = new StringBuilder("\n");
        if (fast) {
            GameTextArea.append(incipit.toString());
            GameTextArea.append(getLook.toString());
        } else {
            s.append(incipit);
            s.append(getLook);
        }
        UniverseLabel1.setVisible(true);
        UniverseLabel2.setVisible(true);
        UniverseLabel2.setText(game.getCurrentRoom().getuniverse());
        NameRoom.setVisible(true);
        NameRoom.setText(game.getCurrentRoom().getDescription());
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
        NewGame.setText("UNIPERFIDA v.1.1 - 2020-2021");
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
        Speed1 = new javax.swing.JCheckBoxMenuItem();
        Speed10 = new javax.swing.JCheckBoxMenuItem();
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

        Speed1.setSelected(true);
        Speed1.setText("Velocita' 1x");
        Speed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Speed1ActionPerformed(evt);
            }
        });
        GameMenu.add(Speed1);

        Speed10.setText("Velocita' 10x");
        Speed10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Speed10ActionPerformed(evt);
            }
        });
        GameMenu.add(Speed10);

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
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ProfessorsName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Insert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Avanti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(5, 5, 5))))
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
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
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
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && !game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
            move = true;
            noroom = false;
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(edoPaper.toString());
            } else {
                s.append(edoPaper);
            }
            game.getRooms().get(27).setPrint(true);
        } else if (game.getCurrentRoom() == game.getRooms().get(27) && game.getRooms().get(27).isPrint()) {
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
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
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
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
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(23) && game.getRooms().get(25).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(23) && !game.getRooms().get(25).isVisible()) {
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(noEnter.toString());
            } else {
                s.append(noEnter);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(25) && !game.getRooms().get(28).isVisible()) {
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(noEnter2.toString());
            } else {
                s.append(noEnter2);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(25) && game.getRooms().get(28).isVisible()) {
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
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
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(toNewUniverse.toString());
                GameTextArea.append(getLook.toString());
            } else {
                s.append(toNewUniverse);
                s.append(getLook);
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(27)) {
            game.setCurrentRoom(game.getRooms().get(1));
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(toNewUniverse.toString());
                GameTextArea.append(getLook.toString());
            } else {
                s.append(toNewUniverse);
                s.append(getLook);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(8) || game.getCurrentRoom() == game.getRooms().get(15)) {
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(noElevator.toString());
            } else {
                s.append(noElevator);
            }
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_PushActionPerformed

    private void UpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom().getUp() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getUp());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
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
        if (game.getCurrentRoom().getDown() != null) {
            game.setCurrentRoom(game.getCurrentRoom().getDown());
            StringBuilder getLook = new StringBuilder("\n" + game.getCurrentRoom().getLook() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(getLook.toString());
            } else {
                s.append(getLook);
            }
            move = true;
            noroom = false;
        } else {
            noroom = true;
            move = false;
        }
        control();
    }//GEN-LAST:event_DownActionPerformed

    private void ReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReadActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(2)) { //cartello in cortile
            StringBuilder getDescription = new StringBuilder("\n" + game.getCurrentRoom().getObjects().get(0).getDescription() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(signWrite.toString());
                GameTextArea.append(getDescription.toString());
            } else {
                s.append(signWrite);
                s.append(getDescription);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) { //bacheca in hall
            StringBuilder getDescription = new StringBuilder("\n" + game.getCurrentRoom().getObjects().get(0).getDescription() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(boardWrite.toString());
                GameTextArea.append(getDescription.toString());
            } else {
                s.append(boardWrite);
                s.append(getDescription);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(7)) {
            StringBuilder getDescription = new StringBuilder("\n" + game.getCurrentRoom().getObjects().get(0).getDescription() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(boardWrite.toString());
                GameTextArea.append(getDescription.toString());
            } else {
                s.append(boardWrite);
                s.append(getDescription);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(10)) {
            StringBuilder getDescription = new StringBuilder("\n\n" + game.getCurrentRoom().getObjects().get(0).getDescription() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(boardWrite.toString());
                GameTextArea.append(getDescription.toString());
            } else {
                s.append(boardWrite);
                s.append(getDescription);
            }
            move = true;
            noroom = false;
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            StringBuilder getDescription = new StringBuilder("\n" + game.getCurrentRoom().getObjects().get(0).getDescription() + "\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(paperWrite.toString());
                GameTextArea.append(getDescription.toString());
            } else {
                s.append(paperWrite);
                s.append(getDescription);
            }
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
                case "a":
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
                    GameTextArea.append("Il nome non e' corretto. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(19)) {
            String impavido = ProfessorsName.getText();
            impavido = impavido.toLowerCase();
            switch (impavido) {
                case "a":
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
                    GameTextArea.append("Il nome non e' corretto. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(21)) {
            String gatto = ProfessorsName.getText();
            gatto = gatto.toLowerCase();
            switch (gatto) {
                case "a":
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
                    GameTextArea.append("Il nome non e' corretto. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(22)) {
            String cinquanta = ProfessorsName.getText();
            cinquanta = cinquanta.toLowerCase();
            switch (cinquanta) {
                case "a":
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
                    GameTextArea.append("Il nome non e' corretto. \nInserisci di nuovo il nome:\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            String number = ProfessorsName.getText();
            switch (number) {
                case "1":
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(endPrint.toString());
                    } else {
                        s.append(endPrint);
                    }
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    endTime = System.currentTimeMillis();
                    seconds = (endTime - startTime) / 1000;
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
                    GameTextArea.append("Inserisci un valore valido.\n");
                    break;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(3)) {
            String chooseCoffee = ProfessorsName.getText();
            switch (chooseCoffee) {
                case "1":
                    game.getInventory().remove(game.getInventory().get(0));
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(coffee1.toString());
                    } else {
                        s.append(coffee1);
                    }
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                case "2":
                    game.getInventory().remove(game.getInventory().get(0));
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(coffee2.toString());
                    } else {
                        s.append(coffee2);
                    }
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    game.getRooms().get(26).setVisible(true);
                    if (game.getRooms().get(3).getCount() != 5) {
                        game.getRooms().get(11).setLook("Ci sono file di banchi. C'e' una nuvola di polvere. Intravedi delle scale.");
                        tm.start();
                        s = new StringBuilder("\n");
                        if (fast) {
                            GameTextArea.append(whatHappened.toString());
                        } else {
                            s.append(whatHappened);
                        }
                    }
                    game.getRooms().get(3).setCount(5);
                    move = true;
                    noroom = false;
                    break;
                case "3":
                    game.getInventory().remove(game.getInventory().get(0));
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(coffee3.toString());
                    } else {
                        s.append(coffee3);
                    }
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                case "4":
                    game.getInventory().remove(game.getInventory().get(0));
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(coffee4.toString());
                    } else {
                        s.append(coffee4);
                    }
                    game.getRooms().get(3).setCount(++a);
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    NewGame.setVisible(true);
                    move = true;
                    noroom = false;
                    break;
                case "0":
                    GameTextArea.append("\nNessun caffe'.");
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
                    tm.start();
                    s = new StringBuilder("\n");
                    if (fast) {
                        GameTextArea.append(okPrint.toString());
                    } else {
                        s.append(okPrint);
                    }
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
            if ("a".equals(antonio)) {
                    Insert.setVisible(false);
                    ProfessorsName.setVisible(false);
                    enterToPlay();
            }else {
                    GameTextArea.append("\nIl nome utente e' stato gia' utilizzato. \nInserisci di nuovo il nome:\n");
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
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(printRossetto.toString());
                } else {
                    s.append(printRossetto);
                }
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("Qui ti sei gia' autenticato!");
                GameTextArea.append("\n");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(19)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #2");
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(printImpavido.toString());
                } else {
                    s.append(printImpavido);
                }
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei gia' autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(21)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #3");
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(printGatto.toString());
                } else {
                    s.append(printGatto);
                }
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei gia' autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(22)) {
            if (!game.getCurrentRoom().getObjects().get(0).isUsed()) {
                NameRoom.setText("Tablet #4");
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(printCinquanta.toString());
                } else {
                    s.append(printCinquanta);
                }
                Insert.setVisible(true);
                ProfessorsName.setVisible(true);
                NewGame.setVisible(false);
                move = false;
                noroom = false;
            } else {
                GameTextArea.append("\nQui ti sei gia' autenticato!");
                move = true;
                noroom = false;
            }
        } else if (game.getCurrentRoom() == game.getRooms().get(26)) {
            if (game.getCurrentRoom().getObjects().get(1).isAvailable()) {
                NameRoom.setText("Telefono");
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(secretaryPhone.toString());
                } else {
                    s.append(secretaryPhone);
                }
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
                NameRoom.setText("Macchinetta del caffe'");
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(coffeeMenu.toString());
                } else {
                    s.append(coffeeMenu);
                }
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
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(okCoin.toString());
            } else {
                s.append(okCoin);
            }
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
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(okCoin.toString());
            } else {
                s.append(okCoin);
            }
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
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(okCoin.toString());
            } else {
                s.append(okCoin);
            }
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
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(noCoin.toString());
            } else {
                s.append(noCoin);
            }
            move = true;
            noroom = false;
        }
        control();
    }//GEN-LAST:event_PickUpActionPerformed

    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenActionPerformed
        // TODO add your handling code here:
        if (game.getCurrentRoom() == game.getRooms().get(25)) {
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(okDoor.toString());
            } else {
                s.append(okDoor);
            }
            move = true;
            noroom = false;
            game.getCurrentRoom().setDescription("Ti trovi nella sala d'attesa del prof. Basilico. La porta e' aperta.");
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
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(collaboratorTalk.toString());
                } else {
                    s.append(collaboratorTalk);
                }
                game.getCurrentRoom().getPeople().get(0).setTalkable(false);
                move = true;
                noroom = false;
            } else if (game.getCurrentRoom() == game.getRooms().get(28)) {
                tm.start();
                s = new StringBuilder("\n");
                if (fast) {
                    GameTextArea.append(professorTalk.toString());
                } else {
                    s.append(professorTalk);
                }
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
        Avanti.setVisible(false);
        Exit.setVisible(true);
        if (game.getRooms().get(3).getCount() == 3) {
            GameTextArea.append("\n\nPremi il tasto esci per uscire.");
        } else if (game.getRooms().get(3).getCount() == 10) {
            NameRoom.setText("Complimenti!");
            GameTextArea.setText("");
            StringBuilder score = new StringBuilder("Il gioco e' terminato. Il punteggio e' : " + (int) seconds + "\nTroverai il tuo nome nella voce -Ranking-\nGrazie per aver giocato!\n");
            tm.start();
            s = new StringBuilder("\n");
            if (fast) {
                GameTextArea.append(score.toString());
            } else {
                s.append(score);
            }
            game.getRooms().get(3).setCount(12);
            Exit.setVisible(false);
            Avanti.setVisible(true);
        } else if (game.getRooms().get(3).getCount() == 12) {
            GameTextArea.append("\nPremi il tasto esci per uscire.");
        }

    }//GEN-LAST:event_AvantiActionPerformed

    private void Speed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Speed1ActionPerformed
        // TODO add your handling code here:                                       
        fast = false;
        Speed1.setSelected(true);
        Speed10.setSelected(false);
    }//GEN-LAST:event_Speed1ActionPerformed

    private void Speed10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Speed10ActionPerformed
        // TODO add your handling code here:
        fast = true;
        Speed10.setSelected(true);
        Speed1.setSelected(false);
    }//GEN-LAST:event_Speed10ActionPerformed

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
        GameTextArea.append("\nU N I P E R F I D A\n");
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
    private javax.swing.JCheckBoxMenuItem Speed1;
    private javax.swing.JCheckBoxMenuItem Speed10;
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
