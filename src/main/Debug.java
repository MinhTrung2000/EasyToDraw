package main;

import java.util.ArrayList;

public class Debug {
    public static ArrayList<Integer> counterContainer = new ArrayList<>();
    
    public static Integer getCounter() {
        counterContainer.add(new Integer(1));
        return counterContainer.get(counterContainer.size() - 1);
    }
}
