/*******************************************************************************
 *
 * University of Texas Rio Grande Valley
 * Computer Engineering
 * Senior Design 1
 * Spring 2020
 * Group 15: Bernie VIllalon, Samuel Solis, Leo Marroquin
 *
 *
 *
 ******************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Lambda {
  public static class Vals {

    private final double lambda, resist;
    public Vals(double[] vals) {
      double[] V_D = new double[45];
      double[] V_S = new double[45];
      double[] I = new double[45];
      for (int i = 0; i < vals.length; i++ )
        if (i < 45)
          V_D[i] = vals[i];
        else if (i < 90)
          V_S[i - 45] = vals[i];
        else
          I[i - 90] = vals[i];

      double[] V_DS = pairer(V_D, V_S);
      int tLow = 0;
      int tUp = tUpper(I);
      int sLow = sLower(I);
      int sUp = I.length - 1;

      LinearFit Triode = new LinearFit(range(V_DS, tLow, tUp), range(I, tLow, tUp));
      LinearFit Saturation = new LinearFit(range(V_DS, sLow, sUp), range(I, sLow, sUp));
      lambda = Saturation.slope / Saturation.intercept;
      resist = 1 / Triode.slope;
    }

    public double lambda() { return lambda; }
    public double resistance() { return resist; }

    private int tUpper(double[] x) {
      int upper = 0;
      //boolean flag = false;
      for (int i = 1; i < x.length; i++)
        if (x[i - 1] / x[i] < 0.915)                                             // As long as value is 5% greater than previous
          upper = i;

      return upper;
    }

    private int sLower(double[] x) {
      int lower = 0;
      //boolean flag = false;
      for (int i = 1; i < x.length; i++)
        if (!(x[i - 1] / x[i] > 0.97))
          lower = i;

      return lower;
    }

    private double[] pairer(double[] x, double[] y) {
      if (x.length != y.length)
        throw new IllegalArgumentException("Array lengths are not equal");
      double[] temp = new double[x.length];
      for (int i = 0; i < temp.length; i++ )
        temp[i] = x[i] - y[i];
      return temp;
    }

    private double[] range(double[] x, int start, int end) {
      double[] temp = new double[end - start];
      for (int i = 0; i < temp.length; i++)
        temp[i] = x[i + start];
      return temp;
    }
  }

  public static void main(String[] args) {
    double[] vals = {0.000,0.100,0.200,0.300,0.400,0.500,0.600,0.700,0.800,0.900,1.000,1.100,1.200,1.300,1.400,1.500,1.600,1.700,1.800,1.900,2.000,2.100,2.200,2.300,2.400,2.500,3.000,3.500,4.000,4.500,5.000,5.500,6.000,6.500,7.000,7.500,8.000,8.500,9.000,9.500,10.000,10.500,11.000,11.500,12.000,0.000,0.096,0.195,0.293,0.389,0.492,0.590,0.688,0.779,0.877,0.974,1.071,1.165,1.261,1.356,1.449,1.539,1.627,1.710,1.783,1.840,1.878,1.898,1.908,1.913,1.917,1.926,1.933,1.936,1.939,1.943,1.949,1.953,1.957,1.960,1.965,1.970,1.973,1.978,1.983,1.990,2.000,2.003,2.007,2.010,0.000000000,0.000967937,0.001966122,0.002954225,0.003922162,0.004960678,0.005948780,0.006936882,0.007854406,0.008842509,0.009820528,0.010798548,0.011746320,0.012714257,0.013672111,0.014609800,0.015517241,0.016404517,0.017241379,0.017977415,0.018552127,0.018935269,0.019136923,0.019239766,0.019291188,0.019328494,0.019419238,0.019489816,0.019520065,0.019550313,0.019590643,0.019651139,0.019691470,0.019731801,0.019762049,0.019812462,0.019862876,0.019893124,0.019943537,0.019993950,0.020064529,0.020165356,0.020195604,0.020235935,0.020266183};

    Vals lam = new Vals(vals);
    System.out.println("Lambda: " + lam.lambda);
    //System.out.println("Triode?Resistance: " + lam.resist + "<- Didn't ask for this so whatev");
  }
}
