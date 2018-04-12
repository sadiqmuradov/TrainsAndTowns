import java.util.List;
import java.util.ArrayList;

public class Graph {
  
  private final int V; // Number of vertices
  private final Vertex[] vArr; // Vertex array
  
  public Graph(List<String> vertexList) {
    V = vertexList.size();
    vArr = new Vertex[V];
    
    for (int i = 0; i < V; i++) {
      vArr[i] = new Vertex(vertexList.get(i), 0, new ArrayList<>());
    }
  }
  
  // Adds an edge into a graph
  public void addEdge(String v1, String v2, int w) {
    int n = -1, i = 0;
    
    for (i = 0; i < vArr.length; i++) {
      if (vArr[i].getName().equals(v1)) {
        n = i;
        break;
      }
    }
    
    List<Vertex> adjList = vArr[n].getAdjList();
    int adjListSize = adjList.size();
    
    for (i = 0; i < adjListSize; i++) {
      if (adjList.get(i).getName().equalsIgnoreCase(v2)) {
        adjList.get(i).setWeight(w);
        break;
      }
    }
    
    if (i == adjListSize) {
      Vertex v = new Vertex(v2, w, null);
      adjList.add(v);
    }
  }
  
  public int getV() {
    return V;
  }
  
  public Vertex[] getVertexArr() {
    return vArr;
  }
  
}