import java.util.Random;
import java.util.*;
public class ProfessorGame{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        int level = get_level();
        int grade = 0;
        for(int i = 0; i <= 10; i++){
            int m = get_int(level);
            int n = get_int(level);
            int ans = m + n;

            for( int j = 0; j <= 3; j++){
                int Ans = scan.nextInt();
                if (Ans == ans){
                    grade++;
                    break;
                }else{
                    System.out.println("EEE");
                }
            } 
        }
    }

    static int get_level() throws Exception {
        Scanner scan = new Scanner(System.in);
        while (true){
            try{
                int level = scan.nextInt();
                if(level < 0){
                    throw new Exception("No negative values allowed");
                }else if(level > 3){
                    System.out.println("Level Cannot be greater than 3");
                }
                return level;
            }catch(Exception e){
                System.out.println("Exception Occured");
            }        
        }
    }

    static int get_int(int level) throws Exception{
        Random rand = new Random();
        int no;

            if (level == 1){
                no = rand.nextInt(10);
            }
            else if(level == 2){
                no = rand.nextInt(100);
            }
            else if(level == 3){
                no = rand.nextInt(1000);
            }
            else{
                throw new Exception("Invalid Level, Enter integer between 1 to 3");
            }
            return no;
            
        }
    }

}