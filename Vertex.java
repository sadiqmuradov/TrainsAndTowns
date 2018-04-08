import java.util.LinkedList;

public class Vertex {
  
  private final String name;
  private int weight;
  private final LinkedList<Vertex> adjList;
  
  public Vertex(String name, int weight, LinkedList<Vertex> adjList) {
    this.name = name;
    this.weight = weight;
    this.adjList = adjList;
  }
  
  public String getName() {
    return name;
  }
  
  public int getWeight() {
    return weight;
  }
  
  public void setWeight(int weight) {
    this.weight = weight;
  }
  
  public LinkedList<Vertex> getAdjList() {
    return adjList;  
  }
  
}