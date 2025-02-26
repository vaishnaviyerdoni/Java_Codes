import java.util.*;
public class ProfessorGame{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int level = get_level(scan);
        int grade = 0;

        for(int i = 0; i < 10; i++){
            int m = get_int(level);
            int n = get_int(level);
            int ans = m + n;
            
            try{
                System.out.print(m +" + "+ n +" = ");
                boolean isCorrect = false;
                
                for(int j = 0; j < 3; j++){
                    int userAns = scan.nextInt();
                    if(userAns == ans){
                        grade++;
                        isCorrect = true;
                        break;
                    }else{
                        System.out.println("EEE");
                    }
                }
                if (!isCorrect){
                    System.out.println("Correct Ans: " + ans);
                }
            }catch(InputMismatchException e){
                System.out.println("Incorrect!");
                scan.next();
            }
        }
        scan.close();
        System.out.println("Final Grade: "+ grade);
    }
    static int get_level(Scanner scan){
        while (true){
            try{
                System.out.println("Enter the Level for your game: ");
                int level = scan.nextInt();
                if (level >= 1 && level <= 3){
                    return level;
                }else{
                    throw new InputMismatchException();
                }
            }catch(InputMismatchException e){
                System.out.println("Invalid Input");
                scan.next();
            }        
        }
    }

    static int get_int(int level){
        Random rand = new Random();

            if (level == 1){
                return rand.nextInt(10);
            }
            else if(level == 2){
                return rand.nextInt(90)+10;
            }
            else if(level == 3){
                return rand.nextInt(900)+100;
            }
            else{
                System.out.println("Enter Proper Integers");
                return 0;
            }    
        }
    }

