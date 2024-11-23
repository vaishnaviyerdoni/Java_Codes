public class Factorial {
    public static void main(String[] args){
        int num = 4;
        int res = factorial(num);
        System.out.println(res);
    }
    
    public static int factorial(int no){
        if (no == 0 || no == 1){
            return 1;
        }else{
            return no * factorial(no - 1);
        }
    }
}