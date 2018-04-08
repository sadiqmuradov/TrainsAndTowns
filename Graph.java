import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
  
  private final int V; // Number of vertices
  private final Vertex[] vArr; // Vertex array
  
  public Graph(List<String> vertexList) {
    V = vertexList.size();
    vArr = new Vertex[V];
    
    for (int i = 0; i < V; i++) {
      vArr[i] = new Vertex(vertexList.get(i), 0, new LinkedList<>());
    }
  }
  
  // Adds an edge into a graph
  public void addEdge(String s, String d, int w) {
    int n = -1, i = 0;
    
    for (i = 0; i < vArr.length; i++) {
      if (vArr[i].getName().equals(s)) {
        n = i;
        break;
      }
    }
    
    List<Vertex> adjList = vArr[n].getAdjList();
    int adjListSize = adjList.size();
    
    for (i = 0; i < adjListSize; i++) {
      if (adjList.get(i).getName().equalsIgnoreCase(d)) {
        adjList.get(i).setWeight(w);
        break;
      }
    }
    
    if (i == adjListSize) {
      Vertex v = new Vertex(d, w, null);
      adjList.add(v);
    }
  }
  
  // Computes distance using DFS algorithm
  public int computeDistance(List<String> vertexList) {
    int sum = 0;
    int trvSize = vertexList.size() - 1;
    
    for (int i = 0; i < trvSize; i++) {
      if (!vertexList.get(i).equalsIgnoreCase(vertexList.get(i + 1))) {
        for (Vertex v : vArr) {
          if (v.getName().equalsIgnoreCase(vertexList.get(i))) {
            List<Vertex> adjList = v.getAdjList();
            int adjListSize = adjList.size();
            int k;

            for (k = 0; k < adjListSize; k++) {
              if (adjList.get(k).getName().equalsIgnoreCase(vertexList.get(i + 1))) {
                sum += adjList.get(k).getWeight();
                break;
              }
            }

            if (k == adjListSize)
              return -1;
            
            break;
          }
        }
      }
    }
    
    return sum;
  }
  
  // Computes trip using DFS algorithm
  public String computeTrip(String v1, String v2, int st, String scm) {
    String trip = "";
    List<Integer> tripCountList = Arrays.asList(0);
    
    if (st > 0) {
      for (Vertex v : vArr) {
        if (v.getName().equalsIgnoreCase(v1)) {
          List<Vertex> adjList = v.getAdjList();
          int adjListSize = adjList.size();
          
          for (int k = 0; k < adjListSize; k++) {
            String adjVertexName = adjList.get(k).getName();
            
            if (adjVertexName.equalsIgnoreCase(v2)) {
              if (scm.equalsIgnoreCase("m") || st == 1) {
                tripCountList.set(0, tripCountList.get(0) + 1);
              	String childTrip = v.getName() + "-" + adjVertexName + " (1 stop)";
                
                if (trip.isEmpty())
                  trip += childTrip;
                else
                  trip += ", " + childTrip;
                
                if (scm.equalsIgnoreCase("m") && st > 2) {
                  List<String> vertexList = new ArrayList<>();
                  vertexList.add(v.getName());
                  vertexList.add(adjVertexName);
                  trip = computeAdjTrip(adjVertexName, v2, 1, tripCountList, vertexList, trip, st, scm);
                }
              } else if (st > 2) {
                List<String> vertexList = new ArrayList<>();
                vertexList.add(v.getName());
                vertexList.add(adjVertexName);
                trip = computeAdjTrip(adjVertexName, v2, 1, tripCountList, vertexList, trip, st, scm);
              }
            } else {
              if (st > 1) {
                List<String> vertexList = new ArrayList<>();
                vertexList.add(v.getName());
                vertexList.add(adjVertexName);
                trip = computeAdjTrip(adjVertexName, v2, 1, tripCountList, vertexList, trip, st, scm);
              }
            }
          }

          break;
        }
      }
    }
    
    if (tripCountList.get(0) > 0)
    	trip = tripCountList.get(0) + ": " + trip;
    else
    	trip = "0: No route";
    
    return trip;
  }
  
  // Computes adjacent trips using DFS algorithm
  private String computeAdjTrip(String v1, String v2, int stopCount, List<Integer> tripCountList, List<String> vertexList, String trip, int st, String scm) {
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          
          if (adjVertexName.equalsIgnoreCase(v2)) {
            if (scm.equalsIgnoreCase("m") && stopCount < st || scm.equalsIgnoreCase("e") && stopCount + 1 == st) {
            	tripCountList.set(0, tripCountList.get(0) + 1);
            	String listTrip = "";
              
              for (String vertex: vertexList)
                listTrip += vertex + "-";
              
              listTrip += adjVertexName + " (" + (stopCount + 1) + " stops)";
              
              if (trip.isEmpty())
                trip += listTrip;
              else
                trip += ", " + listTrip;
              
              if (scm.equalsIgnoreCase("m") && stopCount + 2 < st) {
                List<String> adjVertexList = new ArrayList<>(vertexList);
                adjVertexList.add(adjVertexName);
                trip = computeAdjTrip(adjVertexName, v2, stopCount + 1, tripCountList, adjVertexList, trip, st, scm);
              }
            } else if (scm.equalsIgnoreCase("e") && stopCount + 2 < st) {
              List<String> adjVertexList = new ArrayList<>(vertexList);
              adjVertexList.add(adjVertexName);
              trip = computeAdjTrip(adjVertexName, v2, stopCount + 1, tripCountList, adjVertexList, trip, st, scm);
            }
          } else {
            if (stopCount + 1 < st) {
              List<String> adjVertexList = new ArrayList<>(vertexList);
              adjVertexList.add(adjVertexName);
              trip = computeAdjTrip(adjVertexName, v2, stopCount + 1, tripCountList, adjVertexList, trip, st, scm);
            }
          }
        }
        
        break;
      }
    }
    
    return trip;
  }
  
  // Computes shortest route using DFS algorithm
  public int computeShortestRoute(String v1, String v2) {
    int result = 0;
    
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        Set<Integer> routes = new TreeSet<>();
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          int adjVertexWeight = adjList.get(k).getWeight();
          
          if (adjVertexName.equalsIgnoreCase(v2))
            routes.add(adjVertexWeight);
          else {
            Set<String> vertexSet = new HashSet<>();
            Set<Integer> weightSet = new HashSet<>();
            vertexSet.add(adjVertexName);
            weightSet.add(adjVertexWeight);
            routes = computeAdjShortestRoute(adjVertexName, v2, vertexSet, weightSet, routes);
          }
        }
        
        for (Integer r: routes) {
          result = r;
          break;
        }
        
        break;
      }
    }
    
    return result;
  }
  
  // Computes adjacent shortest routes using DFS algorithm
  private Set<Integer> computeAdjShortestRoute(String v1, String v2, Set<String> vertexSet, Set<Integer> weightSet, Set<Integer> routes) {
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          int adjVertexWeight = adjList.get(k).getWeight();
          
          if (adjVertexName.equalsIgnoreCase(v2)) {
            int totalWeight = 0;
            
            for (int weight: weightSet)
              totalWeight += weight;
            
            totalWeight += adjVertexWeight;
            routes.add(totalWeight);
          } else {
            if (!vertexSet.contains(adjVertexName)) {
              Set<String> adjVertexSet = new HashSet<>(vertexSet);
              Set<Integer> adjWeightSet = new HashSet<>(weightSet);
              adjVertexSet.add(adjVertexName);
              adjWeightSet.add(adjVertexWeight);
              routes = computeAdjShortestRoute(adjVertexName, v2, adjVertexSet, adjWeightSet, routes);
            }
          }
        }
        
        break;
      }
    }
    
    return routes;
  }
  
  // Computes different route using DFS algorithm
  public String computeDifferentRoute(String v1, String v2, int d, String dcm) {
    String trip = "";
    List<Integer> tripCountList = Arrays.asList(0);
    
    if (d > 0) {
      for (Vertex v : vArr) {
        if (v.getName().equalsIgnoreCase(v1)) {
          List<Vertex> adjList = v.getAdjList();
          int adjListSize = adjList.size();
          
          for (int k = 0; k < adjListSize; k++) {
            String adjVertexName = adjList.get(k).getName();
            int adjVertexWeight = adjList.get(k).getWeight();
            
            if (adjVertexName.equalsIgnoreCase(v2)) {
              if (dcm.equalsIgnoreCase("l") && adjVertexWeight < d || dcm.equalsIgnoreCase("e") && adjVertexWeight == d) {
              	tripCountList.set(0, tripCountList.get(0) + 1);
              	String childTrip = v.getName() + adjVertexName;
                
                if (trip.isEmpty())
                  trip += childTrip;
                else
                  trip += ", " + childTrip;
                
                if (adjVertexWeight < d) {
                  List<Integer> weightList = new ArrayList<>();
                  List<String> vertexList = new ArrayList<>();
                  weightList.add(adjVertexWeight);
                  vertexList.add(v.getName());
                  vertexList.add(adjVertexName);
                  trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, weightList, vertexList, trip, d, dcm);
                }
              } else if (dcm.equalsIgnoreCase("e") && adjVertexWeight < d) {
                List<Integer> weightList = new ArrayList<>();
                List<String> vertexList = new ArrayList<>();
                weightList.add(adjVertexWeight);
                vertexList.add(v.getName());
                vertexList.add(adjVertexName);
                trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, weightList, vertexList, trip, d, dcm);
              }
            } else {
              if (adjVertexWeight < d) {
                List<Integer> weightList = new ArrayList<>();
                List<String> vertexList = new ArrayList<>();
                weightList.add(adjVertexWeight);
                vertexList.add(v.getName());
                vertexList.add(adjVertexName);
                trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, weightList, vertexList, trip, d, dcm);
              }
            }
          }
          
          break;
        }
      }
    }
    
    if (tripCountList.get(0) > 0)
    	trip = tripCountList.get(0) + ": " + trip;
    else
    	trip = "0: No route";
    
    return trip;
  }
  
  // Computes adjacent different routes using DFS algorithm
  private String computeAdjDifferentRoute(String v1, String v2, List<Integer> tripCountList, List<Integer> weightList, List<String> vertexList, String trip, int d, String dcm) {
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          int adjVertexWeight = adjList.get(k).getWeight();
          int totalWeight = 0;
            
          for (int weight: weightList)
            totalWeight += weight;
          
          totalWeight += adjVertexWeight;
          
          if (adjVertexName.equalsIgnoreCase(v2)) {
            if (dcm.equalsIgnoreCase("l") && totalWeight < d || dcm.equalsIgnoreCase("e") && totalWeight == d) {
            	tripCountList.set(0, tripCountList.get(0) + 1);
            	String listTrip = "";
              
              for (String vertex: vertexList)
                listTrip += vertex;
              
              listTrip += adjVertexName;
              
              if (trip.isEmpty())
                trip += listTrip;
              else
                trip += ", " + listTrip;
              
              if (totalWeight < d) {
                List<Integer> adjWeightList = new ArrayList<>(weightList);
                List<String> adjVertexList = new ArrayList<>(vertexList);
                adjWeightList.add(adjVertexWeight);
                adjVertexList.add(adjVertexName);
                trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, adjWeightList, adjVertexList, trip, d, dcm);
              }
            } else if (dcm.equalsIgnoreCase("e") && totalWeight < d) {
              List<Integer> adjWeightList = new ArrayList<>(weightList);
              List<String> adjVertexList = new ArrayList<>(vertexList);
              adjWeightList.add(adjVertexWeight);
              adjVertexList.add(adjVertexName);
              trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, adjWeightList, adjVertexList, trip, d, dcm);
            }
          } else {
            if (totalWeight < d) {
              List<Integer> adjWeightList = new ArrayList<>(weightList);
              List<String> adjVertexList = new ArrayList<>(vertexList);
              adjWeightList.add(adjVertexWeight);
              adjVertexList.add(adjVertexName);
              trip = computeAdjDifferentRoute(adjVertexName, v2, tripCountList, adjWeightList, adjVertexList, trip, d, dcm);
            }
          }
        }
        
        break;
      }
    }
    
    return trip;
  }
  
}