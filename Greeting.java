import java.util.*;

public class Greeting{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        System.out.print("Greeting: ");
        String Greeting = scan.nextLine();
        String greet = TitleCase.toTitleCase(Greeting);

        if (greet.equalsIgnoreCase("Hello")){
            System.out.println("$0");
        }

        else{
            String[] str = greet.split(""); 
            if (str[0].equals("H")){
                System.out.println("$20");
            }
            else{
                System.out.println("$100");
            }
        }
        scan.close();

    }
}
