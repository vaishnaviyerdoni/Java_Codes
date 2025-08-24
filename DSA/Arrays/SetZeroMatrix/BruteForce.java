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

    static ArrayList<ArrayList<Integer>> setZero(ArrayList<ArrayList<Integer>> matrix, int n, int m) {
        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if(matrix.get(i).get(j) == 0) {
                        markRow(matrix, m, i);
                        markColumn(matrix, n, j);
                    }
                }
            }

            //setting -1 to 0
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++) {
                    if (matrix.get(i).get(j) == -1){
                        matrix.get(i).set(j, 0);
                    }
                }
            }

            return matrix;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    static void markRow(ArrayList<ArrayList<Integer>> matrix, int m, int ind) {
        for (int i = 0; i < m; i++) {
            if (matrix.get(ind).get(i) != -1){
                matrix.get(ind).set(i, -1);
            }
        }
    }

    static void markColumn(ArrayList<ArrayList<Integer>> matrix, int n, int ind) {
        for (int i = 0; i < n; i++) {
            if(matrix.get(i).get(ind) != 0) {
                matrix.get(i).set(ind, -1);            
            }
        }
    }
}
