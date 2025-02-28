import java.util.*;

public class GuessingGame {
    public static void main(String[] args){
        Random  rand = new Random();
        Scanner scan = new Scanner(System.in);

        int level = get_Level(scan);
        int x = rand.nextInt(level);
        int guess = 0;

        while (guess != x) {
            guess = get_Guess(scan);
            
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

    static int get_Level(Scanner scan){
        while(true){
            try{
                System.out.print("Enter Level: ");
                int level = scan.nextInt();
                if (level <= 0){
                    throw new IllegalArgumentException();
                }

                return level;
            }
            catch(IllegalArgumentException e){
                System.out.println("Improper Input");
            }
            catch(InputMismatchException e){
                System.out.println("Level should be Integer!");
                scan.next();
            
            }    
        }
    }

    static int get_Guess(Scanner scan){
        while(true){
            try{
            System.out.println("Guess the number: ");
            int guess = scan.nextInt();
            if (guess <= 0){
                throw new IllegalArgumentException();
            }
            return guess;
            }
            catch(IllegalArgumentException e){
            System.out.println("Enter the proper guess!");
            }
            catch(InputMismatchException e){
                System.out.println("Guess should be an integer!");
                scan.next();
            }
        }
    }    
}
