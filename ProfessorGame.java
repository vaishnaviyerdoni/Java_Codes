import java.util.*;
public class ProfessorGame{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        int level = get_level(scan);
        int grade = 0;
        
        for(int i = 0; i <= 10; i++){
            int m = get_int(level);
            int n = get_int(level);
            int ans = m + n;


            System.out.print(m + " + " + n +  " = " );
            for( int j = 0; j <= 3; j++){
                int Ans = scan.nextInt();
                if (Ans == ans){
                    grade++;
                    break;
                }else{
                    System.out.println("EEE");
                }
            scan.close();
            System.out.println("Final Score: " + grade);
        } 
    }
}

    static int get_level(Scanner scan){
        while (true){
            try{
                System.out.println("Enter the Level for your game: ");
                int level = scan.nextInt();
                if(level < 0){
                    throw new InputMismatchException();
                }else if(level > 3){
                    throw new InputMismatchException();
                }
                return level;
            }catch(InputMismatchException e){
                e.printStackTrace();
                System.out.println("Exception Occured");
            }        
        }
    }

    static int get_int(int level){
        Random rand = new Random();

            if (level == 1){
                return rand.nextInt(10);
            }
            else if(level == 2){
                return rand.nextInt(100);
            }
            else if(level == 3){
                return rand.nextInt(1000);
            }
            else{
                System.out.println("Enter Proper Integers");
                return 0;
            }    
        }
    }

