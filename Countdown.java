public class Countdown{
    public static void main(String[] args){
        int no = 3;
        countdownthenNums(no);

    }
    public static void countdownthenNums(int num){
        while(num > 0){
            System.out.println(num);
            num--;
        }
        System.out.println("Happy New Year! :)");
    }
}
