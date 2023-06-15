package ui.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.ViewerPipe;
import algorithms.MyDijkstra;
import algorithms.MyPrim;
import helper.MyAlert;
import helper.MyGraphSerializer;
import helper.Helper;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import structure.MyGraph;
import structure.Pothole;
import ui.graph.controls.MyToggleButton;
import ui.graph.controls.PotholeDetails;
import ui.graph.controls.IconButton;

public class GraphCanvas extends StackPane
{
    public org.graphstream.graph.Graph graphViz; // graph for visualisation
    public MyGraph<Pothole> graph; // graph for algorithms and primary functionality
    private FxViewer viewer;
    private ViewerPipe viewerPipe;
    private FxDefaultView view;
    private GraphRenderer renderer;
    private static int nodeCount = 0;
    private String city;

    public GraphCanvas(String city)
    {
        this.city = city;

        // Create a GraphStream graph
        graphViz = new SingleGraph("MyRandomGraph");
        // Create a MyGraph
        ArrayList<MyGraph.Vertex<Pothole>> aryVertices = new ArrayList<>();
        ArrayList<MyGraph.Edge<Pothole>> aryEdges = new ArrayList<>();
        graph = new MyGraph<>(aryVertices, aryEdges);

        // Set the label attribute foreach vertex
        for(nodeCount = 0; nodeCount < 10; ++nodeCount) 
        {
            // graphstream
            org.graphstream.graph.Node node = graphViz.addNode(String.valueOf(nodeCount));
            node.setAttribute("label", "P-" + city + String.valueOf(nodeCount));

            // *** my-graph ***
            MyGraph.Vertex<Pothole> vertex = new MyGraph.Vertex<>(new Pothole(String.valueOf(nodeCount)));
            graph.addVert(vertex);
        }

        // Generate random edges
        for(org.graphstream.graph.Node node : graphViz) 
        {
            int randomNeighborCount = (int) (Math.random() * 3) + 1; // Random number of neighbors (1 to 4)
            for(int i = 0; i < randomNeighborCount; i++) 
            {
                int randomNeighbor = (int) (Math.random() * 9); // Random neighbor index (0 to 9)
                if(randomNeighbor != node.getIndex() && !node.hasEdgeBetween(String.valueOf(randomNeighbor))) 
                {
                    int randomNum = Helper.genRand(5, 20);

                    // graphstream
                    String edgeId = node.getId() + "_" + randomNeighbor;
                    Node otherNode = graphViz.getNode(String.valueOf(randomNeighbor));
                    org.graphstream.graph.Edge edge = graphViz.addEdge(edgeId, node, otherNode);
                    edge.setAttribute("weight", randomNum);
                    edge.setAttribute("label", randomNum);

                    // my-graph
                    MyGraph.Vertex<Pothole> from = null;
                    MyGraph.Vertex<Pothole> to = null;

                    for(MyGraph.Vertex<Pothole> v : graph.getVertices())
                    {
                        if(v.getValue().getName().equals(node.getId()))
                            from = v;
                        if(v.getValue().getName().equals(graphViz.getNode(String.valueOf(randomNeighbor)).getId()))
                            to = v;
                    }

                    // addes edges between specified nodes
                    if(from != null && to != null)
                    {
                        graph.addEdge(from, to, randomNum);
                    }
                }
            }
        }
        
        resetGraph();
    
        // Create a GraphStream viewer
        viewer = new FxViewer(graphViz, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();

        viewerPipe = viewer.newViewerPipe();
        viewerPipe.addAttributeSink(graphViz);

        view = (FxDefaultView) viewer.addDefaultView(true);
        renderer = new GraphRenderer(view);

        // -- ui controls --

        // refresh button
        Button btnReset = new IconButton("data/icons/refresh.png");
        // class details
        PotholeDetails vbDetails = new PotholeDetails();
        // toggle button - graph details
        MyToggleButton tbGraph = new MyToggleButton("Graph");
        // toggle button - graph details
        MyToggleButton tbDetails = new MyToggleButton("Details");
        // save button
        Button btnSave = new IconButton("data/icons/save.png");
        // save button
        Button btnImport = new IconButton("data/icons/import.png");

        tbGraph.setOnAction(e -> {
            if(tbGraph.isSelected())
            {
                tbGraph.setPicture("data/icons/open-eye.png");
                tbGraph.setStyle("-fx-background-color: rgba(57, 182, 244, 0.42);");
                toggleOnClasses();
            }
            if(!tbGraph.isSelected())
            {
                tbGraph.setPicture("data/icons/close-eye.png");
                tbGraph.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
                resetGraph();
            }
        });

        tbDetails.setOnAction(e -> {
            if(tbDetails.isSelected())
            {
                tbDetails.setPicture("data/icons/open-eye.png");
                tbDetails.setStyle("-fx-background-color: rgba(57, 182, 244, 0.42);");
                vbDetails.toFront();
                vbDetails.setVisible(true);
            }
            if(!tbDetails.isSelected())
            {
                tbDetails.setPicture("data/icons/close-eye.png");
                tbDetails.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
                vbDetails.setVisible(false);
            }
        });

        // controls for graph operations
        HBox hbControls = new HBox();
        hbControls.getChildren().addAll(btnReset, btnSave, btnImport, tbGraph, tbDetails);
        hbControls.setSpacing(30);

        StackPane.setMargin(renderer, new Insets(60, 30, 0, 0));
        StackPane.setMargin(hbControls, new Insets(10, 0, 0, 0));
        StackPane.setMargin(vbDetails, new Insets(0, 0, 450, 390));

        btnReset.setOnAction(e -> {
            resetGraph();
            tbGraph.setPicture("data/icons/close-eye.png");
            tbGraph.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
        });

        btnSave.setOnAction(e -> {
            try
            {
                final FileChooser fc = new FileChooser();
                fc.setTitle("Save File");
                fc.setInitialDirectory(new File("data/saves/"));
                File file = fc.showSaveDialog(null);

                if(file != null)
                {
                    MyGraphSerializer.serialize(graph, "data/saves/" + file.getName());
                    MyAlert.savedSuccessfully();
                }
            }
            catch(IOException ex)
            {
                
            }
        });

        btnImport.setOnAction(e -> {
            try
            {
                final FileChooser fc = new FileChooser();
                fc.setTitle("Import File");
                fc.setInitialDirectory(new File("data/saves/"));
                File file = fc.showOpenDialog(null);

                if(file != null)
                {
                    MyGraph<Pothole> g = MyGraphSerializer.deserialize("data/saves/" + file.getName());
                    graph = new MyGraph<>(g.getVertices(), g.getEdges());

                    drawGraph(graph);
                    resetGraph();
                    
                    // reset viewing settings
                    viewer = new FxViewer(graphViz, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
                    viewer.enableAutoLayout();

                    viewerPipe = viewer.newViewerPipe();
                    viewerPipe.addAttributeSink(graphViz);

                    view = (FxDefaultView) viewer.addDefaultView(true);
                    GraphRenderer newRenderer = new GraphRenderer(view);

                    this.getChildren().remove(renderer);

                    this.getChildren().addAll(newRenderer);
                    StackPane.setMargin(newRenderer, new Insets(60, 30, 0, 0));
                }
            }
            // do nothing
            catch(IOException ex)
            {
                
            } 
            catch (ClassNotFoundException ex) 
            {
                
            }
            catch(IdAlreadyInUseException ex)
            {
                
            }
        });

        this.getChildren().addAll(hbControls, renderer, vbDetails);
    }

    public String getNodeInfo(String nodeId)
    {
        resetGraph();

        Node node = graphViz.getNode(nodeId);
        if(node == null)
        {
            MyAlert.elementNotFound();
            return "";
        }

        String s = "";
        for(MyGraph.Vertex<Pothole> v : graph.getVertices()) 
        {
            if(v.getValue().getName().equals(node.getId()))
            {
                s = v.getValue().getSeverity();
                break; // the node has been found
            }
        }

        if(s.equals("1")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
        "size: 25px, 25px;" +
        "text-color: black;" +
        "text-style: bold;" +
        "text-size: 14;" +
        "text-alignment: above;");

        if(s.equals("2")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
        "size: 25px, 25px;" +
        "text-color: black;" +
        "text-style: bold;" +
        "text-size: 14;" +
        "text-alignment: above;");

        if(s.equals("3")) node.setAttribute("ui.style", "fill-color: rgb(228, 11, 11);" +
        "size: 35px, 35px;" +
        "text-color: black;" +
        "text-style: bold;" +
        "text-size: 14;" +
        "text-alignment: above;");

        // search pothole info in my-graph
        String info = "";
        for(MyGraph.Vertex<Pothole> v : graph.getVertices())
        {
            if(v.getValue().getName().equals(node.getId()))
            {
                Pothole p = v.getValue();
                info += "Name: " + "P-" + city + p.getName() + "\n";
                info += "Depth: " + p.getDepth() + "mm\n";
                info += "Diameter/Distance Across: " + p.getDiameter() + "mm\n";
                info += "Degradation Level: Class " + p.getSeverity() + "\n";

                return info;
            }
        }

        // only executes if something bad happens 
        return "No such node exists - please refer to the graph!";
    }

    public String setNodeInfo(String nodeId, double diameter, double depth)
    {
        resetGraph();

        Node node = graphViz.getNode(nodeId);
        if(node == null)
        {
            MyAlert.elementNotFound();
            return "";
        }

        // search pothole info in my-graph
        String info = "";
        for(MyGraph.Vertex<Pothole> v : graph.getVertices())
        {
            if(v.getValue().getName().equals(node.getId()))
            {
                Pothole p = v.getValue();

                // updating diameter and depth
                v.getValue().setDepth(depth);
                v.getValue().setDiameter(diameter);

                // change class level if needed - use depth for now
                // class 1
                if(depth < 25)
                {
                    v.getValue().setSeverity("1");
                }
                // class 2
                else if(depth >= 25 && depth < 50)
                {   
                    v.getValue().setSeverity("2");
                }
                // class 3
                else if(depth > 50)
                {
                    v.getValue().setSeverity("3");
                }

                info += "Name: " + "P-" + city + p.getName() + "\n";
                info += "Depth: " + p.getDepth() + "mm\n";
                info += "Diameter/Distance Across: " + p.getDiameter() + "mm\n";
                info += "Degradation Level: Class " + p.getSeverity() + "\n";

                if(p.getSeverity().equals("1")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
                "size: 25px, 25px;" +
                "text-color: black;" +
                "text-style: bold;" +
                "text-size: 14;" +
                "text-alignment: above;");

                if(p.getSeverity().equals("2")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
                "size: 25px, 25px;" +
                "text-color: black;" +
                "text-style: bold;" +
                "text-size: 14;" +
                "text-alignment: above;");

                if(p.getSeverity().equals("3")) node.setAttribute("ui.style", "fill-color: rgb(228, 11, 11);" +
                "size: 35px, 35px;" +
                "text-color: black;" +
                "text-style: bold;" +
                "text-size: 14;" +
                "text-alignment: above;");

                return info;
            }
        }

        // only executes if something bad happens 
        return "No such node exists - please refer to the graph!";
    }

    public void resetGraph()
    {
        graphViz.removeAttribute("ui.stylesheet");
        graphViz.setAttribute("ui.stylesheet", "url('src/ui/style/stylesheet.css')");
        graphViz.setAttribute("ui.quality");
        graphViz.setAttribute("ui.antialias");
    }

    public int getNumNodes()
    {
        return nodeCount;
    }

    public void addNode(double diameter, double depth) 
    {
        // graphstream
        String nodeId = String.valueOf(nodeCount);
        org.graphstream.graph.Node node = graphViz.addNode(nodeId);
        node.setAttribute("ui.style", "fill-color: green;");
        viewerPipe.pump(); // Update the viewer
        node.setAttribute("label", "P-" + city + nodeCount);

        // my-graph - fresh potholes need to get measured and logged
        graph.addVert(new MyGraph.Vertex<Pothole>(new Pothole(String.valueOf(nodeId), diameter, depth)));

        nodeCount++;
    }

    public void removeNode(String nodeId) 
    {
        try
        {
            // *** graphstream ***
            graphViz.removeNode(nodeId);

            // *** my-graph ***
            for(MyGraph.Vertex<Pothole> v : graph.getVertices())
            {
                // find the node to remove
                if(v.getValue().getName().equals(nodeId))
                {
                    // removes the node (and associated edges) and breaks the loop
                    graph.removeVert(v);
                    break;
                }
            }

            nodeCount--;
        }
        catch(ElementNotFoundException ex)
        {
            MyAlert.elementNotFound();
        }
    }

    public void addEdge(String n1, String n2, String weight)
    {
        // *** graphstream ***
        org.graphstream.graph.Node nodeOne = graphViz.getNode(n1);
        org.graphstream.graph.Node nodeTwo = graphViz.getNode(n2);

        if(nodeOne == null || nodeTwo == null)
        {
            MyAlert.elementNotFound();
            return;
        }

        if(Integer.valueOf(weight) > 20)
        {
            MyAlert.biggerThan20();
            return;
        }

        try
        {
            String edgeId = nodeOne.getId() + "_" + nodeTwo.getId();
            org.graphstream.graph.Edge edge = graphViz.addEdge(edgeId, nodeOne, nodeTwo);
            edge.setAttribute("weight", Integer.parseInt(weight));
            edge.setAttribute("label", Integer.parseInt(weight));
            viewerPipe.pump(); // Update the viewer

            // *** my-graph ***
            MyGraph.Vertex<Pothole> from = null;
            MyGraph.Vertex<Pothole> to = null;

            for(MyGraph.Vertex<Pothole> v : graph.getVertices())
            {
                if(v.getValue().getName().equals(nodeOne.getId()))
                    from = v;
                if(v.getValue().getName().equals(nodeTwo.getId()))
                    to = v;
            }

            // adds edge between specified nodes
            if(from != null && to != null)
            {
                graph.addEdge(from, to, Integer.parseInt(weight));
            }
        }
        catch(IdAlreadyInUseException ex)
        {
            MyAlert.alreadyConnected();
        }
        catch(EdgeRejectedException ex)
        {
            MyAlert.alreadyConnected();
        }
    }

    public void removeEdge(String n1, String n2)
    {
        // *** graphstream ***
        org.graphstream.graph.Node nodeOne = graphViz.getNode(n1);
        org.graphstream.graph.Node nodeTwo = graphViz.getNode(n2);
        
        if(nodeOne == null || nodeTwo == null)
        {
            MyAlert.elementNotFound();
            return;
        }

        try
        {
            graphViz.removeEdge(nodeOne, nodeTwo);
    
            // *** my-graph ***
            MyGraph.Vertex<Pothole> from = null;
            MyGraph.Vertex<Pothole> to = null;
    
            for(MyGraph.Vertex<Pothole> v : graph.getVertices())
            {
                if(v.getValue().getName().equals(nodeOne.getId()))
                    from = v;
                if(v.getValue().getName().equals(nodeTwo.getId()))
                    to = v;
            }
    
            // removes edge between specified nodes
            if(from != null && to != null)
            {
                graph.removeEdge(from, to);;
            }
        }
        catch(ElementNotFoundException ex)
        {
            MyAlert.edgeNotFound();
        }   
    }

    public void toggleOnClasses()
    {
        resetGraph();

        for(Node node : graphViz)
        {
            for(MyGraph.Vertex<Pothole> v : graph.getVertices()) 
            {
                if(v.getValue().getName().equals(node.getId()))
                {
                    Pothole p = v.getValue();
                    graphViz.setAttribute("ui.stylesheet", "url('src/ui/style/toggle-stylesheet.css')");

                    if(p.getSeverity().equals("1")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
                    "size: 25px, 25px;" +
                    "text-color: black;" +
                    "text-style: bold;" +
                    "text-size: 14;" +
                    "text-alignment: above;");

                    if(p.getSeverity().equals("2")) node.setAttribute("ui.style", "fill-color: rgb(238, 255, 0);" +
                    "size: 25px, 25px;" +
                    "text-color: black;" +
                    "text-style: bold;" +
                    "text-size: 14;" +
                    "text-alignment: above;");

                    if(p.getSeverity().equals("3")) node.setAttribute("ui.style", "fill-color: rgb(228, 11, 11);" +
                    "size: 35px, 35px;" +
                    "text-color: black;" +
                    "text-style: bold;" +
                    "text-size: 14;" +
                    "text-alignment: above;");
                }
            }
        }
    }

    private void drawGraph(MyGraph<Pothole> myGraph)
    {
        graphViz.clear();
        graphViz = null;
        graphViz = new SingleGraph("MyRandomGraph");

        // draw nodes
        for(MyGraph.Vertex<Pothole> v : myGraph.getVertices())
        {
            Node node = graphViz.addNode(v.getValue().getName());
            node.setAttribute("label", "P-" + city + v.getValue().getName());
        }
        
        // draw edges
        for(MyGraph.Edge<Pothole> e : myGraph.getEdges())
        {
            MyGraph.Vertex<Pothole> from = e.getFromVertex();
            MyGraph.Vertex<Pothole> to = e.getToVertex();

            Node n1 = graphViz.getNode(from.getValue().getName());
            Node n2 = graphViz.getNode(to.getValue().getName());

            try
            {
                String edgeId = n1.getId() + "_" + n2.getId();
                Edge edge = graphViz.addEdge(edgeId, n1, n2);

                edge.setAttribute("weight", e.getCost());
                edge.setAttribute("label", e.getCost());
            }
            catch(EdgeRejectedException ex)
            {
                // deals with duplicates from my-graph
            }
            catch(IdAlreadyInUseException ex)
            {
                // deals with duplicates from my-graph
            }
        }
    }

    public int primAlgo()
    {
        /* using Prims's algorithm to calculate the minimum spanning tree 
         * N.B Prim's algorithm only works with connected graphs
         */
        
        // check if graph is connected first - floating nodes don't contribute to cost
        for(MyGraph.Vertex<Pothole> v : graph.getVertices())
        {
            if(v.getEdges().isEmpty())
            {
                return -1; // graph is disconnected
            }
        }

        MyGraph.CostPathPair<Pothole> minSpanTree = MyPrim.getMinimumSpanningTree(graph);
        
        // convert from graph edges to graphstream edges (for display)
        for(MyGraph.Edge<Pothole> e : minSpanTree.getPath())
        {
            String e1 = e.getFromVertex().getValue().getName() + "_" + e.getToVertex().getValue().getName();
            String e2 = e.getToVertex().getValue().getName() + "_" + e.getFromVertex().getValue().getName();

            Edge edge1 = graphViz.getEdge(e1);
            Edge edge2 = graphViz.getEdge(e2);

            if(edge1 != null)
            {
                edge1.setAttribute("ui.style", "fill-color: green;");
                edge1.setAttribute("ui.style", "size: 5px;");
                edge1.setAttribute("ui.style", "text-color: red;");
                edge1.setAttribute("ui.style", "text-alignment: above;");
            }
            if(edge2 != null) 
            {
                edge2.setAttribute("ui.style", "fill-color: green;");
                edge2.setAttribute("ui.style", "size: 5px;");
                edge2.setAttribute("ui.style", "text-color: red;");
                edge2.setAttribute("ui.style", "text-alignment: above;");
            }
        }
    
        return minSpanTree.getCost();
    }

    public int dijkstraAlgo(String sStart, String sEnd)
    {
        // some checks before starting algorithm
        if (Integer.valueOf(sStart) >= nodeCount || 
            Integer.valueOf(sEnd) >= nodeCount || 
            Integer.valueOf(sStart) < 0 || 
            Integer.valueOf(sEnd) < 0) 
        {
            MyAlert.elementNotFound();
            return -1;
        }

        int start = Integer.parseInt(sStart);
        int end = Integer.parseInt(sEnd);

        MyGraph.Vertex<Pothole> vStart = null;
        MyGraph.Vertex<Pothole> vEnd = null;

        String startNodeID = "";
        String endNodeID = "";

        // getting the start and end nodes from displayed graph
        for(MyGraph.Vertex<Pothole> v : graph.getVertices()) {
            if(v.getValue().getName().equals(graphViz.getNode(start).getId()))
            {
                vStart = v;
                startNodeID = graphViz.getNode(start).getId();
            }
            if(v.getValue().getName().equals(graphViz.getNode(end).getId()))
            {
                vEnd = v;
                endNodeID = graphViz.getNode(end).getId();
            }
        }

        /* using Dijkstra's algorithm to calculate the shortest path between two nodes */
        MyGraph.CostPathPair<Pothole> shortestPath = MyDijkstra.getShortestPath(graph, vStart, vEnd);


        // iftheres no edges connecting the nodes specified
        if(shortestPath == null)
            return 0;

        ArrayList<MyGraph.Vertex<Pothole>> aryVertices = new ArrayList<>();
        for(MyGraph.Edge<Pothole> e : shortestPath.getPath())
            aryVertices.add(e.getFromVertex()); // gets all vertices except the last one
        aryVertices.add(vEnd); // add last one

        // colour in the nodes of the graph
        for(Node node : graphViz) {
            for(MyGraph.Vertex<Pothole> v : aryVertices) {
                if(v.getValue().getName().equals(node.getId()))
                {
                    // set the colour to purple and change the size to 30
                    node.setAttribute("ui.style", "fill-color: purple; size: 30px, 30px;"); 
                }
            }
        }

        // special colors for start and end nodes
        if(!startNodeID.equals(""))
            graphViz.getNode(startNodeID).setAttribute("ui.style", "fill-color: violet; size: 35px, 35px; ");
        if(!endNodeID.equals(""))
            graphViz.getNode(endNodeID).setAttribute("ui.style", "fill-color: violet; size: 35px, 35px;");

        // get the nodes on the path by getting their Ids
        String aryNodes[] = new String[aryVertices.size()];
        for(int i = 0; i < aryVertices.size(); i++)
            aryNodes[i] = aryVertices.get(i).getValue().getName();

        // now colour in the edges of the graph
        for(int i = 0; i < aryVertices.size(); i++) {
            /*  get the current and next nodes and colour the edge in-between */
            Node nodeCurrent = graphViz.getNode(aryNodes[i]);
            Node nodeNext;

            if(i < aryVertices.size() - 1) 
            {
                nodeNext = graphViz.getNode(aryNodes[i + 1]);

                Edge edge1 = graphViz.getEdge(nodeCurrent.getId() + "_" + nodeNext.getId());
                Edge edge2 = graphViz.getEdge(nodeNext.getId() + "_" + nodeCurrent.getId());

                if(edge1 != null)
                {
                    edge1.setAttribute("ui.style", "fill-color: black;");
                    edge1.setAttribute("ui.style", "size: 5px;");
                    edge1.setAttribute("ui.style", "text-color: red;");
                    edge1.setAttribute("ui.style", "text-alignment: above;");
                }
                if(edge2 != null) 
                {
                    edge2.setAttribute("ui.style", "fill-color: black;");
                    edge2.setAttribute("ui.style", "size: 5px;");
                    edge2.setAttribute("ui.style", "text-color: red;");
                    edge2.setAttribute("ui.style", "text-alignment: above;");
                }
            }
        }

        return shortestPath.getCost();
    }

    public int dijkstraAlgoMid(String sStart, String sEnd, String sMid)
    {
        // some checks before starting algorithm
        if (Integer.valueOf(sStart) >= nodeCount || 
            Integer.valueOf(sMid) >= nodeCount || 
            Integer.valueOf(sEnd) >= nodeCount || 
            Integer.valueOf(sStart) < 0 ||
            Integer.valueOf(sMid) < 0 ||
            Integer.valueOf(sEnd) < 0) 
        {
            MyAlert.elementNotFound();
            return -1;
        }
        
        int start = Integer.parseInt(sStart);
        int mid = Integer.parseInt(sMid);
        int end = Integer.parseInt(sEnd);

        MyGraph.Vertex<Pothole> vStart = null;
        MyGraph.Vertex<Pothole> vMid = null;
        MyGraph.Vertex<Pothole> vEnd = null;

        String startNodeID = "";
        String midNodeID = "";
        String endNodeID = "";

        // getting the start and end nodes from displayed graph
        for(MyGraph.Vertex<Pothole> v : graph.getVertices()) {
            if(v.getValue().getName().equals(graphViz.getNode(start).getId()))
            {
                vStart = v;
                startNodeID = graphViz.getNode(start).getId();
            }
            if(v.getValue().getName().equals(graphViz.getNode(mid).getId()))
            {
                vMid = v;
                midNodeID = graphViz.getNode(mid).getId();
            }
            if(v.getValue().getName().equals(graphViz.getNode(end).getId()))
            {
                vEnd = v;
                endNodeID = graphViz.getNode(end).getId();
            }
        }

        /* using Dijkstra's algorithm to calculate the shortest path between three nodes */
        MyGraph.CostPathPair<Pothole> pathAC = MyDijkstra.getShortestPath(graph, vStart, vMid);
        MyGraph.CostPathPair<Pothole> pathCB = MyDijkstra.getShortestPath(graph, vMid, vEnd);


        // iftheres no edges connecting the nodes specified
        if(pathAC == null || pathCB == null)
            return 0;

        ArrayList<MyGraph.Vertex<Pothole>> aryVertices = new ArrayList<>();

        for(MyGraph.Edge<Pothole> e : pathAC.getPath())
            aryVertices.add(e.getFromVertex()); 
        for(MyGraph.Edge<Pothole> e : pathCB.getPath())
            aryVertices.add(e.getFromVertex());
        aryVertices.add(vEnd);


        // colour in the nodes of the graph
        for(Node node : graphViz) {
            for(MyGraph.Vertex<Pothole> v : aryVertices) {
                if(v.getValue().getName().equals(node.getId()))
                {
                    // set the colour to purple and change the size to 30
                    node.setAttribute("ui.style", "fill-color: purple; size: 30px, 30px;"); 
                }
            }
        }

        // special colors for start, middle & end nodes
        if(!startNodeID.equals(""))
            graphViz.getNode(startNodeID).setAttribute("ui.style", "fill-color: violet; size: 35px, 35px; ");
        if(!midNodeID.equals(""))
            graphViz.getNode(midNodeID).setAttribute("ui.style", "fill-color: rgb(74, 32, 239); size: 35px, 35px; ");
        if(!endNodeID.equals(""))
            graphViz.getNode(endNodeID).setAttribute("ui.style", "fill-color: violet; size: 35px, 35px;");

        // get the nodes on the path by getting their Ids
        String aryNodes[] = new String[aryVertices.size()];
        for(int i = 0; i < aryVertices.size(); i++)
            aryNodes[i] = aryVertices.get(i).getValue().getName();

        // now colour in the edges of the graph
        for(int i = 0; i < aryVertices.size(); i++) {
            /*  get the current and next nodes and colour the edge in-between */
            Node nodeCurrent = graphViz.getNode(aryNodes[i]);
            Node nodeNext;

            if(i < aryVertices.size() - 1) 
            {
                nodeNext = graphViz.getNode(aryNodes[i + 1]);

                Edge edge1 = graphViz.getEdge(nodeCurrent.getId() + "_" + nodeNext.getId());
                Edge edge2 = graphViz.getEdge(nodeNext.getId() + "_" + nodeCurrent.getId());

                if(edge1 != null)
                {
                    edge1.setAttribute("ui.style", "fill-color: black;");
                    edge1.setAttribute("ui.style", "size: 5px;");
                    edge1.setAttribute("ui.style", "text-color: red;");
                    edge1.setAttribute("ui.style", "text-alignment: above;");
                }
                if(edge2 != null) 
                {
                    edge2.setAttribute("ui.style", "fill-color: black;");
                    edge2.setAttribute("ui.style", "size: 5px;");
                    edge2.setAttribute("ui.style", "text-color: red;");
                    edge2.setAttribute("ui.style", "text-alignment: above;");
                }
            }
        }

        return pathAC.getCost() + pathCB.getCost();
    }
}