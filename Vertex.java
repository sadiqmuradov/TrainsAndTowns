import java.util.List;

public class Vertex {
  
  private final String name;
  private int weight;
  private final List<Vertex> adjList;
  
  public Vertex(String name, int weight, List<Vertex> adjList) {
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
  
  public List<Vertex> getAdjList() {
    return adjList;  
  }
  
}