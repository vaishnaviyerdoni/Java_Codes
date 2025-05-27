import java.util.*;

public class Dummy{
    public static void main(String[] args){
        Scanner scan = new Scanner (System.in);
        String myDate = scan.nextLine().trim();
        if (myDate.contains(",")){
            myDate = myDate.replace(",", "");
            System.out.println(myDate);
        }
        
        scan.close();
    }
}