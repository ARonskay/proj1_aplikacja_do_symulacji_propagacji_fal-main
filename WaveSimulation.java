/**
* Simulation of the wave propagation
* Definition of the propagation
*
* @author  Cyber-boolean
* @since   02-2022
*/

package MATLAB;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.JFrame;

class WaveSimulation {
  int STOP = 1;

  public WaveSimulation(int xSource, int ySource, int T) {
    int n = 1;
    int n1, n2, n11, n22;

    final int X_DIMENSION = 400; // xdim
    final int Y_DIMENSION = 400;

    double stabilityFactor = 1.0 / Math.pow(2, 0.5); // S

    double permittivity = (1.0 / (36.0 * Math.PI)) * 1E-9; // epsilon0
    double permeability = 4 * Math.PI * 1E-7; // mu0
    double speedOfLight = 3E8; // c

    double spatialGridStep = 1E-6; // delta
    double temporalGridStep = stabilityFactor * spatialGridStep / speedOfLight; // deltat

    double[][] Ez = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0.0);
    double[][] Hy = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0.0);
    double[][] Hx = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0.0);

    double[][] epsilon = Matrix2.Create(X_DIMENSION, Y_DIMENSION, permittivity);
    double[][] mu = Matrix2.Create(X_DIMENSION, Y_DIMENSION, permeability);

    double[][] sigma = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 4E-4);
    double[][] sigmaStar = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 4E-4);

    double sine = 0;
    double impulse = 0;

    String name = "Wave Simulation";
    String dTypFali = "Sinusoidalna";

    if (T == 0) {
      sine = 1.0D;
      impulse = 0.0D;
      name = "Sine Simulation";
      dTypFali = "Sinusoidalna";
    } else if (T == 1) {
      impulse = 1.0D;
      sine = 0.0D;
      name = "Impulse Simulation";
      dTypFali = "Impulsowa";
    } else if (T == 2) {
      impulse = 0.0D;
      sine = 0.0D;
      name = "CS Simulation";
      dTypFali = "CS";
    }

    double frequency = Math.pow(10, 13); // frequency

    double[][] A = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0);
    A = Matrix2.WiseD(Matrix2.WiseS(mu, Matrix2.ScalarM(sigmaStar, 0.5 * temporalGridStep)), Matrix2.WiseA(mu, Matrix2.ScalarM(sigmaStar, 0.5 * temporalGridStep)));

    double[][] B = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0);
    B = Matrix2.SWRD(temporalGridStep / spatialGridStep, Matrix2.WiseA(mu, Matrix2.ScalarM(sigmaStar, 0.5 * temporalGridStep)));

    double[][] C = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0);
    C = Matrix2.WiseD(Matrix2.WiseS(epsilon, Matrix2.ScalarM(sigma, 0.5 * temporalGridStep)), Matrix2.WiseA(epsilon, Matrix2.ScalarM(sigma, 0.5 * temporalGridStep)));

    double[][] D = Matrix2.Create(X_DIMENSION, Y_DIMENSION, 0);
    D = Matrix2.SWRD(temporalGridStep / spatialGridStep, Matrix2.WiseA(epsilon, Matrix2.ScalarM(sigma, 0.5 * temporalGridStep)));

    double absorb1 = 1.0D; // p0
    double absorb2 = -0.5D; // p2

    double c0 = (speedOfLight / (2 * stabilityFactor)) * (1 - (absorb1 / stabilityFactor)); // c0
    double c1 = -(speedOfLight / (2 * stabilityFactor)) * (1 + (absorb1 / stabilityFactor)); // c1
    double c2 = (speedOfLight / (Math.pow(stabilityFactor, 2)) * (absorb1 + (absorb2 * Math.pow(stabilityFactor, 2)))); // c2
    double c3 = -(absorb2 * speedOfLight) / 2; //c3

    double c0Efffor = -(c0 / c1); // c0efffor
    double c2Efffor = -(c2 / c1); // c2efffor
    double c3Efffor = -(c3 / c1); // c3efffor

    c0 = (speedOfLight / (2 * stabilityFactor)) * (1 + (absorb1 / stabilityFactor)); // c0
    c1 = -(speedOfLight / (2 * stabilityFactor)) * (1 - (absorb1 / stabilityFactor)); // c1
    c2 = -(speedOfLight / (Math.pow(stabilityFactor, 2)) * (absorb1 + (absorb2 * Math.pow(stabilityFactor, 2)))); // c2
    c3 = (absorb2 * speedOfLight) / 2; //c3

    double c1Effrev = -(c1 / c0); // c0effrev
    double c2Effrev = -(c2 / c0); // c2effrev
    double c3Effrev = -(c3 / c0); // c3effrev

    double[][] prev_xfor = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_x_minus_1for = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_yfor = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_y_minus_1for = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_xrev = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_x_minus_1rev = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_yrev = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_y_minus_1rev = Matrix2.Create(X_DIMENSION, 1, 0.0);

    int g = 0;

    double[][] Pomo;
    double[][] Pomo1;
    double[][] Pomo2;
    double[][] Pomo3;
    double[][] Pom1;
    double[][] Pom2;
    double[][] Pom3;
    double[][] Pom4;

    double[][] prev_prev_xfor = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_prev_x_minus_1for = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_prev_xrev = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_prev_x_minus_1rev = Matrix2.Create(1, Y_DIMENSION, 0.0);
    double[][] prev_prev_yrev = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_prev_y_minus_1rev = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_prev_yfor = Matrix2.Create(X_DIMENSION, 1, 0.0);
    double[][] prev_prev_y_minus_1for = Matrix2.Create(X_DIMENSION, 1, 0.0);

    double[][] help = Matrix2.Create(X_DIMENSION, 1, 0);

    ObrazPanel2 canvas = new ObrazPanel2(Ez);
    String MHZorGHZ = "MHz";
    String jedn_dlug = "m";
    double mnoznik_f = 10^6;

    if (Main.MHz.isSelected() == true) {
      MHZorGHZ = "MHz";
      jedn_dlug = "m";
      mnoznik_f = 1E6;
    } else {
      MHZorGHZ = "GHz";
      jedn_dlug = "mm";
      mnoznik_f = 1E9;
    }

    JFrame frame = new JFrame(name);
    int vertexes = 0;
    vertexes = 10;
    int canvasSize = vertexes * vertexes;
    double f = 10;
    double d = 30;

    f = Main.frequencySlider.getValue();
    d = (3 * 1E8)/(f * 1E6);

    frame.setSize(canvasSize, canvasSize);
    frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        STOP = 0;
      }
    });

    String[][] data = {{"Rodzaj fali: ", "X: ", "Y: ", "Czestotliwosc: ", "Dlugosc fali: "},{ dTypFali, Main.SourceX.getText(), Main.SourceY.getText(),Integer.toString(Main.frequencySlider.getValue()) + MHZorGHZ, String.format("%.2f", d) + jedn_dlug }};
    String[][] data_nonsine = {{"Rodzaj fali: ", "X: ", "Y: ", "Czestotliwosc: ", "Dlugosc fali: "},{ dTypFali, Main.SourceX.getText(), Main.SourceY.getText(),"-", "-" }};

    String[] columnNames = { "Rodzaj fali: ", "X: ", "Y: ", "Czestotliwosc: ", "Dlugosc fali: " };

    JTable table = new JTable(data,columnNames);
    table.setBounds(0, 0, 500, 35);

    JTable table2 = new JTable(data_nonsine,columnNames);
    table2.setBounds(0, 0, 500, 35);

    JPanel Linia = new JPanel() {
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(250 - 30/2, 470, 250 + 30/2, 470);
        g.drawLine(250 - 30/2, 465, 250 - 30/2, 475);
        g.drawLine(250 + 30/2, 465, 250 + 30/2, 475);
      }
    };

    Linia.setOpaque(false);
    Linia.setBounds(0, 0, 500, 500);
    JLabel zero = new JLabel();
    //zero.setfont()
    zero.setText("0");
    zero.setBounds(230, 205, 100, 500);

    JLabel dlugosc = new JLabel();
    String s = String.format("%.2f", d) + jedn_dlug;
    dlugosc.setText(s);
    dlugosc.setBounds(265, 205, 100, 500);

    JPanel Symulacja = new JPanel();
    JPanel Group = new JPanel();

    canvas.view.setBounds(0, 0, 500, 475);

    Group.setLayout(new GridLayout(2, 1));
    Group.add(canvas.view);
    Group.add(Linia);
    Group.add(zero);
    Group.add(dlugosc);
    frame.setLayout(null);
    frame.add(canvas.view);

    //frame.add(Group);
    if (dTypFali == "Sinusoidalna") {
      frame.add(table);
    } else {
      frame.add(table2);
    }
    frame.add(table);

    if (dTypFali == "Sinusoidalna") {
      frame.add(Linia);
      frame.add(zero);
      frame.add(dlugosc);
    } else { }

    frame.setSize(500, 525);
    //frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);

    for (n = 1; STOP == 1 ; n++) {
      if (sine == 0 && n == 1) {
        Ez[xSource][ySource] = 0.999999999999999999999999999999999999999999999999999999999999999999999999999999999999;
      }

      n1 = 2;
      n2 = X_DIMENSION - 2;
      n11 = 2;
      n22 = Y_DIMENSION - 2;

      Matrix2.Replace(Hx, Matrix2.WiseS( //-
                      Matrix2.WiseM(Matrix2.Cut(A, n1, n2 - 1, n11, n22 - 1), Matrix2.Cut(Hx, n1, n2 - 1, n11, n22 - 1)),//A(n1:n2-1,n11:n22-1).*Hx(n1:n2-1,n11:n22-1)
                      Matrix2.WiseM( //*
                              Matrix2.Cut(B, n1, n2 - 1, n11, n22 - 1), //B(n1:n2-1,n11:n22-1)
                              Matrix2.WiseS(  //-
                                      Matrix2.Cut(Ez, n1, n2 - 1, n11 + 1, n22), //Ez(n1:n2-1,n11+1:n22)
                                      Matrix2.Cut(Ez, n11, n2 - 1, n11, n22 - 1)))), //Ez(n1:n2-1,n11+1:n22)
              n1, n2 - 1, n11, n22 - 1);

      Matrix2.Replace(Hy, Matrix2.WiseA( //+
                      Matrix2.WiseM(Matrix2.Cut(A, n1, n2 - 1, n11, n22 - 1), Matrix2.Cut(Hy, n1, n2 - 1, n11, n22 - 1)), //A(n1:n2-1,n11:n22-1).*Hy(n1:n2-1,n11:n22-1)
                      Matrix2.WiseM( //*
                              Matrix2.Cut(B, n1, n2 - 1, n11, n22 - 1), //B(n1:n2-1,n11:n22-1).
                              Matrix2.WiseS( //-
                                      Matrix2.Cut(Ez, n1 + 1, n2, n11, n22 - 1), //Ez(n1+1:n2,n11:n22-1)
                                      Matrix2.Cut(Ez, n11, n2 - 1, n11, n22 - 1)))) //Ez(n11:n2-1,n11:n22-1)
              , n1, n2 - 1, n11, n22 - 1);

      //Ez.Replace(Matrix.WiseM());
      Pom1 = Matrix2.WiseM(Matrix2.Cut(C, n1 + 1, n2 - 1, n11 + 1, n22 - 1), Matrix2.Cut(Ez, n1 + 1, n2 - 1, n11 + 1, n22 - 1)); //C(n1+1:n2-1,n11+1:n22-1).*Ez(n1+1:n2-1,n11+1:n22-1)
      Pom2 = Matrix2.WiseA( //+
              Matrix2.WiseS( //-
                      Matrix2.WiseS(Matrix2.Cut(Hy, n1 + 1, n2 - 1, n11 + 1, n22 - 1), Matrix2.Cut(Hy, n1, n2 - 2, n11 + 1, n22 - 1)), //Hy(n1+1:n2-1,n11+1:n22-1)-Hy(n1:n2-2,n11+1:n22-1)
                      Matrix2.Cut(Hx, n1 + 1, n2 - 1, n11 + 1, n22 - 1)), //Hx(n1+1:n2-1,n11+1:n22-1)
              Matrix2.Cut(Hx, n1 + 1, n2 - 1, n11, n22 - 2)); //Hx(n1+1:n2-1,n11:n22-2))
      Pom3 = Matrix2.WiseM(Pom2, Matrix2.Cut(D, n1 + 1, n2 - 1, n11 + 1, n22 - 1)); //.*D(n1+1:n2-1,n11+1:n22-1)
      Pom4 = Matrix2.WiseA(Pom1, Pom3);
      Matrix2.Replace(Ez, Pom4, n1 + 1, n2 - 1, n11 + 1, n22 - 1);

      if (n >= (X_DIMENSION - 4 - xSource)) {
        Pomo = Matrix2.WiseA(Matrix2.Cut(Ez, X_DIMENSION - 3, X_DIMENSION - 3, 3, Y_DIMENSION - 3), Matrix2.Cut(prev_prev_xfor, 1, 1, 3, Y_DIMENSION - 3)); //Ez(xdim-3,3:1:ydim-3)+prev_prev_xfor(1,3:1:ydim-3)
        Pomo = Matrix2.ScalarM(c0Efffor, Pomo); //c0efffor* (Ez(xdim-3,3:1:ydim-3)+prev_prev_xfor(1,3:1:ydim-3))
        Pomo = Matrix2.WiseS(Pomo, Matrix2.Cut(prev_prev_x_minus_1for, 1, 1, 3, Y_DIMENSION - 3)); //-prev_prev_x_minus_1for(1,3:1:ydim-3)
        Pomo1 = Matrix2.WiseA(Matrix2.Cut(prev_xfor, 1, 1, 3, Y_DIMENSION - 3), Matrix2.Cut(prev_x_minus_1for, 1, 1, 3, Y_DIMENSION - 3)); //prev_xfor(1,3:1:ydim-3)+prev_x_minus_1for(1,3:1:ydim-3)
        Pomo1 = Matrix2.ScalarM(c2Efffor, Pomo1);
        Pomo2 = Matrix2.WiseA(Matrix2.Cut(prev_x_minus_1for, 1, 1, 2, Y_DIMENSION - 4), Matrix2.Cut(prev_x_minus_1for, 1, 1, 4, Y_DIMENSION - 2));
        Pomo2 = Matrix2.WiseA(Pomo2, Matrix2.Cut(prev_xfor, 1, 1, 2, Y_DIMENSION - 4));
        Pomo2 = Matrix2.WiseA(Pomo2, Matrix2.Cut(prev_xfor, 1, 1, 4, Y_DIMENSION - 2));
        Pomo2 = Matrix2.ScalarM(c3Efffor, Pomo2);
        Pomo = Matrix2.WiseA(Pomo, Pomo1);
        Pomo = Matrix2.WiseA(Pomo, Pomo2);
        Matrix2.Replace(Ez, Pomo, X_DIMENSION - 2, X_DIMENSION - 2, 3, Y_DIMENSION - 3);
      }

      Matrix2.Replace(prev_prev_xfor, prev_xfor, 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_prev_x_minus_1for, prev_x_minus_1for, 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_xfor, Matrix2.Cut(Ez, X_DIMENSION - 2, X_DIMENSION - 2, 1, Y_DIMENSION), 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_x_minus_1for, Matrix2.Cut(Ez, X_DIMENSION - 3, X_DIMENSION - 3, 1, Y_DIMENSION), 1, 1, 1, Y_DIMENSION);

      if (n >= (xSource - 2)) {
        Pomo1 = Matrix2.WiseA(Matrix2.Cut(Ez, 3, 3, 3, Y_DIMENSION - 3), Matrix2.Cut(prev_prev_x_minus_1rev, 1, 1, 3, Y_DIMENSION - 3));
        Pomo1 = Matrix2.ScalarM(Pomo1, c1Effrev);
        Pomo1 = Matrix2.WiseS(Pomo1, Matrix2.Cut(prev_prev_xrev, 1, 1, 3, Y_DIMENSION - 3)); //-prev_prev_xrev(1,3:1:ydim-3));

        Pomo2 = Matrix2.WiseA(Matrix2.Cut(prev_xrev, 1, 1, 3, Y_DIMENSION - 3), Matrix2.Cut(prev_x_minus_1rev, 1, 1, 3, Y_DIMENSION - 3));
        Pomo2 = Matrix2.ScalarM(Pomo2, c2Effrev);

        Pomo3 = Matrix2.WiseA(Matrix2.Cut(prev_x_minus_1rev, 1, 1, 2, Y_DIMENSION - 4), Matrix2.Cut(prev_x_minus_1rev, 1, 1, 4, Y_DIMENSION - 2));
        Pomo3 = Matrix2.WiseA(Pomo3, Matrix2.Cut(prev_xrev, 1, 1, 2, Y_DIMENSION - 4));
        Pomo3 = Matrix2.WiseA(Pomo3, Matrix2.Cut(prev_xrev, 1, 1, 4, Y_DIMENSION - 2));
        Pomo3 = Matrix2.ScalarM(Pomo3, c3Effrev);
        Pomo = Matrix2.WiseA(Pomo2, Pomo1);
        Pomo = Matrix2.WiseA(Pomo, Pomo3);

        Matrix2.Replace(Ez, Pomo, 2, 2, 3, Y_DIMENSION - 3);
      }

      Matrix2.Replace(prev_prev_xrev, prev_xrev, 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_prev_x_minus_1rev, prev_x_minus_1rev, 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_xrev, Matrix2.Cut(Ez, 3, 3, 1, Y_DIMENSION), 1, 1, 1, Y_DIMENSION);
      Matrix2.Replace(prev_x_minus_1rev, Matrix2.Cut(Ez, 2, 2, 1, Y_DIMENSION), 1, 1, 1, Y_DIMENSION);

      if (n >= (Y_DIMENSION - 4 - ySource)) {
        Pomo = Matrix2.WiseA(Matrix2.Cut(Ez, 3, X_DIMENSION - 3, Y_DIMENSION - 3, Y_DIMENSION - 3), Matrix2.Cut(prev_prev_yfor, 3, X_DIMENSION - 3, 1, 1));
        Pomo = Matrix2.ScalarM(Pomo, c0Efffor);
        Pomo = Matrix2.WiseS(Pomo, Matrix2.Cut(prev_prev_y_minus_1for, 3, X_DIMENSION - 3, 1, 1));
        Pomo1 = Matrix2.WiseA(Matrix2.Cut(prev_yfor, 3, X_DIMENSION - 3, 1, 1), Matrix2.Cut(prev_y_minus_1for, 3, X_DIMENSION - 3, 1, 1));
        Pomo1 = Matrix2.ScalarM(c2Efffor, Pomo1);
        Pomo2 = Matrix2.WiseA(Matrix2.Cut(prev_y_minus_1for, 2, X_DIMENSION - 4, 1, 1), Matrix2.Cut(prev_y_minus_1for, 4, X_DIMENSION - 2, 1, 1));
        Pomo2 = Matrix2.WiseA(Pomo2, Matrix2.Cut(prev_yfor, 2, X_DIMENSION - 4, 1, 1));
        Pomo2 = Matrix2.WiseA(Pomo2, Matrix2.Cut(prev_yfor, 4, X_DIMENSION - 2, 1, 1));
        Pomo2 = Matrix2.ScalarM(c3Efffor, Pomo2);
        Pomo = Matrix2.WiseA(Pomo, Pomo1);
        Pomo = Matrix2.WiseA(Pomo, Pomo2);

        Matrix2.Replace(Ez, Pomo, 3, X_DIMENSION - 3, Y_DIMENSION - 2, Y_DIMENSION - 2);
      }

      Matrix2.Replace(prev_prev_yfor, prev_yfor, 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_prev_y_minus_1for, prev_y_minus_1for, 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_yfor, Matrix2.Cut(Ez, 1, X_DIMENSION, Y_DIMENSION - 2, Y_DIMENSION - 2), 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_y_minus_1for, Matrix2.Cut(Ez, 1, X_DIMENSION, Y_DIMENSION - 3, Y_DIMENSION - 3), 1, X_DIMENSION, 1, 1);

      if (n >= (ySource - 2)) {
        Pomo = Matrix2.ScalarM(-1, Matrix2.Cut(prev_prev_yrev, 3, X_DIMENSION - 3, 1, 1));
        Pomo1 = Matrix2.WiseA(Matrix2.Cut(Ez, 3, X_DIMENSION - 3, 3, 3), Matrix2.Cut(prev_prev_y_minus_1rev, 3, X_DIMENSION - 3, 1, 1));
        Pomo1 = Matrix2.ScalarM(c1Effrev, Pomo1);
        Pomo2 = Matrix2.WiseA(Matrix2.Cut(prev_yrev, 3, X_DIMENSION - 3, 1, 1), Matrix2.Cut(prev_y_minus_1rev, 3, X_DIMENSION - 3, 1, 1));
        Pomo2 = Matrix2.ScalarM(c2Effrev, Pomo2);
        Pomo3 = Matrix2.WiseA(Matrix2.Cut(prev_y_minus_1rev, 2, X_DIMENSION - 4, 1, 1), Matrix2.Cut(prev_y_minus_1rev, 4, X_DIMENSION - 2, 1, 1));
        Pomo3 = Matrix2.WiseA(Pomo3, Matrix2.Cut(prev_yrev, 2, X_DIMENSION - 4, 1, 1));
        Pomo3 = Matrix2.WiseA(Pomo3, Matrix2.Cut(prev_yrev, 4, X_DIMENSION - 2, 1, 1));
        Pomo3 = Matrix2.ScalarM(c3Effrev, Pomo3);
        Pomo = Matrix2.WiseA(Pomo, Pomo1);
        Pomo = Matrix2.WiseA(Pomo, Pomo2);
        Pomo = Matrix2.WiseA(Pomo, Pomo3);
        Matrix2.Replace(Ez, Pomo, 3, X_DIMENSION - 3, 2, 2);
      }

      Matrix2.Replace(prev_prev_yrev, prev_yrev, 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_prev_y_minus_1rev, prev_y_minus_1rev, 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_yrev, Matrix2.Cut(Ez, 1, X_DIMENSION, 3, 3), 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(prev_y_minus_1rev, Matrix2.Cut(Ez, 1, X_DIMENSION, 2, 2), 1, X_DIMENSION, 1, 1);
      Matrix2.Replace(Ez, Matrix2.Cut(prev_prev_xrev, 1, 1, 3, 3), 2, 2, 2, 2);
      Matrix2.Replace(Ez, Matrix2.Cut(prev_prev_xrev, 1, 1, Y_DIMENSION - 3, Y_DIMENSION - 3), 2, 2, Y_DIMENSION - 2, Y_DIMENSION - 2);
      Matrix2.Replace(Ez, Matrix2.Cut(prev_prev_x_minus_1for, 1, 1, 3, 3), X_DIMENSION - 2, X_DIMENSION - 2, 2, 2);
      Matrix2.Replace(Ez, Matrix2.Cut(prev_prev_x_minus_1for, 1, 1, Y_DIMENSION - 3, Y_DIMENSION - 3), X_DIMENSION - 2, X_DIMENSION - 2, Y_DIMENSION - 2, Y_DIMENSION - 2);

      if (impulse == 0) {
        if (sine == 0) {
          Ez[xSource][ySource] = 0.999999999999999999999999999999999999999999999999999999999999999999999999999999999999;
        }

        if (sine == 1) {
          int timeStart = 1; // tstart
          double nLambda = speedOfLight / (frequency * spatialGridStep); // N_lamda
          Ez[xSource][ySource] = Math.sin(2 * Math.PI * (speedOfLight / (spatialGridStep * nLambda)) * (n - timeStart) * temporalGridStep);
        }
      } else {
        Ez[xSource][ySource] = 0.0;
      } try {
        Thread.sleep(20);
        canvas.Change(Ez);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
