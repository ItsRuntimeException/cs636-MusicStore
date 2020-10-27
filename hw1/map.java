
/*
Name: Run Lin
Homework: 1
Class: CS636
*/
import java.util.*;
public class map {
    public static void main(String[] args)
    {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("x", 6);

        for(Map.Entry<String, Integer> entry: m.entrySet())
        {
            System.out.print("<" + entry.getKey() + ", " + entry.getValue() + ">");
        }
    }
}
