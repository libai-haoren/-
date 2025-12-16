package map;

import java.util.HashMap;
import java.util.HashSet;

public class map {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        Integer orDefault = map.getOrDefault(1, 1);
        Integer i = map.get(1);
        System.out.println(i);


    }
}
