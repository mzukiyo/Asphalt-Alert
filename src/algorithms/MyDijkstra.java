package algorithms;

import java.util.*;
import structure.MyGraph;
import structure.Pothole;

public class MyDijkstra
{
    public static MyGraph.CostPathPair<Pothole> getShortestPath(MyGraph<Pothole> graph, 
                                                                MyGraph.Vertex<Pothole> start, 
                                                                MyGraph.Vertex<Pothole> end) 
    {
        // Initialize data structures
        Map<MyGraph.Vertex<Pothole>, List<MyGraph.Edge<Pothole>>> paths = new HashMap<>();
        Map<MyGraph.Vertex<Pothole>, Integer> costs = new HashMap<>();
        PriorityQueue<MyGraph.CostVertexPair<Pothole>> unvisited = new PriorityQueue<>();
    
        for (MyGraph.Vertex<Pothole> v : graph.getVertices()) {
            paths.put(v, new ArrayList<>());
            costs.put(v, Integer.MAX_VALUE);
        }
        costs.put(start, 0);
        unvisited.add(new MyGraph.CostVertexPair<>(0, start));
    
        // Main loop
        while (!unvisited.isEmpty()) {
            MyGraph.CostVertexPair<Pothole> pair = unvisited.remove();
            MyGraph.Vertex<Pothole> vertex = pair.getVertex();
            int cost = pair.getCost();
    
            if (vertex.equals(end)) {
                // We have found the shortest path to the end vertex
                return new MyGraph.CostPathPair<>(cost, paths.get(end));
            }
    
            if (cost > costs.get(vertex)) {
                // This pair has already been processed with a lower cost
                continue;
            }
    
            // Compute costs and paths for all reachable vertices
            for (MyGraph.Edge<Pothole> edge : vertex.getEdges()) {
                MyGraph.Vertex<Pothole> toVertex = edge.getToVertex();
                int newCost = cost + edge.getCost();
    
                if (newCost < costs.get(toVertex)) {
                    // Found a shorter path to the reachable vertex
                    List<MyGraph.Edge<Pothole>> newPath = new ArrayList<>(paths.get(vertex));
                    newPath.add(edge);
                    paths.put(toVertex, newPath);
                    costs.put(toVertex, newCost);
                    unvisited.add(new MyGraph.CostVertexPair<>(newCost, toVertex));
                }
            }
        }
        
        // no path was found
        return null;
    }

    // might not use this?
    public static void getShortestDistanceViaMid(MyGraph<Pothole> graph, 
                                                 MyGraph.Vertex<Pothole> start, 
                                                 MyGraph.Vertex<Pothole> end, 
                                                 MyGraph.Vertex<Pothole> mid,
                                                 MyGraph.CostPathPair<Pothole> pathAC, 
                                                 MyGraph.CostPathPair<Pothole> pathCB)
    {
        // Find the shortest path from A to C
        pathAC = getShortestPath(graph, start, mid);
        if (pathAC == null) {
            // No path exists from A to C
            return;
        }
    
        // Find the shortest path from C to B
        pathCB = getShortestPath(graph, mid, end);
        if (pathCB == null) {
            // No path exists from C to B
            return;
        }
    }
        
}
