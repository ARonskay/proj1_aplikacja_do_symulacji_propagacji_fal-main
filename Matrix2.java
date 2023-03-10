/**
* Simulation of the wave propagation
* Definition of matrices
*
* @author  Cyber-boolean
* @since   02-2022
*/

package MATLAB;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.util.Objects;
import java.util.Vector;

public class Matrix2 {
  public static double[][] WiseM(double[][] M1, double[][] M2) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M2[0].length; j++) {
        POM[i][j] = M1[i][j] * M2[i][j];
      }
    }
    return POM;
  }

  public static double[][] Create(int x, int y, double value) {
    double[][] M = new double[x][y];
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        M[i][j] = value;
      }
    }
    return M;
  }

  public static double[][] WiseD(double[][] M1, double[][] M2) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M2[0].length; j++) {
        POM[i][j] = M1[i][j] / M2[i][j];
      }
    }
    return POM;
  }

  public static double[][] SWRD(double Scalar, double[][] M1) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M1[0].length; j++) {
        POM[i][j] = Scalar / M1[i][j];
      }
    }
    return POM;
  }

  public static double[][] WiseS(double[][] M1, double[][] M2) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M1[0].length; j++) {
        POM[i][j] = M1[i][j] - M2[i][j];
      }
    }
    return POM;
  }

  public static double[][] WiseA(double[][] M1, double[][] M2) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M1[0].length; j++) {
        POM[i][j] = M1[i][j] + M2[i][j];
      }
    }
    return POM;
  }

  public static double[][] ScalarM(double[][] M1, double S) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M1[0].length; j++) {
        POM[i][j] = M1[i][j] * S;;
      }
    }
    return POM;
  }

  public static double[][] ScalarM(double S, double[][] M1) {
    double[][] POM = new double[M1.length][M1[0].length];
    for (int i = 0; i < M1.length; i++) {
      for (int j = 0; j < M1[0].length; j++) {
        POM[i][j] = M1[i][j] * S;
      }
    }
    return POM;
  }

  public static double[][] Cut(double[][] M1 , int x1, int x2, int y1, int y2) {
    x1 = x1 - 1;
    x2 = x2 - 1;
    y1 = y1 - 1;
    y2 = y2 - 1;
    double[][] cut = new double[x2 - x1 + 1][y2 - y1 + 1];

    for (int i = 0; i <= x2 - x1; i++) {
      for (int j = 0; j <= y2 - y1; j++) {
        cut[i][j]=M1[i + x1][j + y1];
      }
    }
    return cut;
  }

  public static void Replace(double[][] M1, double[][] M2 , int x1, int x2, int y1, int y2) {
    x1 = x1 - 1;
    x2 = x2 - 1;
    y1 = y1 - 1;
    y2 = y2 - 1;

    for (int i = 0; i <= x2 - x1; i++) {
      for (int j = 0; j <= y2 - y1; j++) {
        M1[i + x1][j + y1] = M2[i][j];
      }
    }
  }
}
