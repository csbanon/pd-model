// Carlos Santiago Bañón

// Preferential Deletion Model (PDModel)

// This program was developed for the 'An Implementation of Preferential Deletion in Dynamic Models
// of Web-Like Networks' project made as part of the COT 5405: Design and Analysis of Algorithms
// graduate course at the University of Central Florida.

// Run.java
// File used to run the Preferential Deletion Model.

public class Run
{
  private static final int TRIALS = 1;
  private static final int TIME = 1000;
  private static final double P1 = 0.6;
  private static final double P2 = 0.75;
  private static final double P3 = 0.9;

  public static void main(String [] args)
  {
    PDModel M1;
    PDModel M2;
    PDModel M3;

    for (int i = 1; i <= TRIALS; i++)
    {
      System.out.println("=============== TRIAL " + i + "/" + TRIALS + " ===============");

      System.out.println("RUNNING MODEL AT P = " + P1);
      M1 = new PDModel(TIME, P1);
      M1.runModel();
      System.out.println();

      System.out.println("RUNNING MODEL AT P = " + P2);
      M2 = new PDModel(TIME, P2);
      M2.runModel();
      System.out.println();

      System.out.println("RUNNING MODEL AT P = " + P3);
      M3 = new PDModel(TIME, P3);
      M3.runModel();
      System.out.println();
    }
  }
}
