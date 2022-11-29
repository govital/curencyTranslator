package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class ReadCSV
{
    public List<String[]> read() throws Exception
    {
//parsing a CSV file into Scanner class constructor
        List<String[]> ret = new ArrayList<>();
        Scanner sc = new Scanner(new File("currency-conversion.csv"));
        sc.useDelimiter("(\\n)");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
//            System.out.print(sc.next());
            String[] words = sc.next().split(",");
//            System.out.print(Arrays.toString(words));
            ret.add(words);
            String newLine = System.getProperty("line.separator");
//            System.out.print(newLine);//find and returns the next complete token from this scanner
        }
        sc.close();//closes the scanner
        return ret;
    }
}
