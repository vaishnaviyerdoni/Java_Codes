public class RecursionInJava {
    public static void main(String[] args){
        int ans = sumOfnums(10);
        System.out.println(ans);
    }

    public static int sumOfnums(int x){
        if (x > 0){
            return x + sumOfnums(x-1);
        }else{
            return 0;
        }
    }
    
}
