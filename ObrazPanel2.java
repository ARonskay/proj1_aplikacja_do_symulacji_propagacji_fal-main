/**
* Simulation of the wave propagation
* Graphical user interface
*
* @author  Cyber-boolean
* @since   02-2022
*/

package MATLAB;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JPanel;

public class ObrazPanel2 extends JPanel {
  JLabel view;
  BufferedImage surface;
  Graphics g;
  double[][] data;

  public ObrazPanel2(double[][] mat) {
    surface = new BufferedImage(mat.length, mat.length, BufferedImage.TYPE_INT_RGB);
    data = new double[mat.length][mat.length];
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat.length; j++) {
        data[i][j] = mat[i][j];
      }
    }
    for (int x = 0; x < mat.length; x++) {
      for (int y = 0; y < mat.length; y++) {
        Color color = Color.getHSBColor((float) (data[x][y] + 1) / 2, (float) 0.8, 1);
        surface.setRGB(x, y, color.getRGB());
      }
    }
    view = new JLabel(new ImageIcon(surface));
  }

  public void Change(double[][] mat) {
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat.length; j++) {
        data[i][j] = mat[i][j];
      }
    }
    for (int x = 0; x < mat.length; x++) {
      for (int y = 0; y < mat.length; y++) {
        Color color = Color.getHSBColor((float) (data[x][y] + 1) / 2, (float) 0.8, 1);
        surface.setRGB(x, y, color.getRGB());
      }
    }
    view.repaint();
  }
}
