import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Util {
  
  public static int computeDistance(Graph g, List<String> vertexList) {
    int distance = 0;
    int trvSize = vertexList.size() - 1;
    
    for (int i = 0; i < trvSize; i++) {
      if (!vertexList.get(i).equalsIgnoreCase(vertexList.get(i + 1))) {
        Vertex[] vArr = g.getVertexArr();
        
        for (Vertex v : vArr) {
          if (v.getName().equalsIgnoreCase(vertexList.get(i))) {
            List<Vertex> adjList = v.getAdjList();
            int adjListSize = adjList.size();
            int k;
            
            for (k = 0; k < adjListSize; k++) {
              if (adjList.get(k).getName().equalsIgnoreCase(vertexList.get(i + 1))) {
                distance += adjList.get(k).getWeight();
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
    
    return distance;
  }
  
  public static int computeTrip(Graph g, String v1, String v2, int st, String scm) {
    int tripNumber = 0;
    
    if (st > 0) {
      Vertex[] vArr = g.getVertexArr();
      
      for (Vertex v : vArr) {
        if (v.getName().equalsIgnoreCase(v1)) {
          List<Vertex> adjList = v.getAdjList();
          int adjListSize = adjList.size();
          
          for (int k = 0; k < adjListSize; k++) {
            String adjVertexName = adjList.get(k).getName();
            
            if (adjVertexName.equalsIgnoreCase(v2)) {
              if (scm.equalsIgnoreCase("m") || st == 1) {
                tripNumber++;
                
                if (scm.equalsIgnoreCase("m") && st > 2)
                	tripNumber = computeAdjTrip(g, adjVertexName, v2, 1, tripNumber, st, scm);
              } else if (st > 2)
              	tripNumber = computeAdjTrip(g, adjVertexName, v2, 1, tripNumber, st, scm);
            } else {
              if (st > 1)
              	tripNumber = computeAdjTrip(g, adjVertexName, v2, 1, tripNumber, st, scm);
            }
          }
          break;
        }
      }
    }
    
    return tripNumber;
  }
  
  private static int computeAdjTrip(Graph g, String v1, String v2, int stopNumber, int tripNumber, int st, String scm) {
    Vertex[] vArr = g.getVertexArr();
    
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          
          if (adjVertexName.equalsIgnoreCase(v2)) {
            if (scm.equalsIgnoreCase("m") && stopNumber < st || scm.equalsIgnoreCase("e") && stopNumber + 1 == st) {
            	tripNumber++;
              
              if (scm.equalsIgnoreCase("m") && stopNumber + 2 < st)
              	tripNumber = computeAdjTrip(g, adjVertexName, v2, stopNumber + 1, tripNumber, st, scm);
            } else if (scm.equalsIgnoreCase("e") && stopNumber + 2 < st)
            	tripNumber = computeAdjTrip(g, adjVertexName, v2, stopNumber + 1, tripNumber, st, scm);
          } else {
            if (stopNumber + 1 < st)
            	tripNumber = computeAdjTrip(g, adjVertexName, v2, stopNumber + 1, tripNumber, st, scm);
          }
        }
        
        break;
      }
    }
    
    return tripNumber;
  }

  public static int computeShortestRoute(Graph g, String v1, String v2) {
    Vertex[] vArr = g.getVertexArr();
    
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
            List<Integer> weightList = new ArrayList<>();
            vertexSet.add(adjVertexName);
            weightList.add(adjVertexWeight);
            routes = computeAdjShortestRoute(g, adjVertexName, v2, vertexSet, weightList, routes);
          }
        }
        
        for (Integer r: routes) {
          return r;
        }
        
        break;
      }
    }
    
    return 0;
  }
  
  private static Set<Integer> computeAdjShortestRoute(Graph g, String v1, String v2, Set<String> vertexSet, List<Integer> weightList, Set<Integer> routes) {
    Vertex[] vArr = g.getVertexArr();
    
    for (Vertex v : vArr) {
      if (v.getName().equalsIgnoreCase(v1)) {
        List<Vertex> adjList = v.getAdjList();
        int adjListSize = adjList.size();
        
        for (int k = 0; k < adjListSize; k++) {
          String adjVertexName = adjList.get(k).getName();
          int adjVertexWeight = adjList.get(k).getWeight();
          
          if (adjVertexName.equalsIgnoreCase(v2)) {
            int totalWeight = 0;
            
            for (int weight: weightList)
              totalWeight += weight;
            
            totalWeight += adjVertexWeight;
            routes.add(totalWeight);
          } else {
            if (!vertexSet.contains(adjVertexName)) {
              Set<String> adjVertexSet = new HashSet<>(vertexSet);
              List<Integer> adjWeightList = new ArrayList<>(weightList);
              adjVertexSet.add(adjVertexName);
              adjWeightList.add(adjVertexWeight);
              routes = computeAdjShortestRoute(g, adjVertexName, v2, adjVertexSet, adjWeightList, routes);
            }
          }
        }
        
        break;
      }
    }
    
    return routes;
  }
  
  public static int computeDifferentRoute(Graph g, String v1, String v2, int d) {
  	int routeNumber = 0;
  	
    if (d > 0) {
    	Vertex[] vArr = g.getVertexArr();
    	
      for (Vertex v : vArr) {
        if (v.getName().equalsIgnoreCase(v1)) {
          List<Vertex> adjList = v.getAdjList();
          int adjListSize = adjList.size();
          
          for (int k = 0; k < adjListSize; k++) {
            String adjVertexName = adjList.get(k).getName();
            int adjVertexWeight = adjList.get(k).getWeight();
            
            if (adjVertexName.equalsIgnoreCase(v2)) {
              if (adjVertexWeight < d) {
              	routeNumber++;
                
                if (adjVertexWeight + 2 < d) {
                  List<Integer> weightList = new ArrayList<>();
                  weightList.add(adjVertexWeight);
                  routeNumber = computeAdjDifferentRoute(g, adjVertexName, v2, routeNumber, weightList, d);
                }
              }
            } else {
              if (adjVertexWeight + 1 < d) {
                List<Integer> weightList = new ArrayList<>();
                weightList.add(adjVertexWeight);
                routeNumber = computeAdjDifferentRoute(g, adjVertexName, v2, routeNumber, weightList, d);
              }
            }
          }
          
          break;
        }
      }
    }
    
    return routeNumber;
  }
  
  private static int computeAdjDifferentRoute(Graph g, String v1, String v2, int routeNumber, List<Integer> weightList, int d) {
  	Vertex[] vArr = g.getVertexArr();
  	
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
          	if (totalWeight < d) {
          		routeNumber++;
          		
              if (totalWeight + 2 < d) {
                List<Integer> adjWeightList = new ArrayList<>(weightList);
                adjWeightList.add(adjVertexWeight);
                routeNumber = computeAdjDifferentRoute(g, adjVertexName, v2, routeNumber, adjWeightList, d);
              }
            }
          } else {
            if (totalWeight + 1 < d) {
              List<Integer> adjWeightList = new ArrayList<>(weightList);
              adjWeightList.add(adjVertexWeight);
              routeNumber = computeAdjDifferentRoute(g, adjVertexName, v2, routeNumber, adjWeightList, d);
            }
          }
        }
        
        break;
      }
    }
    
    return routeNumber;
  }
  
}
