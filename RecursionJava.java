public class RecursionJava {
    public static void main(String[] args){
        int ans = sumOfNumsInRange(5,10);
        System.out.println(ans);
    }

    public static int sumOfNumsInRange(int start, int end){
        if (end > start){
            return end + sumOfNumsInRange(start, end - 1);
        }else{
            return end;
        }
    }
    
}
