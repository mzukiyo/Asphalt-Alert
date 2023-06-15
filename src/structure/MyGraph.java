package structure;

import java.util.Collection;
import java.util.List;

public class MyGraph<T extends Comparable<T>> extends Graph<T>
{
    public MyGraph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges)
    {   
        super(vertices, edges);
    }

    public void addVert(Vertex<T> vertice)
    {
        getVertices().add(vertice);
    }

    public void addEdge(Vertex<T> from, Vertex<T> to, int weight)
    {
        Edge<T> e = new Edge<>(weight, from, to);
        from.addEdge(e);

        Edge<T> reciprocal = new Edge<T>(e.getCost(), to, from);
        to.addEdge(reciprocal);
        getEdges().add(reciprocal);
    }
    
    public void checkE()
    {
        for(Edge<T> e : getEdges())
            System.out.println(e);
    }

    public void checkV()
    {
        for(Vertex<T> e : getVertices())
            System.out.println(e);
    }

    // remove vertex to graph
    public void removeVert(Vertex<T> vertice)
    {
        getVertices().remove(vertice);

        List<Edge<T>> edgesToRemove = vertice.getEdges();
        
        for(Edge<T> e : edgesToRemove)
        {
            getEdges().remove(e);
        }

        for(Edge<T> e : edgesToRemove)
        {
            Vertex<T> v = e.getToVertex();
            Edge<T> edgeToKill = v.getEdge(vertice);
            v.getEdges().remove(edgeToKill);

            getEdges().remove(edgeToKill);
        }
    }

    // remove edge to graph
    public void removeEdge(Vertex<T> from, Vertex<T> to)
    {
        Edge<T> ed1 = from.getEdge(to);
        Edge<T> ed2 = to.getEdge(from);

        from.getEdges().remove(ed1);
        to.getEdges().remove(ed2);
        
        getEdges().remove(ed1);
        getEdges().remove(ed2);
    }
}
