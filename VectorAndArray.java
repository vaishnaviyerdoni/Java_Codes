import java.util.*;

class VectorAndArray{
    public static void main(String a[]){
        int arr[] = new int[] {1,2,3,4,5};
        Vector<Integer> v = new Vector<>();

        v.add(1);
        v.add(2);
        v.add(3);

        System.out.println(arr[0]);
        System.out.println(v.elementAt(2));


    }
}