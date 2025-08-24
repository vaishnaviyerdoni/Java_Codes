package DSA.Arrays.SetZeroMatrix;

import java.util.*;

public class BruteForce {
    public static void main(String [] args){
        
        ArrayList<ArrayList<Integer>> setZeroMatrix = new ArrayList<>(); 

        setZeroMatrix.add(new ArrayList<>(Arrays.asList(1,1,1)));
        setZeroMatrix.add(new ArrayList<>(Arrays.asList(1,0,1)));
        setZeroMatrix.add(new ArrayList<>(Arrays.asList(1,1,1)));

        int n = setZeroMatrix.size();
        int m = setZeroMatrix.get(0).size();

        ArrayList<ArrayList<Integer>> modifiedMatrix = setZero(setZeroMatrix, n, m);

        for(ArrayList<Integer> row : modifiedMatrix) {
            for(Integer element : row){
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<ArrayList<Integer>> setZero(ArrayList<ArrayList<Integer>> matrix, int n, int m) {

    }
}
