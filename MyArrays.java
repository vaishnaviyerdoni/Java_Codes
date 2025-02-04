import java.util.*;
public class MyArrays {
    public static void main(String[] args){

        List<String> students = new ArrayList<>();
        students.add("harry");
        students.add("ron");
        students.add("hermione");
        students.add("ginny");
        students.add("neville");
        students.add("luna");

        Collections.sort(students);
        System.out.println("Sorted: \n");
        students.forEach((String str) -> System.out.println(str));
    }
}
