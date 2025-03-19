import java.util.*;

public class ExampleSet{
    public static void main(String[] args){
        Set<Integer> set_1 = new HashSet<>();
        //Set of odd integers
        set_1.add(1);
        set_1.add(3);
        set_1.add(5);
        set_1.add(7);
        set_1.add(9);

        Set<Integer> set_2 = new HashSet<>();
        //set of even integers
        set_2.add(2);
        set_2.add(4);
        set_2.add(6);
        set_2.add(8);
        set_2.add(10);

        //Union of the two sets
        set_1.addAll(set_2);
        System.out.println("After performing the union of two sets: ");
        set_1.forEach((Integer val) -> System.out.println(val));
        System.out.println();

        Set<Integer> set1 = new HashSet<>();
        //set of random integers
        set1.add(12);
        set1.add(31);
        set1.add(53);
        set1.add(45);

        Set<Integer> set2 = new HashSet<>();
        //set of random integers
        set2.add(31);
        set2.add(53);
        set2.add(90);
        set2.add(98);
        set2.add(12);

        //Intersection of two sets
        set1.retainAll(set2);
        System.out.println("After performing intersection of two sets: ");
        set1.forEach((Integer val) -> System.out.println(val));
        System.out.println();
        
        Set<Integer> set3 = new HashSet<>();
        //set of random integers
        set3.add(12);
        set3.add(31);
        set3.add(53);
        set3.add(45);
        
        Set<Integer> set4 = new HashSet<>();
        //set of random integers
        set4.add(31);
        set4.add(53);
        set4.add(90);
        set4.add(98);
        set4.add(12);

        //Difference of two sets
        set3.removeAll(set4);
        System.out.println("After performing Difference on two sets: ");
        set3.forEach((Integer val) -> System.out.println(val));

    }
}