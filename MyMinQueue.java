import java.util.*;
public class MyMinQueue{
    public static void main(String[] args){
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();

        minPQ.add(50);
        minPQ.add(20);
        minPQ.add(80);
        minPQ.add(10);

        // This line prints elements from the PQ 
        minPQ.forEach((Integer val) -> System.out.println(val));

        // Removes the top element from the priority queue

        while(!minPQ.isEmpty()){
            int val = minPQ.poll();
            System.out.println("Top Value Removed: " + val);
        }
    }
}