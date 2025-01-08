import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String a[]){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter no: ");
        int no = scan.nextInt();

        System.out.println("Enter j");
        int j = scan.nextInt();

        printMultTable(no, j);
        scan.close();
    }
    

    public static void printMultTable(int no, int j){
        System.out.println();
        for (int i= 1; i <= j; i++){
            System.out.println(no + " x " + i + " = " + (no*i));
        }
    }
}
