import java.util.Scanner;

public  class factorial_num {

    public static void main(String[] args) {
        Scanner scan  = new Scanner(System.in);

        System.out.print("Enter the Number: ");
        int num = scan.nextInt(); 

        long fact = factorial(num);

        System.out.println("The factorial of number is: " + fact);

        scan.close();
    }

    public static long factorial(int no){
        if (no == 1 || no == 0){
            return 1;
        }
        else{
            return no * factorial(no - 1);
        }
    }
    
}