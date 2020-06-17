// Carlos Santiago Bañón

// Preferential Deletion Model (PDModel)

// This program was developed for the 'An Implementation of Preferential Deletion in Dynamic Models
// of Web-Like Networks' project made as part of the COT 5405: Design and Analysis of Algorithms
// graduate course at the University of Central Florida.

// Graph.java
// Definition of the Graph object.

import java.lang.Math;
import java.util.*;

public class Graph
{
  private HashMap<Node, LinkedList<Node>> adjacencyList;
  private HashMap<Integer, Node> nodesList;
  private int numEdges;

  private static final boolean DEBUG = false;

  public Graph()
  {
    adjacencyList = new HashMap<>();
    nodesList = new HashMap<>();
    numEdges = 0;
  }

  // Adds a new edge between two nodes.
  public void addEdge(Node A, Node B)
  {
    // Check if the edge already exists.
    if (hasEdge(A, B))
    {
      System.out.println("WARNING: Graph already contains this edge.");
      return;
    }

    // If node A is not in the graph, add it.
    if (!adjacencyList.keySet().contains(A))
      adjacencyList.put(A, null);

    // If node B is not in the graph, add it.
    if (!adjacencyList.keySet().contains(B))
      adjacencyList.put(B, null);

    addEdgeHelper(A, B);
    addEdgeHelper(B, A);

    numEdges++;
  }

  // Helper method for addEdge().
  public void addEdgeHelper(Node A, Node B)
  {
    LinkedList<Node> tempList = adjacencyList.get(A);

    // Create a new LinkedList if necessary.
    if (tempList == null)
      tempList = new LinkedList<>();
    else
      tempList.remove(B);

    tempList.add(B);
    adjacencyList.put(A, tempList);
  }

  // Creates a new node in the graph and connects it with a predetermined neighbor.
  public void createNode(Node neighbor, int t)
  {
    Node newNode = new Node(t);

    addEdge(newNode, neighbor);
    nodesList.put(t, newNode);
  }

  // Deletes the edge between the source and the destination.
  public void deleteEdge(Node A, Node B)
  {
    if (DEBUG)
    {
      System.out.println("Edge to Delete: (" + A.getCode() + ", " + B.getCode() + ")");
    }

    // Check if the edge exists in the graph.
    if (!hasEdge(A, B))
    {
      System.out.println("WARNING: Edge does not exist in graph.");
      return;
    }

    deleteEdgeHelper(A, B);
    deleteEdgeHelper(B, A);

    numEdges--;
  }

  // Helper method for deleteEdge().
  public void deleteEdgeHelper(Node source, Node destination)
  {
    LinkedList<Node> temp = adjacencyList.get(source);

    if (temp == null)
      return;

    if (DEBUG)
    {
      System.out.println("Deleting Edge (" + source.getCode() + ", " + destination.getCode() + ")");
    }

    temp.remove(destination);
  }

  // (DOUBLE-CHECK) Deletes node u from the graph.
  public void deleteNode(int code)
  {
    if (DEBUG)
    {
      System.out.println("Deleting Node " + code);
    }

    Node node = getNode(code);

    if (getAdjacencyList().get(node).size() == 0)
    {
      adjacencyList.remove(node);
      nodesList.remove(code);
      return;
    }

    if (adjacencyList.get(node).size() == 1)
    {
      deleteEdge(node, adjacencyList.get(node).get(0));
      adjacencyList.remove(node);
      nodesList.remove(code);
      return;
    }

    if (DEBUG)
    {
      System.out.println("Number of Neighbors of " + code + ": " + adjacencyList.get(node).size());
    }

    for (int i = 0; i < adjacencyList.get(node).size(); i++)
    {
      deleteEdge(node, adjacencyList.get(node).get(i));

      if (adjacencyList.get(node).size() == 1)
      {
        deleteEdge(node, adjacencyList.get(node).get(0));
        adjacencyList.remove(node);
        nodesList.remove(code);
        return;
      }
    }

    // Delete node u.
    adjacencyList.remove(node);
    nodesList.remove(code);
  }

  // Returns the adjacency list of the graph.
  public HashMap<Node, LinkedList<Node>> getAdjacencyList()
  {
    return adjacencyList;
  }

  // Returns the average degree of the graph.
  public double getAverageDegree()
  {
    double totalDegrees = 0.0;

    for (Node node : adjacencyList.keySet())
      totalDegrees += adjacencyList.get(node).size();

    return totalDegrees / adjacencyList.size();
  }

  // Returns the degree of the given node in the graph.
  public int getDegree(Node u)
  {
    // Ensure the node exists in the graph.
    if (u == null)
    {
      System.out.println("ERROR! Node does not exist in the graph.");
      return 0;
    }

    int degree = 0;

    for (Node node : adjacencyList.get(u))
    {
      if (node.getCode() == u.getCode())
        degree++;

      degree++;
    }

    return degree;
  }

  public HashMap<Integer, Integer> getDegreeDistribution()
  {
    HashMap<Integer, Integer> degreeDistribution = new HashMap<>();

    for (Node node : getAdjacencyList().keySet())
    {
      int degree = getAdjacencyList().get(node).size();

      if (!degreeDistribution.containsKey(degree))
      {
        degreeDistribution.put(degree, 1);
      }
      else
      {
        int numNeighbors = degreeDistribution.get(degree);
        degreeDistribution.replace(degree, numNeighbors + 1);
      }
    }

    return degreeDistribution;
  }

  // Returns a reference to node u.
  public Node getNode(int u)
  {
    return nodesList.get(u);
  }

  // Returns the list of nodes of the graph.
  public HashMap<Integer, Node> getNodesList()
  {
    return nodesList;
  }

  // Returns the number of edges in the graph.
  public int getNumEdges()
  {
    return numEdges;
  }

  // Returns the number of nodes in the graph.
  public int getNumNodes()
  {
    return adjacencyList.size();
  }

  // Returns true if there exists an edge between the source and the destination.
  // Otherwise, false.
  public boolean hasEdge(Node source, Node destination)
  {
    return adjacencyList.containsKey(source) && adjacencyList.get(source).contains(destination);
  }

  public void initializeGraph()
  {
    Node node = new Node(1);
    getAdjacencyList().put(node, new LinkedList<>());
    getAdjacencyList().get(node).add(node);
    numEdges = 1;
    nodesList.put(1, node);
  }

  // Prints the adjacency list for the graph.
  public void printAdjacencyList()
  {
    System.out.println("Adjacency List of G:");
    System.out.println("====================");

    for (Node node : adjacencyList.keySet())
    {
      System.out.print(node.getCode() + ": ");

      for (Node incidentNode : adjacencyList.get(node))
      {
        System.out.print(incidentNode.getCode() + " ");
      }

      System.out.println();
    }

    System.out.println();
  }

  public void printDegreeDistribution()
  {
    HashMap<Integer, Integer> degreeDistribution = getDegreeDistribution();

    for (Integer neighbors: degreeDistribution.keySet())
    {
      String key = neighbors.toString();
      String value = degreeDistribution.get(neighbors).toString();
      System.out.println(key + " " + value);
    }
  }

  // Prints the degree for every node in the graph.
  public void printDegrees()
  {
    System.out.println("Degrees in G:");
    System.out.println("=============");

    for (Node node : adjacencyList.keySet())
    {
      System.out.println("Degree(" + node.getCode() + "): " + getDegree(node));
    }

    System.out.println();
  }

  // Prints useful statistics related to the graph.
  public void printStatistics(int t)
  {
    System.out.println("Statistics of G at t = " + t + ":");
    System.out.println("==============================");
    System.out.println("Number of Nodes: " + getNumNodes());
    System.out.println("Number of Edges: " + getNumEdges());
    System.out.println("Average Degree of G: " + getAverageDegree());
    System.out.println();
  }
}
