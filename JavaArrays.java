public class JavaArrays {
    public static void main(String[]  args){
        String[]arr = {"Volkswagen", "Audi", "BMW", "Mercedes", "Rolls Royce"};
        System.out.println("The first element in the array is "+arr[0]);
        
        arr[0] = "Ford";

        System.out.println("The first element in the array is "+arr[0]);

        int len  = arr.length;
        System.out.println("The length of arr is "+len);
    } 
    
}
