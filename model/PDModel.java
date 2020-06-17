// Carlos Santiago Bañón

// Preferential Deletion Model (PDModel)

// This program was developed for the 'An Implementation of Preferential Deletion in Dynamic Models
// of Web-Like Networks' project made as part of the COT 5405: Design and Analysis of Algorithms
// graduate course at the University of Central Florida.

// PreferentialDeletionModel.java
// Implementation of the dynamic random graph model with preferential deletion.

import java.lang.Math;
import java.util.*;

public class PDModel
{
  private double probability;
  private int time;
  private Graph G;

  private static final boolean DEBUG = false;

  public PDModel(int time, double probability)
  {
    this.time = time;
    this.probability = probability;
    G = new Graph();
  }

  public void createNode(int t)
  {
    if (DEBUG)
    {
      System.out.println("Creating Node " + t);
    }

    if (G.getNumNodes() == 0)
    {
      G.initializeGraph();
      return;
    }

    HashMap<Node, Double> probabilities = new HashMap<>();
    double sum = 0.0;

    for (Node node : G.getAdjacencyList().keySet())
    {
      sum += linearPreferentialAttachment(node);
      probabilities.put(node, sum);
    }

    if (DEBUG)
    {
      System.out.println("Size of Probabilities: " + probabilities.size());

      for (Node node : probabilities.keySet())
        System.out.println("Probability of " + node.getCode() + ": " + probabilities.get(node));
    }

    Node newNeighbor = null;
    boolean found = false;
    double p = Math.random() * sum;

    for (Node node : probabilities.keySet())
    {
      if (probabilities.get(node) >= p)
      {
        newNeighbor = node;
        found = true;
        break;
      }
    }

    if (DEBUG)
    {
      System.out.println("New Neighbor: " + newNeighbor.getCode());
      System.out.println();
    }

    G.createNode(newNeighbor, t);
  }

  public void deleteRandomNode()
  {
    if (DEBUG)
    {
      System.out.println("Deleting Random Node");
    }

    HashMap<Node, Double> probabilities = new HashMap<>();
    double sum = 0.0;

    for (Node node : G.getAdjacencyList().keySet())
    {
      double p = preferentialDeletion(node);
      probabilities.put(node, (p + sum));
      sum += p;
    }

    if (DEBUG)
    {
      System.out.println("Size of Probabilities: " + probabilities.size());

      for (Node node : probabilities.keySet())
        System.out.println("Probability of " + node.getCode() + ": " + probabilities.get(node));
    }

    int code = 0;
    Node nodeToDelete = null;
    double p = Math.random() * sum;

    for (Node node : probabilities.keySet())
      if (probabilities.get(node) >= p)
      {
        nodeToDelete = node;
        break;
      }

    if (DEBUG)
    {
      System.out.println("Node to Delete: " + nodeToDelete.getCode());
      System.out.println();
    }

    G.deleteNode(nodeToDelete.getCode());
  }

  // Returns the graph used in the model.
  public Graph getGraph()
  {
    return G;
  }

  // Returns the probability parameter for the model.
  public double getProbability()
  {
    return probability;
  }

  // Returns the time parameter for the model.
  public int getTime()
  {
    return time;
  }

  // Returns the probability that a new node is connected to node u, according
  // to the Linear Preferencial Attachment rule.
  public double linearPreferentialAttachment(Node u)
  {
    int degree = G.getDegree(u);
    int numEdges = G.getNumEdges();

    double A = (double) degree;
    double B = (double) (2 * numEdges);

    if (B == 0)
      return 0.0;

    return (A / B);
  }

  // Returns the probability that node u will be deleted from the graph.
  public double preferentialDeletion(Node u)
  {
    int numNodes = G.getNumNodes();
    int numEdges = G.getNumEdges();
    int degree = G.getDegree(u);

    double A = (double) (numNodes - degree);
    double B = (double) ((numNodes * numNodes) - (2 * numEdges));

    if (B == 0)
      return 0.0;

    return (A / B);
  }

  // Prints the linear preferential attachment probability values for each node
  // in the graph.
  public void printLPAValues()
  {
    System.out.println("LPA Values in G:");
    System.out.println("================");

    for (Node node : G.getAdjacencyList().keySet())
    {
      System.out.println("LPA(" + node.getCode() + "): " + linearPreferentialAttachment(node));
    }

    System.out.println();
  }

  // Prints the preferential deletion probability values for each node in the graph.
  public void printPDValues()
  {
    System.out.println("PD Values in G:");
    System.out.println("===============");

    for (Node node : G.getAdjacencyList().keySet())
    {
      System.out.println("PD(" + node.getCode() + "): " + preferentialDeletion(node));
    }

    System.out.println();
  }

  public void runModel()
  {
    G.initializeGraph();

    double p = 0;

    System.out.println("1 " + G.getNumNodes() + " " + G.getNumEdges() + " " + G.getAverageDegree());

    for (int t = 2; t <= time; t++)
    {
      if (DEBUG)
      {
        System.out.println(t + "/" + time);
      }

      p = Math.random();

      if (p < probability)
      {
        createNode(t);
      }
      else
      {
        deleteRandomNode();
      }

      // If the graph becomes empty, re-initialize the graph.
      if (G.getNumNodes() == 0)
      {
        G.initializeGraph();
      }

      if (DEBUG)
      {
        if (t % 1000 == 0)
        {
          System.out.println(t + " " + G.getNumNodes() + " " + G.getNumEdges() + " " + G.getAverageDegree());
        }
      }

      if (DEBUG)
      {
        G.printAdjacencyList();
        G.printStatistics(t);
      }
    }

    if (DEBUG)
    {
      G.printAdjacencyList();
      G.printDegreeDistribution();
    }

    G.printStatistics(time);
  }
}
