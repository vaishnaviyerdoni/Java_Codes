import java.util.*;
public class VanityPlates {
    public static void main(String[] args){
       Scanner scan = new Scanner(System.in);
       System.out.print("Enter the Plate: ");
       String plate = scan.nextLine().trim();
       if (isValid(plate)){
            System.out.println("Valid");
       }
       else{
            System.out.println("Invalid");
       }
       scan.close();
    }

    public static boolean isValid(String p){
        
        if (p.length() < 2 || p.length() > 6){
            return false;
        }

        if (!(p.substring(0,2).matches("[a-zA-Z]+"))){
            return false;
        }

        if (!(p.matches("[a-zA-Z0-9]+"))){
            return false;
        }

        boolean isNum = false;
        for(int i = 0; i < p.length(); i++){
            char ch = p.charAt(i);

            if (Character.isDigit(ch)){
                if ((ch == '0' && !isNum)){
                    return false;
                }
                isNum = true;
            }
            else if (isNum){
                return false;
            }
        }
        return true;
    }
}
