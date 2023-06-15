package structure;

import java.io.Serializable;
import helper.Helper;

public class Pothole implements Comparable<Pothole>, Serializable
{
    private String name;
    private double diameter;
    private double depth;
    private String severity = "0";

    public Pothole(String name, double diameter, double depth, String severity)
    {
        this.name = name;
        this.diameter = diameter;
        this.depth = depth;
        this.severity = severity;
    }

    public Pothole(String name, double diameter, double depth)
    {
        this.name = name;
        this.diameter = diameter;
        this.depth = depth;

        if(depth < 25 )
        {
            this.severity = "1";
        }
        // class 2
        else if(depth >= 25 && depth < 50)
        {
            this.severity = "2";
        }
        // class 3
        else if(depth > 50)
        {
            this.severity = "3";
        }
    }

    // randomised values are used for diameter and depth
    public Pothole(String name)
    {
        this.name = name;

       /* 
        * Potholes can be classified into 3 categories: 
        * Class 1: <25mm depth & <100mm diameter
        * Class 2: 25mm - 50mm depth &  100mm - 300mm diameter
        * Class 3: >50mm depth & >300mm diameter
        * ~Class 4: uncontrolled potholes - (might implement)
        */

        // random values or depth and diameter
        double rDepth = Helper.genRand(10.0, 60.0);
        double rDiameter = 0.0;
        int classLevel = 0;

        // class 1
        if(rDepth < 25 )
        {
            rDiameter = Helper.genRand(0.0, 100.0);
            classLevel = 1;
        }
        // class 2
        else if(rDepth >= 25 && rDepth < 50)
        {
            rDiameter = Helper.genRand(100.0, 300.0);   
            classLevel = 2;
        }
        // class 3
        else if(rDepth > 50)
        {
            rDiameter = Helper.genRand(100.0, 300.0);
            classLevel = 3;
        }

        this.depth = rDepth;
        this.diameter = rDiameter;
        this.severity = String.valueOf(classLevel);
    }

    public String getName() 
    {
        return name;
    }

    public double getDiameter() 
    {
        return diameter;
    }

    public double getDepth() 
    {
        return depth;
    }

    public String getSeverity() 
    {
        return severity;
    }

    public void setDiameter(double diameter) 
    {
        this.diameter = diameter;
    }

    public void setDepth(double depth) 
    {
        this.depth = depth;
    }

    public void setSeverity(String severity) 
    {
        this.severity = severity;
    }

    @Override
    public int compareTo(Pothole o) 
    {
        return severity.compareTo(o.getSeverity());
    }

    @Override
    public String toString() 
    {
        return String.valueOf(name);
    }
}
