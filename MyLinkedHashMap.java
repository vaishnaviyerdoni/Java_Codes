import java.util.*;
public class MyLinkedHashMap {
    public static void main(String[] a){
        Map<Integer, String> MyMap = new LinkedHashMap<>();
        MyMap.put(1, "Harry");
        MyMap.put(2, "Ron");
        MyMap.put(3, "Hermione");
        
        MyMap.get(2);
        MyMap.forEach((Integer key, String val) -> System.out.println(key + " : " + val));
        // Printing in insertion order
    }
    
}
