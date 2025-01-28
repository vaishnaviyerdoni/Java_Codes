import java.util.*;
    public class MyArray{
        public static void main(String[] args){
    
            List<Integer> myArray = new ArrayList<>();
            myArray.add(0,1);
            myArray.add(1,2);
            myArray.add(2,3);
            myArray.add(3,4);
            myArray.add(4,5);
            
            myArray.sort((Integer v1, Integer v2) -> v2 - v1);
            System.out.println("Printing in Descending Order.");
            myArray.forEach((Integer v) -> System.out.println(v)); 
            
    }
}