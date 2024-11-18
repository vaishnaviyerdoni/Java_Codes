public class OddnEven{
    public static void main(String[] args){
        int no = 5;
        boolean ans = isEvenOrOdd(no);
        if (ans == true){
            System.out.println("no is Even");
        }else{
            System.out.println("no is Odd.");
        }
    }

    public static boolean isEvenOrOdd(int num){
        if (num % 2 == 0){
            return true;
        }else{
            return false;
        }
    }
}