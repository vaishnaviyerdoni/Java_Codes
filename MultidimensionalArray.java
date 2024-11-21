public class MultidimensionalArray {
    public static void main(String[] args){
        int [][] nums = {{1,2,3,4},{5,6,7,8}};
        System.out.println("The number at position nums[0][3] is "+nums[0][3]);
        nums[1][2] = 9;
        System.out.println("The number at the position nums[1][2] is "+nums[1][2]);
        System.out.println();
        System.out.println("All the numbers in array are as follows:");

        for (int i = 0; i<nums.length; ++i){
            for (int j = 0; j < nums[i].length; ++j){
                System.out.println(nums[i][j]);
            }
        }
    }    
    
}
