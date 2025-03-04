import java.util.*;

public class Calculator{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        while(true){
            try{
                System.out.println("Enter the Operation: ");
                String exp = scan.nextLine();
                String[] exps = exp.split(" ");
                if(exps.length != 3){
                    throw new NumberFormatException();
                }
                double x = Integer.parseInt(exps[0]);
                double y = Integer.parseInt(exps[2]);

                if (exps[1].equals("+")){
                    double ans = (x + y);
                    System.out.println(ans);
                    break;
                }
                else if(exps[1].equals("-")){
                    double ans = (x - y);
                    System.out.println(ans);
                    break;
                }
                else if (exps[1].equals("/")){
                    if (y == 0){
                        throw new ArithmeticException();
                    }
                    double ans = (x / y);
                    System.out.printf("%.2f%n", ans);
                    break;
                }
                else if (exps[1].equals("*")){
                    double ans = (x * y);
                    System.out.printf("%.2f%n",ans);
                    break;
                }
                else{
                    throw new NumberFormatException();
                }
            }
            catch(NumberFormatException e){
                System.out.println("Enter the expression again!");
            }
            catch(ArithmeticException e){
                System.out.println("Zero cannot be Denominator");
            }
            catch(Exception e){
                System.out.println("Unexpected Error!");
            }
        }
        scan.close();
    }
}