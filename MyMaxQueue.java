import java.util.*;
public class MyMaxQueue{
    public static void main(String[] args){

        PriorityQueue<String> myQueue = new PriorityQueue<>();
        myQueue.add("Hyacinth");
        myQueue.add("Sunflower");
        myQueue.add("Lotus");
        myQueue.add("Mogra");

        myQueue.forEach((String val) ->System.out.println(val));
        
        while(!myQueue.isEmpty()){
            String v = myQueue.poll();
            System.out.println("Top Value is removed: " + v);
        }

    }
}