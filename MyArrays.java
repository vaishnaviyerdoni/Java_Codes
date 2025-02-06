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

        for(int i = 0; i < students.size(); i++){
            if (students.get(i) != null){
                students.set(i, students.get(i).toUpperCase());
            }
        }

        Collections.sort(students);
        System.out.println("Sorted: \n");
        students.forEach((String str) -> System.out.println(str));
    }
}
