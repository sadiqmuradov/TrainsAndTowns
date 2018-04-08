import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
  
  public static void main(String[] args) {
  	Set<String> vertexSet = new TreeSet<>();
    List<String> vertexList = new ArrayList<>();
    
  	if (args.length == 0) {
      System.out.println("NO INPUT PROVIDED !!!");
      System.out.println("Enter graph routes to construct a graph.");
      System.out.println();
    } else {
    	boolean wrongParam = false;
    	
    	for(int i = 1; i < args.length; i++) {
	      if (args[i].length() > 2) {
	    		String v1 = args[i].substring(0, 1);
		      String v2 = args[i].substring(1, 2);
		      boolean intWrongParam = false;
		      
		      try {
		      	if (i < args.length - 1)
		      		Integer.parseInt(args[i].substring(2, args[i].length() - 1));
		      	else
		      		Integer.parseInt(args[i].substring(2));
          } catch (NumberFormatException nfe) {
            intWrongParam = true;
            wrongParam = true;
            System.out.println("Wrong input argument: " + args[i]);
          }
		      
		      if (!intWrongParam) {
			      vertexSet.add(v1);
			      vertexSet.add(v2);
		      }
	      } else {
	      	wrongParam = true;
	      	System.out.println("Wrong input argument: " + args[i]);
	      }
	    }
    	
    	if (wrongParam) {
    		System.out.println("Wrong argument(s) skipped.");
    		System.out.println();
    	}
    	
	    vertexSet.forEach((v) -> {
	      vertexList.add(v);
	    });
    }
    
    Graph g = new Graph(vertexList);
    
    for(int i = 1; i < args.length; i++) {
      if (args[i].length() > 2) {
    		String v1 = args[i].substring(0, 1);
	      String v2 = args[i].substring(1, 2);
	      int w = 0;
	      boolean intWrongParam = false;
	      
	      try {
	      	if (i < args.length - 1)
	      		w = Integer.parseInt(args[i].substring(2, args[i].length() - 1));
	      	else
	      		w = Integer.parseInt(args[i].substring(2));
        } catch (NumberFormatException nfe) {
          intWrongParam = true;
        }
	      
	      if (!intWrongParam)
	      	g.addEdge(v1, v2, w);
	    }
    }
    
    System.out.println("TESTING");
    System.out.println("------------------------------------------------------------------------------------------------------");
    System.out.println("INPUT COMMANDS:");
    System.out.println("d: distance");
    System.out.println("tn: trip number");
    System.out.println("shrl: shortest route length");
    System.out.println("drn: different route number");
    System.out.println("e, q: exit program");
    System.out.println("------------------------------------------------------------------------------------------------------");
    System.out.println("USAGE:");
    System.out.println("d r => [r: route]");
    System.out.println("d A-B-C => Output: 9");
    System.out.println("tn v1 v2 s scm => [v1: start vertex, v2: end vertex, s: stops, i: stop comparator mark (m: max, e: exact)]");
    System.out.println("tn C C 3 m => Output: C-D-C (2 stops), C-E-B-C (3 stops)");
    System.out.println("shrl v1 v2 => [v1: start vertex, v2: end vertex]");
    System.out.println("shrl A C => Output: 9");
    System.out.println("drn v1 v2 d dcm => [v1: start vertex, v2: end vertex, d: distance, i: distance comparator mark (l: less than, e: equal)]");
    System.out.println("drn C C 30 l => Output: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC");
    System.out.println("e => [exit], q => [quit] (same logic, both can be used.)");
    System.out.println("------------------------------------------------------------------------------------------------------");
    System.out.println("Enter command to test the app ...");
    
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String s;
      
      READ_INPUT: while ((s = in.readLine()) != null && s.trim().length() > 0) {
        String[] cmdArr = s.split(" ");
        List<String> cmdList = new ArrayList<>();
        
        for (String cmd: cmdArr) {
          String cmdTrim = cmd.trim();
          
          if (!cmdTrim.isEmpty())
            cmdList.add(cmdTrim);
        }
        
        String firstCmd = cmdList.get(0);
        
        if (firstCmd.equalsIgnoreCase("d")) {
          if (cmdList.size() != 2) {
            System.out.println("Wrong number of parameters!");
            System.out.println("Enter command again ...");
          } else {
            String[] cmdVertexArr = cmdList.get(1).split("-");
            List<String> cmdVertexList = new ArrayList<>();
            
            for (String cmdVertex: cmdVertexArr) {
              String cmdVertexTrim = cmdVertex.trim();
              
              if (cmdVertexTrim.length() == 1)
                cmdVertexList.add(cmdVertexTrim);
              else if (cmdVertexTrim.length() > 1) {
                System.out.println("Wrong command parameter!");
                System.out.println("Enter command again ...");
                continue READ_INPUT;
              }
            }
            
            if (cmdVertexList.size() > 0) {
              int distance = g.computeDistance(cmdVertexList);
              
              if (distance < 0)
                System.out.println("NO SUCH ROUTE");
              else
                System.out.println(distance);
            } else {
              System.out.println("Wrong command parameter!");
              System.out.println("Enter command again ...");
            }
          }
        } else if (firstCmd.equalsIgnoreCase("tn")) {
          if (cmdList.size() != 5) {
            System.out.println("Wrong number of parameters!");
            System.out.println("Enter command again ...");
          } else {
            boolean wrongParam = false;
            
            if (cmdList.get(1).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(1));
            }
            
            if (cmdList.get(2).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(2));
            }
            
            try {
              Integer.parseInt(cmdList.get(3));
            } catch (NumberFormatException nfe) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(3) + " => Number of stops should be integer.");
            }
            
            if (cmdList.get(4).length() > 1 || !cmdList.get(4).equalsIgnoreCase("m") && !cmdList.get(4).equalsIgnoreCase("e")) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(4) + " => Stop comparator mark should be [m: max, e:exact].");
            }
            
            if (wrongParam)
              System.out.println("Enter command again ...");
            else {
              String v1 = cmdList.get(1);
              String v2 = cmdList.get(2);
              int st = Integer.parseInt(cmdList.get(3));
              String scm = cmdList.get(4);
              String trip = g.computeTrip(v1, v2, st, scm);
              System.out.println(trip);
              
              if (st <= 0) {
                System.out.println("Invalid command parameter: " + st + " => Number of stops should be greater than 0.");
                System.out.println("Enter command again ...");
              }
            }
          }
        } else if (firstCmd.equalsIgnoreCase("shrl")) {
          if (cmdList.size() != 3) {
            System.out.println("Wrong number of parameters!");
            System.out.println("Enter command again ...");
          } else {
            boolean wrongParam = false;
            
            if (cmdList.get(1).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(1));
            }
                        
            if (cmdList.get(2).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(2));
            }
            
            if (wrongParam)
              System.out.println("Enter command again ...");
            else {
              String v1 = cmdList.get(1);
              String v2 = cmdList.get(2);
              int shortestRoute = g.computeShortestRoute(v1, v2);
              System.out.println(shortestRoute);
            }
          }
        } else if (firstCmd.equalsIgnoreCase("drn")) {
          if (cmdList.size() != 5) {
            System.out.println("Wrong number of parameters!");
            System.out.println("Enter command again ...");
          } else {
            boolean wrongParam = false;
            
            if (cmdList.get(1).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(1));
            }
            
            if (cmdList.get(2).length() > 1) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(2));
            }
            
            try {
              Integer.parseInt(cmdList.get(3));
            } catch (NumberFormatException nfe) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(3) + " => Distance should be integer.");
            }
            
            if (cmdList.get(4).length() > 1 || !cmdList.get(4).equalsIgnoreCase("l") && !cmdList.get(4).equalsIgnoreCase("e")) {
              wrongParam = true;
              System.out.println("Wrong command parameter: " + cmdList.get(4) + " => Distance comparator mark should be [l: less than, e:equal].");
            }
            
            if (wrongParam)
              System.out.println("Enter command again ...");
            else {
              String v1 = cmdList.get(1);
              String v2 = cmdList.get(2);
              int d = Integer.parseInt(cmdList.get(3));
              String dcm = cmdList.get(4);
              String trip = g.computeDifferentRoute(v1, v2, d, dcm);
              System.out.println(trip);
              
              if (d <= 0) {
                System.out.println("Invalid command parameter: " + d + " => Distance should be greater than 0.");
                System.out.println("Enter command again ...");
              }
            }
          }
        } else if (firstCmd.equalsIgnoreCase("e") || firstCmd.equalsIgnoreCase("q")) {
          return;
        } else {
          System.out.println("Wrong command!");
          System.out.println("Enter command again ...");
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
}