package algorithms;

import java.util.*;
import structure.MyGraph;
import structure.Pothole;

public class MyPrim 
{
    public static MyGraph.CostPathPair<Pothole> getMinimumSpanningTree(MyGraph<Pothole> graph) 
    {
        Objects.requireNonNull(graph, "MyGraph must be non-NULL.");
        if (graph.getType() != MyGraph.TYPE.UNDIRECTED) {
            throw new IllegalArgumentException("Undirected graphs only.");
        }

        int cost = 0;
        MyGraph.Vertex<Pothole> start = graph.getVertices().get(0);
        Set<MyGraph.Vertex<Pothole>> unvisited = new HashSet<>(graph.getVertices());
        unvisited.remove(start);

        List<MyGraph.Edge<Pothole>> path = new ArrayList<>();
        PriorityQueue<MyGraph.Edge<Pothole>> edgesAvailable = new PriorityQueue<>();

        MyGraph.Vertex<Pothole> vertex = start;
        while (!unvisited.isEmpty()) 
        {
            for (MyGraph.Edge<Pothole> e : vertex.getEdges()) 
            {
                if (unvisited.contains(e.getToVertex())) 
                {
                    edgesAvailable.add(e);
                }
            }

            try
            {
                // getting weird errors here...
                MyGraph.Edge<Pothole> e = edgesAvailable.remove();
                cost += e.getCost();
                path.add(e);
    
                vertex = e.getToVertex();
                unvisited.remove(vertex);
            }
            catch(NoSuchElementException ex)
            {
                // do nothing...
            }
        }

        return new MyGraph.CostPathPair<>(cost, path);
    }
}

