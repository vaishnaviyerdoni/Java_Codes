import java.util.*;
public class MyLinkedHashMap_AccessOrder {
    public static void main(String[] a){
        Map<Integer, String> MyMap = new LinkedHashMap<>(16, 0.75F, true);
        MyMap.put(1, "Ginny");
        MyMap.put(2, "Neville");
        MyMap.put(3, "Luna");
        
        MyMap.get(2);
        MyMap.forEach((Integer key, String val) -> System.out.println(key + " : " + val));
        // Printing in Access order
    }
    
}

