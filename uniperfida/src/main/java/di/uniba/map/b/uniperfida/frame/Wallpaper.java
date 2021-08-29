/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.map.b.uniperfida.frame;

/**
 *
 * @author antoniosilvestre
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Wallpaper extends JPanel {

  private final Image img;

  public Wallpaper() {
    img = Toolkit.getDefaultToolkit().createImage("resources/wallpaper/sfondo3.png");
    loadImage(img);
  }

  private void loadImage(Image img) {
    try {
      MediaTracker track = new MediaTracker(this);
      track.addImage(img, 0);
      track.waitForID(0);
    } catch (InterruptedException e) {
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    setOpaque(false);
    g.drawImage(img, 0, 0, null);
    super.paintComponent(g);
  }
}