import java.util.*;
public class MoreOnConditionals{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the time: ");
        int time = scan.nextInt();
        String ans = MyFunc(time);
        System.out.println(ans);
        scan.close();
    }

    public static String MyFunc(int time){
        if (time == 24 || time == 00){
            return "Its Night!";
        }if (time > 4 && time <= 6){
            return "Its Dawn!";
        }else if(time > 6 && time < 11){
            return "Its Morning!";
        }else if(time > 12 && time <= 16){
            return "Its AfterNoon!";
        }else if(time > 17 && time <= 20){
            return "Its Evening";
        }else{
            return "Its Night";
        }
    }

}
