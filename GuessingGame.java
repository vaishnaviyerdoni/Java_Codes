import java.util.*;

public class GuessingGame {
    public static void main(String[] args){
        Random  rand = new Random();
        Scanner scan = new Scanner(System.in);

        int level = get_Value(scan);
        int x = rand.nextInt(level);
        int guess = 0;

        while (guess != x) {
            guess = get_Value(scan);
            
            if (guess < x){
                System.out.println("Too Small!");
            }
            else if(guess > x){
                System.out.println("Too Large!");
            }
            else{
                System.out.println("Just Right!");
            }
        }

        scan.close();
    }    

    static int get_Value(Scanner scan){
        while(true){
            try{
                System.out.print("Enter: ");
                int val = scan.nextInt();
                if (val <= 0){
                    throw new IllegalArgumentException();
                }

                return val;
            }
            catch(IllegalArgumentException e){
                System.out.println("Improper Input");
            }
            catch(InputMismatchException e){
                System.out.println("Value should be Integer!");
                scan.next();
            
            }
            
        }
    }
}
