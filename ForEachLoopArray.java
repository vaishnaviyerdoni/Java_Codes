public class ForEachLoopArray {
    public static void main(String[] args){
        int[][] nums = {{1,2,3,4},{5,6,7,8}};
        System.out.println("The numbers in code as follows: ");
        for(int[] row : nums){
            for(int i : row){
                System.out.println(i);
            }
        }
    }
    
}
