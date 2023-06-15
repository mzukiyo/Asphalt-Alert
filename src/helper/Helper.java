package helper;

import java.util.Random;

public class Helper 
{
    /* Generates random numbers between the upper & lower bounds + example of function overloading :) */
    
    public static int genRand(int lowerBound, int upperBound) 
    {
        Random random = new Random();
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound; 
    }

    public static double genRand(double lowerBound, double upperBound) 
    {
        Random random = new Random();
        double randomDouble = random.nextDouble() * (upperBound - lowerBound) + lowerBound;
        return Math.round(randomDouble * 100.0) / 100.0;
    }
}
