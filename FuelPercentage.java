import java.util.*;

public class FuelPercentage{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        while(true){
            try{
                System.out.print("Enter the Expression: ");
                String expression = scan.nextLine();

                String[] exp = expression.split("/");
                if (exp.length != 2){
                    throw new NumberFormatException();
                }

                int x = Integer.parseInt(exp[0]);
                int y = Integer.parseInt(exp[1]);

                if(y == 0){
                    throw new ArithmeticException();
                }
                if (x > y || y < 0){
                    throw new IllegalArgumentException();
                }

                double c = ((double) x / y) * 100;
                

                if (c >= 95){
                    System.out.println("Fuel tank if FULL!");
                    break;
                }
                else if (c <= 5){
                    System.out.println("Fuel tank is EMPTY!");
                    break;
                }
                else{
                    String fuel = String.format("%.2f", c);
                    System.out.println("Fuel tank is " + fuel + "% filled!");
                    break;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Enter the expression again in the right format!");
            }
            catch(ArithmeticException e){
                System.out.println("Denominator cannot be zero");
            }
            catch(IllegalArgumentException e){
            System.out.println("x should be between zero and y!");
            }
    }
    scan.close();
}
}