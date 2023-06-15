package helper;

import java.io.*;
import structure.MyGraph;
import structure.Pothole;

public class MyGraphSerializer 
{
    public static void serialize(MyGraph<Pothole> graph, String filename) throws IOException 
    {
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(graph);
        out.close();
        fileOut.close();
    }

    @SuppressWarnings("unchecked")
    public static MyGraph<Pothole> deserialize(String filename) throws IOException, ClassNotFoundException 
    {
        FileInputStream fileIn = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        MyGraph<Pothole> graph = (MyGraph<Pothole>) in.readObject();
        in.close();
        fileIn.close();
        return graph;
    }
}
