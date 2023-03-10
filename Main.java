/**
* Simulation of the wave propagation
* Graphical user interface
*
* @author  Cyber-boolean
* @since   02-2022
*/

package MATLAB;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import javax.swing.*;

public class Main extends Frame implements WindowListener,ActionListener {
  static Label nazwalabel , labelwspolrz, labelx, labely ,labelfrequency, labelrodzaj;
  static Button b1;
  static Button b2;
  static JTextField SourceX;
  static JTextField SourceY;
  static JList List;
  static JSlider frequencySlider;
  static Panel panel;
  static JRadioButton Sin, Impulse,CS, MHz, GHz;
  static Font fontLucida = new Font("LUCIDA_SANS", Font.BOLD, 13);
  Font fontMicra = new Font("Micra", Font.BOLD, 25);

  public static void main(String[] args) {
    Main myWindow = new Main("GUI");
    myWindow.setSize(240, 530);
    myWindow.setVisible(true);
    myWindow.setBackground(Color.BLACK);
  }

  public Main(String title) {
    super(title);
    //setLayout(new FlowLayout());
    addWindowListener(this);
    panel = new Panel();
    panel.setBackground(Color.BLACK);
    panel.setLayout(null);

    nazwalabel = new Label("Nazwa? Czy coś innego?");
    nazwalabel.setBackground(Color.BLACK);
    nazwalabel.setFont(new Font("HelveticaNeue-Light", Font.BOLD, 17));
    nazwalabel.setForeground(Color.GREEN);
    nazwalabel.setBounds(5, 5, 210, 30);

    labelwspolrz = new Label("Współrzędne osi:");
    labelwspolrz.setBackground(Color.BLACK);
    labelwspolrz.setFont(new Font("HelveticaNeue-Light", Font.HANGING_BASELINE, 15));
    labelwspolrz.setForeground(Color.RED);
    labelwspolrz.setBounds(15, 45, 210, 30);

    labelx = new Label("X:");
    labelx.setBackground(Color.BLACK);
    labelx.setForeground(Color.GREEN);
    labelx.setBounds(15, 80, 20, 30);
    labelx.setFont(fontLucida);

    SourceX = new JTextField(10);
    SourceX.setBackground(Color.BLACK);
    SourceX.setForeground(Color.GRAY);
    SourceX.setBounds(45, 80, 150, 25);

    labely = new Label("Y:");
    labely.setBackground(Color.BLACK);
    labely.setForeground(Color.GREEN);
    labely.setBounds(15, 120, 20, 30);
    labely.setFont(fontLucida);

    SourceY = new JTextField(10);
    SourceY.setBackground(Color.BLACK);
    SourceY.setForeground(Color.GRAY);
    SourceY.setBounds(45, 120, 150, 25);

    labelfrequency = new Label("Częstotliwość:");
    labelfrequency.setBackground(Color.BLACK);
    labelfrequency.setFont(new Font("HelveticaNeue-Light", Font.HANGING_BASELINE, 15));
    labelfrequency.setForeground(Color.RED);
    labelfrequency.setBounds(15, 160, 210, 30);

    ButtonGroup groupczęstotliw = new ButtonGroup();

    MHz = new JRadioButton("MHz", true);
    MHz.setBackground(Color.BLACK);
    MHz.setForeground(Color.GREEN);
    MHz.setFont(fontLucida);
    MHz.setBounds(15, 190, 210, 30);

    GHz = new JRadioButton("GHz", false);
    GHz.setBackground(Color.BLACK);
    GHz.setForeground(Color.GREEN);
    GHz.setFont(fontLucida);
    GHz.setBounds(15, 220, 210, 30);

    groupczęstotliw.add(MHz);
    groupczęstotliw.add(GHz);

    final int FPS_MIN = 0;
    final int FPS_MAX = 100;
    final int FPS_INIT = 10;

    frequencySlider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);
    frequencySlider.setBounds(5, 260, 200, 40);
    frequencySlider.setBackground(Color.BLACK);
    frequencySlider.setMajorTickSpacing(10);
    frequencySlider.setMinorTickSpacing(10);
    frequencySlider.setPaintLabels(true);
    frequencySlider.setPaintTicks(true);

    labelrodzaj = new Label("Rodzaj:");
    labelrodzaj.setBackground(Color.BLACK);
    labelrodzaj.setFont(new Font("HelveticaNeue-Light", Font.HANGING_BASELINE, 15));
    labelrodzaj.setForeground(Color.RED);
    labelrodzaj.setBounds(15, 310, 210, 30);

    ButtonGroup grouprodzaj = new ButtonGroup();

    Sin = new JRadioButton("Sinus", true);
    Sin.setBackground(Color.BLACK);
    Sin.setForeground(Color.GREEN);
    Sin.setFont(fontLucida);
    Sin.setBounds(15, 340, 210, 30);

    Impulse = new JRadioButton("Impulse ", false);
    Impulse.setBackground(Color.BLACK);
    Impulse.setForeground(Color.GREEN);
    Impulse.setFont(fontLucida);
    Impulse.setBounds(15, 370, 210, 30);

    CS = new JRadioButton("CS ", false);
    CS.setBackground(Color.BLACK);
    CS.setForeground(Color.GREEN);
    CS.setFont(fontLucida);
    CS.setBounds(15, 400, 210, 30);

    grouprodzaj.add(Sin);
    grouprodzaj.add(Impulse);
    grouprodzaj.add(CS);

    b1 = new Button("Start");
    b1.setBounds(65, 445, 90, 40);
    b1.setBackground(Color.cyan);
    b1.setBackground(Color.BLACK);
    b1.setForeground(Color.WHITE);
    b1.setFont(fontLucida);

    panel.add(nazwalabel);
    panel.add(labelwspolrz);
    panel.add(labelx);
    panel.add(SourceX);
    panel.add(labely);
    panel.add(SourceY);
    panel.add(labelfrequency);
    panel.add(MHz);
    panel.add(GHz);
    panel.add(frequencySlider);
    panel.add(labelrodzaj);
    panel.add(Sin);
    panel.add(Impulse);
    panel.add(CS);
    panel.add(b1);

    add(panel);
    b1.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    Thread thread = new Thread("Wave Simulation") {
      public void run() {
        try {
          int ValueX = Integer.parseInt(SourceX.getText());
          int ValueY = Integer.parseInt(SourceY.getText());

          if (Sin.isSelected()) {
            if ((ValueX > 5) && (ValueX < 396) && (ValueY > 5) && (ValueY < 396)) {
              new WaveSimulation(400 - ValueX, 400 - ValueY, 0);
            }
            else {
              System.out.println("Value too low/high");
            }
          }
          else if (Impulse.isSelected()) {
            if ((ValueX > 5) && (ValueX < 396) && (ValueY > 5) && (ValueY < 396)) {
              new WaveSimulation(400 - ValueX, 400 - ValueY, 1);
            }
            else {
              System.out.println("Value too low/high");
            }
          }
          else if(CS.isSelected()) {
            if ((ValueX > 5) && (ValueX < 396) && (ValueY > 5) && (ValueY < 396)) {
              new WaveSimulation(400 - ValueX, 400 - ValueY, 2);
            }
            else {
              System.out.println("Value too low/high");
            }
          }
        } catch (NumberFormatException e) {
          System.out.println("Input String cannot be parsed to Integer.");
        }
      }
    };
    thread.start();
  }

  public void windowClosing(WindowEvent e) {
    dispose();
    System.exit(0);
  }

  public void windowOpened(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
}
